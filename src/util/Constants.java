package util;

import java.io.File;
import java.awt.Color;

public class Constants {
   //Constantes générales
   public static final String TITLE = "- IA for Connect Four -";
   public static final int[] GRID_SIZE = {7, 6};
   public static final int TIME_BT_GAMES_ms = 30;
   public static final int TIME_BT_ROUNDS_ms = 10;


   //Relatif au modèle
   public static final String PSEUDO_P1 = "Joueur 1";
   public static final String PSEUDO_P2 = "Joueur 2";
   public static final Color SWING_PAWN_COLOR_P1 = new Color(243, 235, 12, 255); //YELLOW
   public static final Color SWING_PAWN_COLOR_P2 = new Color(243, 12, 12, 255); //RED


   //Strategie dispos
   public static final String strats[] = { "HumanGui", "Human", "Rdm", "MinMax", "Negamax", "MonteCarlo"};

   //Chemin relatif au projet & ressources
   public static final String ABSOLUTE_PATH = (new File("")).getAbsolutePath();
   public static final String LOGO_PATH = ABSOLUTE_PATH+"/src/resources/logo.jpg";

   //Parallèle GUI
   public static int GUI_ADD_PAWN_COLUMN = -1;
   public static int GUI_NB_GAMES = -1;
}
