package play;

import model.*;
import model.strategy.*;
import util.Constants;
import controller.TerminalInput;

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
    Scanner scanner = new Scanner(System.in);
    TerminalInput terminalInput = new TerminalInput(scanner);
    joueurs[0] = new Player(Constants.PSEUDO_P1, new HumanGui());
    joueurs[1] = new Player(Constants.PSEUDO_P2, new HumanGui());

    ViewInterface view;
    GameInterface game;

    System.out.println("Run with the CLI : c (Not working yet)");
    System.out.println(" Run with a GUI :  f");
    System.out.println("      Exit :       q\n");
    //String choice = terminalInput.askQuestionString("Select : ");

    game = new SecretConnectFour(joueurs[0], joueurs[1], new Gui(joueurs));
    game.launch();
    /*
    if (!choice.equals("q")){
      if (choice.equals("c")){
        game = new SecretConnectFour(joueurs[0], joueurs[1], new Print());
        game.launch();
      } else if (choice.equals("f")){
        game = new SecretConnectFour(joueurs[0], joueurs[1], new Gui(joueurs));
        game.launch();
      }
    } else {
      System.out.println("[Log] : Exit");
    }
    */
    scanner.close();
  }


}