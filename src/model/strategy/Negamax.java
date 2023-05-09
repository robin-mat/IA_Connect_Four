package model.strategy;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.Player;
import model.Square;

public class Negamax implements Strategy {
    private int maxDepth;
    private Player player;
    private Player opponent;

    public int nbrNodesVisited;

    public Negamax(int maxDepth, Player player, Player opponent) {
        this.maxDepth = maxDepth;
        this.player = player;
        this.opponent = opponent;
    }

	public int choice(Square[][] grid){
        if (this.maxDepth==0){
            throw new RuntimeException("aucune branche ne peut etre visité"); 
        }
        this.nbrNodesVisited = 0;
        int move = this.getBestMove(grid, this.maxDepth);
        System.out.println("[Negamax] "+ this.nbrNodesVisited + " situations visitées.");
        return move;
	}

    public ArrayList<Integer> getMoves(Square[][] grid){
        ArrayList<Integer> liste = new ArrayList<Integer>();
        if (!(grid[0][0].getPlayed() instanceof Player)){
            liste.add(0);
        }
        if (!(grid[1][0].getPlayed() instanceof Player)){
            liste.add(1);
        }
        if (!(grid[2][0].getPlayed() instanceof Player)){
            liste.add(2);
        }
        if (!(grid[3][0].getPlayed() instanceof Player)){
            liste.add(3);
        }
        if (!(grid[4][0].getPlayed() instanceof Player)){
            liste.add(4);
        }
        if (!(grid[5][0].getPlayed() instanceof Player)){
            liste.add(5);
        }
        if (!(grid[6][0].getPlayed() instanceof Player)){
            liste.add(6);
        }
        return liste;
    }


    public int getBestMove(Square[][] originalGrid, int depth){
        double bestValue = -999;

        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);
        Random rand = new Random();
        int bestMove = rand.nextInt(coupsDispos.size());

        for (int i = 0; i < coupsDispos.size(); i++) {
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(originalGrid.clone());
            exploration.addPawn(coup+1, this.player);

            double valeur = this.negamaxAlgo(exploration, depth-1, this.player, this.player, this.opponent);
            if (valeur > bestValue){
                bestValue = valeur;
                bestMove = coup;
            }
        }
        return bestMove+1;
    }

    public double negamaxAlgo(Board board, int d, Player currentPlayer, Player player, Player opponent){
        this.nbrNodesVisited++;
        if (d==0){
            if (currentPlayer.equals(player)){
                return this.evaluation(board);
            } else {
                return -this.evaluation(board);
            }
        }

        double m = this.evaluation(board);
        if (currentPlayer.equals(player)){
            m = this.evaluation(board);
        } else {
            m = -this.evaluation(board);
        }

        ArrayList<Integer> coupsDispos = this.getMoves(board.getGrid());
        System.out.println(nbrNodesVisited+" : "+coupsDispos);
        for (int i=0; i<coupsDispos.size(); i++){
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(board.getGrid().clone());
            exploration.addPawn(coup+1, currentPlayer);
            
            m = Math.max(m, (this.negamaxAlgo(exploration, d-1, opponent, opponent, player)));
        }

        return m;
        /*
        if (d==0 || this.isFinish(board)==true){
          if (this.joueur_ia.equals(s.getCurrentPlayer())){
            return this.evaluation(s, this.joueur_ia);
          }
          return -(this.evaluation(s, this.joueur_ia));
        }
        double m = -999;
        ArrayList<Move> coupsDispos = s.getMove(s.getCurrentPlayer());
        for (int i=0; i<coupsDispos.size(); i++){
          m = Math.max(m, -(this.negamaxAlgo(s.play(coupsDispos.get(i), false), d-1)));
        }
        return m;
         */
      }


      public int evaluation(Board board){
        if (this.isFinish(board, this.player)){
            return 999;
        } else {
            return 0;
        }
      }

      public boolean isFinish(Board board, Player player){
        for (int i = 0; i < 7; i++) {
            for (int j = 5; j > -1; j--) {
                Player jVerif = board.getGrid()[i][j].getPlayed();
  
                if (jVerif == player){
                    // Vérifier si le joueur a 4 pièces sur une ligne
                    if (i+3 < 7){
                        if (board.getGrid()[i+1][j].getPlayed() == player) {
                          if (board.getGrid()[i+2][j].getPlayed() == player) {
                              if (board.getGrid()[i+3][j].getPlayed() == player)
                                  return true;
                              }
                          }
                      }
                    }
                
                // Vérifier si le joueur a 4 pièces sur une colonne
		      	if (j-3 > -1){
                    if (board.getGrid()[i][j-1].getPlayed() == player) {
                      if (board.getGrid()[i][j-2].getPlayed() == player) {
                          if (board.getGrid()[i][j-3].getPlayed() == player) {
                              return true;
                          }
                      }
                  }
                }


              // Vérifier si le joueur a 4 pièces sur une diagonale de gauche à droite
              if (j-3 > -1 && i + 3 < 7){
                    if (board.getGrid()[i+1][j-1].getPlayed() == player) {
                      if (board.getGrid()[i+2][j-2].getPlayed() == player) {
                          if (board.getGrid()[i+3][j-3].getPlayed() == player) {
                              return true;
                          }
                      }
                  }
                }




              // Vérifier si le joueur a 4 pièces sur une diagonale de droite à gauche
              if (j-3 > -1 && i - 3 > -1){
                if (board.getGrid()[i-1][j-1].getPlayed() == player) {
                  if (board.getGrid()[i-2][j-2].getPlayed() == player) {
                      if (board.getGrid()[i-3][j-3].getPlayed() == player) {
                          return true;
                      }
                  }
              }
            }
            }
        }
        return false;
      }
    }