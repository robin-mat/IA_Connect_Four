package model.strategy;

import controller.TerminalInput;

public class Human implements Strategy{
	public TerminalInput terminalInput;

	public Human(TerminalInput terminalInput){
		this.terminalInput = terminalInput;
	}

	public int choice(){
		return this.terminalInput.askQuestionInt("Play (from 1 to 7):\nSelect :");
	}
}