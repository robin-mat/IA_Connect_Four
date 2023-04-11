package model;

import java.util.UUID;
import util.Constants;

import model.strategy.*;
import model.*;

import util.Logger;

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

	public int play(BoardProxy proxy, Player currentPlayer, Logger logger){
    	int choice = this.strategy.choice(proxy.getGrid(currentPlayer));
    	while (!proxy.canPlay(choice)){
			logger.write("Unable to perform, select a valid move !");
			choice = this.strategy.choice(proxy.getGrid(currentPlayer));
			logger.write("Choose the column "+choice);
		}
		return choice;
	}

	public void setStrategie(Strategy temp){
		this.strategy = temp;
	}

	public Strategy getStrategie(){
		return this.strategy;
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
