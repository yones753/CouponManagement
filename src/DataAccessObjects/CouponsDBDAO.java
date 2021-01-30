package DataAccessObjects;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionUtils.ConnectionPool;
import JavaBeans.Category;
import JavaBeans.Coupon;



public class CouponsDBDAO implements CouponsDAO {

	private ConnectionPool connectionPool;
	
	public CouponsDBDAO() {
		connectionPool = ConnectionPool.getInstance();
	}
	@Override
	public void addCoupon(Coupon coupon) {
		Connection connection =null ;
	try {
	    connection = connectionPool.getConnection();
	    String query = "INSERT INTO devtech.coupons (company_id,category_id, title, description , start_date ,end_date ,amount , price , image ) VALUES (?,?,?,?,?,?,?,?,?)";
	    PreparedStatement statement = connection.prepareStatement(query);
	    
		statement.setInt(1, coupon.getCompanyId());
		statement.setInt(2, Category.categoryToInt(coupon.getCategory())); 
		statement.setString(3, coupon.getTitle());
		statement.setString(4, coupon.getDescription());
		statement.setDate(5,(Date) coupon.getStartDate()); 
		statement.setDate(6,(Date) coupon.getEndDate());
		statement.setInt(7, coupon.getAmount());
		statement.setDouble(8, coupon.getPrice());
		statement.setString(9, coupon.getImage());
		statement.execute();

		System.out.println("Add "+coupon.getTitle());
	}catch (Exception e) {
		System.out.println("Can't add this coupon!!");
		e.printStackTrace();
	}finally {
		connectionPool.releaseConnection(connection);
	
	}
		
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		Connection connection =null ;
		try {
			connection = connectionPool.getConnection();
			String query = "UPDATE devtech.coupons SET company_id = '"+ coupon.getCompanyId() +"' , category_id = '"+ Category.categoryToInt(coupon.getCategory()) +"',title = '"+ coupon.getTitle() +"',description = '"+ coupon.getDescription() +"' ,start_date = '"+ coupon.getStartDate() + "', end_date = '"+ coupon.getEndDate() + "', amount = '"+coupon.getAmount()+"', price = '"+coupon.getPrice()+"', image = '"+coupon.getImage()+"' WHERE id = "+ this.getCouponIdByCompanyIDandTitle(coupon.getCompanyId(),coupon.getTitle()); 
			connection.createStatement().executeUpdate(query);
			System.out.println("Update the coupons!!");
		} catch (Exception e) {
			System.out.println("Can't update this customer.");
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public void deleteCoupon(int couponID) {
		
		Connection connection =null ;
		try {
			this.deleteAllPurByCouponId(couponID);
			connection = connectionPool.getConnection();
			String query = "DELETE FROM devtech.coupons WHERE id ="+ couponID;
			PreparedStatement statement = connection.prepareStatement(query);
			statement.execute();
//			if(statement.execute()) {
//				System.out.println("Deleted coupon Successfully!");
//			}else
//			{
//				System.out.println("Coupon does not exists!");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		
		}
		
	}

	@Override
	public ArrayList<Coupon> getAllCoupons() {
		Connection connection =null ;
		ArrayList<Coupon> listOfCoupons = new ArrayList<Coupon>();
		try{
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.coupons";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {

				
				listOfCoupons.add(new Coupon(
						resultSet.getInt("id"),
						resultSet.getInt("company_id"),
						Category.intToCategory(resultSet.getInt("category_id")),
						resultSet.getString("title"),
						resultSet.getString("description"),
						resultSet.getDate("start_date"),
						resultSet.getDate("end_date"),
						resultSet.getInt("amount"),
						resultSet.getDouble("price"),
						resultSet.getString("image")));
			}
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}finally {
			connectionPool.releaseConnection(connection);
		}
		return listOfCoupons;
	}

	@Override
	public Coupon getOneCoupon(int couponID) {
		Connection connection =null ;
		Coupon coupon = null;
		try {
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.coupons WHERE id="+ couponID;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				 coupon = new Coupon(
						    resultSet.getInt("id"),
							resultSet.getInt("company_id") ,
							Category.intToCategory(resultSet.getInt("category_id")), 
							resultSet.getString("title"),
							resultSet.getString("description"),
							resultSet.getDate("start_date"),
							resultSet.getDate("end_date"),
							resultSet.getInt("amount"),
							resultSet.getDouble("price"),
							resultSet.getString("image"));
			}
				
					return coupon;
	        }catch (Exception e) {
				e.printStackTrace();
			}finally {
				connectionPool.releaseConnection(connection);
				connection = null;
			}
		return coupon;
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) {
		Connection connection =null ;
		try {
			connection = connectionPool.getConnection();
			String query = "INSERT INTO devtech.customers_vs_coupons (customer_id, coupon_id) VALUES (?,?)";
			
			 PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1,customerID);
			 statement.setInt(2,couponID);
			 statement.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) {
		Connection connection =null ;
		try {
			connection = connectionPool.getConnection();
			String query = "DELETE FROM devtech.customers_vs_coupons WHERE (customer_id = ?) and (coupon_id = ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1,customerID);
			 statement.setInt(2,couponID);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
		
	}
	
	@Override
	public int getCouponIdByCompanyIDandTitle(int companyID , String title) {
		Connection connection = null;
		int id = 0;
		try {
			connection = connectionPool.getConnection();
			String sql = "SELECT * FROM devtech.coupons WHERE company_id = ? AND title = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, companyID);
			statement.setString(2, title);
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
	
	
	public void deletePurchasedCoupon(int couponId) {
	Connection connection=null;
	String query = "DELETE FROM devtech.customers_vs_coupons WHERE (coupon_id = '" + couponId + "');";
	try {
		connection = connectionPool.getConnection();
		connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeUpdate(query);
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	finally {
		if(connection != null)
		{
			connectionPool.releaseConnection(connection);
		}
	}
}
	
	
	@Override
	
public ArrayList<Coupon> getAllCouponsByCompany(int companyID) {
		Connection connection =null ;
		ArrayList<Coupon> listOfCoupons = new ArrayList<Coupon>();
		try{
			connection = connectionPool.getConnection();
			String query = "SELECT * FROM devtech.coupons WHERE company_id=" +companyID ;
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {

				
				listOfCoupons.add(new Coupon(
						resultSet.getInt("id"),
						resultSet.getInt("company_id"),
						Category.intToCategory(resultSet.getInt("category_id")),
						resultSet.getString("title"),
						resultSet.getString("description"),
						resultSet.getDate("start_date"),
						resultSet.getDate("end_date"),
						resultSet.getInt("amount"),
						resultSet.getDouble("price"),
						resultSet.getString("image")));
			}
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}finally {
			connectionPool.releaseConnection(connection);
		}
		return listOfCoupons;
	}
	
	@Override
	public void deleteAllPurByCouponId(int couponID) {

		Connection connection =null ;
		try {
			connection = connectionPool.getConnection();
			String query = "DELETE FROM devtech.customers_vs_coupons WHERE (coupon_id = ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1,couponID);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}
	
	public void deleteAllPurByCustomerId(int customerID) {
		Connection connection =null ;
		try {
			connection = connectionPool.getConnection();
			String query = "DELETE FROM devtech.customers_vs_coupons WHERE (customer_id = ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			 statement.setInt(1,customerID);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}
}
