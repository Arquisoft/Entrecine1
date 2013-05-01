package persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.JdbcExecute;
import models.Movie;
import models.Session;
import models.SessionType;

public class SessionDAO {

	JdbcExecute jdbc;

	public SessionDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public List<Movie> getMovies() throws SQLException {
		String query = "select m.* from movie m";
		jdbc.createStatement(query);
		jdbc.setRs(jdbc.getPs().executeQuery());

		List<Movie> listMovies = new ArrayList<Movie>();
		Movie m;

		while (jdbc.getRs().next()) {
			m = new Movie(jdbc.getRs().getInt("ID"), jdbc.getRs().getString(
					"name"), jdbc.getRs().getString("category"), jdbc.getRs()
					.getString("synopsis"), jdbc.getRs().getString("poster"));
			listMovies.add(m);
		}
		return listMovies;
	}

	public String getSynopsis(Integer id_movie) throws SQLException {
		String query = "select m.synopsis from movie m where m.id=?";
		jdbc.createStatement(query);
		jdbc.getPs().setInt(1, id_movie);
		jdbc.setRs(jdbc.getPs().executeQuery());

		String synopsis = "";

		while (jdbc.getRs().next()) {
			synopsis = jdbc.getRs().getString("synopsis");
		}
		return synopsis;
	}

	public List<Session> getSessionsByMovie(Integer id_movie)
			throws SQLException {
		List<Session> listSessions = new ArrayList<Session>();
		Session session;
		SessionType sessionType;
		String query = "select s.id, s.*, st.* "
				+ "from session s, sessiontype st "
				+ "where s.id_sessionType = st.id " + "and s.id_movie = ?";
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
				session.setId(jdbc.getRs().getInt(1));
				listSessions.add(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSessions;
	}
}
