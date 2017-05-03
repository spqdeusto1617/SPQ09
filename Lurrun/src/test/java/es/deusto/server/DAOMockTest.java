package es.deusto.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;  

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;  
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;  
import org.mockito.runners.MockitoJUnitRunner; 

import org.junit.Before;
import org.junit.Test;
//import org.junit.Ignore;

import es.deusto.server.db.*;
import es.deusto.server.db.dao.*;
import es.deusto.server.db.data.*;
import es.deusto.server.remote.IRemote;
import es.deusto.server.remote.Remote;


//cambiar quitar syso y poner logger.error y .info
/**
 * 
 * @author cortazar
 * Testing of the Service Layer, mocking the DAO layer
 */

@RunWith(MockitoJUnitRunner.class)  
public class DAOMockTest {
	
	DB db;
	
	@Mock
	IDAO dao;
		
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DAOMockTest.class);
	}

	@Before
	public void setUp() throws Exception {		
		db = new DB(dao);

	}

	@Test
	//@Ignore
	public void testRegisterUserCorrectly() {
	
		// Stubbing - return a given value when a specific method is called
		when( dao.retrieveUser("cortazar") ).thenReturn( null );
		User u = new User ("cortazar", "cortazar",false);
		db.registerUser(u);
		
		//Use ArgumentCaptor to capture argument values for further assertions.
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		
		// Setting expectations -  the method storeUser() is called once and the argument is intercepted
		verify (dao).storeUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		System.out.println("Registering mock new user: " + newUser.getLogin());
	
		assertEquals( "cortazar", newUser.getLogin());
		
	}
	
	@Test
	public void testRegisterUserAlreadyExists() {
		User u = new User("cortazar","cortazar",false);
		User u1 = new User("cortazar","dipina",false);
		when( dao.retrieveUser("cortazar") ).thenReturn(u);
		// When the user exist, we update the password
		db.registerUser(u1);
		
		
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		verify (dao).updateUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		System.out.println("Changing password of mock user: " + newUser.getPassword());
		assertEquals( "dipina", newUser.getPassword());
		
	}
	
	
	//DB TESTS
	//test de la base de datos como el usuario no tiene 
	@Test public void getUserGamesTest()
	{
		
		System.out.println("Prueba de test");
		User u = new User("cortazar","cortazar",false);
		
		
		
		
		List<Game>gameList = new ArrayList<Game>();
		try
		{
			gameList= db.getUserGames(u.getLogin());
		}
		catch(NullPointerException e)
		{
			
			System.out.println("User has no games");
		}
		assertEquals( true , gameList.isEmpty());
		
	}
	//Test del juego añadiendose a la db
	@Test public void addGameToDBTest()
	{
		Game g = new Game("Game 1",200,0.2);
		Genre gg = new Genre ("Genre 1");
		Company c = new Company ("Company 1");
				
		
		when( dao.retrieveGame("Game 1") ).thenReturn( null );
		
		db.addGameToDb(g, gg, c);
			
	
		ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass( Game.class );
				
		
		verify (dao).storeGame(gameCaptor.capture()) ;
		Game gtest = gameCaptor.getValue();
		System.out.println("Adding Game " + gtest.getName());
			
		assertEquals( "Game 1", gtest.getName());
		
		
		
		
	}
	
	@Test public void addLicenseToGameTest()
	{
		Game g = new Game("Game 1",200,0.2);
		Genre gg = new Genre ("Genre 1");
		Company c = new Company ("Company 1");
		db.addGameToDb(g, gg, c);
		License l = new License() ;
		l.setGameKey("Esto es una clave");
		
		boolean k =db.addLicenseToGame(g, l);
		assertEquals(true, k);
	}
	
	
	@Test public void addLicenseToUserTest()
	{
		
		User u = new User("cortazar","cortazar",false);
		License l = new License() ;
		l.setGameKey("Esto es una clave 2");
		when( dao.storeLicense(l) ).thenReturn( null );//Puede funcionar?	
		boolean k=db.addLicenseToUser(u, l);
		assertEquals(true, k);
		
		
	}
	
	
/**
	@Test(expected=RemoteException.class)
	public void testSayMessageUserInvalid() throws RemoteException {
		
		when( dao.retrieveUser("cortazar") ).thenReturn( null );
		System.out.println("Say message and invalid user, testing exception");
		
		db.
			
	}
	
	@Test
	public void testSayMessageUserValid() throws RemoteException {
		// Setting up the test data
		User u = new User("cortazar","cortazar");
		Message mes = new Message("testing message");
		mes.setUser(u);
		u.addMessage(mes) ;
		
		//Stubbing
		when( dao.retrieveUser("cortazar") ).thenReturn(u);
		
		//Calling the method under test
		
		m.sayMessage("cortazar", "cortazar", "testing message");
		
		// Verifying the outcome
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass( User.class );
		verify (dao).updateUser(userCaptor.capture());
		User newUser = userCaptor.getValue();
		
		assertEquals( "cortazar", newUser.getMessages().get(0).getUser().getLogin());
		
	}
	*/

}
