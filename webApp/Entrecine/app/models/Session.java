package models;

import java.util.Calendar;

public class Session {

	private int id, id_room, id_movie, id_sessionType;
	private Calendar startDate, endDate;

	public Session(int id, int id_room, int id_movie, int id_sessionType,
			Calendar startDate, Calendar endDate) {
		super();
		this.id = id;
		this.id_room = id_room;
		this.id_movie = id_movie;
		this.id_sessionType = id_sessionType;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

}
