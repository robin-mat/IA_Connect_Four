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
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 71, 71);
		g.setColor(Color.RED);
		g.fillOval(3, 3, 65, 65);
	}

}
