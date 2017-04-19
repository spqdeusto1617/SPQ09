package es.deusto.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import es.deusto.server.data.*;

public interface IMessenger extends Remote {
	
	String sayMessage(String login, String password, String message) throws RemoteException;
	void registerUser(String login, String password,boolean isSuperUser) throws RemoteException;
	User getUserMessages(String login) throws RemoteException;
	void addStufToDb(Game g,Genre gg, Company c) throws RemoteException;
	void addLicenseToUser(User u, License l) throws RemoteException;
	void addLicenseToGame(Game g, License l) throws RemoteException;
	void showGameInfo(String game,String company,String genre) throws RemoteException;
	void showLicenseInfo(String user,String license,String game) throws RemoteException;
	void getGamesFromDB() throws RemoteException;
}
