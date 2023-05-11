package model.strategy;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.Player;
import model.Square;

import model.strategy.evaluation.*;

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
        
        long startTime = System.currentTimeMillis();
        int[] move = this.negamaxAlgo(grid, this.maxDepth, -1, this.player, this.player, this.opponent);
        long endTime = System.currentTimeMillis();
        
        long timeElapsed = endTime - startTime;
        System.out.println("[Negamax] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
        return move[0]+1;
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

    public int[] negamaxAlgo(Square[][] originalGrid, int depth, int move, Player currentPlayer, Player player, Player opponent){
        int[] renvoi = new int[2];
        this.nbrNodesVisited++;

        NegamaxEval evaluation = new NegamaxEval();
        if (depth==0 || evaluation.evaluate(originalGrid, -1, this.player, false)==999){
            if (move!=-1){
                renvoi[0] = move;
                renvoi[1] = evaluation.evaluate(originalGrid, -1, this.player, false);
                //System.out.println("DEBUG : profondeur="+depth+", cout="+move+" , heuristique="+renvoi[1]);
                return renvoi;
            }
        }
        
        int bestValue = -99999;
        int bestMove = -1;
        
        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);

        for (int i = 0; i < coupsDispos.size(); i++) {
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(cloneGrid(originalGrid.clone()));
            exploration.addPawn(coup+1, this.player);

            int[] get = this.negamaxAlgo(exploration.getGrid(), depth-1, coup, this.player, this.player, this.opponent);
            get[1] = depth+get[1];
            if (get[1] > bestValue){
                bestValue = get[1];
                bestMove = get[0];
            }
        }
        renvoi[0] = bestMove;
        renvoi[1] = bestValue;

        if (move!=-1){
            int score = evaluation.evaluate(originalGrid, -1, this.player, false);
            if (score>=bestValue){
                renvoi[0] = move;
                renvoi[1] = score;
            }
        }

        return renvoi;
    }

    public Square[][] cloneGrid(Square[][] grid) {
        Square[][] newGrid = new Square[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Square square = grid[i][j];
                Player played = square.getPlayed();
                newGrid[i][j] = new Square(i, j);
                newGrid[i][j].setPlayed(played);
            }
        }
        return newGrid;
    }
}