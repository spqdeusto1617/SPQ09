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
	public void storeUser(User u) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    try {
	       tx.begin();
	       System.out.println("   * Storing a user: " + u.getLogin());
		       pm.makePersistent(u);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}
					
	    		pm.close();
		    }
		}
	
	public void storeLicense(License u) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    try {
	       tx.begin();
	       System.out.println("   * Storing a License: " + u.getGameKey());
		       pm.makePersistent(u);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}
					
	    		pm.close();
		    }
		}
	@Override
	public License retrieveLicense(String gameKey) {
		System.out.println("Get License from db "+gameKey);
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
		
	public	void updateLicense(License g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a License: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
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
	public void updateUser(User u) {
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(u);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a user: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }

	}

	
	
	public	void storeGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    try {
	       tx.begin();
	       System.out.println("   * Storing a game: " + g.getName());
		       pm.makePersistent(g);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}
					
	    		pm.close();
		    }
		}
	
	
	public	Game retrieveGame(String name){
		System.out.println("Get Game from db "+name);
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
	
	public	void updateGame(Game g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a game: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}
	
	
	public	void storeCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    try {
	       tx.begin();
	       System.out.println("   * Storing a Company: " + c.getName());
		       pm.makePersistent(c);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}
					
	    		pm.close();
		    }
		}
	
	
	public	Company retrieveCompany(String name){
		  System.out.println("Get Company from db "+name);
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
	
	
	public	void updateCompany(Company c){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(c);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a company: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}
	
	public void storeGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
	    try {
	       tx.begin();
	       System.out.println("   * Storing a Genre: " + g.getName());
		       pm.makePersistent(g);
		       tx.commit();
		    } catch (Exception ex) {
		    	System.out.println("   $ Error storing an object: " + ex.getMessage());
		    } finally {
		    	if (tx != null && tx.isActive()) {
		    		tx.rollback();
		    	}
					
	    		pm.close();
		    }
		}
	

	public Genre retrieveGenre(String name){
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
	
	public void updateGenre(Genre g){
		PersistenceManager pm = pmf.getPersistenceManager();
	    Transaction tx = pm.currentTransaction();
	    
	    try {
	    	tx.begin();
	    	pm.makePersistent(g);
	    	tx.commit();
	     } catch (Exception ex) {
		   	System.out.println("Error updating a genre: " + ex.getMessage());
	     } finally {
		   	if (tx != null && tx.isActive()) {
		   		tx.rollback();
		   	}
				
	   		pm.close();
	     }
	}
	
	
	public List<Game> getAllGames() {
		
		System.out.println("Get all Games from db ");
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
}

	
	
	
	
	
	
	
	
	
	
	
	

