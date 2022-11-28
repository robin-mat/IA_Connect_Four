package view;

import util.Logger;
import model.Plate;

public interface ViewInterface{
	public void launch();
	public void setLogger(Logger l);
	public void update();
	public void setPlate(Plate plate);
}