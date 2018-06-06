package controllers;

import models.Company;
import models.Employee;
import play.mvc.Controller;

public class DeleteController extends Controller {

	//Cég törlése:
	public static void deleteCompany(Integer companyId){
		String errorMessage = null;
		if (companyId != null){
			Company company = Company.findById(companyId);
			if (company != null){
				for (Employee employee : company.employeeList){
					employee.delete();
				}
				company.delete();
			} else {
				errorMessage = "A törlendő cég nem létezik!";
			}
		} else {
			errorMessage = "Adja meg a cégazonosítót!";
		}
		
		if (errorMessage != null){
			flash.put("errorMessage", errorMessage);
		}
		
		CompanyController.mainPage();
	}
	
	//Alkalmazott törlése:
	public static void employeeDelete(String idCardNumber){
		String errorMessage = null;
		Company owningCompany = null;
		if (idCardNumber != null){
			Employee employee = Employee.findById(idCardNumber);
			if (employee != null){
				owningCompany = employee.owningCompany;
				employee.delete();
			} else {
				errorMessage = "A törlendő alkalmazott nem létezik!";
			}
		} else {
			errorMessage = "Nincs személyigazolványszám!";
		}
		
		if (errorMessage != null){
			flash.put("errorMessage", errorMessage);
		}
		
		if (owningCompany == null){
			CompanyController.mainPage();
		} else {
			CompanyController.companyDetails(owningCompany.companyId);
		}
	}
}
