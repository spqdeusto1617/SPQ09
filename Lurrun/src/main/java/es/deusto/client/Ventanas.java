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
	private JList list_1;
	private JList list_2;
	private JTextField txtSearchGame;
	private JTextField txtPrice;
	private JTextField txtDiscount;
	
	static Game g = new Game("VTM: Bloodlines",20,0.2);
	static Game g1 =new Game("Golden Sun",15,0.1);
	static Game g2 =new Game("Fable: The Lost Chapters",25,0.25);
	static User u1 = new User("ainhoa", "ainhoa", true);
	static User u2 = new User("Joel", "qwerty", false);
	static User u3 = new User("Cabezali", "qwerty", false);
	static Company c1 = new Company("Tracer");
	static Company c2 = new Company("Zenyata");
	static Genre gem1 = new Genre("hanzo");
	static Genre gem2 = new Genre("wajkekajeh");
	
	
	private static List<Game> games = new ArrayList<Game>();
	private static List<Game> gameUsers = new ArrayList<Game>();
	private static List<Genre> genres= new ArrayList<Genre>();
	private static List<Company> companies = new ArrayList<Company>();
	
	private JTable tableMyGames;
	private JTable allGamesTable;
	private JTextField textField_1;
	private JTextField textField_2;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
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
					
					companies.add(c1);
					companies.add(c2);
					
					genres.add(gem1);
					genres.add(gem2);
					
					games.add(g);
					games.add(g1);
					games.add(g2);
					gameUsers.add(g1);
					
					Ventanas frame = new Ventanas();
					frame.setVisible(true);
			
		
	}
	
	/**
	 * Create the frame.
	 */
	public Ventanas() {
		

		DefaultListModel modelcomp =new DefaultListModel();
		DefaultListModel modelgen =new DefaultListModel();
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
				
				String gg = (String) list_1.getSelectedValue();
				String c= (String) list_2.getSelectedValue();
				if(gnName.length()==0||priceStr.length()==0||discStr.length()==0||gg==null||c==null)
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
//				try {			
//					server.addGame(gnName, price, disc, gg, c);
//				} catch (RemoteException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
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
//				superUserWindow();
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
		
		
		scrollPane = new JScrollPane(list_1);
		scrollPane.setBounds(238, 26, 111, 137);
		addGame.add(scrollPane);
		
		list_1 = new JList(modelgen);
		scrollPane.setViewportView(list_1);
		for(int i = 0; i < genres.size(); i++)
	    {
	    	modelgen.addElement(genres.get(i).getName());
	    }
		scrollPane_1 = new JScrollPane(list_2);
		scrollPane_1.setBounds(439, 24, 105, 139);
		addGame.add(scrollPane_1);
		
		list_2 = new JList(modelcomp);
		for(int i = 0; i < companies.size(); i++)
	    {
	    	modelcomp.addElement(companies.get(i).getName());
	    }
		scrollPane_1.setViewportView(list_2);
			
	}
}
