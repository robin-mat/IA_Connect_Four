package play;

import model.*;
import view.*;

public class Game implements GameInterface {
	private Player player1;
	private Player player2;
	private Player currentPlayer;

	private ViewInterface view;

	public Game(Player p1, Player p2, ViewInterface v){
		this.player1 = p1;
		this.player2 = p2;
		this.currentPlayer = this.player1;
		this.view = v;
	}

	public void launch(){
		this.view.launch();
	}


	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}

	public void changeCurrentPlayer(){
		if (this.player1.equals(this.currentPlayer)){
			this.currentPlayer = this.player2;
		} else {
			this.currentPlayer = this.player1;
		}
	}





}