package es.deusto.server.db.dao;


import javax.jdo.*;

import java.util.ArrayList;
import java.util.List;


import es.deusto.server.db.data.*;

public class DAO implements IDAO {

	private PersistenceManagerFactory pmf;

	public DAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

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
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    	ret=false;
		    
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return ret;
		}

	public boolean storeLicense(License u) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean ret=true;
	    try {
	       tx.begin();
	
		       pm.makePersistent(u);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    	ret =false;
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return ret;
		}
	
	@Override
	public License retrieveLicenseByParameter(String gameKey) {
	
	    PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    pm.getFetchPlan().setMaxFetchDepth(3);
	    License u = null;
	    
	    try {
	        tx.begin();
	        Extent<License> extentP = pm.getExtent(License.class);

	        for (License p : extentP) {

	            if (p.getGameKey().equals(gameKey)) {
	                u = p;
	            }
	        }
	        tx.commit();
	    } catch (Exception ex) {
	        System.out.println("# Error getting Extent: " + ex.getMessage());
	       
	    } finally {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        pm.close();
	    }
	    System.out.println(u);
	    return u;
	}

	public	boolean updateLicense(License g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean ret=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a License: " + ex.getMessage());
		   	ret = false;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}

	   		pm.close();
	     }
	    return ret;
	}

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
			System.out.println("User does not exist: " + jonfe.getMessage());
		}

		finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();
	    }

		return user;
	}

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
		   	System.out.println("Error updating a user: " + ex.getMessage());
		   	r=false;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}

	   		pm.close();
	     }
	    return r;
	}

	public	boolean storeGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r=true;
	    try {
	       tx.begin();
	 
		       pm.makePersistent(g);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    	r=false;
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return r;
		}

	public	Game retrieveGameByParameter(String name){

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);
        Game u = null;
        try {
            tx.begin();
            Extent<Game> extentP = pm.getExtent(Game.class);

            for (Game p : extentP) {

                if (p.getName().equals(name)) {
                    u = p;

                }
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("# Error getting Extent: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        System.out.println(u);
        return u;
	}

	public	boolean updateGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a game: " + ex.getMessage());
		   	r=false;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}

	   		pm.close();
	     }
	    return r;
	}

	public	boolean storeCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r= true;
	    try {
	       tx.begin();
	       System.out.println("   * Storing a Company: " + c.getName());
		       pm.makePersistent(c);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    	r =false;
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return r;
		}

	public	Company retrieveCompanyByParameter(String name){
	
	        PersistenceManager pm = pmf.getPersistenceManager();
	        Transaction tx = pm.currentTransaction();
	        pm.getFetchPlan().setMaxFetchDepth(3);
	        Company u = null;
	        try {
	            tx.begin();
	            Extent<Company> extentP = pm.getExtent(Company.class);

	            for (Company p : extentP) {

	                if (p.getName().equals(name)) {
	                    u = p;
	                }
	            }
	            tx.commit();
	        } catch (Exception ex) {
	            System.out.println("# Error getting Extent: " + ex.getMessage());
	        } finally {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	            pm.close();
	        }
	        System.out.println(u);
	        return u;
	    }

	public	boolean updateCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r= true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(c);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a company: " + ex.getMessage());
		   	r=false;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}

	   		pm.close();
	     }
	    return r;
	}

	public boolean storeGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean r= true;
	    try {
	       tx.begin();
	       System.out.println("   * Storing a Genre: " + g.getName());
		       pm.makePersistent(g);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    	r=false;
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}

	    		pm.close();
		    }
	    return r;
		}

	public Genre retrieveGenreByParameter(String name){
		System.out.println("Get Genre from db "+name);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        pm.getFetchPlan().setMaxFetchDepth(3);
        Genre u = null;
        try {
            tx.begin();
            Extent<Genre> extentP = pm.getExtent(Genre.class);

            for (Genre p : extentP) {

                if (p.getName().equals(name)) {
                    u = p;

                }
            }
            tx.commit();
        } catch (Exception ex) {
            System.out.println("# Error getting Extent: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        System.out.println(u);
        return u;
	}

	public boolean updateGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    boolean r=true;
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a genre: " + ex.getMessage());
		   	r=false;
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}

	   		pm.close();
	     }
	    return r;
	}

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
            System.out.println("# Error getting Extent: " + ex.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        return games;

	}
		
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
			System.out.println("User does not exist: " + jonfe.getMessage());
		}

		finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();
	    }

		return genre;
	}
	
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
			System.out.println("User does not exist: " + jonfe.getMessage());
		}

		finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();
	    }

		return game;
	}
	
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
			System.out.println("User does not exist: " + jonfe.getMessage());
		}

		finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();
	    }

		return company;
	}
	
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
			System.out.println("User does not exist: " + jonfe.getMessage());
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