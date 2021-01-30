package Facade;


import java.util.ArrayList;



import JavaBeans.Category;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public class CompanyFacade extends ClientFacade {

	public CompanyFacade() {

	
	}
	
	private int companyID;

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	
	
	@Override
	public boolean login(String email, String password) {
		if(companiesDAO.isCompanyExists(email, password))
		{
			companyID=companiesDAO.getCompanyIdByEmail(email);//<=CompanyID
			return true;
		}
		else
		{
			return false;
		}
		
		
	}

	public void addCoupon(Coupon coupon) {
		
		boolean flag = true;
		for (Coupon coupo : couponsDAO.getAllCoupons()) {
			if(coupo.getCompanyId() == coupon.getCompanyId()) {
				if(coupo.getTitle().equals(coupon.getTitle())) {
					flag = false;
					break;
				}
			}
		}
		if (flag) {
			couponsDAO.addCoupon(coupon);
		}else {
			System.out.println("Company have "+coupon.getTitle()+" name coupon !!");
		}
	}

	public void updateCoupon(Coupon coupon) {
		
				couponsDAO.updateCoupon(coupon);
	}
	
	public void deleteCoupon(int couponID) {

//		Delete coupon from vs
		for (Customer customer : customersDAO.getAllCustomers()) {
			for (Coupon coupon : customer.getCoupons()) {
				if (coupon.getId() == couponID) {
					couponsDAO.deleteCouponPurchase(customer.getId(), couponID);
					System.out.println("Deleted coupon successfully!");
				}else {
					System.out.println("Company does not exists!");
				}
			}
		}
//		Delete coupon from company 
		couponsDAO.deleteCoupon(couponID);
		System.out.println("Delete coupon");
	}

	public ArrayList<Coupon> getCompanyCoupons() {
		ArrayList<Coupon> listCompanyCoupons = new ArrayList<Coupon>();

		for(Coupon coupon : couponsDAO.getAllCoupons()) {
//			 Only get a coupons specific company
		
			if(getCompanyID() == coupon.getCompanyId()) {
				listCompanyCoupons.add(coupon);
				
			}
		}
		return listCompanyCoupons;
	}

	public ArrayList<Coupon> getCompanyCoupons(Category category) {

		ArrayList<Coupon> listOfCouponCategory = new ArrayList<Coupon>();
//		get only coupons = category specific company
		for (Coupon coupon : this.getCompanyCoupons()) {
			if (coupon.getCategory() == category) {
				listOfCouponCategory.add(coupon);
			}
		}
		return listOfCouponCategory;
	}
	
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {

		ArrayList<Coupon> listOfCouponMaxPrice = new ArrayList<Coupon>();
//		get only coupons = price specific company
		for (Coupon coupon : this.getCompanyCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				listOfCouponMaxPrice.add(coupon);
			}
		}
		return listOfCouponMaxPrice;
	}

	public Company getCompanyDetails() {	
		return  companiesDAO.getOneCompany(companyID);
	}
	public int getCompanyIdByName(String name) {
		return companiesDAO.getCompanyIdByName(name);
	}
	public int getCompanyIdByEmail(String email) {
		return companiesDAO.getCompanyIdByEmail(email);
	}
	
	public int getCouponIdByCompanyIDandTitle(int companyID , String title) {
		
		return couponsDAO.getCouponIdByCompanyIDandTitle(companyID, title);
	}
}
