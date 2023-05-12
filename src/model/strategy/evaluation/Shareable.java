package model.strategy.evaluation;

import model.Player;
import model.Square;
import model.Board;

public class Shareable {
    
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
}
