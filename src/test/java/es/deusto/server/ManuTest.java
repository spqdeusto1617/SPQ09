package es.deusto.server;


import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import java.util.logging.Level;






import es.deusto.server.data.Company;
import es.deusto.server.data.Game;
import es.deusto.server.data.Genre;
import es.deusto.server.data.License;
import es.deusto.server.data.User;
import es.deusto.server.remote.IMessenger;
import es.deusto.server.remote.Messenger;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import org.databene.contiperf.junit.*;
import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.report.EmptyReportModule;
import org.databene.contiperf.junit.ContiPerfRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * RMI Unit test for Simple Client / Server RMI Testing.
 * Testing the only the Remoteness
 */
//@Ignore
public class ManuTest {
	// Properties are hard-coded because we want the test to be executed without external interaction
	
	private static String cwd = ManuTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	final static java.util.logging.Logger logger = LoggerFactory.getLogger(ManuTest.class);
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	private IMessenger messenger;

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ManuTest.class);
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
					
					IMessenger messenger = new Messenger();
					Naming.rebind(name, messenger);
				} catch (RemoteException re) {
					System.err.println(" # Messenger RemoteException: " + re.getMessage());
					re.printStackTrace();
					System.exit(-1);
				} catch (MalformedURLException murle) {
					logger.severe(" # Messenger MalformedURLException: " + murle.getMessage());
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
		messenger = (IMessenger) java.rmi.Naming.lookup(name);
		}
		catch (Exception re) {
			logger.severe(" # Messenger RemoteException: " + re.getMessage());
	//		re.printStackTrace();
			System.exit(-1);
		} 
		
	}
	
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void registerNewUserTest() throws Exception{
		
			logger.info("Test 1 - Register new user");
			messenger.registerUser("ipina", "ipina",false);
		
		assertTrue( true );
	}
	
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void registerExistingUserTest()throws Exception {
		
			logger.info("Test 2 - Register existing user. Change password");
			messenger.registerUser("smith", "smith",false);
			// Silly way of testing the password testing
			messenger.registerUser("smith", "doe",false);
		
		assertTrue( true );
	}
	
	
	@Test 
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void sayMessageValidUser() throws Exception{
		logger.info("Test 3 - Sending message - Valid User");
		String ret = null;
			messenger.registerUser("cortazar","cortazar",false);
			ret = messenger.sayMessage("cortazar", "cortazar", "testing message");
		
		assertEquals("testing message", ret);
	}
	
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void addStufToDb() throws Exception{
		
			logger.info("Test 4 - add stuff");
			messenger.addStufToDb(new Game("Pang",0.99,0), new  Genre("Retro"), new Company("Manu SA"));
		
		assertTrue( true );
	}
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void addLicenseToUser() throws Exception{
		
			logger.info("Test 5 - add license");
			messenger.addLicenseToUser(new User("manu", "123"), new License("ManuLicense"));
		
		assertTrue( true );
	}
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void addLicenseToGame() throws Exception {
		
			logger.info("Test 6 - add license to game");
			messenger.addLicenseToGame(new Game("Pang",0.99,0),new License("ManuLicense"));
		
		assertTrue( true );
	}
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void showGameInfo() throws Exception {
		
			logger.info("Test 7 -  show game info");
			messenger.showGameInfo("Pang", "Manu SA", "Retro");
		
		assertTrue( true );
	}
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void showLicenseInfo() throws Exception {
		
			logger.info("Test 8 - show license");
			messenger.showLicenseInfo("manu", "ManuLicense", "Pang");
		
		assertTrue( true );
	}
	@Test
	@PerfTest(invocations = 1000, threads = 20)   
	@Required(max = 1200, average = 250)
	public void getGamesFromDB() throws Exception {
		
			logger.info("Test 9 - get games");
			messenger.getGamesFromDB();
		
		assertTrue( true );
	}
	@After public  void deleteDatabase() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try
        {
            tx.begin();
	
            logger.info("Deleting test users from persistence. Cleaning up.");
            Query<User> q1 = pm.newQuery(User.class);
            long numberInstancesDeleted = q1.deletePersistentAll();
            logger.severe("Deleted " + numberInstancesDeleted + " user");
			
            tx.commit();
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


}
