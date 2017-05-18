package es.deusto.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import es.deusto.server.db.data.Company;
import es.deusto.server.db.data.Game;
import es.deusto.server.db.data.Genre;
import es.deusto.server.db.data.User;

import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Panel;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Scrollbar;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import java.awt.TextField;
import javax.swing.JComboBox;

public class Ventanas extends JFrame {
	 int counter = 15;
	private JPanel addGame;
	private JTextField userTextField;
	public static enum Estado{LOGIN, SUPERUSER, USER,BUYGAME, ADDGAME}
	public static Estado WindoState;
	private JTable tableAllGames;
	private JTextField textField;
	private JButton btnNewButton;
	private JTable table;
	private JTextField password;
	private JList list;
	private JTextField txtSearchGame;
	private JTextField txtPrice;
	private JTextField txtDiscount;
	
	static Game g = new Game("VTM: Bloodlines",20,0.2);
	static Game g1 =new Game("Golden Sun",15,0.1);
	static Game g2 =new Game("Fable: The Lost Chapters",25,0.25);
	static User u1 = new User("ainhoa", "ainhoa", true);
	static User u2 = new User("Joel", "qwerty", false);
	static User u3 = new User("Cabezali", "qwerty", false);
	static Company c1 = new Company("drop1");
	static Company c2 = new Company("drop2");
	static Genre gem1 = new Genre("pene");
	static Genre gem2 = new Genre("gordo");
	static JComboBox comboBox_1;
	static JComboBox comboBox;
	
	private static List<Game> games = new ArrayList<Game>();
	private static List<Game> gameUsers = new ArrayList<Game>();
	private static List<String> genres= new ArrayList<String>();
	private static List<String> companies = new ArrayList<String>();
	
	private JTable tableMyGames;
	private JTable allGamesTable;
	private JTextField textField_1;
	private JTextField textField_2;
	private JList list_3;
	private JList list_4;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
					
					g.setCompany(c1);
					g.setGenre(gem1);
					g1.setCompany(c2);
					g1.setGenre(gem1);
					g2.setCompany(c1);
					g2.setGenre(gem2);
					u1.setMoney(11811);
					
					companies.add("drop1");
					companies.add("drop2");
					
					genres.add("pene");
					genres.add("gordo");
					
					games.add(g);
					games.add(g1);
					games.add(g2);
					gameUsers.add(g1);
					
					Ventanas frame = new Ventanas();
					frame.setVisible(true);
			
		
	}
	/**
	 * This method shows an array
	 * @param list This is a list
	 * @return String[] Returns an array
	 */
	private String[] toArray(List<String> list){
		int length = list.size();
		String[] array = new String [length];
		for(int i = 0; i<length; i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Create the frame.
	 */
	public Ventanas() {
		
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
				
//				normalUserWindow();
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
						System.out.println("primera con");
						valido=games.get(i).getName().contains(gamename);
						
					}
					if(genre!=null && valido==true && genre!="All"){
						
						System.out.println("segudna con " + i);
						valido=games.get(i).getGenre().getName().contains(genre);
					
						
					}
					if(companyname!=null &&valido==true && companyname!="All")
					{
						
						valido=games.get(i).getCompany().getName().contains(companyname);
						System.out.println("tercera con");
						
					}
					else if((companyname=="All" &&gamename.length()==0&& genre=="All"))
					{
						System.out.println("Cuarta con" + i);
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
}
