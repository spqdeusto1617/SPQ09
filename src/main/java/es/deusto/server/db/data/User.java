package es.deusto.server.db.data;

import java.io.Serializable;
import java.util.List;


import javax.jdo.annotations.*;

import java.util.ArrayList;

//@PersistenceCapable (detachable = "true")

@PersistenceCapable (detachable = "true")
public class User implements Serializable {
	/**
	 * User implements Serializable to be transferred to the RMI client
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	String login=null;
	String password=null;
	double money=0;
	

	boolean isSuperuser=false;

	@Persistent(defaultFetchGroup="true", mappedBy="user", dependentElement="true")
	@Join
	List<License> licenses = new ArrayList<License>();


	public User(String login, String password, boolean isSuperuser) {
		super();
		this.login = login;
		this.password = password;
		this.isSuperuser = isSuperuser;
		this.money=100;
	}

	public boolean getSuperuser() {
		return this.isSuperuser;
	}

	public void setSuperuser(boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}

	public User(String login, String password) {
		this.login = login;
		this.password = password;
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

	public String getLogin() {
		return this.login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	
	
	public boolean compareUserTo(User u2){
		if(this.login.equals(u2.getLogin())){
			if(this.password.equals(u2.getPassword())){
				return true;
			}
		}
		return false;
	}

	public String toString() {
		if (licenses.isEmpty()) {
			return "User: login --> " + this.login + ", password -->  " + this.password + ", Super User -->  " + this.isSuperuser;

		}else{
			StringBuffer licensesStr = new StringBuffer();
			for (License license: this.licenses) {
				licensesStr.append(license.toString() + " - ");
			}

			return "User: login --> " + this.login + ", password -->  " + this.password + ", Super User -->  " 
			+ this.isSuperuser + ", game licenses --> [" + licensesStr + "]";

		}


	}
}


