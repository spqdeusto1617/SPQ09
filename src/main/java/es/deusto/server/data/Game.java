package es.deusto.server.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.*;

@PersistenceCapable 
public class Game implements Serializable {
private static final long serialVersionUID = 1L;
private	String name;
private double price;
private double discount;

Genre genre;


Company company;




@Override
public String toString() {
	return "Game [name=" + name + ", price=" + price + ", discount=" + discount +  "]";
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


public Game(String name, double price, double discount) {
	this.name = name;
	this.price = price;
	this.discount = discount;
}
	
	
public Game(){
	
}
	

}
