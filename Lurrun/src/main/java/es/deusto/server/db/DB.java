package es.deusto.server.db;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import es.deusto.server.db.dao.IDAO;
import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.data.*;

public class DB implements IDB {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	IDAO dao;

	public DB(){
		super();
		dao = new DAO();

	}

	public DB(IDAO udao) {
		super();
		dao = udao;

	}

	public  List<Game> getUserGames(String username) {
		User u= showUser(username);
		List <Game> gameList = new ArrayList<>();
				for (License l :u.getLicenses() ) {
					gameList.add(l.getGame());

	                }
				return gameList;

	}

	public  boolean buyGame(String username, String name) {
		// TODO Auto-generated method stub
		User u = showUser(username);
		Game g = showGame(name);
		License l=	g.getFirstFreeLicense();

		return addLicenseToUser(u, l);

	}

	public boolean registerUser(User u) {


		User user = null;
		boolean ret=true;

		try {
			user = dao.retrieveUser(u.getLogin());
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
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

	public boolean addGameToDb(Game g,Genre gg, Company c) {



		Game game = null;
		Genre genre = null;
		Company company = null;
		boolean ret=true;

		try {

			game  = dao.retrieveGame(g.getName());
			genre = dao.retrieveGenre(gg.getName());
			company = dao.retrieveCompany(c.getName());

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret = false;
		}

		if (game != null && genre != null && company != null  ) {

		}else if (game == null && genre != null && company != null  ){


			g.setCompany(company);
			g.setGenre(genre);


			genre.addGame(g);
			company.addGame(g);

			dao.storeGame(game);
		//	dao.updateGenre(genre);
		//	dao.updateCompany(company);

		}
		else {

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
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (game != null && license != null  ) {



		}else if (game !=null && license == null){

			l.setGame(game);
			game.addLicense(l);

			dao.updateGame(game);

		}
		else if (game== null)  {


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

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (user != null && license != null  ) {

			license.setUser(user);
			user.getLicenses().add(license);

			dao.updateLicense(license);
			dao.updateUser(user);


		}else if ( license == null || user == null ){
			System.out.println("Create the user or the license " + l.getGameKey() + u.getLogin());

		}
		return ret;
	}

	public void showGameInfo(String g,String c,String gen){

		Game game = null;
		Genre genre = null;
		Company company = null;
		try {

			game  = dao.retrieveGame(g);
			genre = dao.retrieveGenre(gen);
			company = dao.retrieveCompany(c);
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}
		if ( game != null && genre != null && company != null) {

			System.out.println("GAME DATA: "+ game.toString());
			System.out.println("GENRE DATA: "+ genre.toString());
			System.out.println("COMPANY DATA: "+ company.toString());

		} else {
			System.out.println("There is no such this data on the database");

		}

	}

	public void showLicenseInfo(String u,String l,String g){
		User user = null;
		Game game = null;
		License license = null;

		try {
			user = dao.retrieveUser(u);
			game  = dao.retrieveGame(g);
			license = dao.retrieveLicense(l);

		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}
		if (user != null && game != null && license != null ) {

			System.out.println("USER DATA: "+ user.toString());
			System.out.println("GAME DATA: "+ game.toString());
			System.out.println("LICENSE DATA: "+ license.toString());

		} else {
			System.out.println("There is no such this data on the database");
		}
	}

	public Game showGame(String name){
		 Game g=dao.retrieveGame(name);
		//dao.retrieveGameByName(name);
		return g;

	}
	public Genre showGenre(String name){
		 Genre genr=dao.retrieveGenre(name);
		//dao.retrieveGenreByName(name);
		return genr;

	}
	public Company showCompany(String name){
		 Company c=dao.retrieveCompany(name);
		// dao.retrieveCompanyyName(name);
		return c;

	}
	public License showLicense(String gameKey){
		 License l=dao.retrieveLicense(gameKey);
		// ao.retrieveLicenseByName(name);
		return l;

	}

	public User showUser(String login){
		 User u=dao.retrieveUser(login);
		// ao.retrieveLicenseByName(name);
		return u;

	}
	@Override
	public List<Game> getAllGames() {
		return dao.getAllGames();

	}

//	public String sayMessage(String login, String password, String message) {
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
//		System.out.println("Retrieving the user: '" + login +"'");
//		User user = null;
//		try {
//			user = dao.retrieveUser(login);
//		} catch (Exception  e) {
//			System.out.println("Exception launched: " + e.getMessage());
//		}
//
//		System.out.println("User retrieved: " + user);
//		if (user != null)  {
//			Message message1 = new Message(message);
//			message1.setUser(user);
//			user.getMessages().add(message1);
//			dao.updateUser(user);
//			cont++;
//			System.out.println(" * Client number: " + cont);
//			return message;
//		}
//		else {
//			System.out.println("Login details supplied for message delivery are not correct");
//			return null;
////			throw new RemoteException("Login details supplied for message delivery are not correct");
//		}
//	}
//
//	public User getUserMessages(String login) {
//		System.out.println("");
//		System.out.println("");
//		System.out.println("Checking whether the user already exits or not: '" + login +"'");
//		User user = null;
//		try {
//			user = dao.retrieveUser(login);
//		} catch (Exception  e) {
//			System.out.println("Exception launched: " + e.getMessage());
//		}
//
//		if (user != null) {
//			System.out.println("Returning the User and its messages to the RMI Client: " + login);
//			return user;
//		} else {
//			System.out.println("The user does not exist, no possibility of retrieving messages ...: " + login);
//			return null;
////			throw new RemoteException("Login details supplied for message retrieval are not correct");
//		}
//	}
}