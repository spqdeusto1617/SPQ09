package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.*;

public interface IDB {
	  boolean loginUser(User u);
	  boolean registerUser(User u);
	  boolean buyGame(String login, String game);
	  
	  List<Game> getAllGames();
	  List<Game> getUserGames(String login);
	  List<String> getAllCompanies();
	  List<String> getAllGenres();
	  boolean addGameToDb(Game g,Genre gg, Company c);
	  boolean isSuperUser(String login);
	
	  User showUser(String login);
	  Game showGame(String name);
	  Company showCompany(String name);
	  Genre showGenre(String name);
}
