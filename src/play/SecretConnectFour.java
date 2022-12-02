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


	private Plate plate;

	private ViewInterface view;

	public SecretConnectFour(Player p1, Player p2, ViewInterface v){
		this.player1 = p1;
		this.player2 = p2;
		this.currentPlayer = this.player1;
		this.view = v;
		this.plate = new Plate(Constants.GRID_SIZE[0], Constants.GRID_SIZE[1]);
		this.logger = new Logger();
	}

	public void launch(){
		this.view.setLogger(this.logger);
		this.view.setPlate(this.plate);
		this.view.launch();
		this.plate.init();
		this.view.update();
		while (!this.isFinish() && this.plate.canPlay()){
			this.logger.write("Waiting "+this.getCurrentPlayer().getName()+", uuid:"+this.getCurrentPlayer().getUuid());
			this.view.setPlate(this.plate);
			int choice = this.currentPlayer.play();
			this.logger.write("Choose the column "+choice);
			while (!this.plate.canPlay(choice)){
				this.logger.write("Unable to perform, select a valid move !");
				choice = this.currentPlayer.play();
				this.logger.write("Choose the column "+choice);
			}

			this.plate.addPawn(choice, this.currentPlayer);
			
			this.changeCurrentPlayer();
			this.view.update();
		}
		this.logger.write("The game is over");

	}

	public boolean isFinish(){
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