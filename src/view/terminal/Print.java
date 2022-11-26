package view.terminal;

import view.ViewInterface;

public class Print implements ViewInterface {
	public Print(){
	}

	public void launch(){
		System.out.println("[Log] : Run project on the CLI");
	}
}