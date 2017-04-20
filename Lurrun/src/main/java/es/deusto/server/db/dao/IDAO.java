package es.deusto.server.db.dao;

import es.deusto.server.db.data.*;
import java.util.List;

public interface IDAO {
	void storeUser(User u);
	User retrieveUser(String login);
	void updateUser(User u);
	
	void storeLicense(License u);
	License retrieveLicense(String gameKey);
	void updateLicense(License u);
	
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
