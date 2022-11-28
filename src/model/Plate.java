package model;

import java.util.ArrayList;

public class Plate{
	private Square[][] grid;
	private int len_x;
	private int len_y;

	public Plate(int dimX, int dimY){
	    this.len_x = dimX;
	    this.len_y = dimY;
	    this.grid = new Square[this.len_x][this.len_y];
	}

	public void init(){
		for (int i=0; i!=this.len_x; i++){
			for (int y=0; y!=this.len_y; y++){
				this.grid[i][y] = new Square(i, y);
			}
		}
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
}