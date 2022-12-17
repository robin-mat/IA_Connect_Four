package view;

import util.Logger;
import model.Board;
import model.BoardProxy;
import play.GameInterface;

public interface ViewInterface{
	public void launch();
	public void setLogger(Logger l);
	public void update();
	public void setBoard(BoardProxy board);
	public void setGameInterface(GameInterface gameInterface);
	public void refreshInfos();
	public void restart();
	public void finish();
}