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

    public boolean columnFull(Square[][] grid, int column, Player player){
        if (grid[column][3].getPlayed() == player){
            return true;
        } else if (grid[column][2].getPlayed() == player){
            return true;
        } else if (grid[column][1].getPlayed() == player){
            return true;
        }
        return false;
    }
}
