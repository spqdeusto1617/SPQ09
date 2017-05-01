package es.deusto.client;


import java.rmi.RemoteException;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import es.deusto.server.db.data.*;
import es.deusto.server.remote.*;


public class Client {
	
	private static String[] mainMenu = {"Show games on store", "Show owned games", "Buy game"};
	private static List<Game> games = null;
	private static String info = "If you want to go back, input 'b'; if you want to exit the application, input 'quit'";
	
	private static void displayMenu(String[] options){
		System.out.println("");
		System.out.println("Insert the option number to select an action. " + info);
		for(int i = 0; i<options.length; i++){
			System.out.println((i+1) + ".- " + options[i]);
		}
	}
	
	private static void showGames(IRemote server, String login){
//		List<License> ownedLicenses = null;
		String sentence = null;
		try {
			
			if(login!=null){
//				User  u = server.getUser(login);
//				ownedLicenses = u.getLicenses();
				sentence = "games owned by user" + login;
				games = server.showOwnedGames(login);
//				System.out.println("Owned Licenses: ");				
				
//				for(License license : ownedLicenses){				
//				System.out.println("	"+license.toString());
//				System.out.println("	"+license.getGame());					
//				}						
			}
			else{
				sentence = "games in the store";
				games = server.showGamesInStore();
			}
			
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
		if(games.isEmpty()){
			System.out.println("No " + sentence);
		}
		else{
			System.out.println("Show " + sentence);
			for(int i = 0; i < games.size(); i++){												
				System.out.println((i+1) + ".-" + games.get(i).toString());	
			}
		}
	}

	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IRemote server = (IRemote) java.rmi.Naming.lookup(name);
			
			boolean log = true;
			while(log){
//				System.out.println("Introduce username:");
//				String login = System.console().readLine();
//				System.out.println("Introduce password:");
//				if(server.registerUser(login, System.console().readLine(), false)){
				String login = "ainhoa";
				if(server.registerUser(login, "qwerty", false)){
					log = false;

					String input = "";						
					do{
						displayMenu(mainMenu);
						input = System.console().readLine();
						switch(input){
						case("1"):
							//Show games
							showGames(server, null);
							break;
						case("2"):
							//Show current user's games
							showGames(server, login);
							break;
						case("3"):
							//Buy game
							System.out.println("Insert a game's Id to select it. " + info);
							showGames(server, null);
							input = System.console().readLine();
							int id = Integer.parseInt(input)-1;
							String gameName = games.get(id).getName();
							if(server.buyGame(login, gameName)){
								System.out.println("Game bought successfully");
							}
							break;
						case("b"):
							break;
						case("quit"):
							break;
						default:
							System.out.println("Invalid input");
							break;
						}
						
					} while(!(input.equals("quit")));
					System.out.println("See you soon! :D");
				}
				else{
					System.out.println("Incorrect login. Try again? Y|N");
					String input = System.console().readLine();
					if(input.equals("N")){
						log = false;
					}
				}
			}

		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}