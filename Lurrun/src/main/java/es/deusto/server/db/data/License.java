package es.deusto.server.db.data;

import java.io.Serializable;
import javax.jdo.annotations.*;



@PersistenceCapable (detachable = "true")
public class License implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private	String gameKey;
	
	@Persistent(defaultFetchGroup="true")
	private Game game;
	
	@Persistent(defaultFetchGroup="true")
	private	User user;

	private boolean isUsed;

	
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public License(String gameKey, boolean isUsed) {
		super();
		this.gameKey = gameKey;
		this.isUsed = isUsed;
	}
	@Override
	public String toString() {
		return "License --> gameKey=" + gameKey +" (Is used: "+ isUsed+ ")"  + "Game "+   game.getName() +  "]";
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
		this.isUsed = false;
	}

}
