package es.deusto.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.server.db.data.*;

public interface IServer extends Remote {
	
//	String sayMessage(String login, String password, String message) throws RemoteException;
//	void registerUser(String login, String password) throws RemoteException;
	String sayHello() throws RemoteException;
	ArrayList<Game> showGamesInStore() throws RemoteException;
	ArrayList<Game> showOwnedGames(String username) throws RemoteException;
	boolean buyGame(String username, int gameId) throws RemoteException;
}
