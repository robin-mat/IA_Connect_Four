package model.strategy.evaluation;

import model.Player;
import model.Square;

public class MonteCarloEval implements Evaluation {

    @Override
    public int evaluate(Square[][] grid, int choice, Player player, boolean isEvaluatedPlayer) {
        int bestscore = 0;
        int col = choice-1;

        // Vérifier les lignes
        int countPlayer = 0;
        int score = 0;
        int countOpponent = 0;
        boolean opponentFoundSuiv = false;
        boolean opponentFoundPrec = false;
        for(int row = 0; row < grid[0].length; row++) {
          score = 0;
          // Vérifier les lignes suivantes
          for(int i = 0; i < 4; i++) {
            if(row+i < grid[0].length){
              if(grid[col][row+i].getPlayed() == player){
                countPlayer++;
              }
              else if(grid[col][row+i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          // Vérifier les lignes précédentes
          for(int i = 0; i < 4; i++) {
            if(row-i >= 0){
              if(grid[col][row-i].getPlayed() == player) {
                countPlayer++;
              }
              else if(grid[col][row-i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          score = computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
          bestscore = Math.max(bestscore, score);
        }


        // Vérifier les colonnes
        countPlayer = 0;
        countOpponent = 0;
        opponentFoundSuiv = false;
        opponentFoundPrec = false;
        for(int row = 0; row < grid[0].length; row++) {
          score = 0;
          // Vérifier les colonnes suivantes
          for(int i = 0; i < 4; i++) {
            if(col+i < grid.length){
              if(grid[col][row].getPlayed() == null){
                if(grid[col+i][row].getPlayed() == player) {
                  countPlayer++;
                }
                else if(grid[col+i][row].getPlayed() == null){
                  continue;
                }
                else{
                  countOpponent++;
                }
              }
            }
          }
          // Vérifier les colonnes précédentes
          for(int i = 0; i < 4; i++) {
            if(col-i >= 0){
              if(grid[col][row].getPlayed() == null){
                if(grid[col-i][row].getPlayed() == player) {
                  countPlayer++;
                }
                else if(grid[col-i][row].getPlayed() == null){
                  continue;
                }
                else{
                  countOpponent++;
                }
              }
            }
          }
          score = computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
          bestscore = Math.max(bestscore, score);
        }

        // Vérifier les diagonales ascendantes
        countPlayer = 0;
        countOpponent = 0;
        opponentFoundSuiv = false;
        opponentFoundPrec = false;
        for(int row = 0; row < grid[0].length; row++) {
          score = 0;
          // Vérifier les diagonales ascendantes suivantes
          for(int i = 0; i < 4; i++) {
            if(col+i < grid.length && row+i < grid[0].length){
              if(grid[col+i][row+i].getPlayed() == player) {
                countPlayer++;
              }
              else if(grid[col+i][row+i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          // Vérifier les diagonales ascendantes précédentes
          for(int i = 0; i < 4; i++) {
            if(col-i >= 0 && row-i >= 0){
              if(grid[col-i][row-i].getPlayed() == player) {
                countPlayer++;
              }
              else if(grid[col-i][row-i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          score = computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
          bestscore = Math.max(bestscore, score);
        }

        // Vérifier les diagonales descendantes
        countPlayer = 0;
        countOpponent = 0;
        opponentFoundSuiv = false;
        opponentFoundPrec = false;
        for(int row = 0; row < grid[0].length; row++) {
          score = 0;
          // Vérifier les diagonales descendantes suivantes
          for(int i = 0; i < 4; i++) {
            if(col+i < grid.length && row-i >= 0){
              if(grid[col+i][row-i].getPlayed() == player) {
                countPlayer++;
              }
              else if(grid[col+i][row-i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          // Vérifier les diagonales descendantes précédentes
          for(int i = 0; i < 4; i++) {
            score = 0;
            if(col-i >= 0 && row+i < grid[0].length){
              if(grid[col-i][row+i].getPlayed() == player) {
                countPlayer++;
              }
              else if(grid[col-i][row+i].getPlayed() == null){
                continue;
              }
              else{
                countOpponent++;
              }
            }
          }
          score = computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
          bestscore = Math.max(bestscore, score);
        }
        System.out.println("Pour le choix: " + choice + " le score est " + score);
        return bestscore;
      }


      private static int computeScore(int countPlayer, int countOpponent, boolean opponentFoundSuiv, boolean opponentFoundPrec, int choice, boolean isMaximizingPlayer) {
        int score = 0;
        //Initialisation des préférences pour rendre les colonnes du milieu plus intéressant
        if(choice == 2 || choice == 6){
          score += 1;
        }
        else if(choice == 3 || choice == 5){
          score += 3;
        }
        else if(choice == 4){
          score += 5;
        }
        //Si on veut maximiser
          // Score pour le joueur actuel
          if (countPlayer == 4) { // le joueur a gagné
            score += 10000;
          }
          else if (countPlayer == 3) { // 3 pions alignés mais pas bloqué d'un des deux côté au moins
            score += 1000;
          }
          else if (countOpponent == 3) { // 1 pion aligné mais pas bloqué d'un des deux côté au moins
            score += 1000;
          }
          else if (countPlayer == 2) { // 2 pions alignés mais pas bloqué d'un des deux côté au moins
            score += 100;
          }
          else if (countPlayer == 1) { // 1 pion aligné mais pas bloqué d'un des deux côté au moins
            score += 10;
          }
        return score;
    }
}