package persistence;

import java.sql.SQLException;
import java.util.List;

import models.*;
import persistence.impl.*;

public class PersistenceFactoryImpl implements PersistenceFactory {

	private CustomerDAO cj = new CustomerDAO();
	private PlaceDAO pj = new PlaceDAO();
	private SessionDAO sj = new SessionDAO();

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
		return sj.getMovies();
	}

	@Override
	public String getSynopsis(Integer id_movie) throws SQLException {
		return sj.getSynopsis(id_movie);
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

}
