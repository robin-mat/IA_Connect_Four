package play;

import model.Player;

public interface GameInterface{
	public void launch();
	public Player getCurrentPlayer();
	public void changeCurrentPlayer();
}