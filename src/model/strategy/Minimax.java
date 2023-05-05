package model.strategy;

import model.Player;
import model.Square;
import model.Evaluation;

public class Minimax implements Strategy {
    private int maxDepth;
    private Player player;
    private Player opponent;

    public Minimax(int maxDepth, Player player, Player opponent) {
        this.maxDepth = maxDepth;
        this.player = player;
        this.opponent = opponent;
    }

    public int choice(Square[][] grid) {
      int bestChoice = -1;
      int bestScore = Integer.MIN_VALUE;

      for (int choice = 1; choice <= 7; choice++) {
          System.out.println("Considering choice " + choice + "...");
          if (!isValidChoice(choice, grid)) {
              System.out.println("Choice " + choice + " is invalid.");
              continue;
          }

          int score = minimax(grid, choice, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
          if (score > bestScore) {
              bestChoice = choice;
              bestScore = score;
          }
      }

      System.out.println("Choosing column " + bestChoice + ".");
      return bestChoice;
  }


  private boolean isValidChoice(int choice, Square[][] grid) {
    if (grid[choice-1][0].getPlayed() instanceof Player){
			return false;
		} else {
			return true;
		}
}

    public void makeMove(Square[][] g, int colum, Player p) {
      int y = 5;
      while (y >= 0 && g[colum-1][y].getPlayed() instanceof Player) {
          y = y - 1;
      }
      if (y >= 0 && colum-1 >= 0 && colum-1 < g.length) {
          g[colum-1][y].setPlayed(p);
      } else {
          // handle invalid move
      }
  }


    private int minimax(Square[][] grid, int choice, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == maxDepth || isTerminalState(grid)) {
            return Evaluation.evaluate(grid, player);
        }

        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;

            for (int col = 0; col < 7; col++) {
                if (!isValidChoice(col + 1, grid)) {
                    continue;
                }

                Square[][] newGrid = grid.clone();
                makeMove(newGrid, col + 1, player);

                int score = minimax(newGrid, col + 1, depth + 1, alpha, beta, false);
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) {
                    break;
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int col = 0; col < 7; col++) {
                if (!isValidChoice(col + 1, grid)) {
                    continue;
                }

                Square[][] newGrid = grid.clone();
                makeMove(newGrid, col + 1, opponent);

                int score = minimax(newGrid, col + 1, depth + 1, alpha, beta, true);
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) {
                    break;
                }
            }

            return bestScore;
        }
    }

    private boolean isTerminalState(Square[][] grid) {
      for (int row = 0; row < grid.length; row++) {
          for (int col = 0; col < grid[0].length - 3; col++) {
              Player p = grid[row][col].getPlayed();
              if (p != null && p == grid[row][col+1].getPlayed() && p == grid[row][col+2].getPlayed() && p == grid[row][col+3].getPlayed()) {
                  return true;
              }
          }
      }

      for (int row = 0; row < grid.length - 3; row++) {
          for (int col = 0; col < grid[0].length; col++) {
              Player p = grid[row][col].getPlayed();
              if (p != null && p == grid[row+1][col].getPlayed() && p == grid[row+2][col].getPlayed() && p == grid[row+3][col].getPlayed()) {
                  return true;
              }
          }
      }

      for (int row = 0; row < grid.length - 3; row++) {
          for (int col = 0; col < grid[0].length - 3; col++) {
              Player p = grid[row][col].getPlayed();
              if (p != null && p == grid[row+1][col+1].getPlayed() && p == grid[row+2][col+2].getPlayed() && p == grid[row+3][col+3].getPlayed()) {
                  return true;
              }
          }
      }

      for (int row = 0; row < grid.length - 3; row++) {
          for (int col = 3; col < grid[0].length; col++) {
              Player p = grid[row][col].getPlayed();
              if (p != null && p == grid[row+1][col-1].getPlayed() && p == grid[row+2][col-2].getPlayed() && p == grid[row+3][col-3].getPlayed()) {
                  return true;
              }
          }
      }

      for (int col = 0; col < grid[0].length; col++) {
          if (grid[0][col].getPlayed() == null) {
              return false;
          }
      }

      return false;
  }
}
