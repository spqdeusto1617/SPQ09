package es.deusto.server.db.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;
private String name;

public String getName() {
	return name;
}

@Override
public String toString() {
	return "Genre [name=" + name + "]";
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
