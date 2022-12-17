package view.terminal;

import view.ViewInterface;
import util.Logger;

import model.Board;
import model.BoardProxy;
import play.GameInterface;

public class Print implements ViewInterface {
	public Print(){
	}

	public void launch(){
		System.out.println("[Log] : Run project on the CLI");
	}

	public void setLogger(Logger l){
		//
	}

	public void update(){
		//
	}
	public void setBoard(BoardProxy board){
		//
	}

	public void setGameInterface(GameInterface gameInterface){
		//
	}

	public void refreshInfos(){
		//
	}

	public void restart(){
		//
	}
	public void finish(){
		//
	}
}