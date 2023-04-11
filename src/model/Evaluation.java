package model;

public class Evaluation {

    public static int evaluate(BoardProxy board, Player player) {
      int score = 0;

      // Evaluer les lignes
      for (int y = 0; y < board.getLen_y(); y++) {
        for (int x = 0; x < board.getLen_x() - 3; x++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (board.getGrid()[x+i][y].getPlayed() == player) {
              countPlayer++;
            } else if (board.getGrid()[x+i][y].getPlayed() != player && board.getGrid()[x+i][y].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les colonnes
      for (int x = 0; x < board.getLen_x(); x++) {
        for (int y = 0; y < board.getLen_y() - 3; y++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (board.getGrid()[x][y+i].getPlayed() == player) {
              countPlayer++;
            }
            else if (board.getGrid()[x+i][y].getPlayed() != player && board.getGrid()[x+i][y].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les diagonales ascendantes
      for (int x = 0; x < board.getLen_x() - 3; x++) {
        for (int y = 0; y < board.getLen_y() - 3; y++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (board.getGrid()[x+i][y+i].getPlayed() == player) {
              countPlayer++;
            }
            else if (board.getGrid()[x+i][y].getPlayed() != player && board.getGrid()[x+i][y].getPlayed() != null) {
              countOpponent++;
            }
          }
          score += computeScore(countPlayer, countOpponent);
        }
      }

      // Evaluer les diagonales descendantes
      for (int x = 0; x < board.getLen_x() - 3; x++) {
        for (int y = 3; y < board.getLen_y(); y++) {
          int countPlayer = 0;
          int countOpponent = 0;
          for (int i = 0; i < 4; i++) {
            if (board.getGrid()[x+i][y-i].getPlayed() == player) {
              countPlayer++;
            }
            else if (board.getGrid()[x+i][y].getPlayed() != player && board.getGrid()[x+i][y].getPlayed() != null) {
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
