package DataAccessObjects;

import java.util.ArrayList;

import JavaBeans.Company;

public interface CompaniesDAO {

	public boolean isCompanyExists(String email ,String password);
	public void addCompany(Company company);
	public void updateCompany(Company company);
	public void deleteCompany(int companyID);
	public ArrayList<Company> getAllCompanies();
	public Company getOneCompany(int copmanyID);

//	get id from DB
	public int getCompanyIdByName(String name);
	int getCompanyIdByEmail(String email);
	
	
	

}
