package model.strategy;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.Player;
import model.Square;

import model.strategy.evaluation.*;

public class Minimax extends Shareable implements Strategy {
  private int maxDepth;
  private Player player;
  private Player opponent;
  private boolean isMaximizing;
  private int alpha;
  private int beta;

  public int nbrNodesVisited;

  public Minimax(int maxDepth, Player player, Player opponent) {
    this.maxDepth = maxDepth;
    this.player = player;
    this.opponent = opponent;
    this.isMaximizing = false;
    this.alpha = -99999;
    this.beta = 99999;
  }

  public int choice(Square[][] grid){
    if (this.maxDepth==0){
      throw new RuntimeException("aucune branche ne peut etre visité");
    }
    this.nbrNodesVisited = 0;

    long startTime = System.currentTimeMillis();
    int[] move = this.minimaxAlgo(grid, this.maxDepth, -1, this.player, this.opponent, this.isMaximizing, this.alpha, this.beta);
    long endTime = System.currentTimeMillis();

    long timeElapsed = endTime - startTime;
    System.out.println("[Minimax] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
    return move[0]+1;
  }


  public int[] minimaxAlgo(Square[][] originalGrid, int depth, int move, Player player, Player opponent, boolean isMaximizing, int alpha, int beta){
    int[] renvoi = new int[2];
    this.nbrNodesVisited++;

    NegamaxEval evaluation = new NegamaxEval();
    if (depth==0 || evaluation.evaluate(originalGrid, -1, this.player, this.opponent, false)>=999){
      if (move!=-1){
        renvoi[0] = move;
        renvoi[1] = evaluation.evaluate(originalGrid, move, this.player, this.opponent, false);
        System.out.println("DEBUG : profondeur="+depth+", cout="+move+" , heuristique="+renvoi[1]);
        return renvoi;
      }
    }

    int bestValue;
    int bestMove = -1;
    if (isMaximizing) {
      bestValue = -99999;
    }
    else {
      bestValue = 99999;
    }

    ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);

    for (int i = 0; i < coupsDispos.size(); i++) {
      int coup = coupsDispos.get(i);
      Board exploration = new Board(7, 6);
      exploration.setGrid(cloneGrid(originalGrid.clone()));
      exploration.addPawn(coup+1, this.player);

      System.out.println("PROFONDEUR = " + depth);
      int[] get = this.minimaxAlgo(exploration.getGrid(), depth-1, coup, this.player, this.opponent, !isMaximizing, this.alpha, this.beta);
      //get[1] = depth*get[1];
      if (isMaximizing) {
        System.out.println("ON MAXIMISE");
        if (get[1] > bestValue){
          bestValue = get[1];
          bestMove = get[0];
          this.alpha = Math.max(this.alpha, bestValue);
        }

        if (this.beta <= this.alpha){
          renvoi[0] = bestMove;
          renvoi[1] = bestValue;
          this.alpha = -99999;
          this.beta = 99999;
          break;
        }
      }
      else {
        if (get[1] < bestValue){
          bestValue = get[1];
          bestMove = get[0];
          this.beta = Math.min(this.beta, bestValue);
        }

        if (this.beta <= this.alpha){
          renvoi[0] = bestMove;
          renvoi[1] = bestValue;
          this.alpha = -99999;
          this.beta = 99999;
          break;
        }
      }
      renvoi[0] = bestMove;
      renvoi[1] = bestValue;

      if (move!=-1){
        int score = evaluation.evaluate(originalGrid, move, this.player, opponent, false);
        if (isMaximizing) {
          if (score>=bestValue){
            renvoi[0] = move;
            renvoi[1] = score;
          }
        }
        else {
          if (score<=bestValue){
            renvoi[0] = move;
            renvoi[1] = score;
          }
        }
      }
    }
    return renvoi;
  }
}
