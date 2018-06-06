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

@Entity
@Table(name = "company")
public class Company extends GenericModel{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="company_id")
	public Integer companyId;
	
	@Column(name = "company_name")
	public String companyName;
	
	@Column(name = "tax_number")
	public String taxNumber;
	
	@Column(name = "address_city")
	public String addressCity;
	
	@Column(name = "address_postcode")
	public Integer addressPostcode;
	
	@Column(name = "address_street")
	public String addressStreet;
	
	@Column(name = "address_housenumber")
	public String addressHouseNumber;
	
	@OneToMany(mappedBy="owningCompany")
	public List<Employee> employeeList;
}
/*
CREATE TABLE company (
	company_id SERIAL,
	company_name text NOT NULL,
	tax_number text CHECK (length(tax_number) = 11),
	address_city text NOT NULL,
	address_postcode integer CHECK (address_postcode>1000),
	address_street text NOT NULL,
	address_housenumber text NOT NULL,
	CONSTRAINT company_pk PRIMARY KEY (company_id)
);
*/