package persistence.impl;

import java.sql.ResultSet;
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
		String query = "select s.*, m.*, r.access"
				+ " from session s, movie m, room r "
				+ " where s.id_room=r.id"
				+ " and s.id_movie=m.id;";
		ResultSet rs = jdbc.getQuery(query);

		List<Session> listSessions = new ArrayList<Session>();
		Session s;
		Movie m;

		while (rs.next()) {
			m = new Movie(rs.getString("name"), rs.getString("category"),
					rs.getString("synopsis"), rs.getString("poster"));
			s = new Session(rs.getInt("id_room"), rs.getInt("id_movie"),
					rs.getInt("id_sessionType"), rs.getDate("startDate"),
					rs.getDate("endDate"));
			s.setId(rs.getInt("id"));
			s.setAccesRoom(rs.getString("access"));
			s.setMovie(m);
			listSessions.add(s);
		}
		return listSessions;
	}
}
