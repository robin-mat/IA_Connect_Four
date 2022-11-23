package view.SwingGUI;

import util.Constants;

import play.GameInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements GameInterface {
	public JPanel grid;

	private JLabel label = new JLabel("Enter your e-mail: ");
	private JTextField textField = new JTextField(20);
	private JButton button = new JButton("OK");

	public Gui() {
		super(Constants.TITLE);
		setIconImage(new ImageIcon(Constants.LOGO_PATH).getImage());

	}

	public void launch(){
		System.out.println("[Log] : Lancement avec fênetre graphique Swing");
		this.menuBar();

		this.manageEvent();

		this.createScreen();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setVisible(true);

		try {
			this.drawGrid(this.grid);
        } catch (Exception e){
          //System.out.println(e);
        }
	}

	public void drawGrid(JPanel grid) throws IndexOutOfBoundsException, Exception{
		for(int i = 0; i < Constants.GRID_SIZE[1]; i++) {
			for(int j = 0; j < Constants.GRID_SIZE[0]; j++) {				
				DrawCase cases = new DrawCase();
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
					System.out.println("[Log] : Fermeture du jeu");
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
					System.out.println("[Log] : Fermeture du jeu");
					dispose();
				} else {
					return;
				}
			}
		});
	}

	private void createScreen(){
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

		this.getContentPane().add(content);
		this.pack();
	}
}