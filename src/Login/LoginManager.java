package Login;


import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;

public class LoginManager {

	private static LoginManager login = null;

	public static LoginManager getInstance() {
		if (login == null) {
			login = new LoginManager();
		}
		return login;
	}

	public LoginManager() {

	}

	public ClientFacade login(String email, String password, ClientType clientType) {

		switch (clientType) {

		case Administrator: {

			ClientFacade admin = new AdminFacade();
			if (admin.login(email, password) && clientType == ClientType.Administrator) {
				
				return admin;
			} else {
				return null;
			}
		} 
		case Company: {
			ClientFacade company = new CompanyFacade();
			if (company.login(email, password) && clientType == ClientType.Company) {
				return company;
			} else {
				return null;
			}
		}
		case Customer: {
			ClientFacade customer = new CustomerFacade();
			if (customer.login(email, password) && clientType == ClientType.Customer) {
				return customer;
			} else {
				return null;
			}

		}
		}
		return null;
	}
}
		
		

		
	
