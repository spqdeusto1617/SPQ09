package es.deusto.server.db.dao;

import es.deusto.server.db.data.*;
import java.util.List;

public interface IDAO {
	boolean storeUser(User u);
	User retrieveUser(String login);
	boolean updateUser(User u);

	boolean storeLicense(License u);
	License retrieveLicense(String gameKey);
	boolean updateLicense(License u);

	boolean storeGame(Game g);
	Game retrieveGame(String name);
	boolean updateGame(Game g);

	boolean storeCompany(Company c);
	Company retrieveCompany(String name);
	boolean updateCompany(Company c);

	boolean storeGenre(Genre g);
	Genre retrieveGenre(String name);
	boolean updateGenre(Genre g);

	
	
	
	
	
	Genre retrieveGenreByName(String name);
	Company retrieveCompanyByName(String name);
	Game retrieveGameByName(String name);
	License retrieveLicenseByName(String gameKey);
	

	//Get all games

	public List<Game> getAllGames();



}
