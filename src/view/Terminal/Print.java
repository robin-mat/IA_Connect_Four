package view.Terminal;

import play.GameInterface;

public class Print implements GameInterface {
	public Print(){
	}

	public void launch(){
		System.out.println("[Log] : Lancement sur le terminal");
		System.out.println("Lancement en console");
	}
}