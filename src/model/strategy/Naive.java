package model.strategy;

import java.util.Random;

public class Naive extends Strategy{
	private Random rng = new Random();

	public int choice(){
		return (this.rng.nextInt(7)+1);
	}
}