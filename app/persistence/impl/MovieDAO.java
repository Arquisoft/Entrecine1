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
		String query = "select m.* " +
						"from movie m";
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
		String query = "select m.synopsis " +
						"from movie m where m.id=?";
		jdbc.createStatement(query);
		jdbc.getPs().setInt(1, id_movie);
		jdbc.setRs(jdbc.getPs().executeQuery());

		String synopsis = "";

		while (jdbc.getRs().next()) {
			synopsis = jdbc.getRs().getString("synopsis");
		}
		return synopsis;
	}
	
	public void addMovie(Movie m) throws SQLException{
		String query = "insert into movie (name, category, synopsis, poster) values (?,?,?,?)";
		jdbc.createStatement(query);
		ps=jdbc.getPs();
		ps.setString(1, m.getName());
		ps.setString(2, m.getCategory());
		ps.setString(3, m.getSynopsis());
		ps.setString(4, m.getPoster());
		ps.executeUpdate();
	}
	
	public void updateMovie(Movie m) throws SQLException{
		String query = "update movie set name=?, category=?, synopsis=?, poster=? where id=?";
		jdbc.createStatement(query);
		ps=jdbc.getPs();
		ps.setString(1, m.getName());
		ps.setString(2, m.getCategory());
		ps.setString(3, m.getSynopsis());
		ps.setString(4, m.getPoster());
		ps.setInt(5, m.getId());
		ps.executeUpdate();
	}

}