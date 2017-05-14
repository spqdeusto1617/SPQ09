package es.deusto.server;

import java.rmi.Naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.server.db.DB;
import es.deusto.server.db.IDB;
import es.deusto.server.db.data.Company;
import es.deusto.server.db.data.Game;
import es.deusto.server.db.data.Genre;
import es.deusto.server.db.data.License;
import es.deusto.server.db.data.User;
import es.deusto.server.remote.*;


public class Server{
	final static Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static void basicData(){
		
		Game g = new Game("VTM: Bloodlines",20,0.2);
		Game g1 =new Game("Golden Sun",15,0.1);
		Game g2 =new Game("Fable: The Lost Chapters",25,0.25);
		Game g3= new Game("Undertale",10,0.2);
		
		Genre gg1 = new Genre ("Vampire");
		Genre gg2 = new Genre ("RPG");
		Genre gg3 = new Genre ("JRPG");
		Genre gg4 = new Genre ("Puzzle");
		
		Company c1 = new Company ("White Wolf");
		Company c2 = new Company ("Camelot");
		Company c3 = new Company ("Lionhead Studios");
		Company c4 = new Company ("Toby Fox");
		
		User u1 = new User("ainhoa", "ainhoa", true);
		User u2 = new User("Joel", "qwerty", false);
		User u3 = new User("Cabezali", "qwerty", false);
		
		IDB db = new DB();
		
		db.addGameToDb( g, gg1, c1);
		db.addGameToDb( g1, gg2, c2);
		db.addGameToDb( g2, gg3, c3);
		db.addGameToDb( g3, gg4, c4);	

		db.registerUser(u1);
		db.registerUser(u2);
		db.registerUser(u3);
	}


	public static void main(String[] args) {
		
		if (args.length != 3) {
			logger.info("[S] How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
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
			basicData();
			
			logger.info("[S] Server '" + name + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
			
		} catch (Exception e) {
			logger.error("[S] Server exception: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
