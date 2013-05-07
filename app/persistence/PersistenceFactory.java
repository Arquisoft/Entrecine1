package persistence;

import java.sql.SQLException;
import java.util.List;

import models.*;

public interface PersistenceFactory {

	Customer getCustomer(String login, String passw) throws SQLException;
	void newCustomer(Customer c) throws SQLException;
	
	List<Movie> getMovies() throws SQLException;
	Movie getMovieById(Integer id_movie) throws SQLException;
	List<Session> getSessionsByMovie(Integer id_movie) throws SQLException;
	
	Session getSessionById(Integer id_session) throws SQLException;
	List<Place> getPlaceBySession(Integer id_session) throws SQLException;
	boolean getAvaliability(int idSession, int seat) throws SQLException;
	void newReservation(Place p) throws SQLException;
	
	void addMovie(Movie m) throws SQLException;
	void updateMovie(Movie m) throws SQLException;
	
	List<SessionType> getSessionsTypes() throws SQLException;
	void changePrice(SessionType st) throws SQLException;
	void changeStartTime(SessionType st) throws SQLException;
	
	SystemUser getSystemUser(String login, String passw);
}
