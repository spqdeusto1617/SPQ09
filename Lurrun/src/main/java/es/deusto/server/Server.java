package es.deusto.server;

import java.rmi.Naming;

import es.deusto.server.db.DB;
import es.deusto.server.db.IDB;
import es.deusto.server.db.data.Company;
import es.deusto.server.db.data.Game;
import es.deusto.server.db.data.Genre;
import es.deusto.server.db.data.License;
import es.deusto.server.db.data.User;
import es.deusto.server.remote.*;


public class Server{

public static void addStuff(){
		
		Game g = new Game("Game 1",200,0.2);
		Game g1 =new Game("Game 2",300,0.1);
		Game g2 =new Game("Game 3",20,0.25);
		Game g3= new Game("Game 4",250,0.2);
		Game g4 =new Game("Game 5",26,0.3);
		Game g5 =new Game("Game 6",34,0.75);
		Game g6 =new Game("Game 7",78,0.80);
		Game g7= new Game("Game 8",69,0.05);
		
		Genre gg1 = new Genre ("Genre 1");
		Genre gg2 = new Genre ("Genre 2");
		Genre gg3 = new Genre ("Genre 3");
		Genre gg4 = new Genre ("Genre 4");
		Genre gg5 = new Genre ("Genre 5");
		
		
		Company c1 = new Company ("Company 1");
		Company c2 = new Company ("Company 2");
		Company c3 = new Company ("Company 3");
		Company c4 = new Company ("Company 4");
		Company c5 = new Company ("Company 5");
		
		
		License l1 = new License ("AAAAA");
		License l2 = new License ("BBBBB");
		License l3 = new License ("CCCCCC");		
		License l4 = new License ("DDDDDDDDDD");
		License l5 = new License ("FFFAAAAAFFFF");
		License l6 = new License ("12345667");
		
		User u1 = new User("aihnoa", "qwerty", false);
		User u2 = new User("Joel", "qwerty", false);
		User u3 = new User("Cabezali", "qwerty", false);
		
		IDB db = new DB();
		

			db.addGameToDb( g, gg1, c1);
			db.addGameToDb( g1, gg2, c2);
			db.addGameToDb( g2, gg3, c3);
			db.addGameToDb( g3, gg4, c4);	
			
			db.addGameToDb( g4, gg3, c5);		
			db.addGameToDb( g5, gg5, c4);			
			db.addGameToDb( g6, gg4, c4);	
			db.addGameToDb( g7, gg3, c3);

			db.addLicenseToGame(g1, l1);
			db.addLicenseToGame(g1, l1);
			db.addLicenseToGame(g3, l2);
			db.addLicenseToGame(g1, l3);
			db.addLicenseToGame(g2, l4);
			db.addLicenseToGame(g3, l5);
		
		
		db.registerUser(u1);
		db.registerUser(u2);
		db.registerUser(u3);
		
		db.buyGame(u1.getLogin(), g1.getName());
		db.buyGame(u2.getLogin(), g1.getName());
	
		
	}
	
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("[S] How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IRemote objServer = new Remote();
			Naming.rebind(name, objServer);
			
			//DB testing
			addStuff();
			
			System.out.println("[S] Server '" + name + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
			
		} catch (Exception e) {
			System.err.println("[S] Server exception: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
