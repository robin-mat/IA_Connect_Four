package model.strategy;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.Player;
import model.Square;

import model.strategy.evaluation.*;

public class MonteCarlo extends Shareable implements Strategy {
    private int maxIterations;
    private Player player;
    private Player opponent;

    public int nbrNodesVisited;

    public MonteCarlo(int maxIterations, Player player, Player opponent) {
        this.maxIterations = maxIterations;
        this.player = player;
        this.opponent = opponent;
    }

    public int choice(Square[][] grid){
        if (maxIterations <= 0) {
            throw new RuntimeException("Le nombre maximum d'itérations doit être supérieur à zéro.");
        }

        this.nbrNodesVisited = 0;
        long startTime = System.currentTimeMillis();
        int[] move = this.monteCarloAlgo(grid, this.maxIterations, -1, this.player, this.player, this.opponent);
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("[MonteCarlo] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
        return move[0]+1;
    }

    public int simulationPartie(Square[][] grid, Player currentPlayer, Player player, Player opponent){
        Board exploration = new Board(7, 6);

        this.nbrNodesVisited++;
        exploration.setGrid(cloneGrid(grid.clone()));

        ArrayList<Integer> coupsDispos = this.getMoves(exploration.getGrid());
        if (coupsDispos.size() == 0){
            return 0;
        }
        Random rand = new Random();

        if (currentPlayer.equals(player)){
            exploration.addPawn(coupsDispos.get(rand.nextInt(coupsDispos.size()))+1 ,player);
            if (isFinish(exploration, currentPlayer)){
                return 1;
            }
            return simulationPartie(exploration.getGrid(), opponent, player, opponent);
        } else {
            exploration.addPawn(coupsDispos.get(rand.nextInt(coupsDispos.size()))+1 ,opponent);
            if (isFinish(exploration, currentPlayer)){
                return 2;
            }
            return simulationPartie(exploration.getGrid(), player, player, opponent);
        }
    }

    public int[] monteCarloAlgo(Square[][] originalGrid, int maxIterations, int move, Player currentPlayer, Player player, Player opponent){
        int[] renvoi = new int[2];

        int bestMove = -1;
        int bestScore = -999;

        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);
        for (int i = 0; i < coupsDispos.size(); i++) {
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(cloneGrid(originalGrid.clone()));
            exploration.addPawn(coup+1, currentPlayer);

            int nb_win_player = 0;
            int nb_win_opponent = 0;
            int nb_null_games = 0;

            //System.out.println(this.maxIterations+" iterations pour le coup en "+coup);
            for (int iteration = 0; iteration < this.maxIterations; iteration++) {
                int resultat = simulationPartie(exploration.getGrid(), opponent, player, opponent);
                if (resultat == 1){
                    nb_win_player += 1;
                } else if (resultat == 2){
                    nb_win_opponent += 1;
                } else {
                    nb_null_games += 1;
                }
            }
            if (nb_win_player > bestScore){
                bestScore = nb_win_player;
                bestMove = coup;
            }
        }
        renvoi[0] = bestMove;
        renvoi[1] = bestScore;
        return renvoi;
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
