package play;

import model.Player;
import util.Constants;

import view.swingGUI.*;
import view.terminal.*;

import java.util.Scanner;


public class Main {
  public static void main (String[] args){
    System.out.println("------------------------------");
    System.out.println(" IA pour «Puissance 4 secret» ");
    System.out.println("------------------------------\n");



    Player[] joueurs;
    joueurs = new Player[2];
    joueurs[0] = new Player(Constants.PSEUDO_J1);
    joueurs[1] = new Player(Constants.PSEUDO_J2);

    GameInterface partie;

    System.out.println("Jouer en console, taper : c");
    System.out.println("Jouer en fênetre, taper : f");
    System.out.println("  Pour quitter, taper : q\n\nEntrer votre choix : ");
    Scanner scanner = new Scanner(System.in);
    String choice = scanner.nextLine();

    if (!choice.equals("q")){
      if (choice.equals("c")){
        partie = new Print();
        partie.launch();
      } else if (choice.equals("f")){
        partie = new Gui();
        partie.launch();
      }
    } else {
      System.out.println("[Log] : Fermeture du jeu");
    }
    scanner.close();
  }


}