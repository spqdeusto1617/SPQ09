package es.deusto.server.db.dao;

import es.deusto.server.db.data.*;
import java.util.List;

public interface IDAO {

	boolean storeUser(User u);
	User retrieveUser(String login);
	boolean updateUser(User u);

	License retrieveLicense(String gameKey);
	boolean updateLicense(License u);

	boolean storeGame(Game g);
	Game retrieveGame(String name);
	boolean updateGame(Game g);

	Company retrieveCompany(String name);
	boolean updateCompany(Company c);

	Genre retrieveGenre(String name);
	boolean updateGenre(Genre g);
	
	List<Game> getAllGames();
	License getFirstLicense(String name);

//	Genre retrieveGenreByParameter(String name);
//	Company retrieveCompanyByParameter(String name);
//	Game retrieveGameByParameter(String name);
}
