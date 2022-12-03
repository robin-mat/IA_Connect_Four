package view;

import util.Logger;
import model.Board;

public interface ViewInterface{
	public void launch();
	public void setLogger(Logger l);
	public void update();
	public void setBoard(Board board);
}