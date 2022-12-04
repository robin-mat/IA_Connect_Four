package model;

public class Square {
	private int posX;
	private int posY;
	private Player played;
	private boolean comboWinner;

	public Square(int positionX, int positionY){
    	this.posX = positionX;
    	this.posY = positionY;
    	this.played = null;
    	this.comboWinner = false;
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

	/**
	* @return 
	**/
	public void setPlayed(Player player){
		this.played = player;
	}

	/**
	* @return 
	**/
	public Boolean getComboWinner(){
		return this.comboWinner;
	}

	/**
	* @return 
	**/
	public void setComboWinner(Boolean b){
		this.comboWinner = b;
	}
}