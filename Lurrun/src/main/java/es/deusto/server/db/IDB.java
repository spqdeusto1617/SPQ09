package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	
	public static List<Game> getAllGames();
	public static List<Game> getUserGames(String username);
	public static boolean buyGame(User u, Game game);
	
	//Make methods private 
//	String sayMessage(String login, String password, String message);
	void registerUser(String login, String password,boolean isSuperUser);
//	User getUserMessages(String login);
	void addStufToDb(Game g,Genre gg, Company c);
	void addLicenseToUser(User u, License l);
	
	void addLicenseToGame(Game g, License l);
	void showGameInfo(String game,String company,String genre);
	void showLicenseInfo(String user,String license,String game);
	Licence getGameFreeLicense(Game g);
	List<Game> getGamesFromDB();
}
