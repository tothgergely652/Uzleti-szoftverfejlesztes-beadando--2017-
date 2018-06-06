package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

public class SelectedCompany{
/**
 * Főoldalon megjelenítendő adatok lekérése
 */
	public Integer companyId;
	
	public String companyName;
	
	public String addressCity;
	
	public Integer countOfEmployee;
	
	public Integer sumOfSalary;
}