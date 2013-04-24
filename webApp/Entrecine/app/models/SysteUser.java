package models;

public class SysteUser {

	private int id;
	private String login, passw, type;
	private boolean mSale, mSession, mMovies;

	public SysteUser(int id, String login, String passw, String type,
			boolean mSale, boolean mSession, boolean mMovies) {
		super();
		this.id = id;
		this.login = login;
		this.passw = passw;
		this.type = type;
		this.mSale = mSale;
		this.mSession = mSession;
		this.mMovies = mMovies;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean ismSale() {
		return mSale;
	}

	public void setmSale(boolean mSale) {
		this.mSale = mSale;
	}

	public boolean ismSession() {
		return mSession;
	}

	public void setmSession(boolean mSession) {
		this.mSession = mSession;
	}

	public boolean ismMovies() {
		return mMovies;
	}

	public void setmMovies(boolean mMovies) {
		this.mMovies = mMovies;
	}

	public int getId() {
		return id;
	}

}
