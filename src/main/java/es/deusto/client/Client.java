package es.deusto.client;

import java.rmi.RMISecurityManager;

import es.deusto.server.data.*;
import es.deusto.server.remote.IMessenger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import es.deusto.server.dao.IUserDAO;
import es.deusto.server.dao.UserDAO;
import es.deusto.server.data.*;

@SuppressWarnings("deprecation")
public class Client {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IMessenger objHello = (IMessenger) java.rmi.Naming.lookup(name);
			// Register to be allowed to send messages
		
			System.out.println("Register a user for the first time: dipina");
			objHello.registerUser("dipina", "dipina");
			System.out.println("Change the password as the user is already registered: cortazar");
			objHello.registerUser("dipina", "cortazar");
			
			System.out.println("Register a user for the first time: dipina");
			
			objHello.registerUser("Javier", "qwerty");
			
		//Método a cambiar	
			Game g =new Game("HL4",200,0.2);
			Genre gg = new Genre ("FPS4");
			Company c = new Company ("Valve4");
			
			
		
			System.out.println("1- Add Stuff AAA");
			objHello.addStufToDb( g, gg, c);
	
			
		//	System.out.println("2- get stuff");
		//	objHello.getStuf();
		
			
		//	System.out.println("3- List of GAMES from DB");
		//	objHello.getGamesFromDB();
			
		
			
			System.out.println("* Message coming from the server: '" + objHello.sayMessage("dipina", "cortazar", "This is test 1!") + "'");
			System.out.println("* Message coming from the server: '" + objHello.sayMessage("dipina", "cortazar", "This is test 2!") + "'");
			User u = objHello.getUserMessages("dipina");
			for (Message m: u.getMessages()) {
				
				System.out.println(m);
		
				
			}
			
		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}