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
	private static SystemUser cacheSystem = null;
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

	public static Result logOut() {
		cacheCustomer = null;
		nombre = "";
		return redirect(routes.Application.index());
	}

	public static Result login(String name, String password) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		cacheCustomer = null;
		cacheSystem = null;
		message = "";
		nombre = "";
		try {
			if (!name.equals("") && !password.equals("")) {
				cacheCustomer = pf.getCustomer(name, password);
				cacheSystem = pf.getSystemUser(name, password);
				if (cacheCustomer != null)
					nombre = cacheCustomer.getSurnames();
				else if (cacheSystem != null)
					return redirect(routes.Application.controlPanel("-1", "-1",
							"-1"));
				else
					message = "Login/password incorrectos";
			} else
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

	public static Result saveRegister(String name, String surname,
			String email, String creditcard, String login, String password) {

		PersistenceFactory pf = new PersistenceFactoryImpl();
		try {
			Customer c = new Customer(name, surname, email, creditcard, login,
					password);

			pf.newCustomer(c);
			cacheCustomer = c;
			nombre = name;
			return redirect(routes.Application.index());
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		return redirect(routes.Application.index());
	}

	public static Result register() {
		return ok(register.render(nombre, "Your new application is ready."));
	}

	// ---------------------
	private static List<Movie> systemMovies = new ArrayList<Movie>();
	private static List<Session> systemSessions = new ArrayList<Session>();
	private static List<SessionType> sessionsTypes = new ArrayList<SessionType>();
	private static int[] systemPlaces = new int[1];
	private static Integer systemIdMovie = -1;
	private static Integer systemIdSession = -1;
	public static Integer systemPlace = -1;

	// ------------
	public static Result controlPanelPayCredit(String creditCard) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		PaymentGateway pg = new PaymentGateway();

		boolean avaliability = true;
		try {

			if (!pg.payment(creditCard))
				return ok(controlPanel
						.render("ERROR: compruebe el numero de la targeta de credito (debe empezar con cc)",
								sessionsTypes, systemMovies, systemSessions,
								systemPlaces));

			if (!avaliability)
				return ok(controlPanel.render(
						"ERROR: la silla ya ha sido reservada", sessionsTypes,
						systemMovies, systemSessions, systemPlaces));

			Date fecha = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

			pf.newReservation(new Place(cacheSession.getId(), 0, systemPlace,
					creditCard, cacheSession.getSessionType().getCost(), fecha));

			return redirect(routes.Application.controlPanel("-1", "-1", "-1"));
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}

		return ok(controlPanel.render("ERROR: SQL", sessionsTypes,
				systemMovies, systemSessions, systemPlaces));
	}

	public static Result controlPanelPay() {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		boolean avaliability = true;
		try {

			if (!avaliability)
				return ok(controlPanel.render(
						"ERROR: la silla ya ha sido reservada", sessionsTypes,
						systemMovies, systemSessions, systemPlaces));

			Date fecha = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);

			pf.newReservation(new Place(cacheSession.getId(), 0, systemPlace,
					"", cacheSession.getSessionType().getCost(), fecha));

			return redirect(routes.Application.controlPanel("-1", "-1", "-1"));
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}

		return ok(controlPanel.render("ERROR: SQL", sessionsTypes,
				systemMovies, systemSessions, systemPlaces));
	}

	public static Result controlPanel(String id_movie, String id_session,
			String place) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		List<Place> systemlp = new ArrayList<Place>();
		message = "";

		try {

			if (!place.equals("-1")) {
				systemPlace = Integer.parseInt(place);
			} else if (!id_session.equals("-1")) {
				systemPlace = -1;
				systemIdSession = Integer.parseInt(id_session);
				cacheSession = pf.getSessionById(systemIdSession);
				systemlp = pf.getPlaceBySession(systemIdSession);
				systemPlaces = new int[cacheSession.getRoom()
						.getSeatingCapacity()];
				for (Place p : systemlp) {
					systemPlaces[p.getSeat() - 1] = -1;
				}
			} else if (!id_movie.equals("-1")) {
				systemPlace = -1;
				systemIdSession = -1;
				systemIdMovie = Integer.parseInt(id_movie);
				systemSessions = pf.getSessionsByMovie(systemIdMovie);
			} else {
				systemSessions = new ArrayList<Session>();
				systemPlaces = new int[1];
				systemPlace = -1;
			}

			sessionsTypes = pf.getSessionsTypes();
			systemMovies = pf.getMovies();
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return ok(controlPanel.render(message, sessionsTypes, systemMovies,
				systemSessions, systemPlaces));
	}

	public static Result newMovie(String name, String category,
			String synopsis, String poster) {
		PersistenceFactory pf = new PersistenceFactoryImpl();
		try {
			Movie movie = new Movie(name, category, synopsis, "assets/images/"
					+ poster + ".jpg");
			pf.addMovie(movie);
			return redirect(routes.Application.controlPanel("-1", "-1", "-1"));
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
			return redirect(routes.Application.controlPanel("-1", "-1", "-1"));
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		} catch (Exception e) {
			message = e.getMessage();
		}
		return null;
	}

}
