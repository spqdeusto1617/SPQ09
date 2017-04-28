package es.deusto.client;


import java.rmi.RemoteException;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import es.deusto.server.db.data.*;
import es.deusto.server.remote.*;


public class Client {
	
	private static String[] mainMenu = {"Show games on store", "Show owned games", "Buy game"};
	
	public static void displayMenu(String[] options){
		System.out.println("Insert the option number to select an action. If you want to go back, input 'b'; if you want to exit the application, input 'quit'");
		for(int i = 0; i<options.length; i++){
			System.out.println((i+1) + ".- " + options[i]);
		}
	}
	
	public static void showGames(IRemote server, String username){
		List<Game> games = null;
		try {
			if(username!=null){
				System.out.println("Games owned iF sTART");
				games = server.showOwnedGames(username);
				System.out.println("Games owned IF FINISHED");
			}
			games = server.showGamesInStore();
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
		for(Game g : games){
			
			System.out.println("Game [name=" + g.getName() + ", price=" + g.getPrice() + ", discount=" + g.getDiscount() 
			+" genre=" + g.getGenre().getName() + ", company " + g.getCompany().getName() +   "]");
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
			
			server.registerUser("dipina", "dipina",false);	
			server.registerUser("javier", "qwerty",false);
	
			String username = "aihnoa";
			
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
					showGames(server, "aihnoa");
					break;
				case("3"):
					//Buy game
					System.out.println("Insert a games Id to select it; If you want to go back, input 'b'; if you want to exit the application, input 'quit'");
					showGames(server, null);
					input = System.console().readLine();
					
					String n = "HL1";
					
					if(server.buyGame(username, n)){
						System.out.println("Game bought successfully");
					}
					break;
				case("b"):
					break;
				default:
					System.out.println("Invalid input");
					break;
				}
				
			} while(!(input.equals("exit")));
			
			
			
			
		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}