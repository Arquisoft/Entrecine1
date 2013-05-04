package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.*;
import persistence.PersistenceFactory;
import persistence.PersistenceFactoryImpl;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	private static Session cacheSession;
	private static Customer cacheCustomer;

	public static Result index() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Movie> lm = new ArrayList<Movie>();
		String message = "";
		try {
			lm = pf.getMovies();
			message = "Exito";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(index.render(message, lm));
	}

	public static Result details(Integer id_movie) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Session> ls = new ArrayList<Session>();
		String message = "";
		String synopsis = "";
		try {
			ls = pf.getSessionsByMovie(id_movie);
			synopsis = pf.getSynopsis(id_movie);

			message = " Exito";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(details.render(message, synopsis, ls));
	}

	public static Result reserve(Integer id_session) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Place> lp = new ArrayList<Place>();
		int[] places = new int[1];
		Session session = null;
		String message = "";

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
	
	public static Result payReserve() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		Session session = cacheSession;
		String message = "";

		return ok(payReserve.render(message));
	}

	public static Result register() {
		return ok(register.render("Your new application is ready."));
	}

}
