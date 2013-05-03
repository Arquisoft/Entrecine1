package persistence.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Session;
import models.SessionType;
import persistence.JdbcExecute;

public class SessionDAO {

	JdbcExecute jdbc;
	PreparedStatement ps;

	public SessionDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	
	public List<Session> getSessionsByMovie(int id_movie) {
		List<Session> listSessions = new ArrayList<Session>();
		Session session;
		SessionType sessionType;
		String query = "select s.*, st.* " +
						"from session s, sessiontype st " +
						"where s.id_sessionType = st.id " +
							"and s.id_movie = ?";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, id_movie);
			jdbc.setRs(jdbc.getPs().executeQuery());

			while (jdbc.getRs().next()) {
				sessionType = new SessionType(jdbc.getRs().getInt("startTime"),
						jdbc.getRs().getString("name"), jdbc.getRs().getDouble(
								"cost"));
				sessionType.setId(jdbc.getRs().getInt("id_sessionType"));
				session = new Session(jdbc.getRs().getInt("id_room"), id_movie,
						jdbc.getRs().getDate("startDate"), jdbc.getRs()
								.getDate("endDate"), sessionType);
				session.setId(jdbc.getRs().getInt("id"));
				listSessions.add(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSessions;
	}
	
}
