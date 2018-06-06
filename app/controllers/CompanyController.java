package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.apache.log4j.Logger;

import models.*;

public class CompanyController extends Controller {
	
	//Főoldal:
	public static void mainPage(){
		
		//adatok összegyűjtése a táblákból
		List<Company> company = Company.findAll();
		List<Employee> employee = Employee.findAll();
		
		//"lekérdezés" létrehozása
		List<SelectedCompany> selectedCompany = new ArrayList<SelectedCompany>();
		for(Company comp : company){
			SelectedCompany selComp = new SelectedCompany();
			selComp.companyId = comp.companyId;
			selComp.companyName = comp.companyName;
			selComp.addressCity = comp.addressCity;
			selComp.countOfEmployee = 0;
			for(Employee emp : employee){ //count
				if(emp.owningCompany.equals(comp)){
					selComp.countOfEmployee++;
				}
			}
			selComp.sumOfSalary = 0;
			for(Employee emp : employee){ //sum
				if(emp.owningCompany.equals(comp)){
					selComp.sumOfSalary += emp.salary;
				}
			}
			selectedCompany.add(selComp);		
		}
		
		renderArgs.put("comp",selectedCompany);
		render("@Application.mainPage");
	}
	
	//Cég megtekintése:
	public static void companyDetails(Integer companyId){
		Company company = null;
		
		if (companyId != null){
			company = Company.findById(companyId);
		}
		
		if (company == null){ //ha nem találjuk a céget - vissza a főoldalra
			mainPage();
		} else {
			renderArgs.put("comp", company);

			List<String> errorList = new ArrayList<String>();
			/*
			 * Először hiányzónak tekintjük az összes munkakört-
			 */
				errorList.add("programozó");
				errorList.add("tesztelő");
				errorList.add("rendszergazda");
				errorList.add("manager");
				errorList.add("irodai adminisztrátor");
			
			List<Employee> emp = new ArrayList<Employee>();
			for(Employee employee : company.employeeList){
					emp.add(employee);
					if(employee.position.equals("Programozó")) errorList.remove("programozó");
					if(employee.position.equals("Tesztelő")) errorList.remove("tesztelő");
					if(employee.position.equals("Rendszergazda")) errorList.remove("rendszergazda");
					if(employee.position.equals("Manager")) errorList.remove("manager");
					if(employee.position.equals("Irodai adminisztrátor")) errorList.remove("irodai adminisztrátor");
				}	
			
			renderArgs.put("emp", emp); //alkalmazottak átadása
			renderArgs.put("error", errorList); //hiányzó posztok átadása
			render("@Application.companyDetails");
		}
	}
}