package view.swingGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Square;

public class DrawCase extends JPanel {
	public Square square;
	public java.awt.Color esthetic;

	public DrawCase(Square square, java.awt.Color color) {
		this.square = square;
		this.esthetic = color;
	}

	public DrawCase(Square square) {
		this(square, new java.awt.Color(255,255,255));
	}

	@Override
	public void paintComponent(Graphics g) {
		//DRAW THE SQUARE
		super.paintComponent(g);
		g.setColor(new java.awt.Color(255,255,255));
		g.fillRect(0, 0, 80, 80);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 80, 80);
		if (this.square.getPlayed() != null){
			g.setColor(this.esthetic);
			g.fillOval(10,10,60,60);
		}
	}

}
