package es.deusto.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.deusto.server.dao.IUserDAO;
import es.deusto.server.dao.UserDAO;
import es.deusto.server.data.*;

public class Messenger extends UnicastRemoteObject implements IMessenger {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	IUserDAO dao;

	public Messenger() throws RemoteException {
		super();
		dao = new UserDAO();

	}
	public Messenger(IUserDAO udao) throws RemoteException {
		super();
		dao = udao;

	}

	public void registerUser(String login, String password,boolean isSuperUser) {

		System.out.println("Checking whether the user already exits or not: '" + login +"'");
		User user = null;
		try {
			user = dao.retrieveUser(login);
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}

		if (user != null) {
			System.out.println("The user exists. So, setting new password for User: " + login);
			user.setPassword(password);
			user.setSuperuser(isSuperUser);
			System.out.println("Password set for User: " + login);
			System.out.println("SuperUser set for User: " + login);
			dao.updateUser(user);
		} else {
			System.out.println("Creating user: " + login);
			user = new User(login, password,isSuperUser);
			dao.storeUser(user);				 
			System.out.println("User created: " + login);
		}
	}

	public void addStufToDb(Game g,Genre gg, Company c) {

		System.out.println("Add Stuff to Db: " );

		Game game = null;
		Genre genre = null;
		Company company = null;

		try {

			game  = dao.retrieveGame(g.getName());
			genre = dao.retrieveGenre(gg.getName());
			company = dao.retrieveCompany(c.getName());

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
		}

		if (game != null && genre != null && company != null  ) {
			System.out.println("The object already exist : " );	
			System.out.println("");
			System.out.println("");

		}else if (game == null && genre != null && company != null  ){	

			System.out.println("Creating game: " + g.getName());
			g.setCompany(company);
			g.setGenre(genre);


			genre.getGenreGames().add(g);
			company.getCompanyGames().add(g);

			System.out.println("Updating Genre: " + gg.getName());
			System.out.println("Updating Company: " + c.getName());

			dao.storeGame(g);
			dao.updateGenre(genre);
			dao.updateCompany(company);				 

			Game  example = dao.retrieveGame(g.getName());
			Genre example1 =dao.retrieveGenre(gg.getName());
			Company example2 =dao.retrieveCompany(c.getName());

			System.out.println("Genre info: " + 		example1.toString() );
			System.out.println("Company info: " + 		example2.toString() );			
			System.out.println("Game info: " + 		example.toString() );
			System.out.println("");
			System.out.println("");

		}
		else {

			System.out.println("Creating game: " + g.getName());
			System.out.println("Creating genre: " + gg.getName());
			System.out.println("Creating company: " + c.getName());
			g.setCompany(c);
			g.setGenre(gg);

			gg.getGenreGames().add(g);
			c.getCompanyGames().add(g);

			dao.storeGame(g);				 
			dao.storeGenre(gg);				 
			dao.storeCompany(c);				 

			Game  example = dao.retrieveGame(g.getName());
			Genre example1 =dao.retrieveGenre(gg.getName());
			Company example2 =dao.retrieveCompany(c.getName());

			System.out.println("Genre info: " + 		example1.toString() );
			System.out.println("Company info: " + 		example2.toString() );			
			System.out.println("Game info: " + 		example.toString() );

			System.out.println("");
			System.out.println("");


		}
	}
	public void addLicenseToGame(Game g, License l) {
		System.out.println("" );
		System.out.println("" );
		System.out.println("Add LICENSE to db: " );

		Game game = null;
		License license = null;

		try {

			game  = dao.retrieveGame(g.getName());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
		}

		if (game != null && license != null  ) {
			System.out.println("The license already exist so i will add it to the user" );	


		}else if (game !=null && license == null){	

			System.out.println("Creating license for game: " + g.getName());
			l.setGame(game);
			game.getLicenses().add(l);

			System.out.println("Updating Game: " + g.getName());

			dao.storeLicense(l);	
			dao.updateGame(game);


			Game  example = dao.retrieveGame(g.getName());
			License example1 =dao.retrieveLicense(l.getGameKey());
			System.out.println("");
			System.out.println("");

			System.out.println("Game info: " + 		example.toString() );			
			System.out.println("License info: " + 		example1.toString() );

			System.out.println("");
			System.out.println("");
		}
		else if (game== null)  {

			System.out.println("The game has not been createad yet: " + g.getName());

		}
	}	
	public void addLicenseToUser(User u, License l) {
		System.out.println("" );
		System.out.println("" );
		System.out.println("Link license with the user db: " );
	
		User user = null;
		License license = null;

		try {


			user = dao.retrieveUser(u.getLogin());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
		}

		if (user != null && license != null  ) {

			license.setUser(user);			
			user.getLicenses().add(license);

			System.out.println("Updating User: " +user.getLogin());


			dao.updateLicense(license);
			dao.updateUser(user);


			License  example = dao.retrieveLicense(license.getGameKey());
			User example1 =dao.retrieveUser(user.getLogin());
			System.out.println("");
			System.out.println("");

			System.out.println("License info: " + example.toString() );
			System.out.println("USer info: " + example1.toString() );			



		}else if ( license == null || user == null ){	

			System.out.println("Create the user or the license " + l.getGameKey() + u.getLogin());

		}

	}

	public void showGameInfo(String g,String c,String gen) throws RemoteException{

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
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("GAME DATA: "+ game.toString());
			System.out.println("GENRE DATA: "+ genre.toString());
			System.out.println("COMPANY DATA: "+ company.toString());

		} else {
			System.out.println("There is no such this data on the database");

		}

	}
	
	public void showLicenseInfo(String u,String l,String g) throws RemoteException{
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
			
			System.out.println("");
			System.out.println("");
			System.out.println("");
			
			System.out.println("USER DATA: "+ user.toString());
			System.out.println("GAME DATA: "+ game.toString());
			System.out.println("LICENSE DATA: "+ license.toString());
			

		} else {
			System.out.println("There is no such this data on the database");

		}

	}
	
	
	
	
	
	
	

	public void getGamesFromDB() throws RemoteException{
		List<Game> games = dao.getAllGames();


		if ( games.isEmpty()) {
			System.out.println("No games in the database");

		} else {
			StringBuffer gamesStr = new StringBuffer();
			System.out.println("Game List: ");
			for (Game game: games) {
				gamesStr.append(game.toString() + " - ");
				System.out.println(gamesStr);
			}


		}
	}




	public String sayMessage(String login, String password, String message) throws RemoteException {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Retrieving the user: '" + login +"'");
		User user = null;
		try {
			user = dao.retrieveUser(login);
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}

		System.out.println("User retrieved: " + user);
		if (user != null)  {
			Message message1 = new Message(message);
			message1.setUser(user);
			user.getMessages().add(message1);
			dao.updateUser(user);					
			cont++;
			System.out.println(" * Client number: " + cont);
			return message;
		}
		else {
			System.out.println("Login details supplied for message delivery are not correct");
			throw new RemoteException("Login details supplied for message delivery are not correct");
		} 
	}

	public User getUserMessages(String login) throws RemoteException {
		System.out.println("");
		System.out.println("");
		System.out.println("Checking whether the user already exits or not: '" + login +"'");
		User user = null;
		try {
			user = dao.retrieveUser(login);
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}

		if (user != null) {
			System.out.println("Returning the User and its messages to the RMI Client: " + login);
			return user;
		} else {
			System.out.println("The user does not exist, no possibility of retrieving messages ...: " + login);
			throw new RemoteException("Login details supplied for message retrieval are not correct");
		}
	}
}
