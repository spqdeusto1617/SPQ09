package lurrun.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILurrunServer extends Remote{
	
	String searchGame(String login, String password, String message) throws RemoteException;
	
}
