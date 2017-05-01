package es.deusto.server.db;

import java.util.ArrayList;
import java.util.List;


import es.deusto.server.db.dao.IDAO;
import es.deusto.server.db.dao.DAO;
import es.deusto.server.db.data.*;

public class DB implements IDB {

	private static final long serialVersionUID = 1L;
	IDAO dao;

	public DB(){
		super();
		dao = new DAO();

	}

	public DB(IDAO udao) {
		super();
		dao = udao;

	}

	public  List<Game> getUserGames(String username) {
		User u= showUser(username);
		List <Game> gameList = new ArrayList<>();
		for (License l :u.getLicenses() ) {
			gameList.add(l.getGame());
        }
		return gameList;

	}

	public  boolean buyGame(String username, String name) {
		User u = showUser(username);
		License l =	showGame(name).getFirstFreeLicense();
		return addLicenseToUser(u, l);
	}

	public boolean registerUser(User u) {
		User user = null;
		try {
			user = dao.retrieveUser(u.getLogin());
		} catch (Exception  e) {
			//System.out.println("Exception launched: " + e.getMessage());
			return false;
		}

		if (user != null) {
			user.setPassword(u.getPassword());
			user.setSuperuser(u.getSuperuser());
			dao.updateUser(user);
		} else {
			dao.storeUser(u);
		}
		return true;
	}

	public boolean addGameToDb(Game g,Genre gg, Company c) {
		Game game = null;
		Genre genre = null;
		Company company = null;
		boolean ret=true;

		try {

			game  = dao.retrieveGame(g.getName());
			genre = dao.retrieveGenre(gg.getName());
			company = dao.retrieveCompany(c.getName());

		} catch (Exception  e) {
			//	System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret = false;
		}

		if (game != null && genre != null && company != null  ) {
			System.out.println("Case 1");
		} else if (game == null && genre != null && company == null  ){
			System.out.println("Case 2");

			g.setCompany(c);
			g.setGenre(genre);

			genre.addGame(g);
			c.addGame(g);

			dao.updateGenre(genre);
			dao.storeGame(g);
		} else if (game == null && genre == null && company != null  ){
			System.out.println("Case 3");

			g.setCompany(company);
			g.setGenre(gg);
	
			gg.addGame(g);
			company.addGame(g);
	
			dao.updateCompany(company);
			dao.storeGame(g);
		} else {
			System.out.println("Case 4");
			
			g.setCompany(c);
			g.setGenre(gg);

			gg.addGame(g);
			c.addGame(g);

			dao.storeGame(g);

		}
		return ret;
	}

	public boolean addLicenseToGame(Game g, License l) {
		Game game = null;
		License license = null;
		boolean ret=true;
		try {

			game  = dao.retrieveGame(g.getName());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception  e) {
			//		System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (game != null && license != null  ) {



		}else if (game !=null && license == null){

			l.setGame(game);
			game.addLicense(l);

			dao.updateGame(game);

		}
		else if (game== null)  {


		}
		return ret;
}

	public boolean addLicenseToUser(User u, License l) {
		User user = null;
		License license = null;
		boolean ret=true;
		try {
			user = dao.retrieveUser(u.getLogin());
			license = dao.retrieveLicense(l.getGameKey());

		} catch (Exception  e) {
			System.out.println("Exception launched in checking if the data already exist: " + e.getMessage());
			ret=false;
		}

		if (user != null && license != null  ) {

			license.setUser(user);
			user.getLicenses().add(license);

			dao.updateUser(user);


		}else if ( license == null || user == null ){
			System.out.println("Create the user or the license " + l.getGameKey() + u.getLogin());

		}
		return ret;
	}
	
	public Game showGame(String name){
		 Game g=dao.retrieveGame(name);
		
		return g;

	}
	public Genre showGenre(String name){
		 Genre genr=dao.retrieveGenre(name);
		return genr;

	}
	public Company showCompany(String name){
		 Company c=dao.retrieveCompany(name);
		return c;

	}
	public License showLicense(String gameKey){
		 License l=dao.retrieveLicense(gameKey);
		return l;

	}

	public User showUser(String login){
		 User u=dao.retrieveUser(login);
		return u;

	}
	@Override
	public List<Game> getAllGames() {
		return dao.getAllGames();

	}


}
