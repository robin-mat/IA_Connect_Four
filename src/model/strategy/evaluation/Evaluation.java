package model.strategy.evaluation;

import model.Player;
import model.Square;

public interface Evaluation {
  public int evaluate(Square[][] grid, int choice, Player player, Player opponent, boolean isMaximizingPlayer);
}
