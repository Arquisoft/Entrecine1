package models;

import java.sql.Date;

public class Session {

	private int id, id_room, id_movie, id_sessionType;
	private Date startDate, endDate;

	private Movie movie;
	private String accesRoom;

	public Session(int id_room, int id_movie, int id_sessionType,
			Date startDate, Date endDate) {
		super();
		this.id_room = id_room;
		this.id_movie = id_movie;
		this.id_sessionType = id_sessionType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getAccesRoom() {
		return accesRoom;
	}

	public void setAccesRoom(String accesRoom) {
		this.accesRoom = accesRoom;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getId_room() {
		return id_room;
	}

	public void setId_room(int id_room) {
		this.id_room = id_room;
	}

	public int getId_movie() {
		return id_movie;
	}

	public void setId_movie(int id_movie) {
		this.id_movie = id_movie;
	}

	public int getId_sessionType() {
		return id_sessionType;
	}

	public void setId_sessionType(int id_sessionType) {
		this.id_sessionType = id_sessionType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
