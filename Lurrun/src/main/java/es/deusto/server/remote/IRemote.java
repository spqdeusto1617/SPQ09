package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.server.db.data.*;

public interface IRemote extends Remote {
	
	boolean registerUser(String login, String password) throws RemoteException;
	boolean loginUser(String login, String password) throws RemoteException;
	List<Game> showGamesInStore() throws RemoteException;
	List<Game> showOwnedGames(String username) throws RemoteException;
	boolean buyGame(String username, String name) throws RemoteException;
	double getUserWallet(String login) throws RemoteException;
	//void setUserWallet(double k, String login) throws RemoteException;
	//SUPERUSER FUNCTION
	boolean isSuperUser(String login) throws RemoteException;
	boolean addGame (String gName, double price, double disc,String gg, String c) throws RemoteException;
	List<String> getAllCompanies() throws RemoteException;
	List<String> getAllGenres() throws RemoteException;
	
//	List<User> getAllUsers() throws RemoteException;
}
