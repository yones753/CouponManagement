package Facade;

import java.util.ArrayList;
import JavaBeans.Category;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public class CustomerFacade extends ClientFacade {

	long millis = System.currentTimeMillis();
	java.sql.Date date = new java.sql.Date(millis);

	public CustomerFacade() {

	}

	private int customerID;

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	@Override
	public boolean login(String email, String password) {
		return customersDAO.isCustomerExists(email, password);
	}

	public void purchaseCoupon(Coupon coupon) {
		System.out.println("Coupon id before "+ coupon.getId());
		boolean flag = true;
//		Get all customers
		for (Customer customer : customersDAO.getAllCustomers()) {
//			Get specific customer
			if (this.getCustomerID() == customer.getId()) {
//				Get all coupon from specific customer
				for (Coupon cop : customer.getCoupons()) {
//					Check if have same coupon or amount = 0 or expired coupon 
					if (cop.getId() == coupon.getId() || cop.getAmount() == 0 || cop.getEndDate().before(date)) {
						flag = false;
						break;
					}
				}
			}

			if (flag) {
				System.out.println("purchased coupon in customer");
				
				couponsDAO.addCouponPurchase(this.getCustomerID(), coupon.getId());
				coupon.setAmount((coupon.getAmount() - 1));
				couponsDAO.updateCoupon(coupon);
			} else {
				System.out.println("Coupon can't be purchased");
			}
		}
	}

	public ArrayList<Coupon> getCustomerCoupons() {
		ArrayList<Coupon> listCoupon = new ArrayList<Coupon>();

		for (Customer customer : customersDAO.getAllCustomers()) {
			for (Coupon coupon : couponsDAO.getAllCoupons()) {
				if (getCustomerID() == customer.getId()) {
					listCoupon.add(coupon);
				}
			}

		}
		return listCoupon;
	}

	public ArrayList<Coupon> getCustomerCoupons(Category category) {
		ArrayList<Coupon> listOfCouponMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : this.getCustomerCoupons()) {
			if (coupon.getCategory() == category) {
				listOfCouponMaxPrice.add(coupon);
			}
		}
		return listOfCouponMaxPrice;
	}

	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {

		ArrayList<Coupon> listOfCouponMaxPrice = new ArrayList<Coupon>();
		for (Coupon coupon : this.getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				listOfCouponMaxPrice.add(coupon);
			}
		}
		return listOfCouponMaxPrice;
	}

	public Customer getCustomerDetails() {

		return customersDAO.getOneCustomer(customerID);

	}
	
	public int getCustomerIdByEmail(String email) {
		return customersDAO.getCustomerIdByEmail(email);
				
	}

}