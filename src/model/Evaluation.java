package model;

public class Evaluation {

  public static int evaluate(Square[][] grid, int choice, Player player, boolean isMaximizingPlayer) {
    int score = 0;
    int col = choice-1;

    // Vérifier les lignes
    int countPlayer = 0;
    int countOpponent = 0;
    boolean opponentFoundSuiv = false;
    boolean opponentFoundPrec = false;
    for(int row = 0; row < grid[0].length; row++) {
      // Vérifier les lignes suivantes
      for(int i = 0; i < 4; i++) {
        System.out.println("row+i= " + (row+i));
        System.out.println("grid[0].length" + grid[0].length);
        if(row+i < grid[0].length){
          if(grid[col][row+i].getPlayed() == player){
            countPlayer++;
          }
          else if(grid[col][row+i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      // Vérifier les lignes précédentes
      for(int i = 0; i < 4; i++) {
        if(row-i >= 0){
          if(grid[col][row-i].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col][row-i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      score += computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
    }


    // Vérifier les colonnes
    countPlayer = 0;
    countOpponent = 0;
    opponentFoundSuiv = false;
    opponentFoundPrec = false;
    for(int row = 0; row < grid[0].length; row++) {
      // Vérifier les colonnes suivantes
      for(int i = 0; i < 4; i++) {
        if(col+i < grid.length){
          if(grid[col+i][row].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col+i][row].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      // Vérifier les colonnes précédentes
      for(int i = 0; i < 4; i++) {
        if(col-i >= 0){
          if(grid[col-i][row].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col-i][row].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      score += computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
    }

    // Vérifier les diagonales ascendantes
    countPlayer = 0;
    countOpponent = 0;
    opponentFoundSuiv = false;
    opponentFoundPrec = false;
    for(int row = 0; row < grid[0].length; row++) {
      countPlayer = 0;
      countOpponent = 0;
      // Vérifier les diagonales ascendantes suivantes
      for(int i = 0; i < 4; i++) {
        if(col+i < grid.length && row+i < grid[0].length){
          if(grid[col+i][row+i].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col+i][row+i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      // Vérifier les diagonales ascendantes précédentes
      for(int i = 0; i < 4; i++) {
        if(col-i >= 0 && row-i >= 0){
          if(grid[col-i][row-i].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col-i][row-i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      score += computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
    }

    // Vérifier les diagonales descendantes
    countPlayer = 0;
    countOpponent = 0;
    opponentFoundSuiv = false;
    opponentFoundPrec = false;
    for(int row = 0; row < grid[0].length; row++) {
      // Vérifier les diagonales descendantes suivantes
      for(int i = 0; i < 4; i++) {
        if(col+i < grid.length && row-i >= 0){
          if(grid[col+i][row-i].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col+i][row-i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      // Vérifier les diagonales descendantes précédentes
      for(int i = 0; i < 4; i++) {
        if(col-i >= 0 && row+i < grid[0].length){
          if(grid[col-i][row+i].getPlayed() == player) {
            countPlayer++;
          }
          else if(grid[col-i][row+i].getPlayed() == null){
            continue;
          }
          else{
            countOpponent++;
          }
        }
      }
      score += computeScore(countPlayer, countOpponent, opponentFoundSuiv, opponentFoundPrec, choice, isMaximizingPlayer);
    }
    return score;
  }


  private static int computeScore(int countPlayer, int countOpponent, boolean opponentFoundSuiv, boolean opponentFoundPrec, int choice, boolean isMaximizingPlayer) {
    int score = 0;
    //Initialisation des préférences pour rendre les colonnes du milieu plus intéressant
    if(choice == 2 || choice == 6){
      score += 1;
    }
    else if(choice == 3 || choice == 5){
      score += 3;
    }
    else if(choice == 4){
      score += 5;
    }
    //Si on veut maximiser
    if(isMaximizingPlayer == true){
      // Score pour le joueur actuel
      if (countPlayer == 4) { // le joueur a gagné
        score += 10000;
      }
      else if (countPlayer == 3 && ((!opponentFoundPrec && choice != 1) || (!opponentFoundSuiv && choice != 7))) { // 3 pions alignés mais pas bloqué d'un des deux côté au moins
        score += 1000;
      }
      else if (countPlayer == 2 && ((!opponentFoundPrec && choice != 1) || (!opponentFoundSuiv && choice != 7))) { // 2 pions alignés mais pas bloqué d'un des deux côté au moins
        score += 100;
      }
      else if (countPlayer == 1 && ((!opponentFoundPrec && choice != 1) || (!opponentFoundSuiv && choice != 7))) { // 1 pion aligné mais pas bloqué d'un des deux côté au moins
        score += 10;
      }
    }
    return score;
  }
}

      /*// Si le joueur est bloqué des deux côtés mais peut encore gagner
      else if (countPlayer == 3 && opponentFoundPrec && opponentFoundSuiv) {
        score += 50;
      }
      else if (countPlayer == 2 && opponentFoundPrec && opponentFoundSuiv) {
        score += 50;
      }
      else if (countPlayer == 2 && opponentFoundPrec && !opponentFoundSuiv && choice < 5) { // possibilité de gagner en jouant à droite
        score += 50;
      }
      else if (countPlayer == 2 && !opponentFoundPrec && opponentFoundSuiv && choice > 2) { // possibilité de gagner en jouant à gauche
        score += 50;
      }
      else if (countPlayer == 1 && opponentFoundPrec && opponentFoundSuiv) {
        score += 50;
      }

      // Score pour l'adversaire
      if (countOpponent == 4) { // l'adversaire a gagné
        score -= 10000;
      }
      else if (countOpponent == 3 && !opponentFoundPrec && !opponentFoundSuiv) {
        score -= 1000;
      }
      else if (countOpponent == 2 && !opponentFoundPrec && !opponentFoundSuiv) {
        score -= 100;
      }
      else if (countOpponent == 1 && !opponentFoundPrec && !opponentFoundSuiv) {
        score -= 10;
      }
    }
    //Si on veut minimiser
    else{
      // Score pour le joueur actuel
      if (countPlayer == 4) { // le joueur a gagné au prochain coup
        score -= 10000;
      }
      else if (countPlayer == 3) {
        if (opponentFoundSuiv && opponentFoundPrec) {
          score += 0; // aucune possibilité de faire un puissance 4
        }
        else if (opponentFoundSuiv || opponentFoundPrec) {
          score += 100; // possibilité de faire un puissance 4 avec une extrémité bloquée
        }
        else {
          score += 1000;
        }
      }
      else if (countPlayer == 2) {
        score += 100;
      }
      else if (countPlayer == 1) {
        score += 10;
      }

      // Score pour l'adversaire
      if (countOpponent == 4) { // l'adversaire a gagné au prochain coup
        score += 10000;
      }
      else if (countOpponent == 3) {
        if (opponentFoundSuiv && opponentFoundPrec) {
          score -= 0; // aucun risque de perte au prochain tour
        }
        else if(opponentFoundSuiv || opponentFoundPrec) {
          score -= 100; // risque de perte au prochain tour si l'adversaire joue sur la case bloquée
        }
        else {
          score -= 1000;
        }
      }
      else if (countOpponent == 2) {
        score -= 100;
      }
      else if (countOpponent == 1) {
        score -= 10;
      }
      return score;
    }
  }*/
