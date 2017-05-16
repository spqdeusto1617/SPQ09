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

public class DB implements IDB {

	private static final long serialVersionUID = 1L;
	
	private int cont = 0;
	IDAO dao;
	final Logger logger = LoggerFactory.getLogger(DB.class);
	private final int DEFAULT_LICENSES = 3;
	
	public DB(){
		super();
		dao = new DAO();
	}
	
	public DB(IDAO dao){
		super();
		this.dao = dao;
	}
	
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

	@Override
	public boolean buyGame(String username, String name) {
		logger.info("Buying game");
		User u = showUser(username);
		Game g = showGame(name);
		License l =	dao.getFirstLicense(name);
		
		logger.info("Setting license used");
		l.setUsed(true);	
		
		logger.info("Updating DB");
		dao.updateLicense(l);
		dao.updateGame(g);
	
		logger.info("Adding license to user");
		addLicenseToUser(u, l);
		return true;
	}
	
	@Override
	public List<Game> getAllGames() {
		return dao.getAllGames();
	}
	
	@Override
	public List<Game> getUserGames(String username) {
		User u = showUser(username);
		List <Game> gameList = new ArrayList<>();
		for (License l : u.getLicenses() ) {
			gameList.add(l.getGame());
        }
		return gameList;
	}

	@Override
	public List<String> getAllCompanies() {
		List<Company> companies = dao.getAllCompanies();
		List<String> compNames = new ArrayList<>();
		for(Company comp : companies){
			compNames.add(comp.getName());
		}
		return compNames;
	}
	
	@Override
	public List<String> getAllGenres() {
		List<Genre> genres = dao.getAllGenres();
		List<String> genNames = new ArrayList<>();
		for(Genre gen : genres){
			genNames.add(gen.getName());
		}
		return genNames;
	}
	
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

	@Override
	public boolean isSuperUser(String login){
		User u = dao.retrieveUser(login);
		return u.getSuperuser();
	}
	
	
	@Override
	public User showUser(String login){
		 User u=dao.retrieveUser(login);
		return u;

	}
	
	@Override
	public Game showGame(String name){
		 Game g=dao.retrieveGame(name);
		
		return g;
	}
	
	@Override
	public Company showCompany(String name){
		 Company c=dao.retrieveCompany(name);
		return c;

	}
	
	@Override
	public Genre showGenre(String name){
		Genre genr=dao.retrieveGenre(name);
		return genr;

	}

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

			license.setUser(user);
			user.addLicense(license);
			
			price=license.getGame().getPrice()*(1-(license.getGame().getDiscount()));
			
		if(price<user.getMoney())	{
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
	
//	private License showLicense(String gameKey){
//		 License l=dao.retrieveLicense(gameKey);
//		return l;
//
//	}
	
//	private List<User> getAllUsers() {
//		return dao.getAllUsers();
//
//	}

//	public Game showGameByParam(String name) {
//		Game g=dao.retrieveGameByParameter(name);
//		
//		return g;
//	}
//
//	public Company showCompanyByParam(String name) {
//		Company c=dao.retrieveCompanyByParameter(name);
//		
//		return c;
//	}
//
//	public Genre showGenreByParam(String name) {
//	Genre g=dao.retrieveGenreByParameter(name);
//		
//		return g;
//	}

}
