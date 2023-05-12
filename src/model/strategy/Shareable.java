package model.strategy;

import java.util.ArrayList;

import model.Player;
import model.Square;

public class Shareable {
    public ArrayList<Integer> getMoves(Square[][] grid){
        ArrayList<Integer> liste = new ArrayList<Integer>();
        if (!(grid[0][0].getPlayed() instanceof Player)){
            liste.add(0);
        }
        if (!(grid[1][0].getPlayed() instanceof Player)){
            liste.add(1);
        }
        if (!(grid[2][0].getPlayed() instanceof Player)){
            liste.add(2);
        }
        if (!(grid[3][0].getPlayed() instanceof Player)){
            liste.add(3);
        }
        if (!(grid[4][0].getPlayed() instanceof Player)){
            liste.add(4);
        }
        if (!(grid[5][0].getPlayed() instanceof Player)){
            liste.add(5);
        }
        if (!(grid[6][0].getPlayed() instanceof Player)){
            liste.add(6);
        }
        return liste;
    }

    public Square[][] cloneGrid(Square[][] grid) {
        Square[][] newGrid = new Square[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Square square = grid[i][j];
                Player played = square.getPlayed();
                newGrid[i][j] = new Square(i, j);
                newGrid[i][j].setPlayed(played);
            }
        }
        return newGrid;
    }
}
