package view;

import util.Constants;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
	public JPanel grid;

	private JLabel label = new JLabel("Enter your e-mail: ");
	private JTextField textField = new JTextField(20);
	private JButton button = new JButton("OK");

	public Gui() {
		super(Constants.TITLE);

		// sets layout manager
		setLayout(new GridBagLayout());

		GridBagConstraints constraint = new GridBagConstraints();
		constraint.insets = new Insets(10, 10, 10, 10);
		constraint.gridx = 0;
		constraint.gridy = 0;

		add(label, constraint);

		constraint.gridx = 1;
		add(textField, constraint);

		constraint.gridx = 0;
		constraint.gridwidth = 2;
		constraint.gridy = 1;

		add(button, constraint);

		// adds menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuFile.add(menuItemExit);

		menuBar.add(menuFile);

		// adds menu bar to the frame
		setJMenuBar(menuBar);

		// adds window event listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				int reply = JOptionPane.showConfirmDialog(Gui.this,
						"Are you sure you want to quit?",
						"Exit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					return;
				}
			}
		});


		Image icon = new ImageIcon(Constants.LOGO_PATH).getImage();
		setIconImage(icon);

		JLabel zonePlate = new JLabel("Plateau");

		zonePlate.setBounds(320, 50 , 300, 300);
	  grid = new JPanel();
	  grid.setLayout(new GridLayout(Constants.GRID_SIZE[1], Constants.GRID_SIZE[0]));
		this.getContentPane().add(grid);
		this.getContentPane().add(zonePlate);
		this.pack();

		// centers on screen
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setVisible(true);
	}

	public void drawGrid(JPanel grid) throws IndexOutOfBoundsException, Exception{
		for(int i = 0; i < Constants.GRID_SIZE[1]; i++) {
			for(int j = 0; j < Constants.GRID_SIZE[0]; j++) {				
				DrawCase cases = new DrawCase();
				cases.setPreferredSize(new Dimension(80,80));
				grid.add(cases);
				System.out.println("zzz");
			}	
		}
		
		this.pack();
	}
}