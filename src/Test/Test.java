package Test;


import java.util.ArrayList;

import ConnectionUtils.ConnectionPool;
import DataAccessObjects.*;
import Facade.*;
import JavaBeans.*;
import Login.*;


public class Test {
	
	
	public static void testAll() {
		DBTestcleaner();
		adminTest();
		companyTest();
		customerTest();
		ConnectionPool.getInstance().closeAllConnections();
	}

	
	static ArrayList<Coupon> getAllCoupons() {
		CouponsDBDAO couponsDAO = new CouponsDBDAO();
		return couponsDAO.getAllCoupons();
	}
	
	 
		public static void DBTestcleaner() {
			
	        CouponsDAO couponsDAO = new CouponsDBDAO();
	        CustomersDAO customersDAO = new CustomersDBDAO();
	        CompaniesDAO companiesDAO = new CompaniesDBDAO();
	        for(int i = 0; i<=1000;i++)
	        {
	            couponsDAO.deleteCoupon(i);
				customersDAO.deleteCustomer(i);
				companiesDAO.deleteCompany(i);
	        }
	        System.out.println("Delete All in DB");

	    }

		
		public static void adminTest() {
			
			System.out.println("---------------------------------------Administrator Test----------------------------------------------");
			
			LoginManager loginManager = LoginManager.getInstance();
			// login 
			ClientFacade client = loginManager.login("admin@admin.com", "admin",ClientType.Administrator);
			AdminFacade adminFacade = (AdminFacade)client;
			
			
			
			// addCompany
			Company Microsoft = new Company("Microsoft", "microsoft@gmail.com", "microsoft12345");
			Company Google = new Company("Google", "google@gmail.com", "google12345");
			System.out.println("Add 2 companies : ");
			adminFacade.addCompany(Google);
			adminFacade.addCompany(Microsoft);
	        Microsoft.setId(adminFacade.getCompanyIdByName(Microsoft.getName()));
	        Google.setId(adminFacade.getCompanyIdByName(Google.getName()));
	        
		
			
		    // update company
	        System.out.println("Update password in microsoft :");
	        Microsoft.setPassword("micros");
			adminFacade.updateCompany(Microsoft);
			
			// deleteCompany
			System.out.println("Delete google :");
			adminFacade.deleteCompany(Google.getId());

			// getAllCompanies
			System.out.println("Get all companies :");
			for(Company company :adminFacade.getAllCompanies()) {
				System.out.println(company.toString());
			}
			
			//getOneCompany(id)
			System.out.println("Get company by id :");
			System.out.println(adminFacade.getOneCompany(Microsoft.getId()).toString());
			
			//addCustomer
	        Customer Yones = new Customer("Yones", "Derawe", "yones@gmail.com", "yones12345");
	        Customer koko = new Customer("Koko", "Boom", "koko@gmail.com", "koko12345");
	        System.out.println("Add to customer :");
			adminFacade.addCustomer(Yones);
			adminFacade.addCustomer(koko);
			Yones.setId(adminFacade.getCustomerIdByEmail(Yones.getEmail()));
			koko.setId(adminFacade.getCustomerIdByEmail(koko.getEmail()));
			
			//updateCustomer
			System.out.println("Update customer :");
			Yones.setFirstName("Yonesss");
			Yones.setPassword("yones112345");
			adminFacade.updateCustomer(Yones);
			
			//deleteCustomer
			System.out.println("Delete customer :");
			adminFacade.deleteCustomer(koko.getId());

			//getAllCustomers
			System.out.println("Get all customers :");
			for(Customer customer : adminFacade.getAllCustomers()) {
				System.out.println(customer.toString());
			}
			
			//getOneCustomer
			System.out.println("Get customer by id :");
			System.out.println(	adminFacade.getOneCustomer(Yones.getId()));
			
		}
		
		
		public static void companyTest() {
			System.out.println(	"---------------------------------------Company Test----------------------------------------------");

			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);

			LoginManager loginManager = LoginManager.getInstance();
			Company Microsoft = new Company("Microsoft", "microsoft@gmail.com", "micros");
			// login
			ClientFacade client = loginManager.login(Microsoft.getEmail(), Microsoft.getPassword(), ClientType.Company);
			
			CompanyFacade companyFacade = (CompanyFacade) client;
			Microsoft.setId(companyFacade.getCompanyIdByEmail(Microsoft.getEmail()));
			companyFacade.setCompanyID(Microsoft.getId());
			
			
			// addCoupon
			
			 PC = new Coupon(companyFacade.getCompanyID(), Category.Electricity,
					"PCmacPro", "The best", date, date, 10, 7000.0, "temp");
			 
			 Coupon Burger = new Coupon(companyFacade.getCompanyID(), Category.Food,
						"PCmacPro", "The best", date, date, 10, 7000.0, "temp");
			 
			Coupon  HD = new Coupon(companyFacade.getCompanyID(), Category.Food, "HD",
					"The best", date, date, 10, 5000.0, "temp");	
			
			
			System.out.println("Add 3 coupons :");
			companyFacade.addCoupon(PC);
			companyFacade.addCoupon(HD);
			companyFacade.addCoupon(Burger);
			PC.setId(companyFacade.getCouponIdByCompanyIDandTitle(PC.getCompanyId(), PC.getTitle()));
			HD.setId(companyFacade.getCouponIdByCompanyIDandTitle(PC.getCompanyId(), PC.getTitle()));
			Burger.setId(companyFacade.getCouponIdByCompanyIDandTitle(Burger.getCompanyId(), Burger.getTitle()));
	
			
			
			// updateCoupon
			System.out.println("Update PC coupon :");
			PC.setEndDate(new java.sql.Date(2025-1900,2,1));
			PC.setAmount(600);
			companyFacade.updateCoupon(PC);

			// deleteCoupon
			System.out.println("Delete coupon HD :");
			companyFacade.deleteCoupon(HD.getId()); 

			// getCompanyCoupons()
			System.out.println("Get All company coupon  :");
	    	for(Coupon coupon :companyFacade.getCompanyCoupons()) {
	    		System.out.println(coupon.toString());
	    	}

			// getCompanyCoupons(Category)
	    	System.out.println("Get All company coupon  where category = Electricity :");
			for (Coupon coupon : companyFacade.getCompanyCoupons(Category.Electricity)) {
				System.out.println(coupon.toString());
			}

			// getCompanyCoupons(maxPrice)
			System.out.println("Get All company coupon where max price 6000 :");
			for (Coupon coupon : companyFacade.getCompanyCoupons(6000)) {
				System.out.println(coupon.toString());
			}
			
			//getCompanyDetails
			System.out.println("Get company details :");
			System.out.println(companyFacade.getCompanyDetails().toString());

		}
		
		public static Coupon PC = null;

		public static void customerTest() {
			System.out.println("---------------------------------------Customer Test----------------------------------------------");

			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			
			
			LoginManager loginManager = LoginManager.getInstance();
			
			// login
			ClientFacade client = loginManager.login("yones@gmail.com", "yones112345", ClientType.Customer);
			CustomerFacade customerFacade = (CustomerFacade) client;
			customerFacade.setCustomerID(customerFacade.getCustomerIdByEmail("yones@gmail.com"));
			
			// purchaseCoupon
			 System.out.println("Customer Yones by PC coupon :");
			customerFacade.purchaseCoupon(PC);
//			CompanyFacade companyFacade = new CompanyFacade();
			

			

			// getCustomerCoupons()
			 System.out.println("Customer Yones , Get all coupons :");
			for (Coupon coupon : customerFacade.getCustomerCoupons()) {
				System.out.println(coupon.toString());
			}

			// getCustomerCoupons(Category)
			 System.out.println("Customer Yones , Get all coupons where category =  Electricity :");
			for (Coupon coupon : customerFacade.getCustomerCoupons(Category.Electricity)) {
				System.out.println(coupon.toString());
			}

			// getCustomerCoupons(maxPrice)
			 System.out.println("Customer Yones , Get all coupons where max price  =  1000 :");
			for (Coupon coupon : customerFacade.getCustomerCoupons(1000)) {
				System.out.println(coupon.toString());
			}
			
			// getCustomerDetails
			 System.out.println("Customer Yones get details :");
			System.out.println(customerFacade.getCustomerDetails().toString());

			
		}
		
		
	
	
}
