package model.strategy;
import model.Player;
import model.Square;
import model.Board;
import model.strategy.evaluation.*;

import java.util.ArrayList;
import java.util.Random;

public class MonteCarlo implements Strategy{
  private int maxIterations;
  private Random random;
  private Player player;
  private Player opponent;

  // Constructeur
  public MonteCarlo(int maxIterations, Player player, Player opponent){
    this.maxIterations = maxIterations;
    this.random = new Random();
    this.player = player;
    this.opponent = opponent;
  }

  //Méthode pour choisir le meilleur coup
  public int choice(Square[][] grid){
    int bestChoice = -1; //Initialisation à un choix innexistant
    int bestScore = Integer.MIN_VALUE; //Initialisation à une valeur minimale
    for(int choice = 1; choice <= 7; choice++){
      System.out.println("Considering choice " + choice + "...");
      if(isValidChoice(choice, grid) == true){ //Si ma colonne n'est pas pleine
        int score = monteCarlo(grid, choice, maxIterations);
        if(score > bestScore){
          bestChoice = choice;
          bestScore = score;
        }
      }
    }
    System.out.println("Choosing column " + bestChoice + ".");
    return bestChoice;
  }

  //Vérifie si le choix est valide
  private boolean isValidChoice(int choice, Square[][] grid) {
    if(grid[choice-1][0].getPlayed() instanceof Player){
      return false;
    }
    else{
      return true;
    }
  }

  //Méthode pour déterminer le coup
  public int monteCarlo(Square[][] grid, int choice, int maxIterations){
    int bestScore = Integer.MIN_VALUE;
    if(isValidChoice(choice, grid) == true){
      for(int i = 0; i < maxIterations; i++){
        Board exploration = new Board(7, 6);
        exploration.setGrid(grid);
        exploration.addPawn(choice, player);
        grid = exploration.getGrid(); //joue
        int score = simulateGame(grid, choice); // évaluer le coup
        bestScore = Math.max(bestScore, score); // choisir le meilleur score
      }
    }
    return bestScore;
  }

  public int simulateGame(Square[][] grid, int choice) {
      Player currentPlayer = opponent;
      while (!isTerminalState(grid)) {
          // Détermine le joueur suivant
          currentPlayer = (currentPlayer == player) ? opponent : player;

          // Trouve un coup valide au hasard pour le joueur actuel
          int currentChoice;
          do {
              currentChoice = this.random.nextInt(7) + 1;
          } while (!isValidChoice(currentChoice, grid));

          // Ajoute le pion sur la grille
          Board exploration = new Board(7, 6);
          exploration.setGrid(grid);
          exploration.addPawn(currentChoice, currentPlayer);
          grid = exploration.getGrid(); //joue
      }
      // Évalue le résultat du jeu pour le joueur actuel
      MonteCarloEval evaluation = new MonteCarloEval();
      return evaluation.evaluate(grid, choice, this.player, currentPlayer == opponent); // évaluation de la grille pour le joueur actuel en prenant en compte si on doit Maximiser ou Minimiser
  }


    // Vérifie si la grille est dans un état terminal (gagné ou grille remplie)
    private boolean isTerminalState(Square[][] grid) {
      // Vérification des lignes
      for(int row = 0; row < grid.length; row++) {
        for(int col = 0; col < grid[0].length - 3; col++) {
          Player p = grid[row][col].getPlayed();
          if(p != null && p == grid[row][col+1].getPlayed() && p == grid[row][col+2].getPlayed() && p == grid[row][col+3].getPlayed()) {
            return true;
          }
        }
      }
      // Vérification des colonnes
      for(int row = 0; row < grid.length - 3; row++) {
        for(int col = 0; col < grid[0].length; col++) {
          Player p = grid[row][col].getPlayed();
          if(p != null && p == grid[row+1][col].getPlayed() && p == grid[row+2][col].getPlayed() && p == grid[row+3][col].getPlayed()) {
            return true;
          }
        }
      }
      // Vérification de la diagonale ascendante
      for(int row = 3; row < grid.length; row++) {
        for(int col = 0; col < grid[0].length - 3; col++) {
          Player p = grid[row][col].getPlayed();
          if(p != null && p == grid[row-1][col+1].getPlayed() && p == grid[row-2][col+2].getPlayed() && p == grid[row-3][col+3].getPlayed()) {
            return true;
          }
        }
      }
      // Vérification de la diagonale descendante
      for(int row = 0; row < grid.length - 3; row++) {
        for(int col = 0; col < grid[0].length - 3; col++) {
          Player p = grid[row][col].getPlayed();
          if(p != null && p == grid[row+1][col+1].getPlayed() && p == grid[row+2][col+2].getPlayed() && p == grid[row+3][col+3].getPlayed()) {
            return true;
          }
        }
      }
      // Vérification de la grille remplie
      for(int col = 0; col < grid[0].length; col++) {
        if(grid[0][col].getPlayed() == null) {
          return false;
        }
      }
      return true;
    }
  }
