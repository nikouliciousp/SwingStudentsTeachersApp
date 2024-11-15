package gr.perisnik.cj.swing.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {

	private static BasicDataSource ds = new BasicDataSource();
	private static Connection conn;
	
	/**
	 * No instances of this class should be available.
	 */
	private DBUtil() {}
	
	static {
		ds.setUrl("jdbc:mysql://localhost:3306/studentsdbcj?serverTimeZone=UTC");
		ds.setUsername("sttdbcj");
		ds.setPassword("perisnik123");
		ds.setInitialSize(8);
		ds.setMaxTotal(32);
		ds.setMinIdle(8);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(100);
	}
	
	public static Connection getConnection() throws SQLException {
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.addSuppressed(null);
			throw e;
		}
		
		return conn;
	}
	
	public static void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.addSuppressed(null);
		}
	}
}
