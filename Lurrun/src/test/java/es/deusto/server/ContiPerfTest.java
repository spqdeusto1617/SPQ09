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
import es.deusto.server.db.dao.*;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.IRemote;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.junit.Rule;
import org.junit.*;
import org.databene.contiperf.junit.*;
import org.databene.contiperf.Required;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.report.EmptyReportModule;
import org.databene.contiperf.junit.ContiPerfRule;

import javax.swing.plaf.synth.SynthSeparatorUI;



public class ContiPerfTest {
	IDB db = new DB();

	
	int count = 0;
	int count1 = 0;
	String a = ""+count;
	String b = ""+count;
	Random rand = new Random();
	
	
	
	final static Logger logger = LoggerFactory.getLogger(ContiPerfTest.class);
	
	@Rule public ContiPerfRule rule = new ContiPerfRule();
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ContiPerfTest.class);
	}
	/*
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
*/
	
	@Test 
	@PerfTest(duration = 5000)
	@Required(average =100,throughput = 70)	
	public void storeGameInvocationFail() {
		Company cc = new Company("PerfTestCompany");
		Genre gr = new Genre("PerfTestGenre");
		Game g = new Game("Perftest" + a, rand.nextInt(5) + 1, 0);
			
		db.addGameToDb(g, gr, cc);
		count++;
		
	}
	
//	@Test 
//	@PerfTest(duration = 5000)
//	@Required(average =100,throughput = 30)
//	
//	public void storeGameInvocationSucces() {
//		Company cc = new Company("PerfTestCompany");
//		Genre gr = new Genre("PerfTestGenre");
//		Game g = new Game("Perftest" + a, rand.nextInt(5) + 1, 0);
//			
//		db.addGameToDb(g, gr, cc);
//		count++;
//		
//	}
	
	@Test 
	@PerfTest(duration = 5000)
	public void storeGameDuration() {		
	
		Company cc = new Company("PerfTestCompany");
		Genre gr = new Genre("PerfTestGenre");
		Game g = new Game("Perftest" + a, rand.nextInt(5) + 1, 0);
			
		db.addGameToDb(g, gr, cc);
		count++;
		
	}

	
	@Test 
	@PerfTest(invocations = 50, threads = 20)
	@Required(max = 400 , median = 120)
	public void loadGame() {
		db.showGame("Perftest" + b);
		count1++;
		}
	
	@Test 
	@PerfTest(invocations = 100, threads = 10)
	@Required(totalTime = 1000)
	public void loadGame2() {
		db.showGame("Perftest" + b);
		count1++;
		}
	/*
	@Test @PerfTest(invocations = 100, threads = 10)
	@Required(totalTime = 1000)public void isSuperUserTest() {
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
	@Test @PerfTest(invocations = 100, threads = 10)
	@Required(totalTime = 1000)public void addGame() {
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
	@Test @PerfTest(invocations = 100, threads = 10)
	@Required(totalTime = 1000)public void getAllCompanies() {
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
	@Test @PerfTest(invocations = 100, threads = 10)
	@Required(totalTime = 1000)public void getAllGenres() {
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
	}*/

}