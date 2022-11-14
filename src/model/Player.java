package model;


import java.util.UUID;
import util.Constants;

public class Player {
	public String name;
	public String uiid;

	public Player(String name){
		this.name = name;
		this.uiid = UUID.randomUUID().toString();
	}
	public Player(){
		this("Joueur sans nom");
	}

	public String getName(){
		return this.name;
	}
	public String getUiid(){
		return this.uiid;
	}
}