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

	public boolean loginUser(String login, String password) throws RemoteException {
		if(login != null || password != null){
			IDB db = new DB();
			//change to objetc the parameters
			User u = new User(login,  password, false);
			return db.loginUser(u);
		} else {
			logger.error("Remote Exception Register User");
			throw new RemoteException();
		}
	}
	
	public boolean registerUser(String login, String password) throws RemoteException {
		if(login != null || password != null){
			IDB db = new DB();
			//change to objetc the parameters
			User u = new User(login,  password, false);
			return db.registerUser(u);
		} else {
			logger.error("Remote Exception Register User");
			throw new RemoteException();
		}
	}
	
	
	@Override
	public List<Game> showGamesInStore() throws RemoteException {
		// call DB to retrieve full list of games
		logger.info("Client asked for games on store");

		IDB db = new DB();
		List<Game> games = db.getAllGames();
		if(games.isEmpty()){logger.error("Remote Exception No games on store");throw new RemoteException();
		}
		else{
			return(games);
		}
	}

	public void setUserWallet(double k, String login) throws RemoteException
	{
		IDB db = new DB();
		User u  = db.showUser(login);

		if(u == null){
			logger.error("Remote exception getUser");
			throw new RemoteException();
		}
		else{
		u.setMoney(k);
		}
		
	}
	public double getUserWallet(String login) throws RemoteException{

		IDB db = new DB();
		User u  = db.showUser(login);

		if(u == null){
			logger.error("Remote exception getUser");
			throw new RemoteException();
		}
		else{
			return(u.getMoney());
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
	public boolean isSuperUser(String login) throws RemoteException {
		IDB db = new DB();
		return db.isSuperUser(login);
	}
	
	@Override
	public boolean addGame(String gName, double price, double disc,String gg, String c) throws RemoteException {
		IDB db = new DB();
		Game g = new Game(gName, price, disc);
		Genre gen = db.showGenre(gg);
		Company comp = db.showCompany(c);
		return db.addGameToDb(g, gen, comp);
	}
	
	@Override
	public List<String> getAllCompanies() {
		IDB db = new DB();
		List<String> companies = db.getAllCompanies();
		return companies;
	}
	
	@Override
	public List<String> getAllGenres() {
		IDB db = new DB();
		List<String> genres = db.getAllGenres();
		return genres;
	}
	
	private String[] toArray(List<String> list){
		int length = list.size();
		String[] array = new String [length];
		for(int i = 0; i<length; i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	protected void finalize () throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}
}
