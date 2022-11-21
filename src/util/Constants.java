package util;

import java.io.File;

public class Constants {
   //Constantes générales
   public static final String TITLE = "- IA for Connect Four -";
   public static final int[] GRID_SIZE = {7, 6};


   //Relatif au modèle
   public static final String PSEUDO_J1 = "Joueur 1";
   public static final String PSEUDO_J2 = "Joueur 2";



   //Chemin relatif au projet & ressources
   public static final String ABSOLUTE_PATH = (new File("")).getAbsolutePath();
   public static final String LOGO_PATH = ABSOLUTE_PATH+"/src/resources/logo.jpg";
}