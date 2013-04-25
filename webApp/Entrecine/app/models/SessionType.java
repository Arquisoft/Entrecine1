package models;

public class SessionType {

	private int id, startTime;
	private String name;
	private double cost;

	public SessionType(int id, int startTime, String name, double cost) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.name = name;
		this.cost = cost;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

}
