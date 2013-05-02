package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Session;
import persistence.PersistenceFactory;
import persistence.PersistenceFactoryImpl;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {

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

	public static Result register() {
		return ok(register.render("Your new application is ready."));
	}
	
}
