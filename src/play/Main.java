package play;

import model.Player;
import util.Constants;

import view.swingGUI.*;
import view.terminal.*;
import view.ViewInterface;

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

    ViewInterface view;
    GameInterface game;

    System.out.println("Run with the CLI : c");
    System.out.println(" Run with a GUI :  f");
    System.out.println("      Exit :       q\n\nSelect : ");
    Scanner scanner = new Scanner(System.in);
    String choice = scanner.nextLine();

    if (!choice.equals("q")){
      if (choice.equals("c")){
        game = new Game(joueurs[0], joueurs[1], new Print());
        game.launch();
      } else if (choice.equals("f")){
        game = new Game(joueurs[0], joueurs[1], new Gui());
        game.launch();
      }
    } else {
      System.out.println("[Log] : Exit");
    }
    scanner.close();
  }


}