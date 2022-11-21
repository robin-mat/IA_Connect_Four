package play;

import model.Player;
import util.Constants;
import view.Gui;


public class Main {
  public static void main (String[] args){
    System.out.println("------------------------------");
    System.out.println(" IA pour «Puissance 4 secret» ");
    System.out.println("------------------------------\n");

    Player[] joueurs;
    joueurs = new Player[2];
    joueurs[0] = new Player(Constants.PSEUDO_J1);
    joueurs[1] = new Player(Constants.PSEUDO_J2);

    System.out.println(joueurs[0].getName());
    System.out.println(joueurs[0].getUiid());

    System.out.println(joueurs[1].getName());
    System.out.println(joueurs[1].getUiid());
    Gui fen = new Gui();
    try {
      fen.drawGrid(fen.grid);
    } catch (Exception e){
      //System.out.println(e);
    }
  }
}