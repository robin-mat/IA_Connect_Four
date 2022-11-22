package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawCase extends JPanel {
	public DrawCase() {
	}

	@Override
	public void paintComponent(Graphics g) {
		//DESSINE LA GRILLE
		super.paintComponent(g);
		g.setColor(new java.awt.Color(255,255,255));
		g.fillRect(0, 0, 80, 80);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 80, 80);
		g.setColor(Color.RED);
		g.fillOval(10,10,60,60);
	}

}
