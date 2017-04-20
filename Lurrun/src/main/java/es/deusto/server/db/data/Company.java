package es.deusto.server.db.data;

import java.io.Serializable;

/**********************************************************************
Copyright (c) 2003 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/


import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;


@PersistenceCapable (detachable = "true")

public class Company implements Serializable
{
	private static final long serialVersionUID = 1L;
    protected String name=null;
    @Persistent(defaultFetchGroup="true", mappedBy="company", dependentElement="true")
	@Join
    List<Game> companyGames = new ArrayList<Game>();
    
    protected Company(){
    }
    
    public List<Game> getCompanyGames() {
		return this.companyGames;
	}

	public void setCompanyGames(List<Game> companyGames) {
		this.companyGames = companyGames;
	}

	public void addGame(Game game) {
    	companyGames.add(game);
	}

	public void removeGame(Game game) {
		companyGames.remove(game);
	}

    public Company(String name)
    {
        this.name = name;
      
    }
	
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String toString() {
		 if (companyGames.isEmpty()) {
			 return "This company has no games Company: name --> " + this.name;
			 
		 } else {
			StringBuffer gamesStr = new StringBuffer();
			for (Game game: this.companyGames) {
				gamesStr.append(game.toString() + " - ");
			}
		
	        return "Company: name --> " + this.name +", games --> [" + gamesStr + "]";
	 
		 }
	 }
}