package view.terminal;

import view.ViewInterface;
import util.Logger;

public class Print implements ViewInterface {
	public Print(){
	}

	public void launch(){
		System.out.println("[Log] : Run project on the CLI");
	}

	public void setLogger(Logger l){
		//
	}
}