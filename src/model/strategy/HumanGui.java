package model.strategy;

import java.util.concurrent.TimeUnit;

import util.Constants;


public class HumanGui extends Strategy{

	public HumanGui(){
	}

	public int choice(){
		Constants.GUI_ADD_PAWN_COLUMN = -1;
		while (Constants.GUI_ADD_PAWN_COLUMN == -1){
			try { TimeUnit.MILLISECONDS.sleep(150); } catch (Exception e) {
				//System.out.println(e);
			}
		}
		int result = Constants.GUI_ADD_PAWN_COLUMN;
		Constants.GUI_ADD_PAWN_COLUMN = -1;
		return result;
	}
}