package model.strategy;

import java.util.Random;

public class Naive extends Strategy{
	private Random rng = new Random();

	public void choice(){
		System.out.println(this.rng.nextInt(6));
	}
}