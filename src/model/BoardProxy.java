package model;

import util.Constants;

public class BoardProxy extends Board {
	private Board board;

	public BoardProxy(int dimX, int dimY) {
		super(dimX, dimY);
	}

	public Square[][] getGrid(Player player){
		Square[][] clone = new Square[this.len_x][this.len_y];
		for (int i=0; i!=this.len_x; i++){
			for (int y=0; y!=this.len_y; y++){
				clone[i][y] = new Square(i, y);
				if (this.grid[i][y].getComboWinner()){
					clone[i][y].setComboWinner(true);
				}
				if (this.grid[i][y].getPlayed() == player){
					clone[i][y] = this.grid[i][y];
				}
			}
		}
		for (int i=0; i!=this.len_x; i++){
			if (clone[i][0].getPlayed() == player){
				clone[i][1].setPlayed(this.grid[i][1].getPlayed());
				clone[i][2].setPlayed(this.grid[i][2].getPlayed());
				clone[i][3].setPlayed(this.grid[i][3].getPlayed());
				clone[i][4].setPlayed(this.grid[i][4].getPlayed());
				clone[i][5].setPlayed(this.grid[i][5].getPlayed());
			}
			if (clone[i][1].getPlayed() == player){
				clone[i][2].setPlayed(this.grid[i][2].getPlayed());
				clone[i][3].setPlayed(this.grid[i][3].getPlayed());
				clone[i][4].setPlayed(this.grid[i][4].getPlayed());
				clone[i][5].setPlayed(this.grid[i][5].getPlayed());
			}
			if (clone[i][2].getPlayed() == player){
				clone[i][3].setPlayed(this.grid[i][3].getPlayed());
				clone[i][4].setPlayed(this.grid[i][4].getPlayed());
				clone[i][5].setPlayed(this.grid[i][5].getPlayed());
			}
			if (clone[i][3].getPlayed() == player){
				clone[i][4].setPlayed(this.grid[i][4].getPlayed());
				clone[i][5].setPlayed(this.grid[i][5].getPlayed());
			}
			if (clone[i][4].getPlayed() == player){
				clone[i][5].setPlayed(this.grid[i][5].getPlayed());
			}
		}
		return clone;
	}
}