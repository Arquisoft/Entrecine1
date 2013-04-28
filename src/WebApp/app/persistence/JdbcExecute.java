package app.persistence;

import java.sql.*;

public class JdbcExecute {

	private static JdbcExecute INSTANCE = null;

	// private static String DRIVER = "org.hsqldb.jdbcDriver";
	private static String URL = "jdbc:hsqldb:hsql://localhost/";
	private static String USER = "sa";
	private static String PASS = "";

	private Connection con;
	private ResultSet rs;
	private Statement st;

	private JdbcExecute() {
	}

	public static JdbcExecute getInstance() {
		createInstance();
		return INSTANCE;
	}

	private static void createInstance() {
		if (INSTANCE == null)
			synchronized (JdbcExecute.class) {
				if (INSTANCE == null)
					INSTANCE = new JdbcExecute();
			}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
		//return DB.getConnection();//no funciona.
	}

	public void setQuery(String query) throws SQLException {
		con = getConnection();
		st = con.createStatement();
		st.executeUpdate(query);
		con.close();
		st.close();
	}

	public ResultSet getQuery(String query) throws SQLException {
		con = getConnection();
		st = con.createStatement();
		rs = st.executeQuery(query);
		con.close();
		st.close();
		return rs;
	}

}
