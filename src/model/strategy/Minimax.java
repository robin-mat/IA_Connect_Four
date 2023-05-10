package model.strategy;
import model.Player;
import model.Square;
import model.Board;

import model.strategy.evaluation.*;

public class Minimax implements Strategy{
  private int depth;
  private Player player;
  private Player opponent;

  // Constructeur
  public Minimax(int depth, Player player, Player opponent){
    this.depth = 0;
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
        int score = minimax(grid, choice, depth, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
  public int minimax(Square[][] grid, int choice, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
    // Vérifier si on est arrivé à la profondeur maximale ou si le jeu est terminé
    if(depth == 0 || isTerminalState(grid)){
      MinmaxEval evaluation = new MinmaxEval();
      return evaluation.evaluate(grid, choice, this.player, isMaximizingPlayer); // évaluation de la grille pour le joueur actuel en prenant en compte si on doit Maximiser ou Minimiser
    }

    // Maximisation du joueur
    if(isMaximizingPlayer){
      int bestScore = Integer.MIN_VALUE;
      for(choice = 1; choice <= 7; choice++) {
        if(isValidChoice(choice, grid) == true) { // si le coup est valide
            Board exploration = new Board(7, 6);
            exploration.setGrid(grid);
            exploration.addPawn(choice, player);
            grid = exploration.getGrid(); //joue
          int score = minimax(grid, choice, depth-1, false, alpha, beta); // évaluer le coup
          bestScore = Math.max(bestScore, score); // choisir le meilleur score
          alpha = Math.max(alpha, bestScore); // mettre à jour alpha
          if(beta <= alpha) { // élagage
            break;
          }
        }
      }
      return bestScore;
    }

    // Minimisation de l'adversaire
    else{
      int bestScore = Integer.MAX_VALUE;
      for(choice = 1; choice <= 7; choice++){
        if(isValidChoice(choice, grid) == true) { // si le coup est valide
            Board exploration = new Board(7, 6);
            exploration.setGrid(grid);
            exploration.addPawn(choice, player);
            grid = exploration.getGrid(); //joue

          int score = minimax(grid, choice, depth-1, true, alpha, beta); // évaluer le coup
          bestScore = Math.max(bestScore, score); // choisir le meilleur score
          beta = Math.max(beta, bestScore); // mettre à jour alpha
          if (beta <= alpha) { // élagage
            break;
          }
        }
      }
      return bestScore;
    }
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

  //Permet de jouer le coup choisi
  public void makeMove(Square[][] g, int colum, Player p) {
    int y = 5;
    while (y >= 0 && g[colum-1][y].getPlayed() instanceof Player) {
      y = y - 1;
    }
    if (y >= 0 && colum-1 >= 0 && colum-1 < g.length) {
      g[colum-1][y].setPlayed(p);
    }
  }
}
