package model.strategy;

import java.util.concurrent.TimeUnit;

import util.Constants;
import model.*;


public class HumanGui implements Strategy{

	public HumanGui(){
	}

	public int choice(Square[][] grid){
		Constants.GUI_ADD_PAWN_COLUMN = -1;
		int result = Constants.GUI_ADD_PAWN_COLUMN;
		while (result == -1){
			try { result = Constants.GUI_ADD_PAWN_COLUMN;TimeUnit.MILLISECONDS.sleep(50); } catch (Exception e) {
				//System.out.println(e);
			}
		}
		return result;
	}
}