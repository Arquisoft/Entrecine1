package persistence.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.JdbcExecute;
import models.*;

public class SessionTypeDAO {

	JdbcExecute jdbc;
	PreparedStatement ps;

	public SessionTypeDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	
	public List<SessionType> getSessionsTypes() {
		List<SessionType> listSessionsTypes = new ArrayList<SessionType>();
		SessionType sessionType;
		String query = "select * " +
						"from sessiontype";
		try {
			jdbc.createStatement(query);
			jdbc.setRs(jdbc.getPs().executeQuery());
			
			while (jdbc.getRs().next()) {
				sessionType = new SessionType(jdbc.getRs().getInt("startTime"),
						jdbc.getRs().getString("name"), jdbc.getRs().getDouble(
								"cost"));
				sessionType.setId(jdbc.getRs().getInt("id"));
				listSessionsTypes.add(sessionType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSessionsTypes;
	}
	
	public void changePrice(SessionType st){
		String query="update sessiontype set cost=? where id=?";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setDouble(1, st.getCost());
			jdbc.getPs().setInt(2, st.getId());
			jdbc.getPs().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changeStartTime(SessionType st){
		String query="update sessiontype set starttime=? where id=?";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, st.getStartTime());
			jdbc.getPs().setInt(2, st.getId());
			jdbc.getPs().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
}
