package app.persistence;

import java.sql.SQLException;
import java.util.List;

import Entrecine.app.models.Customer;
import Entrecine.app.models.Place;
import Entrecine.app.models.Session;

public interface PersistenceFactory {

	void setCustomer(Customer c)  throws SQLException ;

	Customer getCustomer(String login, String passw) throws SQLException ;

	void newCustomer(Customer c) throws SQLException ;

	List<Session> getBillBoard() throws SQLException ;

	boolean getAvaliability(int idSession, int seat) throws SQLException ;

	void newReservation(Place p) throws SQLException ;

}
