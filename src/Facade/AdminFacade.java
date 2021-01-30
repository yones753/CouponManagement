package Facade;


import java.util.ArrayList;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		
	}
	

	@Override
	public boolean login(String email, String password) {
		boolean flag = false;
		if ("admin@admin.com".equals(email) && "admin".equals(password)) {
			flag = true;
		}
		return flag;
	}

	public void addCompany(Company company) {
		boolean flag = true;
		for (Company comp : this.getAllCompanies()) {
			if (comp.getEmail().equals(company.getEmail()) || comp.getName().equals(company.getName())) {
				flag = false;
				break;
			}
		}
		if (flag) {
			this.companiesDAO.addCompany(company);
		} else {
			System.out.println("Can't add a company, You have the same company name or existing email");
		}
	}

	public void updateCompany(Company company) {
		boolean flag = false;
		for (Company comp : this.getAllCompanies()) {
			if (comp.getEmail().equals(company.getEmail()) || comp.getName().equals(company.getName())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			this.companiesDAO.updateCompany(company);
		} else {
			System.out.println("Can't update company");
		}
	}
	
	public void deleteCompany(int companyID) {

//		 Delete coupons from customers_vs_coupons
		for (Coupon coupon : couponsDAO.getAllCouponsByCompany(companyID)) {
			couponsDAO.deleteAllPurByCouponId(coupon.getId());
		}
		
//			delete all coupon from company
		for (Coupon coupon : couponsDAO.getAllCoupons()) {
			if (coupon.getCompanyId() == companyID) {
				couponsDAO.deleteCoupon(coupon.getId());
			}
		}
//			delete the company
		
		  companiesDAO.deleteCompany(companyID);
		  System.out.println("Delete company");
	}
	
	public ArrayList<Company> getAllCompanies() {
		return companiesDAO.getAllCompanies();
	}

	public Company getOneCompany(int companyID) {
		
		return companiesDAO.getOneCompany(companyID);

	}

	public void addCustomer(Customer customer) {
		boolean flag = true;
		for (Customer cust : customersDAO.getAllCustomers()) {
			if (cust.getEmail().equals(customer.getEmail())) {
				flag = false;
				break;
			}
		}
		if (flag) {
			customersDAO.addCustomer(customer);
		}
		else {
			System.out.println("Can't add this customer , Email already exists");
		}
	}
	
	public void updateCustomer(Customer customer) {
		boolean flag = false;
		for (Customer cust : customersDAO.getAllCustomers()) {
			if (cust.getEmail().equals(customer.getEmail())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			customersDAO.updateCustomer(customer);
		}
		else {
			System.out.println("Can't update this customer , existing email");
		}
	}
	
	public void deleteCustomer(int customerID) {

//    	Delete customer from customers_vs_coupons

		couponsDAO.deleteAllPurByCustomerId(customerID);

//    	Delete customer
		customersDAO.deleteCustomer(customerID);
		System.out.println("Delete customer");
	}
	
	public ArrayList<Customer> getAllCustomers(){
		return customersDAO.getAllCustomers();
	}
	
	public Customer getOneCustomer(int customerID) {
		return customersDAO.getOneCustomer(customerID);
	}
	
	public int getCompanyIdByName(String name){
		return companiesDAO.getCompanyIdByName(name);
	}
	
	public int getCustomerIdByEmail(String email) {
		return customersDAO.getCustomerIdByEmail(email);
	}
}
