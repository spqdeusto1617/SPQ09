package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	
	  List<Game> getAllGames();
	  List<Game> getUserGames(String username);
	  boolean buyGame(String u, String game);
	
	//Make methods private 
//	String sayMessage(String login, String password, String message);
	boolean registerUser(String login, String password,boolean isSuperUser);
//	User getUserMessages(String login);
	boolean addGameToDb(Game g,Genre gg, Company c);
	boolean addLicenseToUser(User u, License l);
	
	boolean addLicenseToGame(Game g, License l);
	
	Genre showGenre (String name);
	Game showGame	(String name);
	License showLicense	(String gameKey);
	Company	showCompany	(String name);
	User	showUser	(String login);
	
	//void showGameInfo(String game,String company,String genre);
	//void showLicenseInfo(String user,String license,String game);
	
	
	
	
}
