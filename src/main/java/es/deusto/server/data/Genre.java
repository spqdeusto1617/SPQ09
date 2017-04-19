package es.deusto.server.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable (detachable = "true")
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;
private String name;


@Persistent(defaultFetchGroup="true", mappedBy="genre", dependentElement="true")
@Join
List<Game> genreGames = new ArrayList<Game>();

public void addGame(Game game) {
	genreGames.add(game);
}

public List<Game> getGenreGames() {
	return this.genreGames;
}

public void setGenreGames(List<Game> genreGames) {
	this.genreGames = genreGames;
}

public void removeGame(Game game) {
	genreGames.remove(game);
}


public String getName() {
	return name;
}

@Override
public String toString() {
	 if (genreGames.isEmpty()) {
		 return "This Genre has no games Genre: name --> " + this.name;
		 
	 } else {
		 StringBuffer gamesStr = new StringBuffer();
			for (Game game: this.genreGames) {
				gamesStr.append(game.toString() + " - ");
			}
		
	        return "Genre: name --> " + this.name +", games --> [" + gamesStr + "]";
 
	 }
 }

public Genre(String name) {
	super();
	this.name = name;
}

public Genre() {
	super();
	// TODO Auto-generated constructor stub
}

public void setName(String name) {
	this.name = name;
}
}
