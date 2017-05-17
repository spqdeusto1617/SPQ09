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
import org.junit.Ignore;

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
		 //Launch the RMI registry
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

				String name = "127.0.0.1:1099/MessengerRMIDAO";
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

			String name = "127.0.0.1:1099/MessengerRMIDAO";
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
			logger.info("Test 1 - Register new user");
			remote.registerUser("Carazo", "Carazo");
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}
	@Test public void loginUser() {
		boolean a =true;
		try{
			logger.info("Test 1 - Register new user");
			remote.loginUser("Carazo", "Carazo");
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}

	@Test public void registerExistingUserTest() {
		boolean a =true;
		try{
			logger.info("Test 2 - Register existing user. Change password");
			remote.registerUser("smith", "smith");
			// Silly way of testing the password testing
			remote.registerUser("smith", "doe");

		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a=false;
		} 

		assertTrue( a );
	}	
	
	@Test public void buyGameTestValidation() throws RemoteException {
		boolean  a = true;
		logger.info("Test 9 - buyGameTestValidation()");
		a = remote.buyGame("JunitUser","BF 1942");
		
		assertTrue(a);
	}

	@Test
	public void testGamesFull() throws RemoteException {
		logger.info("Test 10 - testGamesFull()");
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


		License l1 = new License ("AAAAA");
		License l2 = new License ("BBBBB");
		License l3 = new License ("CCCCCC");		
		License l4 = new License ("DDDDDDDDDD");
		License l5 = new License ("FFFAAAAAFFFF");
		License l6 = new License ("12345667");

		User u1 = new User("aihnoa", "qwerty", false);
		User u2 = new User("Joel", "qwerty", false);
		User u3 = new User("Cabezali", "qwerty", false);

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

		db.buyGame(u1.getLogin(), g1.getName());
		db.buyGame(u2.getLogin(), g1.getName());

		assert(true);
	}



	

	@Test public void showGamesTest()
	{
		boolean a =true;

		logger.info("Test 13 - showGamesTest()");
		try {
			remote.showGamesInStore();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			a=false;
		}

		assertTrue(a);
	}	
	
	///////////////Super User//////////////////////////
	@Test public void isSuperUserTest() {
		boolean a =true;
		try{
			logger.info("Test 14 - Is SuperUser?");
			remote.isSuperUser("Carazo");
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}
	@Test public void addGame() {
		boolean a =true;
		try{
			logger.info("Test 15 - Add game");
			remote.addGame("Counter", 11, 0,"shooter", "valve");
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}
	@Test public void getAllCompanies() {
		boolean a =true;
		try{
			logger.info("Test 16 - Get all companies");
			remote.getAllCompanies();
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}
	@Test public void getAllGenres() {
		boolean a =true;
		try{
			logger.info("Test 17 - Get all genres");
			remote.getAllGenres();
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a =false;
		} 

		assertTrue( a );
	}




		@After public  void deleteDatabase() {
			PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
			PersistenceManager pm = pmf.getPersistenceManager();
	        Transaction tx = pm.currentTransaction();
	        try
	        {
	            tx.begin();
		
	            logger.info("Deleting test DB from persistence. Cleaning up.");
	            
	            Query<User> q1 = pm.newQuery(User.class);
	            Query<Game> q2 = pm.newQuery(Game.class);
	            long numberInstancesDeleted = q1.deletePersistentAll();
	            long numberInstancesDeleted1 = q2.deletePersistentAll();
	            
	            
	            logger.info("Deleted " + numberInstancesDeleted + " user"+ "Deleted" +numberInstancesDeleted1 + "Game");
				
	            tx.commit();
	        } catch (Exception ex) {
	    		logger.error("   $ Error Deleting database " + ex.getMessage());
	    	
	    
	    }
	        finally
	        {
	            if (tx.isActive())
	            {
	                tx.rollback();
	            }
	            pm.close();
	        }
			
		}


	@AfterClass static public void tearDown() {
		try	{
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	public Game gameTest() throws RemoteException{
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);
		
		IDB db = new DB();
		
		try {
			db.addGameToDb(g, gr, c);
		} catch (Exception e) {
			logger.error("Exception gameTest");
			e.printStackTrace();
		}
		Game g1=db.showGame(g.getName());
		
		return(g1);
	}
	
	public boolean buyGameTest(){
		boolean  a = true;
		Company c = new Company("Vivendi");
		Genre gr = new Genre("shit");
		Game g = new Game("COD 10", 19.90, 0);
		
		License l = new License ("FFFFGGGG");
		
		User u = new User("Rattata","Junit Pass",false);
		
		IDB db = new DB();
		try {
			db.addGameToDb(g, gr, c);
			db.registerUser(u);
			db.buyGame(u.getLogin(), g.getName());
		} catch (Exception e) {logger.error("Exception License Test");e.printStackTrace();
		a=false;
		}
		
		return(a);
	}
}