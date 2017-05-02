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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.server.db.data.*;
import es.deusto.server.db.*;
import es.deusto.server.db.dao.*;

public class Remote extends UnicastRemoteObject implements IRemote {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;
	final Logger logger = LoggerFactory.getLogger(Remote.class);
	public Remote() throws RemoteException {
		super();

	}

	protected void finalize () throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}

	public boolean registerUser(String login, String password,boolean isSuperUser) throws RemoteException {
	if(login != null || password != null){
		IDB db = new DB();
		//change to objetc the parameters
		User u = new User( login,  password, isSuperUser);
		return	db.registerUser( u);
	}else{
		logger.error("Remote Exception Register User");
		throw new RemoteException();
	}
	}

	public Game gameTest() throws RemoteException{
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);

		IDB db = new DB();

		try {
			db.addGameToDb(g, gr, c);
		} catch (Exception e) {
			logger.error(" Exception  gameTest");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			db .addGameToDb(g, gr, c);
			db.registerUser(u);
			db.addLicenseToGame(g, l);
			//db.buyGame(u.getLogin(), g.getName());
		} catch (Exception e) {
			logger.error("Exception License Test");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		db.showLicense(l.getGameKey());
		return(l);
	}


	@Override
	public List<Game> showGamesInStore() throws RemoteException {
		// call DB to retrieve full list of games
		logger.info("Client asked for games on store");

		IDB db = new DB();
		List<Game> games = db.getAllGames();
		if(games.isEmpty()){
			logger.error("Remote Exception No games on store");
			throw new RemoteException();
		}
		else{
			return(games);
		}
	}


	public User getUser(String login) throws RemoteException{

		IDB db = new DB();
		User u  = db.showUser(login);

		if(u == null){
			logger.error("Remote exception getUser");
			throw new RemoteException();
		}
		else{
			return(u);
		}


	}

	@Override
	public List<Game> showOwnedGames(String username) throws RemoteException {
		// call DB to retrieve specified users list of games
				logger.info("Client asked for games owned");
		IDB db = new DB();
		List<Game> games = db.getUserGames(username);
		if(games.isEmpty()){
			logger.error("Remote exception getUser showOwnedGames ");
			throw new RemoteException();
		}
		else{
			return(games);
		}
	}

	public List<User> getAllUsers() throws RemoteException {
	    // TODO Auto-generated method stub
	    
	    IDB db = new DB();
	    List<User> users = db.getAllUsers();
	    if(users.isEmpty()){
	    logger.error("Remote exception ,No users, getAllUsers" );
	      throw new RemoteException();
	    }
	    else{
	      return(users);
	    }
	  }
	
	
	@Override
	public boolean buyGame(String username, String name) throws RemoteException {
		// call DB to make necessary changes for adding a new game to the users owned list
		if(username!=null || name!=null){
			IDB db = new DB();

			return db.buyGame(username, name);
		}else{
			logger.error("Remote exception buyGame");
			throw new RemoteException();
		}


	}

	@Override
	public boolean addGame(Game game, Genre genre, Company company) throws RemoteException {
		if(game!=null || genre!=null || company!=null){
		IDB db = new DB();
		try {
			return db.addGameToDb(game,genre,company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Remote exception addGame");
			throw new RemoteException();
		}
		}else{
			logger.error("Remote exception addGame");
			throw new RemoteException();
		}
	}
	
	
	

}
