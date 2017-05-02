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
import es.deusto.server.remote.Remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMITest {
	// Properties are hard-coded because we want the test to be executed without external interaction
	
	private static String cwd = RMITest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	
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
	//		re.printStackTrace();
			System.exit(-1);
		} 
		
	}
	
	@Test public void registerNewUserTest() {
		boolean a =true;
		try{
			logger.info("Test 1 - Register new user");
			remote.registerUser("ipina", "ipina",false);
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			 a =false;
		} 
		/*
		 * Very simple test, inserting a valid new user
		 */
		assertTrue( a );
	}
	
	@Test public void registerExistingUserTest() {
		boolean a =true;
		try{
			logger.info("Test 2 - Register existing user. Change password");
			remote.registerUser("smith", "smith",false);
			// Silly way of testing the password testing
			remote.registerUser("smith", "doe",false);
			
		}
		catch (Exception re) {
			logger.error(" # Messenger RemoteException: " + re.getMessage());
			a=false;
		} 
		/*
		 * Very simple test 
		 */
		assertTrue( a );
	}	
	
	//@Test public void sayMessageValidUser() {
	
		@Test public void gameTestValidation() {
		logger.info("Test 3 - Game Test ");
		
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);
		g.setGenre(gr);
		g.setCompany(c);
		
		Game gameTest = null;
		
			
		try {
			gameTest = remote.gameTest();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		assertEquals(g.toString(), gameTest.toString());
		
	}

					@Test public void showUsersTest() {
					      boolean a=true;
					      try{
					        logger.info("Test 5 - Show Users");
					        
					        remote.registerUser("Javier","Carazo",false);
					        remote.getAllUsers();
					        
					      }
					      catch (Exception re){
					        logger.error(" RemoteException: " );
					        logger.trace(re.getMessage());
					        re.printStackTrace();
					        a=false;
					      }
					      
					      assertTrue( a );
					      
					      
					      
					    }
					@Test
					public void testRetrieveByParameters() throws Exception  {
						
						
						logger.info("TEST 7 getByParam");
						Company c = new Company("DICE");
						Genre gr = new Genre("Bellic simulator");
						Game g = new Game("BF 1942", 19.90, 0);

						License l = new License ("GGGG");

						User u = new User("JunitUser","Junit Pass",false);
						IDB db=new DB();
					
						remote.licenseTest();
						
					Game g1=	db.showGameByParam(g.getName());
						
					Company c1=	db.showCompanyByParam(c.getName());
						
					Genre gr1=	db.showGenreByParam(gr.getName());
						
					License l1 =	db.showLicenseByParam(l.getGameKey());
						
						
						
						
						assertEquals(g.getName(), g1.getName());
						assertEquals(c.getName(), c1.getName());
						assertEquals(gr.getName(), gr1.getName());
						assertEquals(l.getGameKey(),l1.getGameKey());
						
					}			
							
					
	/**				
		 @Test(expected=RemoteException.class)
			 public void showUsersFailTest() throws RemoteException{
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
				logger.info("Test 6 - Show Users Fail");
				
				remote.getAllUsers();
			    }
		
		
		
		*/
		
		/*
		@Test(expected=Exception.class)
	 public void gameTestInvalidValidation() throws RemoteException {
		logger.info("Test 3 - Game Test ");
		
		Company c = new Company("White Wolf");
		Genre gr = new Genre("Vampire");
		Game g = new Game("Vampire the Masquerade", 19.90, 0);
		g.setGenre(gr);
		g.setCompany(c);
		
		Game gameTest = null;
		
		
		gameTest = remote.gameTest();		
			
		
			
		
	
		
	}
	
	
		
	
	@Test public void licenseTestValidation() {
		logger.info("Test 4 - License Test");		
		License licenseTest = null;
		
		Company c = new Company("DICE");
		Genre gr = new Genre("Bellic simulator");
		Game g = new Game("BF 1942", 19.90, 0);		
		License l = new License ("GGGG");		
		User u = new User("JunitUser","Junit Pass",false);
		
		g.setCompany(c);
		g.setGenre(gr);
		l.setGame(g);
		
		g.addLicense(l);
		u.addLicense(l);
		
		c.addGame(g);
		gr.addGame(g);
		
		try{
			
			licenseTest = remote.licenseTest();
		
		} catch (RemoteException e){
			
		}	
		
		assertEquals(l.toString(), licenseTest.toString());
	}
	/*
/*
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
	
*/
	@AfterClass static public void tearDown() {
		try	{
			rmiServerThread.join();
			rmiRegistryThread.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		

	} 
}
