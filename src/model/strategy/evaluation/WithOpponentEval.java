package model.strategy.evaluation;

import model.Player;
import model.Board;
import model.Square;

public class WithOpponentEval extends Shareable implements Evaluation {
  @Override
  public int evaluate(Square[][] grid, int choice, Player player, Player opponent) {
    int score = 0;
    Board b = new Board(7, 6);
    b.setGrid(grid.clone());

    //On privilégie les coups au milieu
    if (choice==0 || choice==6){
      score+=10;
    }
    else if (choice==1 || choice==5){
      score+=20;
    }
    else if (choice==2 || choice==4){
      score+=30;
    }
    else if (choice==3){
      score+=50;
    }
    if (choice!=-1){
      if (b.canPlay(choice+1) == false){
        score-=score;
      }
    }

    if (canOpponentWinInOneMove(b, opponent)){
      score -= 300;
    }

    if (canPreventOpponentWinInOneMove(b, opponent)){
      score += 500;
    }

    if (this.isFinish(b, player)){
      score += 999;
    }
    System.out.println("Mon score: " + score + " pour mon choix " + choice);
    return score;
  }

  public boolean canOpponentWinInOneMove(Board board, Player opponent){
    for (int i = 0; i < 7; i++) {
      int countOpponent;
      for (int j = 5; j > -1; j--) {
        Player jVerif = board.getGrid()[i][j].getPlayed();
        if (jVerif == opponent){
          //Vérifier les lignes suivantes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(j+k < board.getGrid()[0].length){
              if(board.getGrid()[i][j+k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les lignes précédentes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(j-k > -1){
              if(board.getGrid()[i][j-k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les colonnes suivantes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i+k < board.getGrid().length){
              if(board.getGrid()[i+k][j].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les colonnes précédentes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i-k > -1){
              if(board.getGrid()[i-k][j].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les diagonales ascendantes suivantes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i+k < board.getGrid().length && j+k < board.getGrid()[0].length){
              if(board.getGrid()[i+k][j+k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les diagonales ascendantes précédentes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i-k > -1 && j-k > -1){
              if(board.getGrid()[i-k][j-k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les diagonales descendantes suivantes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i+k < board.getGrid().length && j-k > -1){
              if(board.getGrid()[i+k][j-k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
          //Vérifier les diagonales descendantes précédentes
          countOpponent = 0;
          for (int k = 0; k < 4; k++){
            if(i-k > -1 && j+k < board.getGrid()[0].length){
              if(board.getGrid()[i-k][j+k].getPlayed() == opponent){
                countOpponent++;
              }
            }
          }
          if(countOpponent >= 3){
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean canPreventOpponentWinInOneMove(Board board, Player opponent){
    for (int i = 0; i < 7; i++) {
      for (int j = 5; j > -1; j--) {
        Player jVerif = board.getGrid()[i][j].getPlayed();
        if (jVerif == opponent){

          // Vérifier si le joueur a 4 pièces sur une ligne
          if (i+3 < 7){
            if (board.getGrid()[i+1][j].getPlayed() == opponent) {
              if (board.getGrid()[i+2][j].getPlayed() == opponent) {
                if (board.getGrid()[i+3][j].getPlayed() == opponent)
                return true;
              }
            }
          }
        }

        // Vérifier si le joueur a 4 pièces sur une colonne
        if (j-3 > -1){
          if (board.getGrid()[i][j-1].getPlayed() == opponent) {
            if (board.getGrid()[i][j-2].getPlayed() == opponent) {
              if (board.getGrid()[i][j-3].getPlayed() == opponent) {
                return true;
              }
            }
          }
        }

        // Vérifier si le joueur a 4 pièces sur une diagonale de gauche à droite
        if (j-3 > -1 && i + 3 < 7){
          if (board.getGrid()[i+1][j-1].getPlayed() == opponent) {
            if (board.getGrid()[i+2][j-2].getPlayed() == opponent) {
              if (board.getGrid()[i+3][j-3].getPlayed() == opponent) {
                return true;
              }
            }
          }
        }

        // Vérifier si le joueur a 4 pièces sur une diagonale de droite à gauche
        if (j-3 > -1 && i - 3 > -1){
          if (board.getGrid()[i-1][j-1].getPlayed() == opponent) {
            if (board.getGrid()[i-2][j-2].getPlayed() == opponent) {
              if (board.getGrid()[i-3][j-3].getPlayed() == opponent) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
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
