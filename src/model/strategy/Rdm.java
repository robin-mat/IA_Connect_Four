package model.strategy;

import java.util.Random;
import model.*;

public class Rdm implements Strategy{
	private Random rng = new Random();

	public int choice(Square[][] grid){
		return (this.rng.nextInt(7)+1);
	}
}