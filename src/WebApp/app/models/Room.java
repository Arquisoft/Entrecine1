package app.models;

public class Room {

	private int id, seatingCapacity;
	private String access;

	public Room(int seatingCapacity, String access) {
		super();
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
	public void setId(int id) {
		this.id = id;
	}
}
