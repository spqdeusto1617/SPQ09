package es.deusto.server.db.data;

import java.io.Serializable;
import java.util.List;


import javax.jdo.annotations.*;

import java.util.ArrayList;

@PersistenceCapable (detachable = "true")
public class User implements Serializable {
	/**
	 * User implements Serializable to be transferred to the RMI client
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	String login=null;
	String password=null;
	public User(String login, String password, boolean isSuperuser) {
		super();
		this.login = login;
		this.password = password;
		this.isSuperuser = isSuperuser;
	}

	boolean isSuperuser=false;
	
	@Persistent(defaultFetchGroup="true", mappedBy="user", dependentElement="true")
	@Join
	List<Message> messages = new ArrayList<Message>();

	
	
	


	public boolean isSuperuser() {
		return isSuperuser;
	}

	public void setSuperuser(boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}


	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
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
	
	 public List<Message> getMessages() {
		 return this.messages;
		 
	}
	 
	 public String toString() {
		 if (messages.isEmpty()) {
			 return "User: login --> " + this.login + ", password -->  " + this.password + ", Super User -->  " + this.isSuperuser;
			 
		 } else {
			 StringBuffer messagesStr = new StringBuffer();
				for (Message message: this.messages) {
					messagesStr.append(message.toString() + " - ");
				}
			
		        return "User: login --> " + this.login + ", password -->  " + this.password + ", Super User -->  " + this.isSuperuser + ", messages --> [" + messagesStr + "]";
	 
		 }
	 }
}

