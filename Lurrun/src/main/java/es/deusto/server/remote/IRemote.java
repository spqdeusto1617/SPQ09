package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.server.db.data.*;

public interface IRemote extends Remote {
	
//	String sayMessage(String login, String password, String message) throws RemoteException;
	boolean registerUser(String login, String password,boolean isSuper) throws RemoteException;
	Game gameTest() throws RemoteException;
	License licenseTest() throws RemoteException;
	
	
	User getUser(String login) throws RemoteException;
	List<Game> showGamesInStore() throws RemoteException;
	List<Game> showOwnedGames(String username) throws RemoteException;
	boolean buyGame(String username, String name) throws RemoteException;
	
	//SUPERUSER FUNCTION
	boolean addGame (Game g,Genre gg, Company c) throws RemoteException;

}
