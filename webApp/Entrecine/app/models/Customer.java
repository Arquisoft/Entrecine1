package models;

public class Customer {

	private int id;
	private String name, surnames, email, creditCard, login, passw;

	public Customer(int id, String name, String surnames, String email,
			String creditcard, String login, String passw) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.email = email;
		this.creditCard = creditcard;
		this.login = login;
		this.passw = passw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreditcard() {
		return creditCard;
	}

	public void setCreditcard(String creditcard) {
		this.creditCard = creditcard;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public int getId() {
		return id;
	}
}
