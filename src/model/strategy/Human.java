package model.strategy;

import controller.TerminalInput;
import model.*;

public class Human implements Strategy{
	public TerminalInput terminalInput;

	public Human(TerminalInput terminalInput){
		this.terminalInput = terminalInput;
	}

	public int choice(Square[][] grid){
		return this.terminalInput.askQuestionInt("Play (from 1 to 7):\nSelect :");
	}
}