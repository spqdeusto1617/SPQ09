package es.deusto.server.db.data;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable 
public class License implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	private	String gameKey;
	Game game;
	User user;
	
	
	
	@Override
	public String toString() {
		return "License [gameKey=" + gameKey + "]";
	}
	public String getGameKey() {
		return gameKey;
	}
	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public License() {
		super();
		// TODO Auto-generated constructor stub
	}
	public License(String gameKey) {
		super();
		this.gameKey = gameKey;
	}
	
}
