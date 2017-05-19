package es.deusto.server;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import es.deusto.server.db.data.Company;
import es.deusto.server.db.data.Game;
import es.deusto.server.db.data.Genre;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;  

import java.rmi.RemoteException;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;  
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;  
import org.mockito.runners.MockitoJUnitRunner; 

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.*;
import org.databene.contiperf.*;



import es.deusto.server.db.*;
import es.deusto.server.db.dao.*;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.IRemote;
import es.deusto.server.remote.Remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cortazar
 * Testing of the Service Layer, mocking the DAO layer
 */

@RunWith(MockitoJUnitRunner.class)  
public class DAOMockTest {
	
	DB db;
	final Logger logger = LoggerFactory.getLogger(DAOMockTest.class);
	static int iteration = 0;
	
	@Mock
	IDAO dao;
	
	
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DAOMockTest.class);
	}

	@Before
	public void setUp() throws Exception {
		logger.info("Entering setUp: {}", iteration++);
		db = new DB(dao);
		logger.info("Leaving setUp");

	}

	@Test
	//@Ignore
	public void testRegisterUserCorrectly() {
		logger.info("Starting testRegisterUserCorrectly() ");
	
		User u = new User ("cortazar", "cortazar",false);
		db.registerUser(u);
		
		//Use ArgumentCaptor to capture argument values for further assertions.
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		
		// Setting expectations -  the method storeUser() is called once and the argument is intercepted
		verify (dao).storeUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		logger.info("Registering mock new user: " + newUser.getLogin());
	
		assertEquals( "cortazar", newUser.getLogin());
		logger.debug("Finishing testRegisterUserCorrectly() ");
	}
	
	
	@Test
	public void testRegisterUserAlreadyExists() {
		User u = new User("cortazar","cortazar",false);
		u.setMoney(80);
		// When the user exist, we update the password
		dao.updateUser(u);
		
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );	
		verify (dao).updateUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		assertEquals(""+80.0 ,""+ newUser.getMoney());
	}
	
	@Test
	public void testGameValid() throws Exception {
	
		// Setting up the test data
				logger.info("Test Game Valid");
				Company c = new Company("Company Mockito");
				Genre gr = new Genre("genre mockito");
				Game g = new Game("Game mockito", 20.90, 0);
				g.setGenre(gr);
				g.setCompany(c);
											
				//Stubbing
				when( dao.retrieveGame(g.getName()) ).thenReturn(null);
				
				//Calling the method under test								
				db.addGameToDb(g, gr, c);
				
				// Verifying the outcome
				ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass( Game.class );
				verify (dao).storeGame(gameCaptor.capture());
				Game newGame = gameCaptor.getValue();
				
				assertEquals(g.toString(), newGame.toString());
				
		
	}	

}