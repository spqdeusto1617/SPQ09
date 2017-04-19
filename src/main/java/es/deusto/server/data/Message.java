package es.deusto.server.data;

import javax.jdo.annotations.PersistenceCapable;

import java.io.Serializable;
import java.util.Date;

@PersistenceCapable
public class Message implements Serializable {
	/**
	 * Messages will be transferred to the RMI client as part of a User
	 */
	private static final long serialVersionUID = 1L;
	User user=null;
	String text=null;
	long timestamp;
	
	
    public Message(String text) {
    	
        this.text = text;
		this.timestamp = System.currentTimeMillis();
    }

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return "Message: message --> " + this.text + ", timestamp -->  " + new Date(this.timestamp);
    }
}