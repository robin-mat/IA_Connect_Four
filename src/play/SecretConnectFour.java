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
		this.view.launch();
		this.plate.init();
		int i = 0;
		while (i<200){
			this.logger.write("Au tour de "+this.getCurrentPlayer().getName()+", uuid:"+this.getCurrentPlayer().getUuid());
			this.logger.write(Integer.toString(i));
			//Attendre une action de la part du joueur
			//Actualiser la vue
			i = i+1;
		}
		this.logger.write("La partie est terminée");

	}

	public boolean isFinish(){
		//TODO
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