package models;

import java.sql.Date;
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
@Table(name = "employee")
public class Employee extends GenericModel{

	@Column(name="employee_name")
	public String employeeName;
	
	@Id
	@Column(name = "id_card_number")
	public String idCardNumber;
	
	@Column(name = "salary")
	public Integer salary;
	
	@Column(name = "date_of_birth")
	public Date dateOfBirth; //tudom Stringbe kellett volna, csak már későn vettem észre, hogy úgy írja a feladat :)
	
	@Column(name = "position")
	public String position;
	
	@ManyToOne
	@JoinColumn(name = "company_id",referencedColumnName="company_id")
	public Company owningCompany;
}
/*
CREATE TABLE employee (
    employee_name text NOT NULL,
    id_card_number text CHECK (length(id_card_number) = 8),
    salary integer,
    date_of_birth date,
    position text NOT NULL,
    company_id integer NOT NULL REFERENCES company (company_id),
    CONSTRAINT employee_pk PRIMARY KEY (id_card_number)
);
*/