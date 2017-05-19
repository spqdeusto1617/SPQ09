package es.deusto.server.db;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.server.db.dao.IDAO;

import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.data.*;

/**
 * This class executes all the basic functions of the database,
 * such as buying a game, registering a user or adding a game for example
 * @author
 * @version 1.0
 * @since 24/03/2017
 */
public class DB implements IDB {

	private static final long serialVersionUID = 1L;

	private int cont = 0;
	IDAO dao;
	final Logger logger = LoggerFactory.getLogger(DB.class);
	private final int DEFAULT_LICENSES = 10;
	
	/**
	 * This is the first constructor for the database
	 * @param unused
	 * @return nothing
	 */
	public DB(){
		super();
		dao = new DAO();
	}
	
	/**
	 * This is the second constructor for the database
	 * @param udao This is the parameter to the constructor
	 * @return nothing
	 */
	public DB(IDAO dao){
		super();
		this.dao = dao;
	}

	/**
	 * This method allows the user to log in
	 * @param u This is a user
	 * @return boolean Returns true or false depending on whether the user exists or not
	 * @see es.deusto.server.db.IDB#loginUser(es.deusto.server.db.data.User)
	 */
	@Override
	public boolean loginUser(User u) {
		User user = null;

		try {
			user = dao.retrieveUser(u.getLogin());
		} catch (Exception  e) {
			logger.error("Exception launched retrieving user: " + e.getMessage());
			return false;
		}
		if(user!=null){
			return u.compareUserTo(user);
		}
		return false;
	}
	
	/**
	 * This method saves a new user
	 * @param u This is a user
	 * @return boolean Returns true or false depending on whether the user exists or not
	 * @see es.deusto.server.db.IDB#registerUser(es.deusto.server.db.data.User)
	 */
	@Override
	public boolean registerUser(User u) {
		try {
			dao.storeUser(u);
		} catch (Exception  e) {
			logger.error("Exception launched storing new user: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Method that gives the user a license when buying a game
	 * @param username This is the login username of a user
	 * @param name This is the name of a game
	 * @return boolean Returns true when the user has the license
	 * @see es.deusto.server.db.IDB#buyGame(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean buyGame(String username, String name) {
		logger.info("Buying game");
		User u = showUser(username);
		Game g = showGame(name);
		License l = dao.getFirstLicense(name);

		logger.info("Setting license used");
		l.setUsed(true);

		logger.info("Updating DB");
		dao.updateLicense(l);

		logger.info("Adding license to user");
		addLicenseToUser(u, l);
		return true;
	}
	
	/**
	 * This method shows a list of games
	 * @param unused
	 * @return List Returns a list of games
	 * @see es.deusto.server.db.IDB#getAllGames()
	 */
	@Override
	public List<Game> getAllGames() {
		return dao.getAllGames();
	}
	
	/**
	 * Method that returns the list of games of a user
	 * @param username This is the login username of a user
	 * @return list Returns a list of games
	 * @see es.deusto.server.db.IDB#getUserGames(java.lang.String)
	 */
	@Override
	public List<Game> getUserGames(String username) {
		User u = showUser(username);
		List <Game> gameList = new ArrayList<>();
		for (License l : u.getLicenses() ) {
			gameList.add(l.getGame());
        }
		return gameList;
	}
	
	/**
	 * This method shows a list of companies
	 * @param unused
	 * @return List Returns a list of companies
	 * @see es.deusto.server.db.IDB#getAllCompanies()
	 */
	@Override
	public List<String> getAllCompanies() {
		List<Company> companies = dao.getAllCompanies();
		List<String> compNames = new ArrayList<>();
		for(Company comp : companies){
			compNames.add(comp.getName());
		}
		return compNames;
	}
	
	/**
	 * This method shows a list of genres
	 * @param unused
	 * @return List Returns a list of genres
	 * @see es.deusto.server.db.IDB#getAllGenres()
	 */
	@Override
	public List<String> getAllGenres() {
		List<Genre> genres = dao.getAllGenres();
		List<String> genNames = new ArrayList<>();
		for(Genre gen : genres){
			genNames.add(gen.getName());
		}
		return genNames;
	}
	
	/**
	 * This method adds a new game or updates a existing one into the database
	 * @param g This is a game
	 * @param gg This is a genre
	 * @param c This is a company
	 * @return boolean Returns true or false depending on whether the game exists in the database or not
	 * @see es.deusto.server.db.IDB#addGameToDb(es.deusto.server.db.data.Game, es.deusto.server.db.data.Genre, es.deusto.server.db.data.Company)
	 */
	@Override
	public boolean addGameToDb(Game g,Genre gg, Company c) {
		Game game = null;
		Genre genre = null;
		Company company = null;
		boolean ret=true;

		game  = dao.retrieveGame(g.getName());
		genre = dao.retrieveGenre(gg.getName());
		company = dao.retrieveCompany(c.getName());

		if (game != null ) {
			ret = false;
		}
		else if ( genre != null && company == null){
			g.setCompany(c);
			g.setGenre(genre);

			genre.addGame(g);
			c.addGame(g);

			dao.updateGenre(genre);
		}
		else if ( genre != null && company != null  ){
		g.setCompany(company);
		g.setGenre(genre);

		genre.addGame(g);
		company.addGame(g);

		 dao.storeGame(g);
		}
		else if (genre == null && company != null  ){
		g.setCompany(company);
		g.setGenre(gg);

		gg.addGame(g);
		company.addGame(g);

		dao.updateCompany(company);
		}
		else if ( genre == null && company == null  ){
			g.setCompany(c);
			g.setGenre(gg);

			gg.addGame(g);
			c.addGame(g);

			dao.storeGame(g);
		}

		for(int i = 0; i<DEFAULT_LICENSES; i++){
			addLicenseToGame(g, new License(createLicenseKey()));
		}

		return ret;
	}
	
	/**
	 * This method shows if a user is also superuser or not
	 * @param login This is the login name of a user
	 * @return boolean Returns if a user is superuser or not
	 * @see es.deusto.server.db.IDB#isSuperUser(java.lang.String)
	 */
	@Override
	public boolean isSuperUser(String login){
		User u = dao.retrieveUser(login);
		return u.getSuperuser();
	}

	/**
	 * This method shows a user
	 * @param name This is the name of a user
	 * return User Returns a user
	 * @see es.deusto.server.db.IDB#showUser(java.lang.String)
	 */
	@Override
	public User showUser(String login){
		 User u=dao.retrieveUser(login);
		return u;

	}
	
	/**
	 * This method shows a game
	 * @param name This is the name of a game
	 * return Game Returns a game
	 * @see es.deusto.server.db.IDB#showGame(java.lang.String)
	 */
	@Override
	public Game showGame(String name){
		 Game g=dao.retrieveGame(name);

		return g;
	}
	
	/**
	 * This method shows the company of a game
	 * @param name This is the name of a company
	 * @return Company Returns the company of a game
	 * @see es.deusto.server.db.IDB#showCompany(java.lang.String)
	 */
	@Override
	public Company showCompany(String name){
		 Company c=dao.retrieveCompany(name);
		return c;

	}
	
	/**
	 * This method shows the genre of a game
	 * @param name This is the name of a genre
	 * @return Genre Returns the genre of a game
	 * @see es.deusto.server.db.IDB#showGenre(java.lang.String)
	 */
	@Override
	public Genre showGenre(String name){
		Genre genr=dao.retrieveGenre(name);
		return genr;

	}
	
	/** This method adds a license to a user
	 * @param u This is a user
	 * @param l This is a license
	 * @return boolean Returns true or false depending on whether the user has a license or not
	 * @see es.deusto.server.db.IDB#addLicenseToUser(es.deusto.server.db.data.User, es.deusto.server.db.data.License)
	 */
	private boolean addLicenseToUser(User u, License l) {
		User user = null;
		License license = null;
		boolean ret=true;
		double price;
		try {
			user = dao.retrieveUser(u.getLogin());
			license = dao.retrieveLicense(l.getGameKey());
		} catch (Exception  e) {
			logger.info("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (user != null && license != null   ) {
			price=license.getGame().getPrice()*(1-(license.getGame().getDiscount()));

			if(price<user.getMoney()) {
				user.setMoney(user.getMoney()-price);
				license.setUser(user);
				user.addLicense(license);
				dao.updateUser(user);
			}
			else{
				logger.error("Not enough money");
			}
		}
		return ret;
	}
	
	/**
	 * This method creates license keys
	 * @param unused
	 * @return String Returns the license key
	 */
	private String createLicenseKey() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
		    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
		    salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

    	}
	
	/**
	 * This method adds a license to a game
	 * @param g This is a game
	 * @param l This is a license
	 * @return boolean Returns true or false depending on whether the game has a license or not
	 * @see es.deusto.server.db.IDB#addLicenseToGame(es.deusto.server.db.data.Game, es.deusto.server.db.data.License)
	 */
	private boolean addLicenseToGame(Game g, License l) {
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
}
