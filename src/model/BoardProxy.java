package model;

public class BoardProxy extends Board {
	private Player player;

	public BoardProxy(int dimX, int dimY) {
		super(dimX, dimY);
	}

	@Override
	public Square[][] getGrid(){
		return this.grid;
	}
}