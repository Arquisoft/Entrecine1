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
	private static String nombre = "";

	public static Result index() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Movie> lm = new ArrayList<Movie>();
		message = "";
		try {
			lm = pf.getMovies();
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(index.render(message, lm, nombre));
	}

	public static Result login(String name, String password) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		cacheCustomer = null;
		message = "";
		nombre = "";
		try {
			cacheCustomer = pf.getCustomer(name, password);
			if (cacheCustomer != null)
				nombre = cacheCustomer.getSurnames();
			else
				message = "Login/password incorrectos";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return redirect(routes.Application.index());
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
		return ok(details.render(nombre, message, cacheMovie.getSynopsis(), ls));
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
		return ok(reserve.render(nombre, message, session, places));
	}

	public static Result payReserve(Integer numSeat) {
		cacheSeat = numSeat;
		message = "";
		return ok(payReserve.render(nombre, message, cacheSession, cacheSeat,
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
				return ok(payReserve
						.render(nombre,
								"ERROR: compruebe el numero de la targeta de credito (debe empezar con cc)",
								cacheSession, cacheSeat, cacheCustomer));
			avaliability = pf.getAvaliability(cacheSession.getId(), cacheSeat);
			if (!avaliability)
				return ok(payReserve.render(nombre,
						"ERROR: la silla ya ha sido reservada", cacheSession,
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

		return ok(payReserve.render(nombre, "ERROR: SQL: " + message,
				cacheSession, cacheSeat, cacheCustomer));
	}

	public static Result paymentResult() {
		message = "";
		int idCustomer = 0;
		Date fecha = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

		if (cacheCustomer != null)
			idCustomer = cacheCustomer.getId();

		return ok(payResult.render(nombre, message, cacheSession, cacheSeat,
				idCustomer, fecha, cacheSession.getId() + "-" + cacheSeat + "-"
						+ idCustomer));
	}

	public static Result register() {
		return ok(register.render(nombre, "Your new application is ready."));
	}

	public static Result controlPanel() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<SessionType> sessionsTypes = new ArrayList<SessionType>();
		message = "";
		try {
			sessionsTypes = pf.getSessionsTypes();
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(controlPanel.render(nombre, message, sessionsTypes));
	}

	public static Result newMovie(String name, String category,
			String synopsis, String poster) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		try {
			Movie movie = new Movie(name, category, synopsis, "assets/images/"
					+ poster + ".jpg");
			pf.addMovie(movie);
			return redirect(routes.Application.controlPanel());
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		return null;
	}

	public static Result updateSession(Integer idSession, Integer startTime,
			Double amount) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		try {
			SessionType st = null;
			for (int i = 0; i < pf.getSessionsTypes().size() - 1; i++)
				if (pf.getSessionsTypes().get(i).getId() == idSession)
					st = new SessionType(startTime, pf.getSessionsTypes()
							.get(i).getName(), amount);
			pf.changePrice(st);
			pf.changeStartTime(st);
			return redirect(routes.Application.controlPanel());
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		return null;
	}

}
