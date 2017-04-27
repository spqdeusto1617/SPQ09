package es.deusto.server.db;

import java.util.List;

import es.deusto.server.db.data.Game;

public class DbMethods{

	public static List<Game> getAllGames() {
		IDB db = new DB();
		return db.getGamesFromDB();
	}

	public static List<Game> getUserGames(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean buyGame(User u, Game game) {
		// TODO Auto-generated method stub
		
		Game g = null;
		License l = 
		return false;
	}

}
