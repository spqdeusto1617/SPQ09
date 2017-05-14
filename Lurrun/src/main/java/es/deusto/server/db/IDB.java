package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	  boolean loginUser(User u);
	  boolean registerUser(User u);
	  boolean buyGame(String login, String game);
	  
	  List<Game> getAllGames();
	  List<Game> getUserGames(String login);
	  
	  boolean addLicenseToGame(Game g, License l);
	  boolean addGameToDb(Game g,Genre gg, Company c);
	
	  Game showGame(String name);
	  User showUser(String login);
}
