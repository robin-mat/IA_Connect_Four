package model;

import util.Constants;

public class BoardProxy extends Board {
	private Board board;
	private String state;
	private Player p1;
	private Player p2;

	public BoardProxy(int dimX, int dimY, Player p1, Player p2) {
		super(dimX, dimY);
		this.state = "current";
		this.p1 = p1;
		this.p2 = p2;
	}

	public void setState(String temp){
		this.state = temp;
	}

	public Square[][] getGridView(Player player){
		Square[][] clone = new Square[this.len_x][this.len_y];
		if (this.state == "omniscient"){
			for (int i=0; i!=this.len_x; i++){
				for (int y=0; y!=this.len_y; y++){
					clone[i][y] = this.grid[i][y];
				}
			}
		}
		else if (this.state == "p1"){
			for (int i=0; i!=this.len_x; i++){
				for (int y=0; y!=this.len_y; y++){
					if (this.grid[i][y].getPlayed() == p1){
						clone[i][y] = this.grid[i][y];
					} else {
						clone[i][y] = new Square(i, y);
					}
				}
			}
		}
		else if (this.state == "p2"){
			for (int i=0; i!=this.len_x; i++){
				for (int y=0; y!=this.len_y; y++){
					if (this.grid[i][y].getPlayed() == p2){
						clone[i][y] = this.grid[i][y];
					} else {
						clone[i][y] = new Square(i, y);
					}
				}
			}
		}
		else if (this.state == "current"){
			return this.getGrid(player);
		}
		return clone;
	}

	public Square[][] getFullGrid(){
		return this.grid;
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
			if (this.grid[i][0].getPlayed() instanceof Player){
				clone[i][0].setPlayed(this.grid[i][0].getPlayed());
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
