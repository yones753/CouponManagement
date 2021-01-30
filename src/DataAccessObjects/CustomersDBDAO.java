package DataAccessObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ConnectionUtils.ConnectionPool;
import JavaBeans.Customer;


public class CustomersDBDAO implements CustomersDAO {
	
	private ConnectionPool connectionPool;
	

	public CustomersDBDAO() {
		connectionPool = ConnectionPool.getInstance();
	}
	

	@Override
	public boolean isCustomerExists(String email, String password) {
		boolean flag = false;
		Connection connection = null;

		try {
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.customers WHERE email = ? AND password = ? ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
//				System.out.println("exist customer");
				flag = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			connectionPool.releaseConnection(connection);
		}
		return flag;
	}

	@Override
	public void addCustomer(Customer customer) {
		Connection connection =null ;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "INSERT INTO devtech.customers (first_name,last_name,email,password) VALUES (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.execute();
//			customer.setId(this.getCustomerIdByEmail(customer.getEmail()));
			System.out.println("Add  customer ");
		} catch (Exception e) {
			System.out.println("Can't add this customer.");
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
	
		Connection connection =null ;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "UPDATE devtech.customers SET first_name = '"+customer.getFirstName()+"', last_name = '"+customer.getLastName()+"', email = '"+customer.getEmail()+"', password = '"+customer.getPassword()+"' WHERE id ="+ this.getCustomerIdByEmail(customer.getEmail());
				connection.createStatement().executeUpdate(query);
				System.out.println("Update the customer!!");
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);	
		}
	}

	@Override
	public void deleteCustomer(int customerID) {
		Connection connection =null ;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = " DELETE FROM devtech.customers WHERE id =" + customerID;
			PreparedStatement statement = connection.prepareStatement(query);
			statement.execute();
//			System.out.println("Delete customer ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
	
		}
	}

	@Override
	public ArrayList<Customer> getAllCustomers() {
		Connection connection =null ;
		ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "SELECT * FROM devtech.customers";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				listOfCustomers.add(new Customer(
						resultSet.getInt("id"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						resultSet.getString("password")));
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			connectionPool.releaseConnection(connection);
			
		}
		return listOfCustomers;
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		Connection connection =null ;
		Customer customer = null;
		
		try {
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.customers WHERE id=" + customerID;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				customer = new Customer(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"),
						resultSet.getString("password"));
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			connectionPool.releaseConnection(connection);
		}
		return customer;
	}
	
	@Override
	public int getCustomerIdByEmail(String email) {
		Connection connection =null ;
		int id =0;
		try {
			connection = connectionPool.getConnection();
			String sql = "SELECT * FROM devtech.customers WHERE email = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
		
			if (resultSet.next()) {
				id = resultSet.getInt("id");
				return id;
			}
		} catch (Exception e) {
			System.out.println("There is No Customer like this.");
			System.out.println(e.getMessage());
		} finally {
			connectionPool.releaseConnection(connection);
			
		}
		return id;
	
	}


}
