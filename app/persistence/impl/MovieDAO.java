package persistence.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Movie;

import persistence.JdbcExecute;

public class MovieDAO {

	
	JdbcExecute jdbc;
	PreparedStatement ps;

	public MovieDAO() {
		jdbc = JdbcExecute.getInstance();
	}

	public List<Movie> getMovies() throws SQLException {
		String query = "select m.* " + "from movie m";
		jdbc.createStatement(query);
		jdbc.setRs(jdbc.getPs().executeQuery());

		List<Movie> listMovies = new ArrayList<Movie>();
		Movie m;

		while (jdbc.getRs().next()) {
			m = new Movie(jdbc.getRs().getString("name"), jdbc.getRs()
					.getString("category"), jdbc.getRs().getString("synopsis"),
					jdbc.getRs().getString("poster"));
			m.setId(jdbc.getRs().getInt("ID"));
			listMovies.add(m);
		}
		return listMovies;
	}

	public Movie getMovieById(Integer id_movie) throws SQLException {
		String query = "select m.* " + "from movie m where m.id=?";
		jdbc.createStatement(query);
		jdbc.getPs().setInt(1, id_movie);
		jdbc.setRs(jdbc.getPs().executeQuery());

		Movie movie = null;

		if (jdbc.getRs().next()) {
			movie = new Movie(jdbc.getRs()
					.getString("name"), jdbc.getRs().getString("category"),
					jdbc.getRs().getString("synopsis"), jdbc.getRs().getString(
							"poster"));
			movie.setId(jdbc.getRs().getInt("ID"));
		}
		return movie;
	}

	public void addMovie(Movie m) throws SQLException {
		String query = "insert into movie (name, category, synopsis, poster) values (?,?,?,?)";
		jdbc.createStatement(query);
		ps = jdbc.getPs();
		ps.setString(1, m.getName());
		ps.setString(2, m.getCategory());
		ps.setString(3, m.getSynopsis());
		ps.setString(4, m.getPoster());
		ps.executeUpdate();
	}

	public void updateMovie(Movie m) throws SQLException {
		String query = "update movie set name=?, category=?, synopsis=?, poster=? where id=?";
		jdbc.createStatement(query);
		ps = jdbc.getPs();
		ps.setString(1, m.getName());
		ps.setString(2, m.getCategory());
		ps.setString(3, m.getSynopsis());
		ps.setString(4, m.getPoster());
		ps.setInt(5, m.getId());
		ps.executeUpdate();
	}

}
