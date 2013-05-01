package persistence;

import java.sql.*;

public class JdbcExecute {

	private static JdbcExecute INSTANCE = null;

	// private static String DRIVER = "org.hsqldb.jdbcDriver";
	private static String URL = "jdbc:hsqldb:hsql://localhost/";
	private static String USER = "sa";
	private static String PASS = "";

	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps ;

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
		// return DB.getConnection();//no funciona.
	}
/*
	public void setQuery(String query) throws SQLException {
		con = getConnection();
		ps = con.createStatement();
		ps.executeUpdate(query);
		con.close();
		ps.close();
	}

	public ResultSet getQuery(String query) throws SQLException {
		con = getConnection();
		ps = con.createStatement();
		rs = ps.executeQuery(query);
		con.close();
		ps.close();
		return rs;
	}*/

	public void createStatement(String query) {
		try {
			con = getConnection();
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			rs.close();
			con.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}

	public ResultSet getRs() {
		return rs;
	}
	
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public PreparedStatement getPs() {
		return ps;
	}

}
