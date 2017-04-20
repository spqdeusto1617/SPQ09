package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.server.db.data.*;

public interface IRemote extends Remote {
	
//	String sayMessage(String login, String password, String message) throws RemoteException;
//	void registerUser(String login, String password) throws RemoteException;
	Game sayHello() throws RemoteException;
	ArrayList<Game> showGamesInStore() throws RemoteException;
	ArrayList<Game> showOwnedGames(String username) throws RemoteException;
	boolean buyGame(String username, int gameId) throws RemoteException;
}
