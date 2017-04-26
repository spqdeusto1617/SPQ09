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
import es.deusto.server.db.DbMethods;
import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.dao.IDAO;


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
	
	public void registerUser(String login, String password) {
		try
        {	
            tx.begin();
            System.out.println("Checking whether the user already exits or not: '" + login +"'");
			User user = null;
			try {
				user = pm.getObjectById(User.class, login);
			} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
				System.out.println("Exception launched: " + jonfe.getMessage());
			}
			System.out.println("User: " + user);
			if (user != null) {
				System.out.println("Setting password user: " + user);
				user.setPassword(password);
				System.out.println("Password set user: " + user);
			} else {
				System.out.println("Creating user: " + user);
				user = new User(login, password);
				pm.makePersistent(user);					 
				System.out.println("User created: " + user);
			}
			tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
        }
	}

	public Game sayHello(){
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);
		g.setGenre(gr);
		g.setCompany(c);
		return(g);
	}


	@Override
	public List<Game> showGamesInStore() throws RemoteException {
		// call DB to retrieve full list of games
		System.out.println("Client asked for games on store");
		List<Game> games = DbMethods.getAllGames();
		if(games.isEmpty()){
			throw new RemoteException("No games on store");
		}
		else{
			return(games);
		}
	}

	@Override
	public List<Game> showOwnedGames(String username) throws RemoteException {
		// call DB to retrieve specified users list of games
		System.out.println("Client asked for games owned");
		List<Game> games = DbMethods.getUserGames(username);
		if(games.isEmpty()){
			throw new RemoteException("No games on store");
		}
		else{
			return(games);
		}
	}

	@Override
	public boolean buyGame(String username, int gameId) throws RemoteException {
		// call DB to make necessary changes for adding a new game to the users owned list
		return DbMethods.buyGame(username, gameId);
	}

}
