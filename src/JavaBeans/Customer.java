package JavaBeans;

import java.util.ArrayList;

public class Customer {

	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private static ArrayList<Coupon> coupons ;
	
	
//------------------------------------	Constructors ------------------------------------------------------	
	public Customer() {
		
	}
	
public Customer(String firstName, String lastName, String email, String password) {
	
	
	    this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();
	}

	public Customer(int id, String firstName, String lastName, String email, String password) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = new ArrayList<Coupon>();

	}



//------------------------------------	Getters && Setters ------------------------------------------------------	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}


//------------------------------------	toString ------------------------------------------------------
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}
	
	
	
	
	
	
}
