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
//		    	logger.error("   $ Error storing an object: " + ex.getMessage());
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
	 * This method updates a license
	 * @param g This is a license
	 * @return boolean Returns true or false depending on whether the license exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateLicense(es.deusto.server.db.data.License)
	 */
	public	boolean updateLicense(License g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean ret=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
//	    	logger.error("Error updating a License: " + ex.getMessage());
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
	 * This method retrieves a user
	 * @param login This is the login name of a user
	 * @return User Returns a user 
	 * @see es.deusto.server.db.dao.IDAO#retrieveUser(java.lang.String)
	 */
	@Override
	public User retrieveUser(String login) {
		User user = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			user = pm.getObjectById(User.class, login);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
			////////////logger.warn("User does not exist: " + jonfe.getMessage());
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
	    	   	////////////logger.error("Error updating a user: " + ex.getMessage());
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
	 * This method stores a game
	 * @param g This is a game
	 * @return boolean Returns true or false depending on whether the game exists or not
	 * @see es.deusto.server.db.dao.IDAO#storeGame(es.deusto.server.db.data.Game)
	 */
	public	boolean storeGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r=true;
	    try {
	       tx.begin();
	 
		       pm.makePersistent(g);
		       tx.commit();
		    } catch (Exception ex) {
		    	 	////////////logger.error("   $ Error storing an object: " + ex.getMessage());
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
	 * This method retrieves a game with a given parameter
	 * @param name This is the name of a game
	 * @return Game Returns a game
	 * @see es.deusto.server.db.dao.IDAO#retrieveGameByParameter(java.lang.String)
	 */
//	public	Game retrieveGameByParameter(String name){
//
//        PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx = pm.currentTransaction();
//        pm.getFetchPlan().setMaxFetchDepth(3);
//        Game g = null;
//        try {
//            tx.begin();
//            Extent<Game> extentP = pm.getExtent(Game.class);
//
//            for (Game p : extentP) {
//
//                if (p.getName().equals(name)) {
//                    g= p;
//           
//                }
//            }
//            tx.commit();
//        } catch (Exception ex) {
////        	   ////////////logger.error("# Error getting Extent Game: " + ex.getMessage());
//        } finally {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            pm.close();
//        }
//        //      ////////////logger.error(u);
//        return g;
//	}

	/**
	 * This method updates a game
	 * @param g This is a game
	 * @return boolean Returns true or false depending on whether the game exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateGame(es.deusto.server.db.data.Game)
	 */
	public	boolean updateGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
//	    	   	////////////logger.error("Error updating a game: " + ex.getMessage());
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
	 * This method retrieves a company with a given parameter
	 * @param name This is the name of a company
	 * @return Company Returns a company
	 * @see es.deusto.server.db.dao.IDAO#retrieveCompanyByParameter(java.lang.String)
	 */
//	public	Company retrieveCompanyByParameter(String name){
//	
//	        PersistenceManager pm = pmf.getPersistenceManager();
//	        Transaction tx = pm.currentTransaction();
//	        pm.getFetchPlan().setMaxFetchDepth(3);
//	        Company u = null;
//	        try {
//	            tx.begin();
//	            Extent<Company> extentP = pm.getExtent(Company.class);
//
//	            for (Company p : extentP) {
//
//	                if (p.getName().equals(name)) {
//	                    u = p;
//	                }
//	            }
//	            tx.commit();
//	        } catch (Exception ex) {
//	        	      ////////////logger.error("# Error getting Extent: " + ex.getMessage());
//	        } finally {
//	            if (tx.isActive()) {
//	                tx.rollback();
//	            }
//	            pm.close();
//	        }
//	        //     ////////////logger.error(u);
//	        return u;
//	    }

	/**
	 * This method updates a company 
	 * @param c This is a company
	 * @return boolean Returns true or false depending on whether the company exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateCompany(es.deusto.server.db.data.Company)
	 */
	public	boolean updateCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r= true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(c);
	    	tx.commit();
	     } catch (Exception ex) {
	    	    	////////////logger.error("Error updating a company: " + ex.getMessage());
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
	 * This method retrieves a genre with a given parameter
	 * @param name This is the name of a genre
	 * @return Genre Returns a genre
	 * @see es.deusto.server.db.dao.IDAO#retrieveGenreByParameter(java.lang.String)
	 */
//	public Genre retrieveGenreByParameter(String name){
//		//		////////////logger.error("Get Genre from db "+name);
//        PersistenceManager pm = pmf.getPersistenceManager();
//        Transaction tx = pm.currentTransaction();
//        pm.getFetchPlan().setMaxFetchDepth(3);
//        Genre u = null;
//        try {
//            tx.begin();
//            Extent<Genre> extentP = pm.getExtent(Genre.class);
//
//            for (Genre p : extentP) {
//
//                if (p.getName().equals(name)) {
//                    u = p;
//
//                }
//            }
//            tx.commit();
//        } catch (Exception ex) {
//        	       ////////////logger.error("# Error getting Extent: " + ex.getMessage());
//        } finally {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//            pm.close();
//        }
//        //   ////////////logger.error(u);
//        return u;
//	}

	/**
	 * This method updates a genre
	 * @param g This is a genre
	 * @return boolean Returns true or false depending on whether the genre exists or not
	 * @see es.deusto.server.db.dao.IDAO#updateGenre(es.deusto.server.db.data.Genre)
	 */
	public boolean updateGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
	    	 	   	////////////logger.error("Error updating a genre: " + ex.getMessage());
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
	public List<Game> getAllGames() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);

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
        	   //logger.error("# Error getting Extent getAllGames: " + ex.getMessage());
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
        pm.getFetchPlan().setMaxFetchDepth(3);

        List<Company> companies = new ArrayList<>();
        try {
            tx.begin();
            Extent<Company> extentP = pm.getExtent(Company.class);

            for (Company p : extentP) {

               companies.add(p);
               p.getName();
//               p.getCompany();
//               p.getGenre();
                }

            tx.commit();
        } catch (Exception ex) {
        	   //logger.error("# Error getting Extent getAllGames: " + ex.getMessage());
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
        pm.getFetchPlan().setMaxFetchDepth(3);

        List<Genre> genres = new ArrayList<>();
        try {
            tx.begin();
            Extent<Genre> extentP = pm.getExtent(Genre.class);

            for (Genre p : extentP) {

               genres.add(p);
               p.getName();
//               p.getCompany();
//               p.getGenre();
                }

            tx.commit();
        } catch (Exception ex) {
        	   //logger.error("# Error getting Extent getAllGames: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return genres;
	}
	
	/**
	 * This method shows a list of users
	 * @param unused
	 * @return List Returns a list of users
	 * @see es.deusto.server.db.dao.IDAO#getAllUsers()
	 */
	public List<User> getAllUsers() {

		
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);

        List<User> users=new ArrayList<>();
        try {
            tx.begin();
            Extent<User> extentP = pm.getExtent(User.class);

            for (User u : extentP) {

               users.add(u);
               
                }

            tx.commit();
        } catch (Exception ex) {
        	   ////////////logger.error("# Error getting Extent getAllUsers: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        return users;

	}
	
	
/**
 * This method returns the first license stored in the database
 * @param name This is the name of a license
 * @return License Returns a license
 * @see es.deusto.server.db.dao.IDAO#getFirstLicense(java.lang.String)
 */
public License getFirstLicense(String name) {
	PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    pm.getFetchPlan().setMaxFetchDepth(3);
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
    	   //logger.error("# Error getting Extent getLicenses: " + ex.getMessage());
    } finally {
        if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
    }
    return u;
	}
	
	
	/**
	 * This methos retrieves a genre
	 * @param name This is the name of a genre
	 * @return Genre Returns a genre
	 * @see es.deusto.server.db.dao.IDAO#retrieveGenre(java.lang.String)
	 */
	public Genre retrieveGenre(String name) {
		Genre genre = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			genre = pm.getObjectById(Genre.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
					//logger.warn("Genre does not exist: " + jonfe.getMessage());
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
	 * This method retrieves a game
	 * @param name This is the name of a game
	 * @return Game Returns a game
	 * @see es.deusto.server.db.dao.IDAO#retrieveGame(java.lang.String)
	 */
	public Game retrieveGame(String name) {
		Game game = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			game = pm.getObjectById(Game.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
					//logger.warn("Game does not exist: " + jonfe.getMessage());
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
	 * This method retrieves a company
	 * @param name This is the name of a company
	 * @return Company Returns a company
	 * @see es.deusto.server.db.dao.IDAO#retrieveCompany(java.lang.String)
	 */
	public Company retrieveCompany(String name) {
		Company company = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			company = pm.getObjectById(Company.class, name);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
			//logger.warn("Company does not exist: " + jonfe.getMessage());
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
	 * This method retrieves a license
	 * @param gameKey This is the key of a license
	 * @return License Returns a license
	 * @see es.deusto.server.db.dao.IDAO#retrieveLicense(java.lang.String)
	 */
	public License retrieveLicense(String gameKey) {
		License license = null;
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			license = pm.getObjectById(License.class, gameKey);
			tx.commit();
		} catch (javax.jdo.JDOObjectNotFoundException jonfe)
		{
			//logger.warn("User does not exist: " + jonfe.getMessage());
		}

		finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();
	    }

		return license;
	}
	
}
