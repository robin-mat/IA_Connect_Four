package model.strategy;
import model.*;
import play.SecretConnectFour;

public class Minimax implements Strategy {
    private BoardProxy board;
    private int maxDepth;
    private Player maxPlayer;
    private SecretConnectFour secretConnectFour;
    private Evaluation evaluation;

    public Minimax(BoardProxy board, int maxDepth, Player maxPlayer, SecretConnectFour secretConnectFour, Evaluation evaluation) {
      this.board = board;
      this.maxDepth = maxDepth;
      this.maxPlayer = maxPlayer;
      this.secretConnectFour = secretConnectFour;
      this.evaluation = evaluation;
    }

    public int[] minimax(int depth, int alpha, int beta, Player player) {
      if (board.canPlay() == false || secretConnectFour.isFinish()) { // Cas où l'on ne peut pas jouer, on renvoie 0
        int[] resultat = new int[0];
        return resultat;
      }
      int bestColumn = -1;
      if (player == maxPlayer) {
        int maxScore = Integer.MIN_VALUE;
        for (int col = 1; col <= board.getLen_x(); col++) {
          if (board.canPlay(col)) {
            BoardProxy childBoard = new BoardProxy(board.getLen_x(), board.getLen_y(), null, null);
            childBoard.grid = board.getGrid(maxPlayer);
            childBoard.addPawn(col, player);
            int[] currentScore = minimax(depth - 1, alpha, beta, secretConnectFour.getWaitingPlayer());
            int currentMaxScore = currentScore[0];
            if (currentMaxScore > maxScore) {
              maxScore = currentMaxScore;
              bestColumn = col;
            }
            alpha = Math.max(alpha, maxScore);
            if (beta <= alpha) {
              break;
            }
          }
        }
        int[] result = { maxScore, bestColumn };
        return result;
      }
      else {
        int minScore = Integer.MAX_VALUE;
        for (int col = 1; col <= board.getLen_x(); col++) {
          if (board.canPlay(col)) {
            BoardProxy childBoard = new BoardProxy(board.getLen_x(), board.getLen_y(), null, null);
            childBoard.grid = board.getGrid(maxPlayer);
            childBoard.addPawn(col, player);
            int[] currentScore = minimax(depth - 1, alpha, beta, secretConnectFour.getWaitingPlayer());
            int currentMinScore = currentScore[0];
            if (currentMinScore < minScore) {
              minScore = currentMinScore;
              bestColumn = col;
            }
            beta = Math.min(beta, minScore);
            if (beta <= alpha) {
              break;
            }
          }
        }
        int[] result = { minScore, bestColumn };
        return result;
      }
    }

    @Override
    public int choice(Square[][] grid) {
    board.setGrid(grid);
    int[] result = minimax(maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);
    return result[1]; //bestColumn
  }
}
