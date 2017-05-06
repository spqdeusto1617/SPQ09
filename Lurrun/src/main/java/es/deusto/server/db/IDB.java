package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	
	  List<Game> getAllGames();
	  List<User> getAllUsers();
	  List<Game> getUserGames(String login);
	  
	  boolean buyGame(String login, String game);
	  boolean registerUser(User u);

	  boolean addGameToDb(Game g,Genre gg, Company c);
	  boolean addLicenseToUser(User u, License l);	
	  boolean addLicenseToGame(Game g, License l);
	
	  Genre showGenre (String name);
	  Game showGame	(String name);
	  License showLicense	(String gameKey);
	  Company	showCompany	(String name);
	  User	showUser	(String login);
	  
	  Game  showGameByParam(String name);
	  Company	showCompanyByParam(String name);
	  Genre 	showGenreByParam(String name);
//	  License showLicenseByParam(String gameKey);
		
}
