package model.strategy;

import java.util.ArrayList;
import java.util.Random;

import model.Board;
import model.Player;
import model.Square;

import model.strategy.evaluation.*;

public class MonteCarlo extends Shareable implements Strategy {
    private int maxIterations;
    private Player player;
    private Player opponent;

    public int nbrNodesVisited;

    public MonteCarlo(int maxIterations, Player player, Player opponent) {
        this.maxIterations = maxIterations;
        this.player = player;
        this.opponent = opponent;
    }

    public int choice(Square[][] grid){
        if (maxIterations == 0) {
            throw new RuntimeException("Le nombre maximum d'itérations doit être supérieur à zéro.");
        }

        this.nbrNodesVisited = 0;
        long startTime = System.currentTimeMillis();
        int[] move = this.monteCarloAlgo(grid, this.maxIterations, -1, this.player, this.player, this.opponent);
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("[MonteCarlo] "+ this.nbrNodesVisited + " situations visitées | temps passé : "+timeElapsed+" ms");
        return move[0]+1;
    }

    public int[] monteCarloAlgo(Square[][] originalGrid, int maxIterations, int move, Player currentPlayer, Player player, Player opponent){
        int[] renvoi = new int[2];
        this.nbrNodesVisited++;

        NegamaxEval evaluation = new NegamaxEval();

        // Créer une copie du plateau original pour faire les simulations
        Board board = new Board(7, 6);
        board.setGrid(cloneGrid(originalGrid.clone()));

        // Obtenir la liste des coups possibles
        ArrayList<Integer> coupsDispos = this.getMoves(originalGrid);

        // Initialiser les statistiques des coups
        int[] nbCoupsGagnants = new int[coupsDispos.size()];
        int[] nbCoupsSimules = new int[coupsDispos.size()];

        // Faire les simulations pour chaque coup possible
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (int i = 0; i < coupsDispos.size(); i++) {
                int coup = coupsDispos.get(i);

                // Cloner le plateau pour faire la simulation
                Board simulation = new Board(7, 6);
                simulation.setGrid(cloneGrid(board.getGrid()));

                // Faire le coup
                simulation.addPawn(coup + 1, currentPlayer);

                // Faire des coups aléatoires jusqu'à la fin de la partie
                Random random = new Random();
                while (simulation.canPlay() == true) {
                    ArrayList<Integer> coupsPossibles = this.getMoves(simulation.getGrid());
                    int coupAleatoire = coupsPossibles.get(random.nextInt(coupsPossibles.size()));
                    simulation.addPawn(coupAleatoire + 1, currentPlayer);
                    currentPlayer = (currentPlayer == player) ? opponent : player;
                }

                // Enregistrer le résultat de la simulation
                int resultat = evaluation.evaluate(simulation.getGrid(), -1, player, false);
                if (resultat == 999) {
                    nbCoupsGagnants[i]++;
                }
                nbCoupsSimules[i]++;
            }
        }

        // Trouver le coup le plus gagnant
        int meilleurCoup = -1;
        double meilleurScore = -1.0;
        for (int i = 0; i < coupsDispos.size(); i++) {
            double score = (double) nbCoupsGagnants[i] / nbCoupsSimules[i];
            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurCoup = coupsDispos.get(i);
            }
        }

        renvoi[0] = meilleurCoup;
        renvoi[1] = evaluation.evaluate(originalGrid, -1, this.player, false);

        if (move != -1) {
            int score = evaluation.evaluate(originalGrid, -1, this.player, false);
            if (currentPlayer == player) {
                if (score >= meilleurScore) {
                    renvoi[0] = move;
                    renvoi[1] = score;
                }
            } else {
                if (score <= meilleurScore) {
                    renvoi[0] = move;
                    renvoi[1] = score;
                }
            }
        }

        return renvoi;
    }
  }
