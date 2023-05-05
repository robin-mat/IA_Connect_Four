package model;

public class Evaluation {

    public static int evaluate(Square[][] grid, Player player) {
      int score = 0;
      // Evaluer les lignes
      for (int row = 0; row < grid.length - 3; row++) {
          for (int col = 0; col < grid[0].length; col++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (grid[row+i][col].getPlayed() == player) {
              countPlayer++;
            } else if (grid[row+i][col].getPlayed() != player && grid[row+i][col].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les colonnes
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[0].length - 3; col++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (grid[row][col+i].getPlayed() == player) {
              countPlayer++;
            }
            else if (grid[row][col+i].getPlayed() != player && grid[row][col+i].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les diagonales ascendantes
      for (int row = 0; row < grid.length - 3; row++) {
        for (int col = 0; col < grid[0].length - 3; col++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (grid[row+i][col+i].getPlayed() == player) {
              countPlayer++;
            }
            else if (grid[row+i][col+i].getPlayed() != player && grid[row+i][col+i].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les diagonales descendantes
      for (int row = 3; row < grid.length; row++) {
        for (int col = 3; col < grid[0].length; col++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (grid[row-i][col-i].getPlayed() == player) {
              countPlayer++;
            }
            else if (grid[row-i][col-i].getPlayed() != player && grid[row-i][col-i].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }
      return score;
    }

private static int computeScore(int countPlayer, int countOpponent) {
  if (countPlayer == 4) {
    // le joueur a gagné
    return 1000;
  } else if (countOpponent == 4) {
    // l'adversaire a gagné
    return -1000;
  } else if (countPlayer == 3 && countOpponent == 0) {
    // 3 pions du joueur alignés, pas d'adversaire dans la ligne
    return 100;
  } else if (countPlayer == 2 && countOpponent == 0) {
      // 2 pions du joueur alignés, pas d'adversaire dans la ligne
      return 10;
  } else if (countPlayer == 1 && countOpponent == 0) {
      // 1 pion du joueur aligné, pas d'adversaire dans la ligne
      return 1;
  } else if (countOpponent == 3 && countPlayer == 0) {
      // 3 pions de l'adversaire alignés, pas de joueur dans la ligne
      return -100;
  } else if (countOpponent == 2 && countPlayer == 0) {
      // 2 pions de l'adversaire alignés, pas de joueur dans la ligne
      return -10;
  } else if (countOpponent == 1 && countPlayer == 0) {
      // 1 pion de l'adversaire aligné, pas de joueur dans la ligne
      return -1;
  } else {
      // aucune condition remplie
      return 0;
    }
  }
}
