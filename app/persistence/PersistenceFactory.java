package persistence;

import java.sql.SQLException;
import java.util.List;

import models.Customer;
import models.Movie;
import models.Place;
import models.Session;


public interface PersistenceFactory {

	void setCustomer(Customer c) throws SQLException;

	Customer getCustomer(String login, String passw) throws SQLException;

	void newCustomer(Customer c) throws SQLException;

	List<Movie> getMovies() throws SQLException;

	String getSynopsis(Integer id_movie) throws SQLException;

	List<Session> getSessionsByMovie(Integer id_movie) throws SQLException;

	boolean getAvaliability(int idSession, int seat) throws SQLException;

	void newReservation(Place p) throws SQLException;

}
