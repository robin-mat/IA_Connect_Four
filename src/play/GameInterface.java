package play;

import model.Player;
import model.BoardProxy;

public interface GameInterface{
	public void launch();
	public Player getCurrentPlayer();
	public Player getWinner();
	public void changeCurrentPlayer();
	public int getRounds();
	public BoardProxy getboardProxy();
	public int getP1Score();
	public int getP2Score();
	public Player getP1();
	public Player getP2();
	public Player getWaitingPlayer();
}