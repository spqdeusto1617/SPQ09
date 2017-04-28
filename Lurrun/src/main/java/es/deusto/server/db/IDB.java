package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	
	  List<Game> getAllGames();
	  List<Game> getUserGames(String username);
	  boolean buyGame(User u, Game game);
	
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
	User	showUser	(String license);
	
	//void showGameInfo(String game,String company,String genre);
	//void showLicenseInfo(String user,String license,String game);
	
	
	
	License getGameFreeLicense(Game g);
	List<Game> getGamesFromDB();
}
