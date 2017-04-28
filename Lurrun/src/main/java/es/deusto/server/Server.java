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
		
		Game g =new Game("HL1",200,0.2);
		Game g1 =new Game("HL2",200,0.2);
		Game g2 =new Game("Skyrim",200,0.2);
		Game g3= new Game("Oblivion",200,0.2);
		Genre gg = new Genre ("FPS");
		Genre gg1 = new Genre ("Rol");
		Company c = new Company ("Valve");
		Company c1 = new Company ("Bethesda");
		License l = new License ("Hl1:ABCD");
		License l1 = new License ("Sky:ABCDEF");
		User a =  new User ("aihnoa","qwerty",false);
		
		IDB db = new DB();
		
		db.addGameToDb( g, gg, c);
		
		db.addGameToDb( g1, gg, c);
		db.addGameToDb( g2, gg1, c1);
		db.addGameToDb( g3, gg1, c1);
	
	
		db.addLicenseToGame(g, l);
		db.addLicenseToGame(g2, l1);
		
		db.registerUser("aihnoa", "qwerty", false);

	//	db.buyGame(a.getLogin(), g.getName());
	//	db.buyGame(a.getLogin(), g2.getName());
	
		
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
