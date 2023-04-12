package play;

import model.*;
import view.*;
import util.Constants;
import util.Logger;

import java.util.concurrent.TimeUnit;

import java.util.Random;

public class SecretConnectFour implements GameInterface {
	public Logger logger;

	private Player player1;
	private Player player2;
	public Player winner;
	private Player currentPlayer;
	private int rounds;


	private BoardProxy boardProxy;

	private ViewInterface view;

	public SecretConnectFour(Player p1, Player p2, ViewInterface v){
		this.player1 = p1;
		this.player2 = p2;
		this.currentPlayer = this.player1;
		this.view = v;
		this.boardProxy = new BoardProxy(Constants.GRID_SIZE[0], Constants.GRID_SIZE[1], player1, player2);
		this.logger = new Logger();
	}

	public void launch(){
		this.view.setGameInterface(this);
		this.choiceRandomCurrentPlayer();
		this.view.setLogger(this.logger);
		this.view.setBoard(this.boardProxy);
		this.view.launch();
		Constants.GUI_NB_GAMES = -1;
		int result = Constants.GUI_NB_GAMES;
		while (result == -1){
			try { result = Constants.GUI_NB_GAMES;TimeUnit.MILLISECONDS.sleep(50); } catch (Exception e) {
				//System.out.println(e);
			}
		}
		for (int i = 0; i < result; i++) {
			this.winner = null;
			this.rounds = 0;
			this.play1Game();
			try {TimeUnit.MILLISECONDS.sleep(Constants.TIME_BT_GAMES_ms); } catch (Exception e) {
				//System.out.println(e);
			}
			this.boardProxy.init();
			this.view.setBoard(this.boardProxy);
			this.view.update();
        }
        this.view.finish();
	}

	public void play1Game(){
		this.boardProxy.init();
		this.view.update();
		this.choiceRandomCurrentPlayer();
		while (!this.isFinish() && this.boardProxy.canPlay()){
			this.changeCurrentPlayer();
			this.view.refreshInfos();
			this.logger.write("Waiting "+this.getCurrentPlayer().getName()+", uuid:"+this.getCurrentPlayer().getUuid());
			//this.view.setBoard(this.boardProxy);
			int choice = this.currentPlayer.play(this.boardProxy, this.getCurrentPlayer(), this.logger);
			this.logger.write("Choose the column "+choice);

			this.boardProxy.addPawn(choice, this.currentPlayer);
			this.rounds = this.rounds+1;
			this.view.update();
			try {TimeUnit.MILLISECONDS.sleep(Constants.TIME_BT_ROUNDS_ms); } catch (Exception e) {
				//System.out.println(e);
			}
		}
		this.view.update();
		this.logger.write("--------------------");
		this.logger.write("  The game is over  ");
		if (this.winner != null){
			this.logger.write("Winner is "+this.winner.getName());
			this.winner.add1Score();
		} else {
			this.logger.write("Draw");
		}
		this.logger.write("--------------------");
		this.view.refreshInfos();
	}

	public boolean isFinish(){
		int compt_pawn = 0;
		int[] temp = this.boardProxy.getLastPawn();
		this.logger.write("Last pawn placed = ("+Integer.toString(temp[0])+","+Integer.toString(temp[1])+")");

		for (int i = 0; i < 7; i++) {
	      for (int j = 5; j > -1; j--) {
	      	Player jVerif = this.boardProxy.getGrid()[i][j].getPlayed();

	      	if (jVerif != null){
	      		// Vérifier si le joueur a 4 pièces sur une ligne
		      	if (i+3 < 7){
		      		if (jVerif.equals(this.boardProxy.getGrid()[i+1][j].getPlayed())) {
				        if (jVerif.equals(this.boardProxy.getGrid()[i+2][j].getPlayed())) {
				            if (jVerif.equals(this.boardProxy.getGrid()[i+3][j].getPlayed())) {
				            	this.boardProxy.getGrid()[i][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+1][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+2][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+3][j].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}

		      	// Vérifier si le joueur a 4 pièces sur une colonne
		      	if (j-3 > -1){
		      		if (jVerif.equals(this.boardProxy.getGrid()[i][j-1].getPlayed())) {
				        if (jVerif.equals(this.boardProxy.getGrid()[i][j-2].getPlayed())) {
				            if (jVerif.equals(this.boardProxy.getGrid()[i][j-3].getPlayed())) {
				            	this.boardProxy.getGrid()[i][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i][j-1].setComboWinner(true);
				            	this.boardProxy.getGrid()[i][j-2].setComboWinner(true);
				            	this.boardProxy.getGrid()[i][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}


	        	// Vérifier si le joueur a 4 pièces sur une diagonale de gauche à droite
	        	if (j-3 > -1 && i + 3 < 7){
		      		if (jVerif.equals(this.boardProxy.getGrid()[i+1][j-1].getPlayed())) {
				        if (jVerif.equals(this.boardProxy.getGrid()[i+2][j-2].getPlayed())) {
				            if (jVerif.equals(this.boardProxy.getGrid()[i+3][j-3].getPlayed())) {
				            	this.boardProxy.getGrid()[i][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+1][j-1].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+2][j-2].setComboWinner(true);
				            	this.boardProxy.getGrid()[i+3][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}




	        	// Vérifier si le joueur a 4 pièces sur une diagonale de droite à gauche
	        	if (j-3 > -1 && i - 3 > -1){
		      		if (jVerif.equals(this.boardProxy.getGrid()[i-1][j-1].getPlayed())) {
				        if (jVerif.equals(this.boardProxy.getGrid()[i-2][j-2].getPlayed())) {
				            if (jVerif.equals(this.boardProxy.getGrid()[i-3][j-3].getPlayed())) {
				            	this.boardProxy.getGrid()[i][j].setComboWinner(true);
				            	this.boardProxy.getGrid()[i-1][j-1].setComboWinner(true);
				            	this.boardProxy.getGrid()[i-2][j-2].setComboWinner(true);
				            	this.boardProxy.getGrid()[i-3][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}
	      	}
	      }
	    }
		return false;
		}


	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}

	public Player getWaitingPlayer(){
		if (this.player1.equals(this.currentPlayer)){
			return this.player2;
		} else {
			return this.player1;
		}
	}

	public void choiceRandomCurrentPlayer(){
		int randomNumber = (int)(Math.random() * 2);

		if (randomNumber == 0){
			this.currentPlayer = this.player2;
		} else {
			this.currentPlayer = this.player1;
		}
	}

	public void changeCurrentPlayer(){
		if (this.player1.equals(this.currentPlayer)){
			this.currentPlayer = this.player2;
		} else {
			this.currentPlayer = this.player1;
		}
	}

	public Player getWinner(){
		return this.winner;
	}
	public int getRounds(){
		return this.rounds;
	}

	public BoardProxy getboardProxy(){
		return this.boardProxy;
	}

	public int getP1Score(){
		return this.player1.getScore();
	}

	public int getP2Score(){
		return this.player2.getScore();
	}

	public Player getP2(){
		return this.player2;
	}

	public Player getP1(){
		return this.player1;
	}
}
