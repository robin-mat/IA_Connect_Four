package view.swingGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Square;

import util.Constants;

public class DrawCase extends JPanel {
	public Square square;
	public java.awt.Color esthetic;
	private int column;

	public DrawCase(int column, Square square, java.awt.Color color) {
		this.square = square;
		this.esthetic = color;
		this.column = column;


		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) {
				Constants.GUI_ADD_PAWN_COLUMN = column;
			} 
		});
	}

	public DrawCase(int i, Square square) {
		this(i, square, new java.awt.Color(255,255,255));
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
