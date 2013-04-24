package models;

public class Room {

	private int id, seatingCapacity;
	private String access;

	public Room(int id, int seatingCapacity, String access) {
		super();
		this.id = id;
		this.seatingCapacity = seatingCapacity;
		this.access = access;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public int getId() {
		return id;
	}

}
