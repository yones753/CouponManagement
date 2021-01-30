package ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ConnectionPool {

	private ArrayList<Connection> availablConnections = new ArrayList<Connection>();
	private Set<Connection> useConnections = new HashSet<Connection>();
	private final int MAX_CONNECTIONS = 10;
	private static ConnectionPool instance = null;
	private String DB_URL;
	private String JDBC_DRIVER;
	private String user ;
	private String password;
// ----------------------------------------Function get connection---------------------------------------
	
	
	
//	Initialize connection -> Pool
	private ConnectionPool() throws SQLException{
		
		JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
		DB_URL= "jdbc:mysql://localhost:3306/devtech?useSSL=false&serverTimezone=UTC";
		user ="root";
		password="Yones12!";
		
		for(int cont =0; cont < MAX_CONNECTIONS ; cont++) {
			availablConnections.add(this.createConnection());
		}
	}
	
	public synchronized static ConnectionPool getInstance() {
		if( instance == null) {
			try {
				instance = new ConnectionPool();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private Connection createConnection() throws SQLException{
		try {
			Class.forName(JDBC_DRIVER);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DB_URL, user, password);
	}
	
	

//	get connection from Pool
	public Connection getConnection() {
		if( availablConnections.size() == 0) {
			System.out.println("All connetion are used !!");
			return null;
		}
		else {
//			System.out.println( "Connection to DataBase ...");
			Connection con = availablConnections.remove(availablConnections.size() -1);
			useConnections.add(con);
			return con;
		}
		
	}
	
//	return connection back to Pool 
	public boolean releaseConnection(Connection con) {
		if(con != null) {
			useConnections.remove(con);
			availablConnections.add(con);
			return true ;
		}
		else {
			return false;
		}
	}

	
//	Check how many connection not use
	public int getFreeConnectionCount() {
		if(availablConnections.size() == 0) {
			System.out.println("Connection free : ");
		}
		return availablConnections.size();
	}
	
//	Check how many connection not use
	public int getUsedConnectionCount() {
		if(useConnections.size() == 0) {
			System.out.println("Connection used : ");
		}
		return useConnections.size();
	}
	
	
// -------------------------Function for closing all connection--------------------------------------------
	public void closeAllConnections() {
		for (Connection connection : availablConnections) {
			try {
				connection.close();

			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
		System.out.println(" All connection are closed !!");
	}
}
