package persistence.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.JdbcExecute;
import models.Movie;
import models.Session;
import models.SessionType;

public class SessionDAO {

	JdbcExecute jdbc;
	PreparedStatement ps;

	public SessionDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	/*
	public List<Session> getBillBoard() throws SQLException {
		String query = "select s.*, m.name, m.category, m.synopsis, r.access"
				+ " from session s, movie m, room r " + " where s.id_room=r.id"
				+ " and s.id_movie=m.id;";
		jdbc.createStatement(query);
		jdbc.setRs(jdbc.getPs().executeQuery());

		List<Session> listSessions = new ArrayList<Session>();
		Session s;
		Movie m;

		while (jdbc.getRs().next()) {
			m = new Movie(jdbc.getRs().getString("name"), jdbc.getRs()
					.getString("category"), jdbc.getRs().getString("synopsis"));
			s = new Session(jdbc.getRs().getInt("id_room"), jdbc.getRs()
					.getInt("id_movie"), jdbc.getRs().getInt("id_sessionType"),
					jdbc.getRs().getDate("startDate"), jdbc.getRs().getDate(
							"endDate"));
			s.setId(jdbc.getRs().getInt("id"));
			s.setAccesRoom(jdbc.getRs().getString("access"));
			s.setMovie(m);
			listSessions.add(s);
		}
		return listSessions;
	}*/
	
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
				session.setAccesRoom(jdbc.getRs().getString("access"));
				listSessions.add(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSessions;
	}
	
}
