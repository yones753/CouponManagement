package Facade;


import DataAccessObjects.*;


public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CouponsDAO couponsDAO;
	protected CustomersDAO customersDAO;
	
	public CompaniesDAO getCompaniesDAO() {
		return companiesDAO;
	}

	public CouponsDAO getCouponsDAO() {
		return couponsDAO;
	}

	public CustomersDAO getCustomersDAO() {
		return customersDAO;
	}


	
	public ClientFacade() {

		this.companiesDAO = new CompaniesDBDAO();
		this.couponsDAO = new CouponsDBDAO();
		this.customersDAO = new CustomersDBDAO();
	}


	public abstract boolean login(String email , String password);
		
	
}
