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
		String query = "select * from place where seat = ? and id_session = ?";
		boolean taken = true;
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, seat);
			jdbc.getPs().setInt(2, idSession);
			jdbc.setRs(jdbc.getPs().executeQuery());
			if (jdbc.getRs().next())
				taken = false;
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taken;
	}

	public void newReservation(Place p) {
		String query = "INSERT INTO "
				+ "Place(id_session, seat, id_customer, creditcard, amount,paymentdate)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
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
