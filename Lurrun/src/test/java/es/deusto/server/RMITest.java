package es.deusto.server;


import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import junit.framework.JUnit4TestAdapter;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.After;
//import org.junit.Ignore;

import es.deusto.server.db.*;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.IRemote;
import es.deusto.server.remote.Remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;


import javax.swing.plaf.synth.SynthSeparatorUI;


//@PerfTest
public class RMITest {
	// Properties are hard-coded because we want the test to be executed without external interaction

	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	int count = 0;
	private IRemote remote;
	final static Logger logger = LoggerFactory.getLogger(RMITest.class);

	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RMITest.class);
	}

	@BeforeClass static public void setUp() {
		// Launch the RMI registry
		class RMIRegistryRunnable implements Runnable {

			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(1099);
					logger.info("BeforeClass: RMI registry ready.");
				} catch (Exception e) {
					logger.info("Exception starting RMI registry:");
					e.printStackTrace();
				}	
			}
		}

		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();

		}

		class RMIServerRunnable implements Runnable {

			public void run() {
				logger.info("This is a test to check how mvn test executes this test without external interaction; JVM properties by program");
				logger.info("**************: " + cwd);
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

				if (System.getSecurityManager() == null) {
					System.setSecurityManager(new SecurityManager());
				}

				String name = "//127.0.0.1:1099/MessengerRMIDAO";
				logger.info("BeforeClass - Setting the server ready TestServer name: " + name);

				try {

					IRemote remote = new Remote();
					Naming.rebind(name, remote);
				} catch (RemoteException re) {
					logger.error(" # Messenger RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.error(" # Messenger MalformedURLException: " + murle.getMessage());
					murle.printStackTrace();
					System.exit(-1);
				}
			}
		}
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}

	@Before public void setUpClient() {

		try {
			System.setProperty("java.security.policy", "target\\test-classes\\security\\java.policy");

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			String name = "//127.0.0.1:1099/MessengerRMIDAO";
			logger.info("BeforeTest - Setting the client ready for calling TestServer name: " + name);
			remote = (IRemote) java.rmi.Naming.lookup(name);
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			System.exit(-1);
		} 

	}

	@Test public void registerNewUserTest() {
		boolean a =true;
		try{
			logger.info("Test  - Register new user");
			remote.registerUser("Carazo", "Carazo");
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}

	@Test public void loginTest() {
		boolean a =true;
		try{
			logger.info("Test  - Login Test");
			remote.loginUser("Carazo", "Carazo");
		

		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a=false;
		} 

		assertTrue( a );
	}	


	@Test public void gameTestValidation() {
		logger.info("Test  - gameTestValidation()");
		IDB db = new DB();
		Company c = new Company("Riot");
		Genre gr = new Genre("MOBA");
		Game g = new Game("LOL", 19.90, 0);
		g.setGenre(gr);
		g.setCompany(c);
		
		Game gameTest = null;
	
		db.addGameToDb(g, gr, c);
		
		try {
			remote.addGame("Dota2", 23, 0.2, gr.getName(), c.getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		gameTest = db.showGame("Dota2");
		assertEquals("Dota2", gameTest.getName());

	}


	@Test
	public void testGamesFull() throws RemoteException {
		logger.info("Test  - testGamesFull()");
		Game g = new Game("Game 1",200,0.2);
		Game g1 =new Game("Game 2",300,0.1);
		Game g2 =new Game("Game 3",20,0.25);
		Game g3= new Game("Game 4",250,0.2);
		Game g4 =new Game("Game 5",26,0.3);
		Game g5 =new Game("Game 6",34,0.75);
		Game g6 =new Game("Game 7",78,0.80);
		Game g7= new Game("Game 8",69,0.05);

		Genre gg1 = new Genre ("Genre 1");
		Genre gg2 = new Genre ("Genre 2");
		Genre gg3 = new Genre ("Genre 3");
		Genre gg4 = new Genre ("Genre 4");
		Genre gg5 = new Genre ("Genre 5");


		Company c1 = new Company ("Company 1");
		Company c2 = new Company ("Company 2");
		Company c3 = new Company ("Company 3");
		Company c4 = new Company ("Company 4");
		Company c5 = new Company ("Company 5");


		User u1 = new User("aihnoa", "qwerty");
		User u2 = new User("Joel", "qwerty");
		User u3 = new User("Cabezali", "qwerty");

		IDB db = new DB();


		db.addGameToDb( g, gg1, c1);
		db.addGameToDb( g1, gg2, c2);
		db.addGameToDb( g2, gg3, c3);
		db.addGameToDb( g3, gg4, c4);	

		db.addGameToDb( g4, gg3, c5);		
		db.addGameToDb( g5, gg5, c4);			
		db.addGameToDb( g6, gg4, c4);	
		db.addGameToDb( g7, gg3, c3);


		db.registerUser(u1);
		db.registerUser(u2);
		db.registerUser(u3);
		assert(true);
	}

	


	@Test public void getAllGenresAndCompaies()
	{	
		boolean  a = true;
	
	try {
		logger.info("Test  - getAllGenresAndCompaies");
		remote.getAllGenres();
		remote.getAllCompanies();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		a=false;
	}

	assertTrue(a);
	}
	
	@Test public void showGamesTest()
	{
		boolean a =true;

		logger.info("Test  - showGamesTest()");
		try {
			remote.showGamesInStore();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			a=false;
		}

		assertTrue(a);
	}	
	@Test public void showOwnedGAmes()
	{
		boolean a =true;

		logger.info("Test  - Buy and Show games()");
		try {
			remote.buyGame("aihnoa","Game 1");
			remote.showOwnedGames("aihnoa");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			a=false;
		}

		assertTrue(a);
	}

	@Test public void getUSerWallet()
	{
		boolean a =true;

		logger.info("Test  - getUserWallet");
		try {
			IDB db = new DB();
			User u = new User("JC", "qwerty");
			db.registerUser(u);
			remote.getUserWallet(u.getLogin());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			a=false;
		}

		assertTrue(a);
	}
	
	@Test public void isSuperUserTest()
	{
		boolean a =true;

		logger.info("Test  - isSuperUserTest()");
		try {
			IDB db = new DB();
			User u = new User("Mike", "qwerty",false);
			db.registerUser(u);
			a = remote.isSuperUser(u.getLogin());
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}

		assertFalse(a);
	}
//	@Test(expected = RemoteException.class)
//	public void nullUser()
//	{
//		boolean a =true;
//
//		logger.error("Test  - NullUserTest");
//		try {
//			remote.showOwnedGames("Paco");
//		
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			a =false;
//	
//		}
//
//		assertTrue(a);
//	}


	@AfterClass static public void tearDown() {
		try	{
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}


	
	
}