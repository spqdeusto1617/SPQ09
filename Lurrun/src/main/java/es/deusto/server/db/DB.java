package es.deusto.server.db;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.server.db.dao.IDAO;

import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.data.*;

public class DB implements IDB {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	IDAO dao;
	final Logger logger = LoggerFactory.getLogger(DB.class);
	public DB(){
		super();
		dao = new DAO();
	}

	public DB(IDAO udao) {
		super();
		dao = udao;
	}

	public  List<Game> getUserGames(String username) {
		User u = showUser(username);
		List <Game> gameList = new ArrayList<>();
		for (License l : u.getLicenses() ) {
			gameList.add(l.getGame());
            }
		return gameList;
	}

	public  boolean buyGame(String username, String name) {
		User u = showUser(username);
		Game g = showGame(name);
		License l= dao.getFirstLicense(name);
	
		l.setUsed(true);	
		
		dao.updateLicense(l);
		dao.updateGame(g);
	
		addLicenseToUser(u, l);
		return true;
	}

	public boolean registerUser(User u) {
		User user = null;
		boolean ret=true;

		try {
			user = dao.retrieveUser(u.getLogin());
		} catch (Exception  e) {
			logger.error("Exception launched: " + e.getMessage());
			ret=false;
		}

		if (user != null) {
			user.setPassword(u.getPassword());
			user.setSuperuser(u.getSuperuser());
		
			dao.updateUser(user);
		} else {
			dao.storeUser(u);
		}
		return ret;
	}

	public boolean addGameToDb(Game g,Genre gg, Company c)  {
		Game game = null;
		Genre genre = null;
		Company company = null;
		boolean ret=true;

		game  = dao.retrieveGame(g.getName());
		genre = dao.retrieveGenre(gg.getName());
		company = dao.retrieveCompany(c.getName());

		if (game != null) {
			ret = false; 
			
			
		} else if (genre != null && company == null){
			g.setCompany(c);
			g.setGenre(genre);

			genre.addGame(g);
			c.addGame(g);

			dao.updateGenre(genre);
			//dao.storeGame(g);
		} else if (genre != null && company != null){
			g.setCompany(company);
			g.setGenre(genre);

			genre.addGame(g);
			company.addGame(g);

			//dao.updateGenre(genre);
			//dao.updateCompany(company);
			 dao.storeGame(g);
		} else if (genre == null && company != null){
			g.setCompany(company);
			g.setGenre(gg);

			gg.addGame(g);
			company.addGame(g);

			dao.updateCompany(company);
			// dao.storeGame(g);
		}
		else  if (genre == null && company == null){
			g.setCompany(c);
			g.setGenre(gg);

			gg.addGame(g);
			c.addGame(g);

			dao.storeGame(g);
		}
		return ret;
	}

	public boolean addLicenseToGame(Game g, License l) {
		Game game = null;
		License license = null;
		boolean ret=true;
		try {
			game  = dao.retrieveGame(g.getName());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception  e) {
			logger.error("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (game !=null && license == null){
			l.setGame(game);
			game.addLicense(l);

			dao.updateGame(game);
		}
		return ret;
	}

	public boolean addLicenseToUser(User u, License l) {
		User user = null;
		License license = null;
		boolean ret=true;
		try {
			user = dao.retrieveUser(u.getLogin());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception e) {
			logger.info("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}
		
		if (user != null && license != null) {
			license.setUser(user);
			user.addLicense(license);
			
			dao.updateUser(user);

		}
		return ret;
	}
	
	public Game showGame(String name){
		Game g=dao.retrieveGame(name);
		return g;

	}
	public Genre showGenre(String name){
		 Genre genr=dao.retrieveGenre(name);
		return genr;

	}
	public Company showCompany(String name){
		Company c=dao.retrieveCompany(name);
		return c;

	}
	public License showLicense(String gameKey){
		License l=dao.retrieveLicense(gameKey);
		return l;

	}

	public User showUser(String login){
		User u=dao.retrieveUser(login);
		return u;

	}
	@Override
	public List<Game> getAllGames() {
		return dao.getAllGames();

	}
	public List<User> getAllUsers() {
		return dao.getAllUsers();

	}

	@Override
	public Game showGameByParam(String name) {
		Game g=dao.retrieveGameByParameter(name);
		
		return g;
	}

	@Override
	public Company showCompanyByParam(String name) {
		Company c=dao.retrieveCompanyByParameter(name);
		
		return c;
	}

	@Override
	public Genre showGenreByParam(String name) {
	Genre g=dao.retrieveGenreByParameter(name);
		
		return g;
	}
}
