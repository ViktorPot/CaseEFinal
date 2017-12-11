/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;

/**
 *
 * @author Viktor
 */
public class GA {

    private static ArrayList<Solution> population;
    
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    private static int[][] shifting(int[][] currentRoster, int iterations) {
        int[][] r1 = new int[currentRoster.length][currentRoster[0].length];
        int[][] bestRoster = currentRoster;
        int i1;
        // minimum = Double.POSITIVE_INFINITY;
        for (i1 = 0; i1 < iterations; i1++) {
            r1 = bestRoster;
            int[][] r2 = shiftRows(r1);

            bestRoster = compareRoster(r1, r2);

        }
        return bestRoster;
    }

    private static int[][] shiftRows(int[][] currentRoster) {
        int min = 0, max = currentRoster.length - 1;
        int[][] roster = new int[currentRoster.length][currentRoster[0].length];

        int rand1 = min + (int) (Math.random() * ((max - min) + 1));
        int rand2 = min + (int) (Math.random() * ((max - min) + 1));
        roster[rand2] = currentRoster[rand1];

        return roster;

    }

    private static int[][] muteer(int[][] roster, int iterations) {
        int[][] r1 = roster;
        int[][] bestRoster = roster;
        int i1;
        // minimum = Double.POSITIVE_INFINITY;
        for (i1 = 0; i1 < 50000; i1++) {
            r1 = bestRoster;
            int[][] r2 = randomMutatie(r1, 100);

            bestRoster = compareRoster(r1, r2);
        }
        return bestRoster;

    }

    private static int[][] randomMutatie(int[][] currentRoster, int iterations) {
        for (int t = 0; t < iterations; t++) {
            int i = (int) Math.random() * currentRoster.length + 1;
            int j = (int) Math.random() * currentRoster[0].length + 1;
            currentRoster[i][j] = (int) (2 * Math.random());
        }
        return currentRoster;
    }

    private static int[][] compareRoster(int[][] r1, int[][] r2) {
        if (Heuristic.getFitness(r1) <= Heuristic.getFitness(r2)) {
            return r1;

        } else {

            return r2;
        }
    }
}
