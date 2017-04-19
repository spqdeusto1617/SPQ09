package es.deusto.server.dao;

import es.deusto.server.data.*;
import java.util.List;

public interface IUserDAO {
	void storeUser(User u);
	User retrieveUser(String login);
	void updateUser(User u);
	
	void storeGame(Game g);
	Game retrieveGame(String name);
	void updateGame(Game g);
	
	void storeCompany(Company c);
	Company retrieveCompany(String name);
	void updateCompany(Company c);
	
	void storeGenre(Genre g);
	Genre retrieveGenre(String name);
	void updateGenre(Genre g);
	

	//Get all games
	
	public List<Game> getAllGames();
	   
	

}