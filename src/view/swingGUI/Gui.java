package view.swingGUI;

import util.Constants;
import util.Logger;

import controller.ActionListenerGui;
import model.*;

import model.strategy.*;

import controller.TerminalInput;

import play.GameInterface;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.ViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements ViewInterface {
	private GameInterface gameInterface;
	private Logger logger;
	public JPanel grid;
	public BoardProxy board;

	private Player p1;
	private Player p2;

	private Dimension screenSize;

	//Swing vars
	private JLabel currentPlayer;
	private JLabel rounds;

	private JCheckBox proxyViewOmni;
	private JCheckBox proxyViewCurrentPlayer;
	private JCheckBox proxyViewP1;
	private JCheckBox proxyViewP2;
	private JComboBox p1StratChoice;
	private JComboBox p2StratChoice;

	private JLabel nbvictoriesJ1;
	private JLabel nbvictoriesJ2;

	private JSpinner spin;
	private JButton start;

	private TerminalInput terminalInput;


	public Gui(Player[] players, TerminalInput terminalInput) {
		super(Constants.TITLE);
		this.p1 = players[0];
		this.p2 = players[1];
		this.terminalInput = terminalInput;
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
			//this.logger.write(e);
		}
	}

	public void restart(){
		//
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
		Player joueur = this.gameInterface.getWaitingPlayer();
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				DrawCase cases;
				if (this.board.getFullGrid()[j][i].getComboWinner()){
					if (this.board.getFullGrid()[j][i].getPlayed() == this.p1){
						cases = new DrawCase(j+1, this.board.getFullGrid()[j][i], Constants.SWING_PAWN_COLOR_P1, true);
					} else {
						cases = new DrawCase(j+1, this.board.getFullGrid()[j][i], Constants.SWING_PAWN_COLOR_P2, true);
					}
				} else if (this.board.getGridView(joueur)[j][i].getPlayed() ==  this.p1){
					cases = new DrawCase(j+1, this.board.getGridView(joueur)[j][i], Constants.SWING_PAWN_COLOR_P1);
				} else if (this.board.getGridView(joueur)[j][i].getPlayed() ==  this.p2){
					cases = new DrawCase(j+1, this.board.getGridView(joueur)[j][i], Constants.SWING_PAWN_COLOR_P2);
				} else {
					cases = new DrawCase(j+1, this.board.getGridView(joueur)[j][i]);
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
		infos.setLayout(new GridLayout(2, 1));


		this.currentPlayer = new JLabel("Current player : ?");
		this.currentPlayer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.currentPlayer.setBackground(new java.awt.Color(140, 140, 140));
		this.currentPlayer.setOpaque(true);
    infos.add(this.currentPlayer);

    this.rounds = new JLabel("Rounds : 0");
    infos.add(this.rounds);

    panel.add(infos);

		//Nombre de partie
		JPanel nbgames = new JPanel();
		nbgames.setBorder(BorderFactory.createTitledBorder("Number of games"));
		nbgames.setLayout(new GridLayout(1, 2));

		this.spin = new JSpinner(new SpinnerNumberModel(5, 0, 1000, 1));
		nbgames.add(this.spin);

		this.start = new JButton("Start");
		this.start.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		      Constants.GUI_NB_GAMES = (int) spin.getValue();
				start.setBackground(Color.BLACK);
				start.setText("In progress");
				start.setEnabled(false);
				spin.setEnabled(false);
				//proxyViewOmni.setEnabled(false);
  				//proxyViewP1.setEnabled(false);
  				//proxyViewP2.setEnabled(false);

				//p1StratChoice.setEnabled(false);
  				//p2StratChoice.setEnabled(false);
			}
		});
		this.start.setBounds(100,100,100,40);
		this.start.setBackground(new java.awt.Color(140, 140, 140));
		this.start.setOpaque(true);
		nbgames.add(this.start);

		infos.add(nbgames);


		//Nombre de victoire par joueurs
		JPanel nbvictories = new JPanel();
		nbvictories.setBorder(BorderFactory.createTitledBorder("Number of victories"));
		nbvictories.setLayout(new GridLayout(1, 2));

		this.nbvictoriesJ1 = new JLabel("J1 : "+this.gameInterface.getP1Score());
		nbvictories.add(this.nbvictoriesJ1);
		this.nbvictoriesJ2 = new JLabel("J2 : "+this.gameInterface.getP2Score());
		nbvictories.add(this.nbvictoriesJ2);
		infos.add(nbvictories);


		//Choix
		JPanel choices = new JPanel();
    choices.setBorder(BorderFactory.createTitledBorder("Settings"));
    choices.setLayout(new GridLayout(3, 1));

    JPanel proxyView = new JPanel();
    proxyView.setBorder(BorderFactory.createTitledBorder("Current view"));



    proxyViewCurrentPlayer  = new JCheckBox("Current player");
    proxyViewCurrentPlayer.setForeground(new java.awt.Color(0, 0, 255));
    proxyViewCurrentPlayer.setSelected(true);
    proxyViewOmni = new JCheckBox("Omniscient");
    proxyViewP1 = new JCheckBox("Player 1");
    proxyViewP2 = new JCheckBox("Player 2");
    proxyView.add(proxyViewOmni);
	proxyView.add(proxyViewP1);
	proxyView.add(proxyViewP2);
	proxyView.add(proxyViewCurrentPlayer);

	proxyViewCurrentPlayer.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewCurrentPlayer.setForeground(Color.BLACK);
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		if (proxyViewCurrentPlayer.isSelected()){proxyViewCurrentPlayer.setForeground(new java.awt.Color(0, 0, 255));
				logger.write("[GUI view] : Current Player");
				board.setState("current");}
    		proxyViewP1.setSelected(false);
    		proxyViewP2.setSelected(false);
    		proxyViewOmni.setSelected(false);
    	}
    });
    proxyViewOmni.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewCurrentPlayer.setForeground(Color.BLACK);
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		if (proxyViewOmni.isSelected()){proxyViewOmni.setForeground(new java.awt.Color(0, 0, 255));
				logger.write("[GUI view] : Omniscient view");
				board.setState("omniscient");}
    		proxyViewP1.setSelected(false);
    		proxyViewP2.setSelected(false);
    		proxyViewCurrentPlayer.setSelected(false);
    	}
    });
    proxyViewP1.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewCurrentPlayer.setForeground(Color.BLACK);
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		proxyViewOmni.setSelected(false);
    		if (proxyViewP1.isSelected()){proxyViewP1.setForeground(new java.awt.Color(0, 0, 255));
				logger.write("[GUI view] : P1");
				board.setState("p1");}
    		proxyViewP2.setSelected(false);
    		proxyViewCurrentPlayer.setSelected(false);
    	}
    });
    proxyViewP2.addItemListener(new ItemListener() {
    	@Override
    	public void itemStateChanged(ItemEvent e) {
    		proxyViewCurrentPlayer.setForeground(Color.BLACK);
    		proxyViewOmni.setForeground(Color.BLACK);
    		proxyViewP1.setForeground(Color.BLACK);
    		proxyViewP2.setForeground(Color.BLACK);
    		proxyViewOmni.setSelected(false);
    		proxyViewP1.setSelected(false);
    		proxyViewCurrentPlayer.setSelected(false);
    		if (proxyViewP2.isSelected()){proxyViewP2.setForeground(new java.awt.Color(0, 0, 255));
				logger.write("[GUI view] : P2");
				board.setState("p2");}
    	}
    });

    choices.add(proxyView);

    JPanel p1Strat = new JPanel();
    JPanel p2Strat = new JPanel();

    p1Strat.setBorder(BorderFactory.createTitledBorder("Player 1 strategy"));
    p2Strat.setBorder(BorderFactory.createTitledBorder("Player 2 strategy"));

    p1StratChoice = new JComboBox(Constants.strats);
    p2StratChoice = new JComboBox(Constants.strats);



		p1StratChoice.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
						selectStrat(p1StratChoice.getSelectedItem().toString(), p1);
				}
			}
		);

		p2StratChoice.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
					selectStrat(p2StratChoice.getSelectedItem().toString(), p2);
				}
			}
		);

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

	public void selectStrat(String strat, Player p){
		this.logger.write("[#CHANGE STRAT#] " + p.getName()+" : "+ strat);
		if (strat == "HumanGui"){
			p.setStrategie(new HumanGui());
		} else if (strat == "Human"){
			p.setStrategie(new Human(this.terminalInput));
		}  else if (strat == "Rdm"){
			p.setStrategie(new Rdm());
		}  else if (strat == "MinMax"){
			p.setStrategie(new Minimax(5, p, gameInterface.getWaitingPlayer()));
		}  else if (strat == "Negamax"){
			p.setStrategie(new Negamax(5, p, gameInterface.getWaitingPlayer()));
		}else if (strat == "MonteCarlo"){
		p.setStrategie(new MonteCarlo(6, p, gameInterface.getWaitingPlayer()));
	}
	}

	public void refreshInfos(){
		if (this.gameInterface.getCurrentPlayer() == this.p1){
			this.currentPlayer.setForeground(Constants.SWING_PAWN_COLOR_P1);
		} else {
			this.currentPlayer.setForeground(Constants.SWING_PAWN_COLOR_P2);
		}
		if (this.gameInterface.getWinner() != null){
			this.currentPlayer.setText("Winner : "+this.gameInterface.getWinner().getName());
		} else {
			this.currentPlayer.setText("Current player : "+this.gameInterface.getCurrentPlayer().getName());
		}
		if (!this.gameInterface.getboardProxy().canPlay() && this.gameInterface.getWinner() == null){
			this.currentPlayer.setForeground(new java.awt.Color(0, 128, 0));
			this.currentPlayer.setText("Draw");
		}

		this.nbvictoriesJ1.setText("J1 : "+this.gameInterface.getP1Score());
		this.nbvictoriesJ2.setText("J2 : "+this.gameInterface.getP2Score());
		this.rounds.setText("Rounds : "+this.gameInterface.getRounds());
	}

	public void finish(){
		if (this.gameInterface.getP2Score()>this.gameInterface.getP1Score()){
			this.currentPlayer.setText("BIG WINNER : "+this.gameInterface.getP2().getName());
		} else if (this.gameInterface.getP1Score()>this.gameInterface.getP2Score()){
			this.currentPlayer.setText("BIG WINNER : "+this.gameInterface.getP1().getName());
		} else {
			this.currentPlayer.setText("BIG DRAW");
		}
		this.rounds.setText("");
		this.start.setText("End");
	}

	public void setLogger(Logger l){
		this.logger = l;
	}

	public void setBoard(BoardProxy board){
		this.board = board;
	}

	public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();

    if (source == proxyViewOmni) {
        //...make a note of it...
    }
	}
}
