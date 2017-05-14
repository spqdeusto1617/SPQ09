package es.deusto.server.db.dao;

import es.deusto.server.db.data.*;
import java.util.List;

public interface IDAO {

	boolean storeUser(User u);
	User retrieveUser(String login);
	boolean updateUser(User u);

	//boolean storeLicense(License l);
	License retrieveLicense(String gameKey);
	boolean updateLicense(License u);

	boolean storeGame(Game g);
	Game retrieveGame(String name);
	boolean updateGame(Game g);

//	boolean storeCompany(Company c);
	Company retrieveCompany(String name);
	boolean updateCompany(Company c);

//	boolean storeGenre(Genre g);
	Genre retrieveGenre(String name);
	boolean updateGenre(Genre g);


	Genre retrieveGenreByParameter(String name);
	Company retrieveCompanyByParameter(String name);
	Game retrieveGameByParameter(String name);
	//License retrieveLicenseByParameter(String gameKey);


	//Get all games

	public List<Game> getAllGames();
	public License getFirstLicense(String name);
	public List<User> getAllUsers();


}
