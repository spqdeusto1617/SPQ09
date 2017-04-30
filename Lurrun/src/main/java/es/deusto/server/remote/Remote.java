package es.deusto.server.remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import es.deusto.server.db.data.*;
import es.deusto.server.db.*;
import es.deusto.server.db.dao.*;

public class Remote extends UnicastRemoteObject implements IRemote {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

	public Remote() throws RemoteException {
		super();
//		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
//		this.pm = pmf.getPersistenceManager();
//		this.tx = pm.currentTransaction();
	}
	
	protected void finalize () throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}
	
	public boolean registerUser(String login, String password,boolean isSuperUser) {
		IDB db = new DB();
		//change to objetc the parameters
		User u = new User( login,  password, isSuperUser);
		return	db.registerUser( u);
	}

	public Game gameTest(){
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);
		
		IDB db = new DB();
		
		db.addGameToDb(g, gr, c);
		Game g1=db.showGame(g.getName());
		
		return(g1);
	}
	public License licenseTest(){
		Company c = new Company("DICE");
		Genre gr = new Genre("Bellic simulator");
		Game g = new Game("BF 1942", 19.90, 0);
		
		License l = new License ("GGGG");
		
		User u = new User("JunitUser","Junit Pass",false);
		
		IDB db = new DB();
		db .addGameToDb(g, gr, c);
		db.registerUser(u);
		db.addLicenseToGame(g, l);
		db.buyGame(u.getLogin(), g.getName());
		
		db.showLicense(l.getGameKey());
		return(l);
	}


	@Override
	public List<Game> showGamesInStore() throws RemoteException {
		// call DB to retrieve full list of games
		//	System.out.println("Client asked for games on store");
		IDB db = new DB();
		List<Game> games = db.getAllGames();
		if(games.isEmpty()){
			throw new RemoteException("No games on store");
		}
		else{
			return(games);
		}
	}

	
	public User getUser(String login) throws RemoteException{
		
		IDB db = new DB();
		User u  = db.showUser(login);
		
		if(u == null){
			throw new RemoteException("No games owned");
		}
		else{
			return(u);
		}
		
		
	}

	@Override
	public List<Game> showOwnedGames(String username) throws RemoteException {
		// call DB to retrieve specified users list of games
		//		System.out.println("Client asked for games owned");
		IDB db = new DB();
		List<Game> games = db.getUserGames(username);
		if(games.isEmpty()){
			throw new RemoteException("No games owned");
		}
		else{
			return(games);
		}
	}

	@Override
	public boolean buyGame(String username, String name) throws RemoteException {
		// call DB to make necessary changes for adding a new game to the users owned list
		IDB db = new DB();
		return db.buyGame(username, name);
	}

	@Override
	public boolean addGame(Game game, Genre genre, Company company) throws RemoteException {
		IDB db = new DB();
		return db.addGameToDb(game,genre,company);
	}

}
