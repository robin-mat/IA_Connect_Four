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

    public int nbrNodesVisited;

    public Minimax(int maxDepth, Player player, Player opponent) {
        this.maxDepth = maxDepth;
        this.player = player;
        this.opponent = opponent;
    }

	public int choice(Square[][] grid){
        if (this.maxDepth==0){
            throw new RuntimeException("aucune branche ne peut etre visité");
        }
        this.nbrNodesVisited = 0;

        long startTime = System.currentTimeMillis();
        int[] move = this.minimaxAlgo(grid, this.maxDepth, -1, this.player, this.player, this.opponent);
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;
        System.out.println("[Minimax] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
        return move[0]+1;
	}

    public int[] minimaxAlgo(Square[][] originalGrid, int depth, int move, Player currentPlayer, Player player, Player opponent){
        int[] renvoi = new int[2];
        this.nbrNodesVisited++;

        NegamaxEval evaluation = new NegamaxEval();
        if (depth==0 || evaluation.evaluate(originalGrid, -1, this.player, opponent, false)==999){
            if (move!=-1){
                renvoi[0] = move;
                renvoi[1] = evaluation.evaluate(originalGrid, -1, this.player, opponent, false);
                return renvoi;
            }
        }

        int bestValue;
        if (currentPlayer == player) {
            bestValue = -99999;
        } else {
            bestValue = 99999;
        }
        int bestMove = -1;

        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);

        for (int i = 0; i < coupsDispos.size(); i++) {
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(cloneGrid(originalGrid.clone()));
            exploration.addPawn(coup+1, currentPlayer);

            int[] get = this.minimaxAlgo(exploration.getGrid(), depth-1, coup, opponent, player, opponent);
            get[1] = depth+get[1];

            if (currentPlayer == player) {
                if (get[1] > bestValue){
                    bestValue = get[1];
                    bestMove = get[0];
                }
            } else {
                if (get[1] < bestValue){
                    bestValue = get[1];
                    bestMove = get[0];
                }
            }
        }

        renvoi[0] = bestMove;
        renvoi[1] = bestValue;

        if (move!=-1){
            int score = evaluation.evaluate(originalGrid, -1, this.player, opponent, false);
            if (currentPlayer == player) {
                if (score>=bestValue){
                    renvoi[0] = move;
                    renvoi[1] = score;
                }
            } else {
                if (score<=bestValue){
                    renvoi[0] = move;
                    renvoi[1] = score;
                }
            }
        }

        return renvoi;
    }
  }
