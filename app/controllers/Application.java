package controllers;

import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

import models.*;
import persistence.*;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	private static Session cacheSession = null;
	private static Customer cacheCustomer = null;
	private static Movie cacheMovie = null;
	private static Integer cacheSeat = null;
	private static String message = "";

	public static Result index() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Movie> lm = new ArrayList<Movie>();
		message = "";
		try {
			lm = pf.getMovies();
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(index.render(message, lm));
	}

	public static Result details(Integer id_movie) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Session> ls = new ArrayList<Session>();
		message = "";

		try {
			ls = pf.getSessionsByMovie(id_movie);
			cacheMovie = pf.getMovieById(id_movie);
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(details.render(message, cacheMovie.getSynopsis(), ls));
	}

	public static Result reserve(Integer id_session) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Place> lp = new ArrayList<Place>();
		int[] places = new int[1];
		Session session = null;
		message = "";
		try {
			session = pf.getSessionById(id_session);
			cacheSession = session;
			lp = pf.getPlaceBySession(id_session);
			places = new int[session.getRoom().getSeatingCapacity()];
			for (Place p : lp) {
				places[p.getSeat() - 1] = -1;
			}
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(reserve.render(message, session, places));
	}

	public static Result payReserve(Integer numSeat) {
		cacheSeat = numSeat;
		message = "";
		return ok(payReserve.render(message, cacheSession, cacheSeat,
				cacheCustomer));
	}

	public static Result payment(String creditCard) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		PaymentGateway pg = new PaymentGateway();
		GraphDBHandler gh = GraphDBHandler.getConnection();
		int idCustomer = 0;
		boolean avaliability = true;
		try {

			if (!pg.payment(creditCard))
				return ok(payReserve.render(
						"ERROR: compruebe el numero de la targeta de credito (debe empezar con cc)",
						cacheSession, cacheSeat, cacheCustomer));
			avaliability = pf.getAvaliability(cacheSession.getId(), cacheSeat);
			if (!avaliability)
				return ok(payReserve.render(
						"ERROR: la silla ya a sido reservada", cacheSession,
						cacheSeat, cacheCustomer));

			Date fecha = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

			if (cacheCustomer != null) {
				idCustomer = cacheCustomer.getId();
				gh.registerSale(cacheCustomer, cacheMovie);
			}

			pf.newReservation(new Place(cacheSession.getId(), idCustomer,
					cacheSeat, creditCard, cacheSession.getSessionType()
							.getCost(), fecha));

			return redirect(routes.Application.paymentResult());
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}

		return ok(payReserve.render("ERROR: SQL: " + message, cacheSession,
				cacheSeat, cacheCustomer));
	}

	public static Result paymentResult() {
		message = "";
		int idCustomer = 0;
		Date fecha = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

		if (cacheCustomer != null)
			idCustomer = cacheCustomer.getId();

		return ok(payResult.render(message, cacheSession, cacheSeat,
				idCustomer, fecha));
	}

	public static Result register() {
		return ok(register.render("Your new application is ready."));
	}

}
