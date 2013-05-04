package models;

import java.sql.Date;

public class Session {

	private int id, id_room, id_movie;
	private Date startDate, endDate;

	private Movie movie;
	private Room room;
	
	private SessionType sessionType;

	public Session(int id_room, int id_movie, Date startDate, Date endDate, SessionType sessionType) {
		super();
		this.id_room = id_room;
		this.id_movie = id_movie;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sessionType = sessionType;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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

	public SessionType getSessionType() {
		return sessionType;
	}

	public void setSsessionType(SessionType sessionType) {
		this.sessionType = sessionType;
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
