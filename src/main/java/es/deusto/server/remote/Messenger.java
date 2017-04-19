package es.deusto.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
	
	public void registerUser(String login, String password) {
			
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
				System.out.println("Password set for User: " + login);
				dao.updateUser(user);
			} else {
				System.out.println("Creating user: " + login);
				user = new User(login, password);
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

	
		
			
		}
}
	
	public void getStuf() throws RemoteException{
		User user = null;
		Game game = null;
		Genre genre = null;
		Company company = null;
		try {
			user = dao.retrieveUser("Javier");
			game  = dao.retrieveGame("HL8");
			genre = dao.retrieveGenre("FPS8");
			company = dao.retrieveCompany("Valve8");
		} catch (Exception  e) {
			System.out.println("Exception launched: " + e.getMessage());
		}
		if (user != null || game != null || genre != null || company != null) {
			System.out.println("USER DATA: "+ user.toString());
			System.out.println("GAME DATA: "+ game.toString());
			System.out.println("GENRE DATA: "+ genre.toString());
			System.out.println("COMPANY DATA: "+ company.toString());
			
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
