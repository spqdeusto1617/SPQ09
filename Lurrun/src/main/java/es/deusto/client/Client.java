package es.deusto.client;


import java.rmi.RemoteException;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.*;


public class Client {
	
	private static String[] mainMenu = {"Show games on store", "Show owned games", "Buy game", "Add game"};
	private static List<Game> games = null;
	final static Logger logger = LoggerFactory.getLogger(Client.class);
	private static boolean superuser = false;
	private static String defInfo = "Insert the option number to select an action. If you want to exit the application, input 'quit'.";
	
	private static void displayMenu(String[] options, String info){
		logger.info(info);
		int len = options.length;
		if(!superuser){
			len--;
		}
		for(int i = 0; i<len; i++){
			logger.info((i+1) + ".- " + options[i]);
		}
	}
	
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
//			logger.info(e.getMessage());
			games = null;
		}
		
		if(games == null || games.isEmpty()){
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
				logger.info("To log in press '1'; for registering press '2'");
				int logreg = Integer.parseInt(System.console().readLine());
				boolean pass = false;
				logger.info("Insert username:");
				String login = System.console().readLine();
				logger.info("Insert password:");
				String password = String.valueOf(System.console().readPassword());
				
				try{
					if(logreg == 1){
						pass = server.loginUser(login, password);
						superuser = server.isSuperUser(login);
					}
					else if (logreg == 2){
						pass = server.registerUser(login, password);
					}
					else{
						logger.error("Non valid input");
					}
				} catch (RemoteException e){
					logger.error("Remote exception when trying to log in");
					pass = false;
					
				}
				if(pass){
					if(superuser){
						logger.info("Hello superuser!");
					}
					log = false;
					String input = "";						
					do{
						displayMenu(mainMenu, defInfo);
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
							logger.info("Insert a game's Id to select it. If you want to go back, input 'b'.");
							logger.info("Available money: " + server.getUserWallet(login));
							showGames(server, null);
							input = System.console().readLine();
							if(input.equals("b")){
								break;
							}
							int id = Integer.parseInt(input)-1;
							String gameName = "";
							try{
								gameName = games.get(id).getName();
							} catch (Exception e){
								logger.error("Invalid input");
								break;
							}
							try{
								if(server.buyGame(login, gameName)){
									logger.info("Game bought successfully");
								}
							} catch (RemoteException e){
								logger.error("Remote exception when trying to buy a game");
							}
							break;
						case("4"):
							//Add new game only if superUser
							if(superuser){
								String gName = "";
								double gPrice = 0.0;
								double gDisc = 0.0;
								//Input name, price and discount
								logger.info("Input new game name:");
								gName = System.console().readLine();
								try{
									logger.info("Input new game price:");
									gPrice = Double.parseDouble(System.console().readLine());
									logger.info("Input new game discount:");
									gDisc = Double.parseDouble(System.console().readLine());
								} catch (Exception e){
									logger.info("Invalid input");
									break;
								}
								
								try{
									//Choose Company
									int choose = 0;
									String[] chooseList = server.getAllCompanies();
									displayMenu(chooseList, "Select a company");
									choose = Integer.parseInt(System.console().readLine())-1;
									String cName = chooseList[choose];
									
									//Coose Genre
									chooseList = server.getAllGenres();
									displayMenu(chooseList, "Select a Genre");
									choose = Integer.parseInt(System.console().readLine())-1;
									String ggName = chooseList[choose];
									if(server.addGame(gName, gPrice, gDisc, ggName, cName)){
										logger.info("New game added successfully");
									}
								} catch (RemoteException e){
									logger.error("Remote exception on the process of adding a game to the DB");
								}
								
								break;
							}
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
			System.err.println("[GenericException] Unexpected exception caught on the code: " + e.getMessage());
			e.printStackTrace();
		}
	}
}