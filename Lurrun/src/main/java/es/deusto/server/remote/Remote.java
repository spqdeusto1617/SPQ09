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
/**
 * This class checks if the main processes, such as buying a game or registering a user, work correctly
 * @author 
 * @version 1.0
 * @since 24/03/2017 
 */
public class Remote extends UnicastRemoteObject implements IRemote {

	private static final long serialVersionUID = 1L;
	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;
	final Logger logger = LoggerFactory.getLogger(Remote.class);
	
	/**
	 * This is the constructor for the Remote class
	 * @param unused
	 * @return nothing
	 * @throws RemoteException
	 */
	public Remote() throws RemoteException {
		super();

	}
	/**
	 * This method allows a user to log in
	 * @param login This is the login name of a user
	 * @param password This is the login password of a user
	 * @return boolean Returns true if the user can log in and false if not
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#loginUser(java.lang.String, java.lang.String)
	 */
	public boolean loginUser(String login, String password) throws RemoteException {
		if(login != null || password != null){
			IDB db = new DB();
			//change to objetc the parameters
			User u = new User(login,  password);
			return db.loginUser(u);
		} else {
			logger.error("Remote Exception Register User");
			throw new RemoteException();
		}
	}
	/**
	 * This method checks if the registration process works correctly
	 * @param login This is the login name of the user
	 * @param password This is the password for the login
	 * @param isSuperUser This is true if the user is a Superuser or false if not
	 * @return IDB Returns the registered user in the database
	 * @exception RemoteException 
	 * @see es.deusto.server.remote.IRemote#registerUser(java.lang.String, java.lang.String, boolean)
	 */
	public boolean registerUser(String login, String password) throws RemoteException {
		if(login != null || password != null){
			IDB db = new DB();
			//change to objetc the parameters
			User u = new User(login,  password);
			return db.registerUser(u);
		} else {
			logger.error("Remote Exception Register User");
			throw new RemoteException();
		}
	}
	
	/**
	 * This method shows all the games available in the store
	 * @param unused
	 * @return List Returns a list of games stored in the database
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#showGamesInStore()
	 */
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
	/**
	 * This method changes the amount of money in the wallet
	 * @param k This is the amount of money
	 * @param login  This is the login name of a user
	 * @return nothing
	 * @exception RemoteException
	 */
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
	/**
	 * This method shows the wallet of a user
	 * @param login This is the user login name
	 * @return double Returns the amount of money a user has in the wallet
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#getUserWallet(java.lang.String)
	 */
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
	/**
	 * This method shows all the games a user owns
	 * @param username This is the name of the user
	 * @return List Returns a list of games stored in the database
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#showOwnedGames(java.lang.String)
	 */
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
	/**
	 * This method buys a game and adds it to the user owned list
	 * @param username This is the name of the user
	 * @param name this is the name of a game
	 * @return IDB Returns a game stored in the database
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#buyGame(java.lang.String, java.lang.String)
	 */
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
	/**
	 * This method shows if a user is superuser or not
	 * @param login This is the login name of a user
	 * @return boolean Returns if the user is also superuser
	 * @see es.deusto.server.remote.IRemote#isSuperUser(java.lang.String)
	 */
	@Override
	public boolean isSuperUser(String login) throws RemoteException {
		IDB db = new DB();
		return db.isSuperUser(login);
	}
	/**
	 * This method adds a game to the database
	 * @param game This is the name of a game
	 * @param genre This is the genre of a game
	 * @param company This is the company of a game
	 * @return IDB Returns a game stored in the database
	 * @exception RemoteException
	 * @see es.deusto.server.remote.IRemote#addGame(es.deusto.server.db.data.Game, es.deusto.server.db.data.Genre, es.deusto.server.db.data.Company)
	 */
	@Override
	public boolean addGame(String gName, double price, double disc,String gg, String c) throws RemoteException {
		IDB db = new DB();
		Game g = new Game(gName, price, disc);
		Genre gen = db.showGenre(gg);
		Company comp = db.showCompany(c);
		return db.addGameToDb(g, gen, comp);
	}
	/**
	 * This method shows all the companies
	 * @param unused
	 * @return List<String> Returns a list of companies
	 * @see es.deusto.server.remote.IRemote#getAllCompanies()
	 */
	@Override
	public List<String> getAllCompanies() {
		IDB db = new DB();
		List<String> companies = db.getAllCompanies();
		return companies;
	}
	/**
	 * This method shows all the genres
	 * @param unused
	 * @return List<String> Returns a list of genres
	 * @see es.deusto.server.remote.IRemote#getAllGenres()
	 */
	@Override
	public List<String> getAllGenres() {
		IDB db = new DB();
		List<String> genres = db.getAllGenres();
		return genres;
	}
	/**
	 * This method shows an array
	 * @param list This is a list
	 * @return String[] Returns an array
	 */
	private String[] toArray(List<String> list){
		int length = list.size();
		String[] array = new String [length];
		for(int i = 0; i<length; i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * This method finalizes the connection with the database
	 * @param unused
	 * @return none
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize () throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}
}
