package controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.Company;
import models.Employee;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import util.ValidationUtil;

public class CreationController extends Controller {
	
	//Cég hozzáadása:
	public static void createCompanyForm(){
		render("@Application.createCompany"); //'cég létrehozása' képernyőre átirányítás
	}

	public static void createCompany(@Required(message = "A név megadása kötelező!") String companyName, 
									 @Required(message = "Az adószám megadása kötelező!")String taxNumber, 
									 @Required(message = "A város megadása kötelező!") String addressCity,
									 @Required(message = "Az irányítószám megadása kötelező!") String addressPostcode,
									 @Required(message = "Az utca megadása kötelező!") String addressStreet,
									 @Required(message = "A házszám megadása kötelező!") String addressHouseNumber){
		
		//irányítószám validáció
		Integer postCode = null;
		if (!ValidationUtil.isInteger(addressPostcode)){ 
			validation.addError("addressPostcode", "Az írányítószám nem lehet szöveges adat!");
		} else {
			postCode = Integer.valueOf(addressPostcode);
			
			if (postCode >= 10000 || postCode < 1000){
				validation.addError("addressPostcode", "Az irányítószám 1000 és 9999 közötti szám lehet!"); 
			}
		}
		
		//adószám validáció
		String taxNum = "";
		if (!ValidationUtil.isTaxNumber(taxNumber)){ 
			validation.addError("taxNumber", "Az adószám első 3 karaktere csak szám lehet és 11 karakter hosszúnak kell lennie!");
		} else {
			taxNum = taxNumber;
		}
		
		Company company = Company.find(" taxNumber = ?", taxNumber).first();
		if (company != null){
			validation.addError("taxNumber", "Ilyen adószámmal már létezik cég!");
		}
		
		//házszám validáció
		String houseNum = "";
		if (!ValidationUtil.isHouseNumber(addressHouseNumber)){ 
			validation.addError("addressHouseNumber", "A házszámnak kell számot tartalmaznia!");
		} else {
			houseNum = addressHouseNumber;
		}
				
		if (validation.hasErrors()){
			params.flash();
			render("@Application.createCompany");
		} else {
			company = new Company();
			company.companyName = companyName;
			company.taxNumber = taxNum;
			company.addressCity = addressCity;
			company.addressPostcode = postCode;
			company.addressStreet = addressStreet;
			company.addressHouseNumber = houseNum;
			company.save();
			
			CompanyController.mainPage();
		}
		
	}
	
	//Alkalmazott hozzáadása:
	@Before
	public static void preparePage(){ //előkészítés - alkalmazott beviteléhez szükséges adatok átadása
		List<Company> company = Company.findAll();
		renderArgs.put("company", company);
		
		List<String> select = new ArrayList<String>();
		//select-ben megjelenő option-ok
			select.add("Programozó");
			select.add("Tesztelő");
			select.add("Rendszergazda");
			select.add("Manager");
			select.add("Irodai adminisztrátor");
		renderArgs.put("select", select);	
	}
	
	public static void addEmployeeForm(){
		List<Company> company = (List<Company>) renderArgs.get("company");
		if (company.size() == 0) {
			flash.put("errorMessage", "Nincsenek alkalmazottak!");
			CompanyController.mainPage();
		} else {
			render("@Application.addEmployee");
		}
	}

	public static void addEmployee( @Required(message = "A cég választása kötelező!") Integer companyId,
								    @Required(message = "A személyigazolvány szám megadása kötelező!") String idCardNumber, 
									@Required(message = "A név megadása kötelező") String employeeName,
									@Required(message = "A fizetés megadása kötelező") String salary,
									@Required(message = "A születési dátum megadása kötelező") String dateOfBirth,
									@Required(message = "A feladatkör megadása kötelező") String position
									) throws ParseException{
		
		//személyigazolvány validáció
		String idCard = "";
		if (!ValidationUtil.isIdCard(idCardNumber)){ 
			validation.addError("idCardNumber", "Hibás személyigazolvány szám!");
		} else {
			idCard = idCardNumber;
		}
		
		Employee emp = Employee.find(" idCardNumber = ?", idCardNumber).first();
		if (emp != null){
			validation.addError("idCardNumber", "Ilyen személyigazolvány számmal már létezik alkalmazott!");
		}
		
		//fizetés validáció
		Integer sal = null;
		if (!ValidationUtil.isInteger(salary)){ 
			validation.addError("salary", "A fizetés csak számformátumó lehet!");
		} else {
			sal = Integer.valueOf(salary);
			
			if (sal < 100000){
				validation.addError("salary", "A fizetés minimum 100000 Ft kell legyen!"); 
			}
		}
		
		//születési dátum validáció
		java.util.Date birthDate = null;
		if (!ValidationUtil.isBirthDate(dateOfBirth)){ 
			validation.addError("dateOfBirth", "Hibásan adtad meg a dátumot! (yyyy-MM-dd)");
		} else {
			birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
		}
		
		if (validation.hasErrors()){
			params.flash();
			render("@Application.addEmployee");
		} else {
			
			Employee employee = new Employee();
			employee.employeeName = employeeName;
			employee.idCardNumber = idCard;
			employee.salary = sal;
			employee.dateOfBirth = new java.sql.Date(birthDate.getTime()); 
			employee.position = position;
			employee.owningCompany = Company.findById(companyId);
			employee.save();
			
			CompanyController.companyDetails(companyId);
		}
	}
}