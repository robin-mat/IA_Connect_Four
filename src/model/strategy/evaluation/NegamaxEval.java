package model.strategy.evaluation;

import model.Player;
import model.Board;
import model.Square;

public class NegamaxEval extends Shareable implements Evaluation {
    @Override
    public int evaluate(Square[][] grid, int choice, Player player, boolean isMaximizingPlayer) {
        int score = 0;

        //On privilégie les coups au milieu
        if (choice==0 || choice==6){
            score+=10;
        } else if (choice==1 || choice==5){
            score+=20;
        } else if (choice==2 || choice==4){
            score+=30;
        } else if (choice==3){
            score+=50;
        }

        Board b = new Board(7, 6);
        b.setGrid(grid.clone());
        if (this.isFinish(b, player)){
            score += 999;
        }
        return score;
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
