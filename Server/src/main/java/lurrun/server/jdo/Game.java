package lurrun.server.jdo;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import org.datanucleus.enhancement.*;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import java.io.Serializable;
import java.util.*;


@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Game extends Company {
	
	private	String name;
	private double price;
	private double discount;
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
	@Override
	public String toString() {
		return "Game [name=" + name + ", price=" + price + ", discount=" + discount + "final price:"+ price*discount +"]";
	}
	public Game(String name, double price, double discount) {
		this.name = name;
		this.price = price;
		this.discount = discount;
	}
		
		
	public Game(){
		
	}
	

}


