package persistence.impl;

import java.sql.SQLException;

import persistence.JdbcExecute;
import models.Customer;

public class CustomerDAO {

	JdbcExecute jdbc;

	
	public CustomerDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public Customer getCustomer(String login, String passw) {
		String query = "select * " + "from customer "
				+ "where login = ? and passw = ?";
		Customer customer = null;
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setString(1, login);
			jdbc.getPs().setString(2, passw);
			jdbc.setRs(jdbc.getPs().executeQuery());
			while (jdbc.getRs().next()) {
				customer = new Customer(jdbc.getRs().getString(2), jdbc.getRs()
						.getString(3), jdbc.getRs().getString(4), jdbc.getRs()
						.getString(5), jdbc.getRs().getString(6), jdbc.getRs()
						.getString(7));
				customer.setId(jdbc.getRs().getInt(1));
			}
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public void newCustomer(Customer c) {
		String query = "INSERT INTO "
				+ "customer (name, surnames, email, creditcard, login, passw)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setString(1, c.getName());
			jdbc.getPs().setString(2, c.getSurnames());
			jdbc.getPs().setString(3, c.getEmail());
			jdbc.getPs().setString(4, c.getCreditcard());
			jdbc.getPs().setString(5, c.getLogin());
			jdbc.getPs().setString(6, c.getPassw());
			jdbc.getPs().executeUpdate();
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
