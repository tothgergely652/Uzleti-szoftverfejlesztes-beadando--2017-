///Hibrnate
CREATE SEQUENCE public.hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

///Adatbázis létrehozása
CREATE DATABASE "Beadando"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Hungarian_Hungary.1250'
    LC_CTYPE = 'Hungarian_Hungary.1250'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

///Táblák
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

CREATE TABLE employee (
    employee_name text NOT NULL,
    id_card_number text CHECK (length(id_card_number) = 8),
    salary integer,
    date_of_birth date,
    position text NOT NULL,
    company_id integer NOT NULL REFERENCES company (company_id),
    CONSTRAINT employee_pk PRIMARY KEY (id_card_number)
);

///Reset
DROP TABLE employee;
DROP TABLE company;
DROP DATABASE "Beadando";