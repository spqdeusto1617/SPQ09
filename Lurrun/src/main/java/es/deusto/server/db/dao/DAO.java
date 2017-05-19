package es.deusto.server.db.dao;

import javax.jdo.*;

import java.util.ArrayList;
import java.util.List;

import es.deusto.server.db.data.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the link between the database and the server
 * @author 
 * @version 1.0
 * @since 24/03/2017
 */
public class DAO implements IDAO {

	private PersistenceManagerFactory pmf;
	final Logger logger = LoggerFactory.getLogger(DAO.class);
	
	public DAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	/**
	 * This method stores a user
	 * @param u This is a user
	 * @return boolean Returns true or false depending on whether the user exists or not
	 * @see es.deusto.server.db.dao.IDAO#storeUser(es.deusto.server.db.data.User)
	 */
	@Override
	public boolean storeUser(User u) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    	boolean ret=true;
		try {
	       		tx.begin();
	      
		       	pm.makePersistent(u);
		       	tx.commit();
		    } catch (Exception ex) {
		    	ret=false;
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return ret;
	}
	
	/**
	 * This method retrieves a user
	 * @param login This is the login name of a user
	 * @return User Returns a user 
	 * @see es.deusto.server.db.dao.IDAO#retrieveUser(java.lang.String)
	 */
	@Override
	public User retrieveUser(String login) {
		User user = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(5);
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			user = pm.getObjectById(User.class, login);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
		}
		finally {
	    		if (tx != null && tx.isActive()) {
	    			tx.rollback();
	    		}
    			pm.close();
		}
		return user;
	}
	
	/**
	 * This method updates a user
	 * @param u This is a user
	 * @return boolean Returns true or false depending on whether the user exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateUser(es.deusto.server.db.data.User)
	 */
	@Override
	public boolean updateUser(User u) {
	PersistenceManager pm = pmf.getPersistenceManager();
	Transaction tx = pm.currentTransaction();
	boolean r =true;
	try {
		tx.begin();
		pm.makePersistent(u);
		tx.commit();
	} catch (Exception ex) {
		r=false;
	} finally {
		if (tx != null && tx.isActive()) {
		tx.rollback();
	}
		pm.close();
	}
		return r;
	}

	/**
	 * This method retrieves a license
	 * @param gameKey This is the key of a license
	 * @return License Returns a license
	 * @see es.deusto.server.db.dao.IDAO#retrieveLicense(java.lang.String)
	 */
	@Override
	public License retrieveLicense(String gameKey) {
		License license = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(5);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			license = pm.getObjectById(License.class, gameKey);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
		}

		finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
    			pm.close();
	   	}
		return license;
	}
	
	/** 
	 * This method updates a license
	 * @param g This is a license
	 * @return boolean Returns true or false depending on whether the license exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateLicense(es.deusto.server.db.data.License)
	 */
	@Override
	public boolean updateLicense(License g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean ret=true;
		try {
			tx.begin();
			pm.makePersistent(g);
			tx.commit();
		} catch (Exception ex) {
			ret = false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return ret;
	}

	/**
	 * This method stores a game
	 * @param g This is a game
	 * @return boolean Returns true or false depending on whether the game exists or not
	 * @see es.deusto.server.db.dao.IDAO#storeGame(es.deusto.server.db.data.Game)
	 */
	@Override
	public	boolean storeGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r=true;
	    	try {
		    	tx.begin();
			pm.makePersistent(g);
			tx.commit();
		} catch (Exception ex) {
			r=false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	    	return r;
	}
	
	/**
	 * This method retrieves a game
	 * @param name This is the name of a game
	 * @return Game Returns a game
	 * @see es.deusto.server.db.dao.IDAO#retrieveGame(java.lang.String)
	 */
	@Override
	public Game retrieveGame(String name) {
		Game game = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(5);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			game = pm.getObjectById(Game.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
		}

		finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
    			pm.close();
	    	}
		return game;
	}
	
	/**
	 * This method updates a game
	 * @param g This is a game
	 * @return boolean Returns true or false depending on whether the game exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateGame(es.deusto.server.db.data.Game)
	 */
	@Override
	public boolean updateGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   r=false;
	     } finally {
		if (tx != null && tx.isActive()) {
		tx.rollback();
		}

		pm.close();
	     }
	    return r;
	}

	/**
	 * This method retrieves a company
	 * @param name This is the name of a company
	 * @return Company Returns a company
	 * @see es.deusto.server.db.dao.IDAO#retrieveCompany(java.lang.String)
	 */
	@Override
	public Company retrieveCompany(String name) {
		Company company = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(5);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			company = pm.getObjectById(Company.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
		}

		finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return company;
	}
	
	/**
	 * This method updates a company 
	 * @param c This is a company
	 * @return boolean Returns true or false depending on whether the company exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateCompany(es.deusto.server.db.data.Company)
	 */
	@Override
	public boolean updateCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r= true;
		try {
			tx.begin();
			pm.makePersistent(c);
			tx.commit();
		} catch (Exception ex) {
			r=false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return r;
	}

	/**
	 * This methos retrieves a genre
	 * @param name This is the name of a genre
	 * @return Genre Returns a genre
	 * @see es.deusto.server.db.dao.IDAO#retrieveGenre(java.lang.String)
	 */
	@Override
	public Genre retrieveGenre(String name) {
		Genre genre = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(5);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			genre = pm.getObjectById(Genre.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
		}
		finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return genre;
	}
	
	/**
	 * This method updates a genre
	 * @param g This is a genre
	 * @return boolean Returns true or false depending on whether the genre exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateGenre(es.deusto.server.db.data.Genre)
	 */
	@Override
	public boolean updateGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r=true;
		try {
		tx.begin();
		pm.makePersistent(g);
		tx.commit();
		} catch (Exception ex) {
		    r=false;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return r;
	}

	/**
	 * This method shows a list of games
	 * @param unused
	 * @return List Returns a list of games
	 * @see es.deusto.server.db.dao.IDAO#getAllGames()
	 */
	@Override
	public List<Game> getAllGames() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		pm.getFetchPlan().setMaxFetchDepth(5);

		List<Game> games=new ArrayList<>();
		try {
		    tx.begin();
		    Extent<Game> extentP = pm.getExtent(Game.class);

		    for (Game p : extentP) {
		       games.add(p);
		       p.getName();
		       p.getCompany();
		       p.getGenre();
		    }

		    tx.commit();
		} catch (Exception ex) {
		} finally {
		    if (tx.isActive()) {
			tx.rollback();
		    }
		    pm.close();
		}
		return games;
	}
	
	/**
	 * This method shows all the companies
	 * @param unused
	 * @return List Returns a list of companies
	 * @see es.deusto.server.db.dao.IDAO#getAllCompanies()
	 */
	@Override
	public List<Company> getAllCompanies() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		pm.getFetchPlan().setMaxFetchDepth(5);

		List<Company> companies = new ArrayList<>();
		try {
			tx.begin();
			Extent<Company> extentP = pm.getExtent(Company.class);

			for (Company p : extentP) {
				companies.add(p);
				p.getName();
			}
			tx.commit();
		} catch (Exception ex) {
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return companies;
	}
	
	/**
	 * This method shows all the genres
	 * @param unused
	 * @return List Returns a list of genres
	 * @see es.deusto.server.db.dao.IDAO#getAllGenres()
	 */
	@Override
	public List<Genre> getAllGenres() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		pm.getFetchPlan().setMaxFetchDepth(5);

		List<Genre> genres = new ArrayList<>();
		try {
			tx.begin();
			Extent<Genre> extentP = pm.getExtent(Genre.class);

			for (Genre p : extentP) {
				genres.add(p);
				p.getName();
			}
			tx.commit();
		} catch (Exception ex) {
		} finally {
		    if (tx.isActive()) {
			tx.rollback();
		    }
		    pm.close();
		}
		return genres;
	}
	
	/**
	 * This method returns the first license stored in the database
	 * @param name This is the name of a license
	 * @return License Returns a license
	 * @see es.deusto.server.db.dao.IDAO#getFirstLicense(java.lang.String)
	 */
	@Override
	public License getFirstLicense(String name) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		pm.getFetchPlan().setMaxFetchDepth(5);
		License u = null;

		try {
		    tx.begin();

		    Extent<License> extentP = pm.getExtent(License.class);

		    for (License l : extentP) {
			if(l.getGame().getName().equals(name) && !l.isUsed() ){            		
				u = l;
				break;
			}
		    }

		    tx.commit();
		} catch (Exception ex) {
		} finally {
		    if (tx.isActive()) {
			tx.rollback();
		    }
		    pm.close();
		}
        return u;
	}

	
}
