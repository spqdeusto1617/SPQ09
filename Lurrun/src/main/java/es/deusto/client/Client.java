package es.deusto.client;


import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import es.deusto.server.db.data.*;
import es.deusto.server.remote.*;


public class Client extends JFrame{
	
	private static String[] mainMenu = {"Show games on store", "Show owned games", "Buy game", "Add game"};
	private static List<Game> games = new ArrayList<Game>();
	
	final static Logger logger = LoggerFactory.getLogger(Client.class);
	private static boolean superuser = false;
	private static String defInfo = "Insert the option number to select an action. If you want to exit the application, input 'quit'.";
	private static String name;
	private static IRemote server;
	
	
	
	private String loggedUser;	
	private JList list;
	private JPanel addGame;
	private JTextField userTextField;	
	private JTextField passwordField;
	private JTable tableMyGames;
	private JTable tableAllGames;
	private JTextField txtSearchGame;
	private JTextField txtSearchGenre;
	private JTextField txtsearchCompany;
	private JTextField txtPrice;
	private JTextField txtDiscount;
	
	private JButton btnNewButton;
	private JTable table;
	private JTable table_1;

	
	
	public void loginWindow()
	{
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 175);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setLayout(null);
		addGame.setName("Log in");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
			
			String user = userTextField.getText().toString();		
			
			String pass = passwordField.getText().toString();
			boolean k=true;
			//if(user y pass bien y normal user)			
			if(user.length()!=0 && pass.length()!=0){
			try {
				k=server.loginUser(user, pass);
				superuser=server.isSuperUser(user);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			if(k && !superuser)
			{			
			loggedUser= user;	
			normalUserWindow();	
			}
			else if(k && superuser)
			{
			superUserWindow();				
			}
			else
			{
				JOptionPane.showMessageDialog(addGame,
					    "Invalid User or password.",
					    "",
					    JOptionPane.ERROR_MESSAGE);
				
			}
				
			}else
			{
				JOptionPane.showMessageDialog(addGame,
					    "Empty User or password.",
					    "",
					    JOptionPane.ERROR_MESSAGE);
				
			}
			
			}
			
		});
		
		
		btnLogin.setBounds(236, 33, 110, 23);
		addGame.add(btnLogin);
		
		userTextField = new JTextField();
		userTextField.setBounds(83, 45, 86, 20);
		addGame.add(userTextField);
		userTextField.setColumns(10);
		
		JTextPane txtpnPass = new JTextPane();
		txtpnPass.setEditable(false);
		txtpnPass.setEnabled(false);
		txtpnPass.setText("Password");
		txtpnPass.setBounds(10, 76, 60, 20);
		addGame.add(txtpnPass);
		
		JTextPane txtpnUser = new JTextPane();
		txtpnUser.setEnabled(false);
		txtpnUser.setEditable(false);
		txtpnUser.setText("User");
		txtpnUser.setBounds(10, 45, 50, 20);
		addGame.add(txtpnUser);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(83, 76, 86, 20);
		addGame.add(passwordField);	
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(236, 67, 110, 23);
		addGame.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			 
				registerUserWindow();			
			}
		});
	}
	public void registerUserWindow()
	{
		
		setTitle("Register ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 175);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setLayout(null);
		addGame.setName("Log in");
		
		userTextField = new JTextField();
		userTextField.setBounds(83, 45, 86, 20);
		addGame.add(userTextField);
		userTextField.setColumns(10);
		
		JTextPane txtpnPass = new JTextPane();
		txtpnPass.setEditable(false);
		txtpnPass.setEnabled(false);
		txtpnPass.setText("Password");
		txtpnPass.setBounds(10, 76, 60, 20);
		addGame.add(txtpnPass);
		
		JTextPane txtpnUser = new JTextPane();
		txtpnUser.setEnabled(false);
		txtpnUser.setEditable(false);
		txtpnUser.setText("User");
		txtpnUser.setBounds(10, 45, 50, 20);
		addGame.add(txtpnUser);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(83, 76, 86, 20);
		addGame.add(passwordField);	
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String user = userTextField.getText().toString();		
				
				String pass = passwordField.getText().toString();
				
				if(user.length()!=0 && pass.length()!=0)
				try {
					server.registerUser(user, pass);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				else
				{
					JOptionPane.showMessageDialog(addGame,
						    "Invalid User or password.",
						    "",
						    JOptionPane.ERROR_MESSAGE);
					
					
				}
			}
		});
		
		btnRegister.setBounds(239, 42, 110, 54);
		addGame.add(btnRegister);
	}
	public void normalUserWindow()
	{
		
		setTitle("User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 405);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setName("User");
		addGame.setLayout(null);
		
		JButton btnBuyGame = new JButton("Buy game");
		btnBuyGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buyGameWindow();
			}
		});
		btnBuyGame.setBounds(472, 93, 90, 23);
		addGame.add(btnBuyGame);
		
		JTabbedPane userTab = new JTabbedPane(JTabbedPane.TOP);
		userTab.setBounds(10, 22, 433, 319);
		addGame.add(userTab);
		
		
		//TABLA
		String col[] = {"Name","Company","Gernre" ,"Price" ,"Discount"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		DefaultTableModel tableModel1 = new DefaultTableModel(col, 0) ;
		try {
			games=server.showGamesInStore();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < games.size(); i++)
		{
			   String name = games.get(i).getName();
			   double price = games.get(i).getPrice();
			   double discount = games.get(i).getDiscount();
			   String companyname = games.get(i).getCompany().getName();
			   String genre = games.get(i).getGenre().getName();
			   Object[] data = { name, companyname, genre ,price, discount};
			   tableModel.addRow(data);

		}
		
		Panel myGamesPanel = new Panel();
		userTab.addTab("My games", null, myGamesPanel, null);
		myGamesPanel.setLayout(null);
		
		try {
			games= server.showOwnedGames(loggedUser);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < games.size(); i++){
			   String name = games.get(i).getName();
			   double price = games.get(i).getPrice();
			   double discount = games.get(i).getDiscount();
			   String companyname = games.get(i).getCompany().getName();
			   String genre = games.get(i).getGenre().getName();
			   Object[] data = { name, companyname, genre ,price, discount};
			  
			tableModel1.addRow(data);

			}
	
		tableMyGames = new JTable(tableModel);
		tableMyGames.setBounds(0, 0, 428, 291);
		myGamesPanel.add(tableMyGames);
		
		Panel allGamesPanel = new Panel();
		allGamesPanel.setLayout(null);
		userTab.addTab("All Games", null, allGamesPanel, null);
		
		tableAllGames = new JTable(tableModel1);
		tableAllGames.setBounds(0, 0, 428, 291);
		allGamesPanel.add(tableAllGames);
		
		
		
		

		
		JButton btnMyWallet = new JButton("My Wallet");
		btnMyWallet.setBounds(473, 127, 89, 23);
		addGame.add(btnMyWallet);	
		btnMyWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			System.out.println("Enseña el dinero en un pop up");
			}
		});
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			loginWindow();
			}
		});
		btnLogOut.setBounds(472, 296, 89, 23);
		addGame.add(btnLogOut);
	}
	public void buyGameWindow()
	{
		DefaultListModel model = new DefaultListModel();
		setTitle("Buy Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 308);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setName("User");
		addGame.setLayout(null);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				normalUserWindow();
			}
		});
		btnCancel.setBounds(272, 238, 89, 23);
		addGame.add(btnCancel);
		
		JButton btnBuyGame_1 = new JButton("Buy Game");
		btnBuyGame_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selected = (String) list.getSelectedValue();
				if(selected!=null)
				try {
					server.buyGame(loggedUser, selected);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				else
				{
					JOptionPane.showMessageDialog(addGame,
						    "No game selected.",
						    "",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		btnBuyGame_1.setBounds(93, 238, 89, 23);
		addGame.add(btnBuyGame_1);
		
		txtSearchGame = new JTextField();
		txtSearchGame.setBounds(92, 11, 75, 20);
		addGame.add(txtSearchGame);
		txtSearchGame.setColumns(10);
		
		JTextPane txtpnSearch = new JTextPane();
		txtpnSearch.setEditable(false);
		txtpnSearch.setEnabled(false);
		txtpnSearch.setText("Name");
		txtpnSearch.setBounds(29, 11, 42, 20);
		addGame.add(txtpnSearch);
		
		JTextPane txtpnGenre = new JTextPane();
		txtpnGenre.setText("Genre");
		txtpnGenre.setEnabled(false);
		txtpnGenre.setEditable(false);
		txtpnGenre.setBounds(28, 42, 33, 20);
		addGame.add(txtpnGenre);
		
		txtSearchGenre = new JTextField();
		txtSearchGenre.setColumns(10);
		txtSearchGenre.setBounds(93, 42, 75, 20);
		addGame.add(txtSearchGenre);
		
		JTextPane txtpnCompany = new JTextPane();
		txtpnCompany.setText("Company");
		txtpnCompany.setEnabled(false);
		txtpnCompany.setEditable(false);
		txtpnCompany.setBounds(196, 11, 66, 20);
		addGame.add(txtpnCompany);
		
		txtsearchCompany = new JTextField();
		txtsearchCompany.setColumns(10);
		txtsearchCompany.setBounds(272, 11, 75, 20);
		addGame.add(txtsearchCompany);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String gamename= txtsearchCompany.getText();
				String genre =txtSearchGenre.getText();
				String companyname=txtSearchGame.getText();
				boolean valido=true;
				model.removeAllElements();
				for(int i = 0; i < games.size(); i++)
				{
					
					if(gamename.length()!=0)
					{
						valido=games.get(i).getName().contains(gamename);
						
					}
					if(genre.length()!=0 && valido==true){
						valido=games.get(i).getGenre().getName().contains(genre);
						
					}
					if(companyname.length()!=0 &&valido==true)
					{
						valido=games.get(i).getCompany().getName().contains(companyname);
						
					}
					if(companyname.length()!=0&&gamename.length()!=0&&genre.length()!=0)
					{
						valido=true;
						
					}									
					if(valido)
					{
						model.addElement(games.get(i).toString());
					}
					
				}
				
				
			}
		});
		btnSearch.setBounds(357, 27, 89, 23);
		addGame.add(btnSearch);
		
		
		
	    list = new JList(model);   
	    JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(46, 85, 365, 138);	   
	    for (int i = 0; i < games.size(); i++)
	    {	    
	      model.addElement(games.get(i).getName());
	    }
		
	    addGame.add(scrollPane);
		
		
		
	}
	public void superUserWindow()
	{
		
		String col[] = {"Name","Company","Gernre" ,"Price" ,"Discount"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
	
	
		for (int i = 0; i < games.size(); i++)
		{
			   String name = games.get(i).getName();
			   double price = games.get(i).getPrice();
			   double discount = games.get(i).getDiscount();
//			   String companyname = games.get(i).getCompany().getName();
//			   String genre = games.get(i).getGenre().getName();
			   Object[] data = { name ,price, discount};
			   tableModel.addRow(data);

		}
//
		setTitle("Super User Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 481);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setLayout(null);
		addGame.setName("Log in");
		
		JButton btnAddGame_1 = new JButton("Add game");
		btnAddGame_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGameWindow();
			}
		});
		btnAddGame_1.setBounds(161, 395, 178, 23);
		addGame.add(btnAddGame_1);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			loginWindow();
			}
		});
		btnExit.setBounds(427, 395, 186, 23);
		addGame.add(btnExit);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(50, 41, 652, 331);
		addGame.add(tabbedPane);
		
		JPanel shopPanel = new JPanel();
		tabbedPane.addTab("Shop", null, shopPanel, null);
		shopPanel.setLayout(null);
		
		table = new JTable(tableModel);
		table.setBounds(0, 0, 647, 303);
		shopPanel.add(table);		
	}
	public void addGameWindow()	
	{
		
		
		setTitle("Add Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 249);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setName("User");
		addGame.setLayout(null);
		
		JButton btnAddGame = new JButton("Add Game");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String gnName = txtSearchGame.getText();
				double price =  Double.parseDouble(txtPrice.getText());			
				double disc = Double.parseDouble(txtPrice.getText());
				String gg = txtSearchGenre.getText();
				String c= txtsearchCompany.getText();
				if(gnName.length()==0||gnName.length()==0||gnName.length()==0||gg.length()==0||c.length()==0)
				{
					JOptionPane.showMessageDialog(addGame,
						    "Fill all fields please.",
						    "",
						    JOptionPane.ERROR_MESSAGE);				
				}
				else{
				try {
					server.addGame(gnName, price, disc, gg, c);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
			}
		});
		btnAddGame.setBounds(123, 159, 119, 23);
		addGame.add(btnAddGame);
		
		txtSearchGame = new JTextField();
		txtSearchGame.setBounds(80, 24, 75, 20);
		addGame.add(txtSearchGame);
		txtSearchGame.setColumns(10);
		
		JTextPane txtpnSearch1 = new JTextPane();
		txtpnSearch1.setEditable(false);
		txtpnSearch1.setEnabled(false);
		txtpnSearch1.setText("Name");
		txtpnSearch1.setBounds(20, 24, 50, 20);
		addGame.add(txtpnSearch1);
		
		JTextPane txtpnGenre1 = new JTextPane();
		txtpnGenre1.setText("Genre");
		txtpnGenre1.setEnabled(false);
		txtpnGenre1.setEditable(false);
		txtpnGenre1.setBounds(337, 24, 75, 20);
		addGame.add(txtpnGenre1);
		
		txtSearchGenre = new JTextField();
		txtSearchGenre.setColumns(10);
		txtSearchGenre.setBounds(422, 24, 75, 20);
		addGame.add(txtSearchGenre);
		
		JTextPane txtpnCompany1 = new JTextPane();
		txtpnCompany1.setText("Company");
		txtpnCompany1.setEnabled(false);
		txtpnCompany1.setEditable(false);
		txtpnCompany1.setBounds(165, 24, 66, 20);
		addGame.add(txtpnCompany1);
		
		txtsearchCompany = new JTextField();
		txtsearchCompany.setColumns(10);
		txtsearchCompany.setBounds(252, 24, 75, 20);
		addGame.add(txtsearchCompany);
		
		btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superUserWindow();
			}
		});
		btnNewButton.setBounds(305, 159, 125, 23);
		addGame.add(btnNewButton);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(80, 55, 75, 20);
		addGame.add(txtPrice);
		
		txtDiscount = new JTextField();
		txtDiscount.setColumns(10);
		txtDiscount.setBounds(80, 86, 75, 20);
		addGame.add(txtDiscount);
		
		JTextPane Price = new JTextPane();
		Price.setEditable(false);
		Price.setEnabled(false);
		Price.setText("Price");
		Price.setBounds(20, 55, 50, 20);
		addGame.add(Price);
		
		JTextPane txtpnDiscount = new JTextPane();
		txtpnDiscount.setText("Discount");
		txtpnDiscount.setEnabled(false);
		txtpnDiscount.setEditable(false);
		txtpnDiscount.setBounds(20, 86, 50, 20);
		addGame.add(txtpnDiscount);

		
	}		
	public Client() 
	{
					

			loginWindow();		

		
		
			
	}

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
			 name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			 server = (IRemote) java.rmi.Naming.lookup(name);
			
			
				Client frame = new Client();
				frame.setVisible(true);	
			
			
			
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