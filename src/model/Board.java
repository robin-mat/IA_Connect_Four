package model;

import java.util.ArrayList;

public class Board{
	public Square[][] grid;
	private int[] lastPawn;
	private int len_x;
	private int len_y;

	public Board(int dimX, int dimY){
	    this.len_x = dimX;
	    this.len_y = dimY;
	    this.lastPawn = new int[2];
	    this.lastPawn[0] = -1;
	    this.lastPawn[1] = -1;
	    this.grid = new Square[this.len_x][this.len_y];
	}

	public void init(){
		for (int i=0; i!=this.len_x; i++){
			for (int y=0; y!=this.len_y; y++){
				this.grid[i][y] = new Square(i, y);
			}
		}
	}

	public void addPawn(int colum, Player p){
		//TODO
		int y = 5;
		while (this.grid[colum-1][y].getPlayed() instanceof Player){
			y = y-1;
		}
		this.grid[colum-1][y].setPlayed(p);
	    this.lastPawn[0] = colum-1;
	    this.lastPawn[1] = y;
	}

	public boolean canPlay(int colum){
		if (this.grid[colum-1][0].getPlayed() instanceof Player){
			return false;
		} else {
			return true;
		}
	}

	public boolean canPlay(){
		for (int i=0; i!=this.len_x; i++){
			for (int y=0; y!=this.len_y; y++){
				if (this.grid[i][y].getPlayed() == null){
					return true;
				}
			}
		}
		return false;
	}

	public Square[][] getGrid(){
		return this.grid;
	}

	public int getLen_x(){
		return this.len_x;
	}
	public int getLen_y(){
		return this.len_y;
	}

	public int[] getLastPawn(){
		return this.lastPawn;
	}
}