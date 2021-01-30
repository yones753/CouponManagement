package DataAccessObjects;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ConnectionUtils.ConnectionPool;
import JavaBeans.Company;


public class CompaniesDBDAO implements CompaniesDAO {
	
	private ConnectionPool connectionPool;

	public CompaniesDBDAO() {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean isCompanyExists(String email, String password) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.companies WHERE email = ? AND password = ? "; // The query from mySql
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery(); 
			 if(resultSet.next()) {		
				 flag = true;
			 }
		} catch (Exception e) { // Exception
			System.out.println(e.getMessage());
		} finally {
			connectionPool.releaseConnection(connection); // Release Connection after finish using !
			
		}
		return flag;
	}

	@Override
	public void addCompany(Company company) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "INSERT INTO devtech.companies (name,email,password) VALUES (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.execute();
//			company.setId(this.getCompanyIdByName(company.getName()));
			System.out.println("Add company "+company.getName());
		} catch (Exception e) {
			System.out.println("Can't add this Company.");
			System.out.println(e.getMessage());
		} finally {
			connectionPool.releaseConnection(connection);

		}
	}

	@Override
	public void updateCompany(Company company) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();		
			String query = "UPDATE devtech.companies SET  email = '"+company.getEmail()+"', password = '"+company.getPassword()+"' WHERE id = "+ this.getCompanyIdByName(company.getName());
			connection.createStatement().executeUpdate(query);
			System.out.println("Update Company "+company.getName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't update this company");
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	public void deleteCompany(int companyID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "DELETE FROM devtech.companies WHERE id ="+companyID;
			PreparedStatement statement = connection.prepareStatement(query);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	public ArrayList<Company> getAllCompanies() {
		Connection connection = null;
		ArrayList<Company> listOfCompanies = new ArrayList<Company>();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String query = "SELECT * FROM devtech.companies";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				listOfCompanies.add(new Company(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("email"),
						resultSet.getString("password")));
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			connectionPool.releaseConnection(connection);

		}
		return listOfCompanies;
	}

	@Override
	public Company getOneCompany(int companyID) {
		Connection connection = null;
		Company company = null;
		try {
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.companies WHERE id=" + companyID;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				company = new Company(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"),
						resultSet.getString("password"));
			}

		} catch (Exception e) {
			System.out.println("Value is not exist !");
			System.err.println(e.getLocalizedMessage());

		} finally {
			connectionPool.releaseConnection(connection);

		}
		return company;
	}
	
	@Override
	public int getCompanyIdByName(String name) {
		Connection connection = null;
		int id = -1;
		try {
			connection = connectionPool.getConnection();
			String sql = "SELECT * FROM devtech.companies WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				return id;
			}

			
		} catch (SQLException e) {
			System.out.println("There is No Company like this.");
			System.out.println(e.getMessage());
		} finally {
			connectionPool.releaseConnection(connection);

		}
		return id;

	}
	
	@Override
	public int getCompanyIdByEmail(String email) {
		Connection connection = null;
		int id = -1;
		try {
			connection = connectionPool.getConnection();
			String sql = "SELECT * FROM devtech.companies WHERE email = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				id = resultSet.getInt("id");
				return id;
			}

			
		} catch (SQLException e) {
			System.out.println("There is No Company like this.");
			System.out.println(e.getMessage());
		} finally {
			connectionPool.releaseConnection(connection);

		}
		return id;

	}

}
