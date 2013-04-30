package models;

public class Movie {

	private int id;
	private String name, category, synopsis, poster;

	public Movie(String name, String category, String synopsis, String poster) {
		super();
		this.name = name;
		this.category = category;
		this.synopsis = synopsis;
		this.poster = poster;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
