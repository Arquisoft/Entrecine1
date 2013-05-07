package persistence.impl;

import java.sql.SQLException;

import persistence.JdbcExecute;
import models.*;

public class SystemUserDAO {

	JdbcExecute jdbc;
	
	public SystemUserDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	
	public SystemUser getSystemUser(String login, String passw) {
		String query = "select * " +
				"from systemUser " +
				"where login = ? and passw = ?";
		SystemUser user = null;
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setString(1, login);
			jdbc.getPs().setString(2, passw);
			jdbc.setRs(jdbc.getPs().executeQuery());
			while (jdbc.getRs().next()){
				user = new SystemUser(jdbc.getRs().getString(2), jdbc.getRs()
						.getString(3), jdbc.getRs().getString(4), jdbc.getRs()
						.getBoolean(5), jdbc.getRs().getBoolean(6), jdbc.getRs()
						.getBoolean(7));
				user.setId(jdbc.getRs().getInt(1));
			}
			jdbc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
