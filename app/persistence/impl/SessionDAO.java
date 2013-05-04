package persistence.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.JdbcExecute;
import models.*;

public class SessionDAO {

	JdbcExecute jdbc;
	PreparedStatement ps;

	public SessionDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public List<Movie> getMovies() throws SQLException {
		String query = "select m.* " + "from movie m";
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
		String query = "select m.synopsis " + "from movie m where m.id=?";
		jdbc.createStatement(query);
		jdbc.getPs().setInt(1, id_movie);
		jdbc.setRs(jdbc.getPs().executeQuery());

		String synopsis = "";

		while (jdbc.getRs().next()) {
			synopsis = jdbc.getRs().getString("synopsis");
		}
		return synopsis;
	}

	public List<Session> getSessionsByMovie(int id_movie) {
		List<Session> listSessions = new ArrayList<Session>();
		Session session;
		SessionType sessionType;
		String query = "select s.*, st.* " + "from session s, sessiontype st "
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
				session.setId(jdbc.getRs().getInt("id"));
				listSessions.add(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSessions;
	}

	public Session getSessionById(Integer id_session) {
		Session session = null;
		SessionType sessionType;
		Room room;
		String query = "select s.*, r.access, r.seatingcapacity, st.name, st.starttime, st.cost "
				+ " from session s, room r, sessiontype st "
				+ " where s.id_room=r.id and s.id_sessiontype=st.id and s.id=?";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, id_session);
			jdbc.setRs(jdbc.getPs().executeQuery());

			if (jdbc.getRs().next()) {
				room = new Room(jdbc.getRs().getInt("seatingcapacity"), jdbc
						.getRs().getString("access"));
				room.setId(jdbc.getRs().getInt("id_room"));

				sessionType = new SessionType(jdbc.getRs().getInt("starttime"),
						jdbc.getRs().getString("name"), jdbc.getRs().getDouble(
								"cost"));
				sessionType.setId(jdbc.getRs().getInt("id_sessionType"));

				session = new Session(jdbc.getRs().getInt("id_room"), jdbc
						.getRs().getInt("id_movie"), jdbc.getRs().getDate(
						"startDate"), jdbc.getRs().getDate("endDate"),
						sessionType);
				session.setId(id_session);
				session.setRoom(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return session;
	}

	public List<Place> getPlaceBySession(Integer id_session) {
		List<Place> places = new ArrayList<Place>();
		String query = "select seat from place where id_session = ?";
		try {
			jdbc.createStatement(query);
			jdbc.getPs().setInt(1, id_session);
			jdbc.setRs(jdbc.getPs().executeQuery());

			while (jdbc.getRs().next()) {
				places.add(new Place(id_session, jdbc.getRs().getInt("seat")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return places;
	}
}
