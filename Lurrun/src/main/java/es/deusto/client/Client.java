package es.deusto.client;


import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JList list_1;
	private JList list_2;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;


	private JComboBox comboBox;
	private JComboBox comboBox_1;
	
	private static List<Game> games = new ArrayList<Game>();
	private static List<Game> gameUsers = new ArrayList<Game>();
	private static List<String> genres= new ArrayList<String>();
	private static List<String> companies = new ArrayList<String>();
	
	public void loadAllArrayList()
	{
		
		try {
			companies=server.getAllCompanies();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			genres=server.getAllGenres();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			games=server.showGamesInStore();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loginWindow()
	{
		setResizable(false);
		loadAllArrayList();
		
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
			catch (NullPointerException e1)
			{
				JOptionPane.showMessageDialog(addGame,
					    "Invalid User or password.",
					    "",
					    JOptionPane.ERROR_MESSAGE);
				
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
				String user = userTextField.getText().toString();			
				String pass = passwordField.getText().toString();			
				if(user.length()!=0 && pass.length()!=0)
				try {
					server.registerUser(user, pass);
					loggedUser=user;
					normalUserWindow();
				
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
	}	
	public void normalUserWindow()
	{
		setResizable(false);
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
		String col1[] = {"Name","Gernre","Company" };
		DefaultTableModel storeTable = new DefaultTableModel(col, 0);
		DefaultTableModel mygamesTable = new DefaultTableModel(col1, 0) ;
		try {
			
			games=server.showGamesInStore();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
		}
		Object[] header={"Name","Genre","Company","Price","Discount"};
		storeTable.addRow(header);
		for (int i = 0; i < games.size(); i++)
		{
				
			   String name1 = games.get(i).getName();
			 
			   double price = games.get(i).getPrice();
			   double discount = games.get(i).getDiscount();
			   String companyname = games.get(i).getCompany().getName();
			   String genre = games.get(i).getGenre().getName();
			   Object[] data = { name1 ,companyname, genre ,price, discount};//
			   storeTable.addRow(data);

		}
		
		Panel myGamesPanel = new Panel();
		userTab.addTab("My games", null, myGamesPanel, null);
		myGamesPanel.setLayout(null);
		
		try {
			
			gameUsers= server.showOwnedGames(loggedUser);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			
		}
		
		try{
			Object[] header1={"Name","Genre","Company"};
			mygamesTable.addRow(header1);
		if(!gameUsers.isEmpty())
			
		for (int i = 0; i < gameUsers.size(); i++){
			   String name1 = gameUsers.get(i).getName();

			   String companyname = gameUsers.get(i).getCompany().getName();
			   String genre = gameUsers.get(i).getGenre().getName();
			   Object[] data = { name1,genre ,companyname }; //, genre
			  
			   mygamesTable.addRow(data);

			}
		}catch(NullPointerException e)
		{

//			e.printStackTrace();
		}
	
		tableMyGames = new JTable(mygamesTable);
		tableMyGames.setBounds(0, 0, 428, 291);
		myGamesPanel.add(tableMyGames);
		
		Panel allGamesPanel = new Panel();
		allGamesPanel.setLayout(null);
		userTab.addTab("All Games", null, allGamesPanel, null);
		
		tableAllGames = new JTable(storeTable);
		tableAllGames.setBounds(0, 0, 428, 291);
		allGamesPanel.add(tableAllGames);	
		
		JButton btnMyWallet = new JButton("My Wallet");
		btnMyWallet.setBounds(473, 127, 89, 23);
		addGame.add(btnMyWallet);	
		btnMyWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
					JOptionPane.showMessageDialog(addGame,
						    "You have"
						    + server.getUserWallet(loggedUser) + "lelreles",    		//
						    "Wallet",
						    JOptionPane.PLAIN_MESSAGE);
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
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
		

		setResizable(false);
		DefaultListModel modelgam = new DefaultListModel();
		
		setTitle("Buy Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 656, 342);
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
		btnCancel.setBounds(335, 253, 113, 37);
		addGame.add(btnCancel);
		
		JButton btnBuyGame_1 = new JButton("Buy Game");
		btnBuyGame_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String selected = (String) list.getSelectedValue();				
				
			
				if(selected!=null){
				
				String[] parts = selected.split("//");
				selected=parts[1].trim();
				
				boolean puedePagar=false;
				int r=0;
				for( r=0;r<games.size();r++)
				{
					System.out.println("entra");
				if(games.get(r).getName().equals(selected))
				{
					System.out.println("encuentra");
					
					try {
						if(games.get(r).getPrice() <=server.getUserWallet(loggedUser) )
						{
							
							puedePagar=true;
							
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						System.out.println("coge mal la wallet");
						e1.printStackTrace();
					}
					
					break;
				}
				}
				if(puedePagar)
				try {	
					System.out.println("pop up");				
					int selectedOption = JOptionPane.showConfirmDialog(addGame, 
                            "Are you sure you want to buy this game", 
                            "Confirm", 
                            JOptionPane.YES_NO_OPTION); 
							if (selectedOption == JOptionPane.YES_OPTION) 
							{
								
								server.buyGame(loggedUser, selected);						
								normalUserWindow();				
								server.setUserWallet(server.getUserWallet(loggedUser)-games.get(r).getPrice(), loggedUser);
							}													
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		btnBuyGame_1.setBounds(130, 253, 140, 37);
		addGame.add(btnBuyGame_1);
		
		txtSearchGame = new JTextField();
		txtSearchGame.setBounds(92, 42, 75, 20);
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
		txtpnGenre.setBounds(215, 11, 66, 20);
		addGame.add(txtpnGenre);
		
		JTextPane txtpnCompany = new JTextPane();
		txtpnCompany.setText("Company");
		txtpnCompany.setEnabled(false);
		txtpnCompany.setEditable(false);		
		txtpnCompany.setBounds(373, 11, 66, 20);
		addGame.add(txtpnCompany);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String gamename= txtSearchGame.getText();
				String genre =(String) comboBox.getSelectedItem().toString();
				String companyname=(String) comboBox_1.getSelectedItem().toString();
				boolean valido=true;
				modelgam.removeAllElements();
				for(int i = 0; i < games.size(); i++)
				{
					
					if(gamename.length()!=0)
					{
						
						valido=games.get(i).getName().contains(gamename);
						
					}
					if(genre!=null && valido==true && genre!="All"){
						
						valido=games.get(i).getGenre().getName().contains(genre);
					
						
					}
					if(companyname!=null &&valido==true && companyname!="All")
					{
						
						valido=games.get(i).getCompany().getName().contains(companyname);
						
					}
					else if((companyname=="All" &&gamename.length()==0&& genre=="All"))
					{
						valido=true;	
						
					}									
					if(valido)
					{
						modelgam.addElement("//  " +games.get(i).getName() + "  // Price ->" + games.get(i).getPrice()+ "  Discount ->  " + games.get(i).getDiscount() );
					}
					valido=true;
				}
				if(list.getModel().getSize()==0)
				{
					JOptionPane.showMessageDialog(addGame,
						    "No Game found try, search is case sensitive.",
						    "",
						    JOptionPane.OK_OPTION);
				}
				
			}
		});
		btnSearch.setBounds(551, 41, 89, 23);
		addGame.add(btnSearch);
		
		
		
	    list = new JList(modelgam);   
	    for (int i = 0; i < games.size(); i++)
	    {	    
	    	modelgam.addElement("//  "+games.get(i).getName() + "  // Price ->" + games.get(i).getPrice()+ "// Discount ->  " + games.get(i).getDiscount() );
	    }
	    
	    
	    JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(82, 104, 439, 138);	   
		 	List<String> genresStat = genres;
		 	genresStat.add(0, "All");
		 	comboBox = new JComboBox(genresStat.toArray());	
			comboBox.setBounds(215, 41, 113, 23);
			addGame.add(comboBox);
			List<String>companiesStat = companies;
			companiesStat.add(0, "All");
			comboBox_1 = new JComboBox(companiesStat.toArray());
			comboBox_1.setBounds(383, 42, 119, 20);
			addGame.add(comboBox_1);
	    
	    
	    addGame.add(scrollPane);
	    
	}
	public void superUserWindow()
	{
		loadAllArrayList();
		setResizable(false);
		String col[] = {"Name","Company","Gernre" ,"Price" ,"Discount"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
	
	
		for (int i = 0; i < games.size(); i++)
		{
			   String name = games.get(i).getName();
			   double price = games.get(i).getPrice();
			   double discount = games.get(i).getDiscount();
			   String companyname = games.get(i).getCompany().getName();
			   String genre = games.get(i).getGenre().getName();
			   Object[] data = { name , genre , companyname ,price, discount};
			   tableModel.addRow(data);

		}

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
		loadAllArrayList();		
		setResizable(false);
		setTitle("Add Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 249);
		addGame = new JPanel();
		addGame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(addGame);
		addGame.setName("User");
		addGame.setLayout(null);
		
		JButton btnAddGame = new JButton("Add Game");
		btnAddGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String gnName = txtSearchGame.getText();
				String priceStr=txtPrice.getText();
				String discStr= txtDiscount.getText();
				
				String gg = (String) comboBox.getSelectedItem().toString();
				String c= (String) comboBox_1.getSelectedItem().toString();
				if(gnName.length()==0||priceStr.length()==0||discStr.length()==0)//gg==null||c==null
				{
					
					
					
					JOptionPane.showMessageDialog(addGame,
						    "Fill all fields please.",
						    "",
						    JOptionPane.ERROR_MESSAGE);				
				}
				else
				{
				try{
				double price =  Double.parseDouble(txtPrice.getText());
				
				double disc = Double.parseDouble(txtDiscount.getText());
				if(disc<=100 || disc >0){
					if(price>=0){
				try {			
					server.addGame(gnName, price, disc, gg, c);
					superUserWindow();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(addGame,
							  "Game Already in the database",
							 "",
								    JOptionPane.ERROR_MESSAGE);	
				}
					}
					else
					{
						JOptionPane.showMessageDialog(addGame,
								  "Insert a price value that's 0 or higher",
								 "Invalid Price",
									    JOptionPane.ERROR_MESSAGE);	
					}
				}
				else{
					JOptionPane.showMessageDialog(addGame,
							  "Insert a discount from 0 to 100",
							 "Invalid Discount",
								    JOptionPane.ERROR_MESSAGE);		
				}
				}catch(NumberFormatException e3)
				{
				JOptionPane.showMessageDialog(addGame,
							  "Price or Discount not numbers.",
							 "",
								    JOptionPane.ERROR_MESSAGE);				
//				
				}
				
				}
			}
		});
		btnAddGame.setBounds(120, 186, 119, 23);
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
		txtpnGenre1.setBounds(354, 24, 75, 20);
		addGame.add(txtpnGenre1);
		
		JTextPane txtpnCompany1 = new JTextPane();
		txtpnCompany1.setText("Company");
		txtpnCompany1.setEnabled(false);
		txtpnCompany1.setEditable(false);
		txtpnCompany1.setBounds(173, 24, 57, 20);
		addGame.add(txtpnCompany1);
		
		btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				superUserWindow();
			}
		});
		btnNewButton.setBounds(337, 186, 125, 23);
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
		
		 comboBox = new JComboBox(genres.toArray());
		comboBox.setBounds(231, 24, 113, 23);
		addGame.add(comboBox);
		comboBox_1 = new JComboBox(companies.toArray());
		comboBox_1.setBounds(455, 24, 119, 20);
		addGame.add(comboBox_1);
		
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
			
			
//			
//			boolean log = true;
//			while(log){
//				logger.info("To log in press '1'; for registering press '2'");
//				int logreg = Integer.parseInt(System.console().readLine());
//				boolean pass = false;
//				logger.info("Insert username:");
//				String login = System.console().readLine();
//				logger.info("Insert password:");
//				String password = String.valueOf(System.console().readPassword());
//				
//				try{
//					if(logreg == 1){
//						pass = server.loginUser(login, password);
//						superuser = server.isSuperUser(login);
//					}
//					else if (logreg == 2){
//						pass = server.registerUser(login, password);
//					}
//					else{
//						logger.error("Non valid input");
//					}
//				} catch (RemoteException e){
//					logger.error("Remote exception when trying to log in");
//					pass = false;
//					
//				}
//				if(pass){
//					if(superuser){
//						logger.info("Hello superuser!");
//					}
//					log = false;
//					String input = "";						
//					do{
//						displayMenu(mainMenu, defInfo);
//						input = System.console().readLine();
//						switch(input){
//						case("1"):
//							//Show games
//							showGames(server, null);
//							break;
//						case("2"):
//							//Show current user's games
//							showGames(server, login);
//							break;
//						case("3"):
//							//Buy game
//							logger.info("Insert a game's Id to select it. If you want to go back, input 'b'.");
//							logger.info("Available money: " + server.getUserWallet(login));
//							showGames(server, null);
//							input = System.console().readLine();
//							if(input.equals("b")){
//								break;
//							}
//							int id = Integer.parseInt(input)-1;
//							String gameName = "";
//							try{
//								gameName = games.get(id).getName();
//							} catch (Exception e){
//								logger.error("Invalid input");
//								break;
//							}
//							try{
//								if(server.buyGame(login, gameName)){
//									logger.info("Game bought successfully");
//								}
//							} catch (RemoteException e){
//								logger.error("Remote exception when trying to buy a game");
//							}
//							break;
//						case("4"):
//							//Add new game only if superUser
//							if(superuser){
//								String gName = "";
//								double gPrice = 0.0;
//								double gDisc = 0.0;
//								//Input name, price and discount
//								logger.info("Input new game name:");
//								gName = System.console().readLine();
//								try{
//									logger.info("Input new game price:");
//									gPrice = Double.parseDouble(System.console().readLine());
//									logger.info("Input new game discount:");
//									gDisc = Double.parseDouble(System.console().readLine());
//								} catch (Exception e){
//									logger.info("Invalid input");
//									break;
//								}
//								
//								try{
//									//Choose Company
//									int choose = 0;
//									String[] chooseList = server.getAllCompanies();
//									displayMenu(chooseList, "Select a company");
//									choose = Integer.parseInt(System.console().readLine())-1;
//									String cName = chooseList[choose];
//									
//									//Coose Genre
//									chooseList = server.getAllGenres();
//									displayMenu(chooseList, "Select a Genre");
//									choose = Integer.parseInt(System.console().readLine())-1;
//									String ggName = chooseList[choose];
//									if(server.addGame(gName, gPrice, gDisc, ggName, cName)){
//										logger.info("New game added successfully");
//									}
//								} catch (RemoteException e){
//									logger.error("Remote exception on the process of adding a game to the DB");
//								}
//								
//								break;
//							}
//						case("quit"):
//							break;
//						default:
//							logger.info("Invalid input");
//							break;
//						}
//						
//					} while(!(input.equals("quit")));
//					logger.info("See you soon! :D");
//				}
//				else{
//					logger.info("Incorrect login. Try again? Y|N");
//					String input = System.console().readLine();
//					if(input.equals("N")){
//						log = false;
//					}
//				}
//			}
//
		} catch (Exception e) {			System.err.println("[GenericException] Unexpected exception caught on the code: " + e.getMessage());
			e.printStackTrace();
		}
	}
}