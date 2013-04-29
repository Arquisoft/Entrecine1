package persistence.impl;

import java.sql.SQLException;
import persistence.JdbcExecute;

import models.Place;

public class PlaceDAO {
	
	JdbcExecute jdbc;
	
	public PlaceDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public boolean getAvaliability(int idSession, int seat) {
		String query = "select p.* " +
						"from session s, place p " +
						"where p.seat = ? and p.id_session = s.id and s.id = ?";
		boolean taken = false;
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, idSession);
			jdbc.getPs().setInt(2, seat);
			jdbc.setRs(jdbc.getPs().executeQuery());
			taken = jdbc.getRs().next();
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taken;
	}

	public void newReservation(Place p) {
		String query = "INSERT INTO " +
						"Place('id_session', 'seat', 'id_customer', 'creditcard', 'amount', 'paymentdate')" +
						" VALUES (?, ?, ?, ?, ?, ?)";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, p.getId_session());
			jdbc.getPs().setInt(2, p.getSeat());
			jdbc.getPs().setInt(3, p.getId_customer());
			jdbc.getPs().setString(4, p.getCreditCard());
			jdbc.getPs().setDouble(5, p.getAmount());
			jdbc.getPs().setDate(6, p.getPaymentDate());
			jdbc.getPs().executeUpdate();
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
