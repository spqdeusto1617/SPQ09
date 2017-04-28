package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.server.db.data.*;

public interface IRemote extends Remote {
	
//	String sayMessage(String login, String password, String message) throws RemoteException;
	 boolean registerUser(String login, String password,boolean isSuper) throws RemoteException;
	Game sayHello() throws RemoteException;
	
	List<Game> showGamesInStore() throws RemoteException;
	List<Game> showOwnedGames(String username) throws RemoteException;
	boolean buyGame(String username, String name) throws RemoteException;
}
