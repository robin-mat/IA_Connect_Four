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
			this.changeCurrentPlayer();
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
		}
		this.logger.write("The game is over");

	}

	public boolean isFinish(){
		//UTILE POUR UNE OPTI TODO :
		int[] temp = this.board.getLastPawn();
		this.logger.write("Last pawn placed = ("+Integer.toString(temp[0])+","+Integer.toString(temp[1])+")");
		//
		//
		//


		for (int i=0; i!=this.board.getLen_x(); i++){ //Verifie si win 4 horizontale
			for (int y=0; y!=this.board.getLen_y()-3; y++){
				if(this.board.getGrid()[i][y].getPlayed() != null){
					if (this.board.getGrid()[i][y].getPlayed() == this.board.getGrid()[i][y+1].getPlayed()){
						if (this.board.getGrid()[i][y+1].getPlayed() == this.board.getGrid()[i][y+2].getPlayed()){
							if (this.board.getGrid()[i][y+2].getPlayed() == this.board.getGrid()[i][y+3].getPlayed()){
								this.logger.write("The winner is " + getCurrentPlayer().getName());
								return true;
							}
						}
					}
				}
			}
		}
		for (int i=0; i!=this.board.getLen_x()-3; i++){ //Verifie si win 4 verticale
			for (int y=0; y!=this.board.getLen_y(); y++){
				if(this.board.getGrid()[i][y].getPlayed() != null){
					if (this.board.getGrid()[i][y].getPlayed() == this.board.getGrid()[i+1][y].getPlayed()){
						if (this.board.getGrid()[i+1][y].getPlayed() == this.board.getGrid()[i+2][y].getPlayed()){
							if (this.board.getGrid()[i+2][y].getPlayed() == this.board.getGrid()[i+3][y].getPlayed()){
								this.logger.write("The winner is " + getCurrentPlayer().getName());
								return true;
							}
						}
					}
				}
			}
		}
		for (int i=0; i!=this.board.getLen_x()-3; i++){ //Verifie si win 4 diagonal en forme /
			for (int y=3; y!=this.board.getLen_y(); y++){
				if(this.board.getGrid()[i][y].getPlayed() != null){
					if (this.board.getGrid()[i][y].getPlayed() == this.board.getGrid()[i+1][y-1].getPlayed()){
						if (this.board.getGrid()[i+1][y-1].getPlayed() == this.board.getGrid()[i+2][y-2].getPlayed()){
							if (this.board.getGrid()[i+2][y-2].getPlayed() == this.board.getGrid()[i+3][y-3].getPlayed()){
								this.logger.write("The winner is " + getCurrentPlayer().getName());
								return true;
							}
						}
					}
				}
			}
		}
		for (int i=0; i!=this.board.getLen_x()-3; i++){ //Verifie si win 4 diagonal en forme \
			for (int y=0; y!=this.board.getLen_y()-3; y++){
				if(this.board.getGrid()[i][y].getPlayed() != null){
					if (this.board.getGrid()[i][y].getPlayed() == this.board.getGrid()[i+1][y+1].getPlayed()){
						if (this.board.getGrid()[i+1][y+1].getPlayed() == this.board.getGrid()[i+2][y+2].getPlayed()){
							if (this.board.getGrid()[i+2][y+2].getPlayed() == this.board.getGrid()[i+3][y+3].getPlayed()){
								this.logger.write("The winner is " + getCurrentPlayer().getName());
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

					/*}
					else if ((this.plate.getGrid()[i][y].getPlayed() == this.plate.getGrid()[i+1][y].getPlayed()) && (this.plate.getGrid()[i+1][y].getPlayed() == this.plate.getGrid()[i+2][y].getPlayed()) && (this.plate.getGrid()[i+2][y].getPlayed() == this.plate.getGrid()[i+3][y].getPlayed())){ // 4 pions à la horizontale = Fini
						this.logger.write("The winner is " + getCurrentPlayer().getName());
						return true;
					}
					else if ((this.plate.getGrid()[i][y].getPlayed() == this.plate.getGrid()[i+1][y+1].getPlayed()) && (this.plate.getGrid()[i][y].getPlayed() == this.plate.getGrid()[i+2][y+2].getPlayed()) && (this.plate.getGrid()[i][y].getPlayed() == this.plate.getGrid()[i+3][y+3].getPlayed())){ // 4 pions en diagonal = Fini
						this.logger.write("The winner is " + getCurrentPlayer().getName());
						return true;
					}
				}
		}
	}
	return false;
}*/


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

