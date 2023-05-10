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
        
        long startTime = System.currentTimeMillis();
        int move = this.getBestMove(grid, this.maxDepth);
        long endTime = System.currentTimeMillis();
        
        long timeElapsed = endTime - startTime;
        System.out.println("[Negamax] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
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

    public void printtab(Square[][] grid){
        System.out.println(grid[0][0].getPlayed()+" "+grid[1][0].getPlayed()+" "+grid[2][0].getPlayed()+" "+grid[3][0].getPlayed()+" "+grid[4][0].getPlayed()+" "+grid[5][0].getPlayed()+" "+grid[6][0].getPlayed());
        System.out.println(grid[0][1].getPlayed()+" "+grid[1][1].getPlayed()+" "+grid[2][1].getPlayed()+" "+grid[3][1].getPlayed()+" "+grid[4][1].getPlayed()+" "+grid[5][1].getPlayed()+" "+grid[6][1].getPlayed());
        System.out.println(grid[0][2].getPlayed()+" "+grid[1][2].getPlayed()+" "+grid[2][2].getPlayed()+" "+grid[3][2].getPlayed()+" "+grid[4][2].getPlayed()+" "+grid[5][2].getPlayed()+" "+grid[6][2].getPlayed());
        System.out.println(grid[0][3].getPlayed()+" "+grid[1][3].getPlayed()+" "+grid[2][3].getPlayed()+" "+grid[3][3].getPlayed()+" "+grid[4][3].getPlayed()+" "+grid[5][3].getPlayed()+" "+grid[6][3].getPlayed());
        System.out.println(grid[0][4].getPlayed()+" "+grid[1][4].getPlayed()+" "+grid[2][4].getPlayed()+" "+grid[3][4].getPlayed()+" "+grid[4][4].getPlayed()+" "+grid[5][4].getPlayed()+" "+grid[6][4].getPlayed());
        System.out.println(grid[0][5].getPlayed()+" "+grid[1][5].getPlayed()+" "+grid[2][5].getPlayed()+" "+grid[3][5].getPlayed()+" "+grid[4][5].getPlayed()+" "+grid[5][5].getPlayed()+" "+grid[6][5].getPlayed());
    }

    public int getBestMove(Square[][] originalGrid, int depth){
        double bestValue = -999;

        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);
        Random rand = new Random();
        int bestMove = rand.nextInt(coupsDispos.size());
        
        //System.out.println("coups dispos :"+coupsDispos);
        for (int i = 0; i < coupsDispos.size(); i++) {
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(cloneGrid(originalGrid.clone()));
            exploration.addPawn(coup+1, this.player);
            //printtab(exploration.getGrid());

            //System.out.println(this.howManyPawnPerColum(exploration, 0, this.player));
            double valeur = this.negamaxAlgo(exploration, depth-1, this.player, this.player, this.opponent);
            if (valeur > bestValue){
                bestValue = valeur;
                bestMove = coup;
            }
        }
        return bestMove+1;
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
        //System.out.println(nbrNodesVisited+" : "+coupsDispos);

        //System.out.println("coups dispos pour la visite du noeud "+nbrNodesVisited+" :"+coupsDispos);
        for (int i=0; i<coupsDispos.size(); i++){
            int coup = coupsDispos.get(i);
            Board exploration = new Board(7, 6);
            exploration.setGrid(cloneGrid(board.getGrid().clone()));
            exploration.addPawn(coup+1, currentPlayer);
            
            m = Math.max(m, (this.negamaxAlgo(exploration, d-1, opponent, opponent, player)));
        }

        return m;
      }


      public int howManyPawnPerColum(Board board, int column, Player player){
        Square[][] grid = board.getGrid();
        int temp = 0;
        for (int i = 0; i < 6; i++) {
            if (grid[column][i].getPlayed() == player){
                temp += 1;
            }
        }
        return temp;
        
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