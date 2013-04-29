package persistence.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.JdbcExecute;
import models.Movie;
import models.Session;

public class SessionDAO {

	JdbcExecute jdbc;

	public SessionDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public List<Session> getBillBoard() throws SQLException {
		String query = "select s.*, m.name, m.category, m.synopsis, r.access"
				+ " from session s, movie m, room r "
				+ " where s.id_room=r.id" + " and s.id_movie=m.id;";
		jdbc.setRs(jdbc.getPs().executeQuery(query));

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
	}
}
