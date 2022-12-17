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


	private Board board;

	private ViewInterface view;

	public SecretConnectFour(Player p1, Player p2, ViewInterface v){
		this.player1 = p1;
		this.player2 = p2;
		this.currentPlayer = this.player1;
		this.view = v;
		this.board = new Board(Constants.GRID_SIZE[0], Constants.GRID_SIZE[1]);
		this.logger = new Logger();
	}

	public void launch(){
		this.view.setGameInterface(this);
		this.choiceRandomCurrentPlayer();
		this.view.setLogger(this.logger);
		this.view.setBoard(this.board);
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
			try {TimeUnit.MILLISECONDS.sleep(1000); } catch (Exception e) {
				//System.out.println(e);
			}
			this.board.init();
			this.view.setBoard(this.board);
			this.view.update();
        }
	}

	public void play1Game(){
		this.board.init();
		this.view.update();
		this.choiceRandomCurrentPlayer();
		while (!this.isFinish() && this.board.canPlay()){
			this.changeCurrentPlayer();
			this.view.refreshInfos();
			this.logger.write("Waiting "+this.getCurrentPlayer().getName()+", uuid:"+this.getCurrentPlayer().getUuid());
			//this.view.setBoard(this.board);
			int choice = this.currentPlayer.play();
			this.logger.write("Choose the column "+choice);
			while (!this.board.canPlay(choice)){
				this.logger.write("Unable to perform, select a valid move !");
				choice = this.currentPlayer.play();
				this.logger.write("Choose the column "+choice);
			}
			this.board.addPawn(choice, this.currentPlayer);
			this.rounds = this.rounds+1;
			this.view.update();
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
		int[] temp = this.board.getLastPawn();
		this.logger.write("Last pawn placed = ("+Integer.toString(temp[0])+","+Integer.toString(temp[1])+")");

		for (int i = 0; i < 7; i++) {
	      for (int j = 5; j > -1; j--) {
	      	Player jVerif = this.board.getGrid()[i][j].getPlayed();

	      	if (jVerif != null){
	      		// Vérifier si le joueur a 4 pièces sur une ligne
		      	if (i+3 < 7){
		      		if (jVerif.equals(this.board.getGrid()[i+1][j].getPlayed())) {
				        if (jVerif.equals(this.board.getGrid()[i+2][j].getPlayed())) {
				            if (jVerif.equals(this.board.getGrid()[i+3][j].getPlayed())) {
				            	this.board.getGrid()[i][j].setComboWinner(true);
				            	this.board.getGrid()[i+1][j].setComboWinner(true);
				            	this.board.getGrid()[i+2][j].setComboWinner(true);
				            	this.board.getGrid()[i+3][j].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}

		      	// Vérifier si le joueur a 4 pièces sur une colonne
		      	if (j-3 > -1){
		      		if (jVerif.equals(this.board.getGrid()[i][j-1].getPlayed())) {
				        if (jVerif.equals(this.board.getGrid()[i][j-2].getPlayed())) {
				            if (jVerif.equals(this.board.getGrid()[i][j-3].getPlayed())) {
				            	this.board.getGrid()[i][j].setComboWinner(true);
				            	this.board.getGrid()[i][j-1].setComboWinner(true);
				            	this.board.getGrid()[i][j-2].setComboWinner(true);
				            	this.board.getGrid()[i][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}


	        	// Vérifier si le joueur a 4 pièces sur une diagonale de gauche à droite
	        	if (j-3 > -1 && i + 3 < 7){
		      		if (jVerif.equals(this.board.getGrid()[i+1][j-1].getPlayed())) {
				        if (jVerif.equals(this.board.getGrid()[i+2][j-2].getPlayed())) {
				            if (jVerif.equals(this.board.getGrid()[i+3][j-3].getPlayed())) {
				            	this.board.getGrid()[i][j].setComboWinner(true);
				            	this.board.getGrid()[i+1][j-1].setComboWinner(true);
				            	this.board.getGrid()[i+2][j-2].setComboWinner(true);
				            	this.board.getGrid()[i+3][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}




	        	// Vérifier si le joueur a 4 pièces sur une diagonale de droite à gauche
	        	if (j-3 > -1 && i - 3 > -1){
		      		if (jVerif.equals(this.board.getGrid()[i-1][j-1].getPlayed())) {
				        if (jVerif.equals(this.board.getGrid()[i-2][j-2].getPlayed())) {
				            if (jVerif.equals(this.board.getGrid()[i-3][j-3].getPlayed())) {
				            	this.board.getGrid()[i][j].setComboWinner(true);
				            	this.board.getGrid()[i-1][j-1].setComboWinner(true);
				            	this.board.getGrid()[i-2][j-2].setComboWinner(true);
				            	this.board.getGrid()[i-3][j-3].setComboWinner(true);
					            this.winner = getCurrentPlayer();
					            return true;
					        }
				        }
			        }
		      	}
	      	}
	      }
	    }
		/*
		if((temp[0] != -1) && (temp[1] != -1)){

			//Horizontale
			if(temp[1] < this.board.getLen_y()-1){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]][temp[1]+1].getPlayed()){
					compt_pawn += 1;
					if(temp[1] < this.board.getLen_y()-2){
						if (this.board.getGrid()[temp[0]][temp[1]+1].getPlayed() == this.board.getGrid()[temp[0]][temp[1]+2].getPlayed()){
							compt_pawn += 1;
							if(temp[1] < this.board.getLen_y()-3){
								if (this.board.getGrid()[temp[0]][temp[1]+2].getPlayed() == this.board.getGrid()[temp[0]][temp[1]+3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			if(temp[1] > 0){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]][temp[1]-1].getPlayed()){
					compt_pawn += 1;
					if(temp[1] > 1){
						if (this.board.getGrid()[temp[0]][temp[1]-1].getPlayed() == this.board.getGrid()[temp[0]][temp[1]-2].getPlayed()){
							compt_pawn += 1;
							if(temp[1] > 2){
								if (this.board.getGrid()[temp[0]][temp[1]-2].getPlayed() == this.board.getGrid()[temp[0]][temp[1]-3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}

			//Verticale
			if(compt_pawn < 3){
				compt_pawn = 0;
			}
			if(temp[0] < this.board.getLen_x()-1){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]+1][temp[1]].getPlayed()){
					compt_pawn += 1;
					if(temp[0] < this.board.getLen_x()-2){
						if (this.board.getGrid()[temp[0]+1][temp[1]].getPlayed() == this.board.getGrid()[temp[0]+2][temp[1]].getPlayed()){
							compt_pawn += 1;
							if(temp[0] < this.board.getLen_x()-3){
								if (this.board.getGrid()[temp[0]+2][temp[1]].getPlayed() == this.board.getGrid()[temp[0]+3][temp[1]].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			if(temp[0] > 0){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]-1][temp[1]].getPlayed()){
					compt_pawn += 1;
					if(temp[0] > 1){
						if (this.board.getGrid()[temp[0]-1][temp[1]].getPlayed() == this.board.getGrid()[temp[0]-2][temp[1]].getPlayed()){
							compt_pawn += 1;
							if(temp[0] > 2){
								if (this.board.getGrid()[temp[0]-2][temp[1]].getPlayed() == this.board.getGrid()[temp[0]-3][temp[1]].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}

			//Diagonale \
			if(compt_pawn < 3){
				compt_pawn = 0;
			}
			if((temp[0] < this.board.getLen_x()-1) && (temp[1] < this.board.getLen_y()-1)){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]+1][temp[1]+1].getPlayed()){
					compt_pawn += 1;
					if((temp[0] < this.board.getLen_x()-2) && (temp[1] < this.board.getLen_y()-2)){
						if (this.board.getGrid()[temp[0]+1][temp[1]+1].getPlayed() == this.board.getGrid()[temp[0]+2][temp[1]+2].getPlayed()){
							compt_pawn += 1;
							if((temp[0] < this.board.getLen_x()-3) && (temp[1] < this.board.getLen_y()-3)){
								if (this.board.getGrid()[temp[0]+2][temp[1]+2].getPlayed() == this.board.getGrid()[temp[0]+3][temp[1]+3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			if((temp[0] > 0) && (temp[1] > 0)){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]-1][temp[1]-1].getPlayed()){
					compt_pawn += 1;
					if((temp[0] > 1) && (temp[1] > 1)){
						if (this.board.getGrid()[temp[0]-1][temp[1]-1].getPlayed() == this.board.getGrid()[temp[0]-2][temp[1]-2].getPlayed()){
							compt_pawn += 1;
							if((temp[0] > 2) && (temp[1] > 2)){
								if (this.board.getGrid()[temp[0]-2][temp[1]-2].getPlayed() == this.board.getGrid()[temp[0]-3][temp[1]-3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			//Diagonale /
			if(compt_pawn < 3){
				compt_pawn = 0;
			}
			if((temp[0] > 0) && (temp[1] < this.board.getLen_y()-1)){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]-1][temp[1]+1].getPlayed()){
					compt_pawn += 1;
					if((temp[0] > 1) && (temp[1] < this.board.getLen_y()-2)){
						if (this.board.getGrid()[temp[0]-1][temp[1]+1].getPlayed() == this.board.getGrid()[temp[0]-2][temp[1]+2].getPlayed()){
							compt_pawn += 1;
							if((temp[0] > 2) && (temp[1] < this.board.getLen_y()-3)){
								if (this.board.getGrid()[temp[0]-2][temp[1]+2].getPlayed() == this.board.getGrid()[temp[0]-3][temp[1]+3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			if((temp[0] > this.board.getLen_x()-1) && (temp[1] < 0)){
				if (this.board.getGrid()[temp[0]][temp[1]].getPlayed() == this.board.getGrid()[temp[0]+1][temp[1]-1].getPlayed()){
					compt_pawn += 1;
					if((temp[0] > this.board.getLen_x()-2) && (temp[1] < 1)){
						if (this.board.getGrid()[temp[0]+1][temp[1]-1].getPlayed() == this.board.getGrid()[temp[0]+2][temp[1]-2].getPlayed()){
							compt_pawn += 1;
							if((temp[0] > this.board.getLen_x()-3) && (temp[1] < 2)){
								if (this.board.getGrid()[temp[0]+2][temp[1]-2].getPlayed() == this.board.getGrid()[temp[0]+3][temp[1]-3].getPlayed()){
									compt_pawn += 1;
								}
							}
						}
					}
				}
			}
			this.logger.write("Mon compt_pawn = " + compt_pawn);
			if(compt_pawn == 3){
					this.winner = getCurrentPlayer();
					return true;
				}
			}
		*/

		return false;
		}


	public Player getCurrentPlayer(){
		return this.currentPlayer;
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

	public Board getBoard(){
		return this.board;
	}

	public int getP1Score(){
		return this.player1.getScore();
	}

	public int getP2Score(){
		return this.player2.getScore();
	}
}
