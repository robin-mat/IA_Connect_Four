package model;

public class Square {
	private int posX;
	private int posY;
	private Player played;

	public Square(int positionX, int positionY){
    	this.posX = positionX;
    	this.posY = positionY;
    	this.played = null;
    }

    /**
	* @return retourner la position de la case axe X
	**/
	public int getPosX(){
		return this.posX;
	}

	/**
	* @return retourner la position de la case axe y
	**/
	public int getPosY(){
		return this.posY;
	}

	/**
	* @return 
	**/
	public Player getPlayed(){
		return this.played;
	}
}