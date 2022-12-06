package view.swingGUI;

import util.Constants;
import util.Logger;

import controller.ActionListenerGui;
import model.*;
import play.GameInterface;

import view.ViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;

public class Gui extends JFrame implements ViewInterface {
	private GameInterface gameInterface;
	private Logger logger;
	public JPanel grid;
	public Board board;

	private Player j1;
	private Player j2;

	private Dimension screenSize;

	//Swing vars
	private JLabel currentPlayer;
	private JLabel rounds;

	private JCheckBox proxyViewOmni;
  private JCheckBox proxyViewP1;
  private JCheckBox proxyViewP2;
  private JComboBox p1StratChoice;
  private JComboBox p2StratChoice;

	public Gui(Player[] players) {
		super(Constants.TITLE);
		this.j1 = players[0];
		this.j2 = players[1];
		setIconImage(new ImageIcon(Constants.LOGO_PATH).getImage());
	}

	public void setGameInterface(GameInterface gameInterface){
		this.gameInterface = gameInterface;
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
      this.logger.write(e);
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
				if (this.board.getGrid()[j][i].getComboWinner()){
					if (this.board.getGrid()[j][i].getPlayed() ==  this.j1){
						cases = new DrawCase(j+1, this.board.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J1, true);
					} else {
						cases = new DrawCase(j+1, this.board.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J2, true);
					}
				} else if (this.board.getGrid()[j][i].getPlayed() ==  this.j1){
					cases = new DrawCase(j+1, this.board.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J1);
				} else if (this.board.getGrid()[j][i].getPlayed() ==  this.j2){
					cases = new DrawCase(j+1, this.board.getGrid()[j][i], Constants.SWING_PAWN_COLOR_J2);
				} else {
					cases = new DrawCase(j+1, this.board.getGrid()[j][i]);
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
    panel.setLayout(new GridLayout(3, 1));

    //Titre
    JLabel label = new JLabel("<HTML><H1><U>"+Constants.TITLE+"</U></H1></HTML>");
    label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);

		//Infos
		JPanel infos = new JPanel();
    infos.setBorder(BorderFactory.createTitledBorder("Informations"));

    
		this.currentPlayer = new JLabel("Current player : ?");
		this.currentPlayer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.currentPlayer.setBackground(new java.awt.Color(140, 140, 140));
		this.currentPlayer.setOpaque(true);
    infos.add(this.currentPlayer);

    this.rounds = new JLabel("Rounds : 0");
    infos.add(this.rounds);

    panel.add(infos);

		//Choix
		JPanel choices = new JPanel();
    choices.setBorder(BorderFactory.createTitledBorder("Settings"));
    choices.setLayout(new GridLayout(3, 1));

    JPanel proxyView = new JPanel();
    proxyView.setBorder(BorderFactory.createTitledBorder("Current view"));



    proxyViewOmni = new JCheckBox("Omniscient");
    proxyViewOmni.setForeground(new java.awt.Color(0, 0, 255));
    proxyViewOmni.setSelected(true);
    proxyViewP1 = new JCheckBox("Player 1");
    proxyViewP2 = new JCheckBox("Player 2");
    proxyView.add(proxyViewOmni);
		proxyView.add(proxyViewP1);
		proxyView.add(proxyViewP2);
    proxyViewOmni.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		if (proxyViewOmni.isSelected()){proxyViewOmni.setForeground(new java.awt.Color(0, 0, 255));}
    		proxyViewP1.setSelected(false);
    		proxyViewP2.setSelected(false);
    	}
    });
    proxyViewP1.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		proxyViewOmni.setSelected(false);
    		if (proxyViewP1.isSelected()){proxyViewP1.setForeground(new java.awt.Color(0, 0, 255));}
    		proxyViewP2.setSelected(false);
    	}
    });
    proxyViewP2.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		proxyViewOmni.setSelected(false);
    		proxyViewP1.setSelected(false);
    		if (proxyViewP2.isSelected()){proxyViewP2.setForeground(new java.awt.Color(0, 0, 255));}
    	}
    });

    choices.add(proxyView);

    JPanel p1Strat = new JPanel();
    JPanel p2Strat = new JPanel();

    p1Strat.setBorder(BorderFactory.createTitledBorder("Player 1 strategy"));
    p2Strat.setBorder(BorderFactory.createTitledBorder("Player 2 strategy"));

    String strats[] = { "Random", "Human (Click)", "Human (Input)", "MinMax"}; 
  
    p1StratChoice = new JComboBox(strats);
    p2StratChoice = new JComboBox(strats);

    p1Strat.add(p1StratChoice);
    p2Strat.add(p2StratChoice);

    choices.add(p1Strat);
    choices.add(p2Strat);

    panel.add(choices);


	  panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
	  panel.setBackground(new java.awt.Color(194, 194, 194));
	  content.add(panel);




	  grid = new JPanel();
	  grid.setLayout(new GridLayout(Constants.GRID_SIZE[1], Constants.GRID_SIZE[0]));
	  grid.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
	  content.add(grid, BorderLayout.CENTER);


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
    //onglets.addKeyListener(new ActionListenerGui());
	}

	public void refreshInfos(){
		if (this.gameInterface.getCurrentPlayer() == this.j1){
			this.currentPlayer.setForeground(Constants.SWING_PAWN_COLOR_J1);
		} else {
			this.currentPlayer.setForeground(Constants.SWING_PAWN_COLOR_J2);
		}
		if (this.gameInterface.getWinner() != null){
			this.currentPlayer.setText("Winner : "+this.gameInterface.getWinner().getName());
		} else {
			this.currentPlayer.setText("Current player : "+this.gameInterface.getCurrentPlayer().getName());
		}
		if (!this.gameInterface.getBoard().canPlay() && this.gameInterface.getWinner() == null){
			this.currentPlayer.setForeground(new java.awt.Color(0, 128, 0));
			this.currentPlayer.setText("Draw");
		}


		this.rounds.setText("Rounds : "+this.gameInterface.getRounds());
	}

	public void setLogger(Logger l){
		this.logger = l;
	}

	public void setBoard(Board board){
		this.board = board;
	}

	public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();

    if (source == proxyViewOmni) {
        //...make a note of it...
    }
    System.out.println("ehehe");
	}
}