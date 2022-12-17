package play;

import model.Player;
import model.Board;

public interface GameInterface{
	public void launch();
	public Player getCurrentPlayer();
	public Player getWinner();
	public void changeCurrentPlayer();
	public int getRounds();
	public Board getBoard();
	public int getP1Score();
	public int getP2Score();
}