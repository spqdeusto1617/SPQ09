package es.deusto.client;


import java.rmi.RemoteException;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.*;


public class Client {
	
	private static String[] mainMenu = {"Show games on store", "Show owned games", "Buy game"};
	private static List<Game> games = null;
	final static Logger logger = LoggerFactory.getLogger(Client.class);
	private static void displayMenu(String[] options){
		
		logger.info("\nInsert the option number to select an action. If you want to exit the application, input 'quit'.");
		for(int i = 0; i<options.length; i++){
			logger.info((i+1) + ".- " + options[i]);
		}
	}
	/**
	 * Method to show the games that the logged user owns
	 * @param server, login
	 */
	private static void showGames(IRemote server, String login){
		List<License> ownedLicenses = null;
		String sentence = null;
		try {
			if(login!=null){
				sentence = "games owned by user " + login;
				games = server.showOwnedGames(login);	
			}
			else{
				sentence = "games in the store";
				games = server.showGamesInStore();
			}
			
		} catch (RemoteException e) {
			logger.info(e.getMessage());
		}
		
		if(games.isEmpty()){
			logger.info("No " + sentence);
		} 
		else {
			logger.info("Show " + sentence);
			for(int i = 0; i < games.size(); i++){
				Game g = games.get(i);
				logger.info((i+1) + ".-" + g.toString());
				List<License> la = g.getLicenses();
				if(!la.isEmpty()){
					for(License a : la){
						if (a.isUsed()==false){
							logger.info("Free license avaliable");
							break;
						}
					}
				} 
				else {
					logger.info("No available licenses");
				}
			}	
		}
	}

	public static void main(String[] args) {
		
		if (args.length != 3) {
			logger.info("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
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
				logger.info("\nFor loggin press '1'; for registering press '2'");
				int logreg = Integer.parseInt(System.console().readLine());
				boolean pass = false;
				logger.info("Insert username:");
				String login = System.console().readLine();
				logger.info("Insert password:");
				String password = String.valueOf(System.console().readPassword());
				if(logreg == 1){
					pass = server.loginUser(login, password);
				}
				else if (logreg == 2){
					pass = server.registerUser(login, password);
				}
				else{
					logger.error("Non valid input");
				}
				
				
				if(pass){
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
							logger.info("\nInsert a game's Id to select it. If you want to go back, input 'b'.");
							logger.info("Available money: " + server.getUserWallet(login));
							showGames(server, null);
							input = System.console().readLine();
							if(input.equals("b")){
								break;
							}
							int id = Integer.parseInt(input)-1;
							String gameName = games.get(id).getName();
							if(server.buyGame(login, gameName)){
								logger.info("Game bought successfully");
							}
							break;
						case("quit"):
							break;
						default:
							logger.info("Invalid input");
							break;
						}
						
					} while(!(input.equals("quit")));
					logger.info("See you soon! :D");
				}
				else{
					logger.info("Incorrect login. Try again? Y|N");
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