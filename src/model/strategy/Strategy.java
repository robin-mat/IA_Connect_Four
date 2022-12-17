package model.strategy;

import model.*;

public interface Strategy{
	public abstract int choice(Square[][] grid);
}