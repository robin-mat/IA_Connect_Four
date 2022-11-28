package view.swingGUI;

import util.Constants;
import util.Logger;

import model.*;

import view.ViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements ViewInterface {
	private Logger logger;
	public JPanel grid;
	public Plate plate;

	private Player j1;
	private Player j2;

	private Dimension screenSize;

	public Gui(Player[] players) {
		super(Constants.TITLE);
		this.j1 = players[0];
		this.j2 = players[1];
		setIconImage(new ImageIcon(Constants.LOGO_PATH).getImage());
	}

	public void launch(){
		this.logger.write("Run project with the Swing GUI");
		this.menuBar();

		this.manageEvent();

		this.createScreen();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setVisible(true);

		try {
			this.drawGrid(this.grid);
    } catch (Exception e){
      //this.logger.write(e);
    }
	}

	public void update(){
		try {
			this.grid.removeAll();
			this.drawGrid(this.grid);
    } catch (Exception e){
      this.logger.write(e);
    }
	}

	public void drawGrid(JPanel grid) throws IndexOutOfBoundsException, Exception{
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				DrawCase cases;
				if (this.plate.getGrid()[j][i].getPlayed() ==  this.j1){
					cases = new DrawCase(this.plate.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J1);
				} else if (this.plate.getGrid()[j][i].getPlayed() ==  this.j2){
					cases = new DrawCase(this.plate.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J2);
				} else {
					cases = new DrawCase(this.plate.getGrid()[j][i]);
				}
				cases.setPreferredSize(new Dimension(80,80));
				grid.add(cases);
			}	
		}
		
		this.pack();
	}

	private void menuBar(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("Game");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
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
		menuFile.add(menuItemExit);
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
	}

	private void manageEvent(){
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
	}

	private void createScreen(){
		JTabbedPane onglets = new JTabbedPane();
		onglets.setBounds(30,20,300,300);

		JPanel content = new JPanel();
		content.setLayout(new GridLayout(1, 2));

		JPanel panel = new JPanel();
    LayoutManager layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);  
    panel.setLayout(layout);

    //Titre
    JLabel label = new JLabel("<HTML><H1><U>"+Constants.TITLE+"</U></H1></HTML>");
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);

		//Infos
		JPanel infos = new JPanel();
    infos.setBorder(BorderFactory.createTitledBorder("Informations"));


    panel.add(infos);

		//Choix
		JPanel choices = new JPanel();
    choices.setBorder(BorderFactory.createTitledBorder("Settings"));
    panel.add(choices);


	  panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
	  panel.setBackground(new java.awt.Color(194, 194, 194));
	  content.add(panel, BorderLayout.CENTER);




	  grid = new JPanel();
	  grid.setLayout(new GridLayout(Constants.GRID_SIZE[1], Constants.GRID_SIZE[0]));
	  grid.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
	  content.add(grid);


	  content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

	  JPanel logs = new JPanel();
	  JTextArea showLogs = new JTextArea();
	  this.logger.setJTextArea(showLogs);
    showLogs.setEditable(false);
	  this.logger.write("Window opening");
	  JScrollPane scrollArea = new JScrollPane(showLogs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollArea.setPreferredSize(new Dimension(1100,450));
	  logs.add(scrollArea, BorderLayout.CENTER);

	  onglets.add("Main", content);
	  onglets.add("Logs", logs);

		this.getContentPane().add(onglets);
		this.pack();
	}


	public void setLogger(Logger l){
		this.logger = l;
	}

	public void setPlate(Plate plate){
		this.plate = plate;
	}
}