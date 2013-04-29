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
		return ok(index.render("Your new application is ready."));
	}

	public static Result sessions() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Session> ls = new ArrayList<Session>();
		String message = "";
		try {
			ls = pf.getBillBoard();
			message = " Exito";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(billBoard.render(message, ls));
	}
}
