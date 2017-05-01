package es.deusto.server.db.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.dao.IDAO;

import javax.jdo.annotations.*;


//@PersistenceCapable (detachable = "true")

@PersistenceCapable (detachable = "false")
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private	String name;
	
	private double price;
	private double discount;

	@Persistent(defaultFetchGroup="true")
	Genre genre;
	
	@Persistent(defaultFetchGroup="true")
	Company company;


	@Persistent(defaultFetchGroup="true", mappedBy="game", dependentElement="true")
	@Join
	List<License> licenses = new ArrayList<License>();

	public Game(String name, double price, double discount) {
		this.name = name;
		this.price = price;
		this.discount = discount;
	}

	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void addLicense(License license) {
		licenses.add(license);
	}

	public void removeLicense(License license) {
		licenses.remove(license);
	}

	public List<License> getLicenses() {
		 return this.licenses;
	}

	public License getFirstFreeLicense(){
		License license = this.licenses.get(0);
		licenses.remove(0);
		IDAO dao= new DAO();
		dao.updateGame(this);
		return license;
	}
	
	public String toString() {
		
		return "Game [name=" + this.name + ", price=" + this.price + ", discount=" 
				 + this.discount  + "]";
	}

}
