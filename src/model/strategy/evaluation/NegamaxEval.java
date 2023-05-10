package model.strategy.evaluation;

import model.Player;
import model.Board;
import model.Square;

public class NegamaxEval implements Evaluation {
    @Override
    public int evaluate(Square[][] grid, int choice, Player player, boolean isMaximizingPlayer) {
        Board b = new Board(7, 6);
        b.setGrid(grid.clone());
        if (this.isFinish(b, player)){
            return 999;
        } else {
            return 0;
        }
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
