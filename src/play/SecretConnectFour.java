package play;

import model.*;
import view.*;
import util.Constants;
import util.Logger;

public class SecretConnectFour implements GameInterface {
	public Logger logger;

	private Player player1;
	private Player player2;
	private Player currentPlayer;


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
		this.view.setLogger(this.logger);
		this.view.setBoard(this.board);
		this.view.launch();
		this.board.init();
		this.view.update();
		while (!this.isFinish() && this.board.canPlay()){

			this.logger.write("Waiting "+this.getCurrentPlayer().getName()+", uuid:"+this.getCurrentPlayer().getUuid());
			this.view.setBoard(this.board);
			int choice = this.currentPlayer.play();
			this.logger.write("Choose the column "+choice);
			while (!this.board.canPlay(choice)){
				this.logger.write("Unable to perform, select a valid move !");
				choice = this.currentPlayer.play();
				this.logger.write("Choose the column "+choice);
			}
			this.board.addPawn(choice, this.currentPlayer);
			this.view.update();
			this.changeCurrentPlayer();
		}
		this.logger.write("The game is over");

	}

	public boolean isFinish(){
		int compt_pawn = 0;
		int[] temp = this.board.getLastPawn();
		this.logger.write("Last pawn placed = ("+Integer.toString(temp[0])+","+Integer.toString(temp[1])+")");
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
				this.logger.write("The winner is " + getCurrentPlayer().getName());
				return true;
				}
			}
			return false;
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
