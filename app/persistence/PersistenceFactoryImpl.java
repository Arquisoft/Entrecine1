package persistence;

import java.sql.SQLException;
import java.util.List;

import models.*;
import persistence.impl.*;

public class PersistenceFactoryImpl implements PersistenceFactory {

	private CustomerDAO cj = new CustomerDAO();
	private PlaceDAO pj = new PlaceDAO();
	private SessionDAO sj = new SessionDAO();
	private MovieDAO mj = new MovieDAO();
	private SessionTypeDAO st = new SessionTypeDAO();
	private SystemUserDAO su = new SystemUserDAO();

	@Override
	public void setCustomer(Customer c) throws SQLException {
		cj.setCustomer(c);
	}

	@Override
	public Customer getCustomer(String login, String passw) throws SQLException {
		return cj.getCustomer(login, passw);
	}

	@Override
	public void newCustomer(Customer c) throws SQLException {
		cj.newCustomer(c);
	}

	@Override
	public List<Movie> getMovies() throws SQLException {
		return mj.getMovies();
	}

	@Override
	public Movie getMovieById(Integer id_movie) throws SQLException {
		return mj.getMovieById(id_movie);
	}

	@Override
	public List<Session> getSessionsByMovie(Integer id_movie)
			throws SQLException {
		return sj.getSessionsByMovie(id_movie);
	}

	@Override
	public boolean getAvaliability(int idSession, int seat) throws SQLException {
		return pj.getAvaliability(idSession, seat);
	}

	@Override
	public void newReservation(Place p) throws SQLException {
		pj.newReservation(p);
	}

	@Override
	public Session getSessionById(Integer id_session) throws SQLException {
		return sj.getSessionById(id_session);
	}

	@Override
	public List<Place> getPlaceBySession(Integer id_session)
			throws SQLException {
		return sj.getPlaceBySession(id_session);
	}

	@Override
	public void addMovie(Movie m) throws SQLException {
		mj.addMovie(m);
	}

	@Override
	public void updateMovie(Movie m) throws SQLException {
		mj.updateMovie(m);
	}

	@Override
	public void changePrice(SessionType st) throws SQLException {
		this.st.changePrice(st);
	}

	@Override
	public void changeStartTime(SessionType st) throws SQLException {
		this.st.changeStartTime(st);
	}

	@Override
	public List<SessionType> getSessionsTypes() throws SQLException {
		return st.getSessionsTypes();
	}

	@Override
	public SystemUser getSystemUser(String login, String passw) {
		return su.getSystemUser(login, passw);
	}
}
