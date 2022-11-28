package model;


import java.util.UUID;
import util.Constants;

public class Player {
	public String name;
	public String uuid;

	public Player(String name){
		this.name = name;
		this.uuid = UUID.randomUUID().toString();
	}
	public Player(){
		this("No name Player");
	}

	public String getName(){
		return this.name;
	}
	public String getUuid(){
		return this.uuid;
	}
}