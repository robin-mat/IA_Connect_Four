package model.strategy;

import java.util.Random;

public class Naive implements Strategy{
	private Random rng = new Random();

	public int choice(){
		return (this.rng.nextInt(7)+1);
	}
}