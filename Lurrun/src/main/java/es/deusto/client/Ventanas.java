package es.deusto.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import es.deusto.server.db.data.Game;
import es.deusto.server.db.data.User;

import java.awt.FlowLayout;

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
	private JTextField txtSearchGenre;
	private JTextField txtsearchCompany;
	private JButton btnNewButton;
	private JTable table;
	private JTextField password;
	static Game g = new Game("VTM: Bloodlines",20,0.2);
	static Game g1 =new Game("Golden Sun",15,0.1);
	static Game g2 =new Game("Fable: The Lost Chapters",25,0.25);
	static User u1 = new User("ainhoa", "ainhoa", true);
	static User u2 = new User("Joel", "qwerty", false);
	static User u3 = new User("Cabezali", "qwerty", false);
	
	
	private static List<Game> games = new ArrayList<Game>();
	private JTable myGamesTable;
	private JTable allGamesTable;
	private JTextField textField_1;
	private JTextField textField_2;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
					games.add(g);
					games.add(g1);
					games.add(g2);
					Ventanas frame = new Ventanas();
					frame.setVisible(true);
			
		
	}
	
	/**
	 * Create the frame.
	 */
	public Ventanas() {
		
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
			}
		});
		btnAddGame.setBounds(123, 159, 119, 23);
		addGame.add(btnAddGame);
		
		textField = new JTextField();
		textField.setBounds(80, 24, 75, 20);
		addGame.add(textField);
		textField.setColumns(10);
		
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
			}
		});
		btnNewButton.setBounds(305, 159, 125, 23);
		addGame.add(btnNewButton);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(80, 55, 75, 20);
		addGame.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(80, 86, 75, 20);
		addGame.add(textField_2);
		
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
}
