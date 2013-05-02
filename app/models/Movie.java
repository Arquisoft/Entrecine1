package models;

public class Movie {

	private Integer id;
	private String name, category, synopsis, poster;

	public Movie(Integer id, String name, String category, String synopsis,
			String poster) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.synopsis = synopsis;
		this.poster = poster;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
