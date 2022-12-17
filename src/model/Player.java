package model;

import java.util.UUID;
import util.Constants;

import model.strategy.*;

public class Player {
	public String name;
	public String uuid;
	private int score;

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

	public void add1Score(){
		this.score+=1;
	}
	public int getScore(){
		return this.score;
	}
}