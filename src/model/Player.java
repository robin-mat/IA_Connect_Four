package model;

import java.util.UUID;
import util.Constants;

import model.strategy.*;

public class Player {
	public String name;
	public String uuid;

	private Strategy strategy;

	public Player(String name, Strategy strategy){
		this.name = name;
		this.strategy = strategy;
		this.uuid = UUID.randomUUID().toString();
	}
	public Player(){
	}

	public int play(){
    	return this.strategy.choice();
	}

	public String getName(){
		return this.name;
	}
	public String getUuid(){
		return this.uuid;
	}
}