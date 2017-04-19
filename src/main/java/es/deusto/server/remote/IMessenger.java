package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.server.data.Company;
import es.deusto.server.data.Game;
import es.deusto.server.data.Genre;
import es.deusto.server.data.User;

public interface IMessenger extends Remote {
	
	String sayMessage(String login, String password, String message) throws RemoteException;
	void registerUser(String login, String password) throws RemoteException;
	User getUserMessages(String login) throws RemoteException;
	void addStufToDb(Game g,Genre gg, Company c) throws RemoteException;
	void getStuf() throws RemoteException;
	void getGamesFromDB() throws RemoteException;
}
