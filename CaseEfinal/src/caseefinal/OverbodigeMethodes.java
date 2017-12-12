/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import static caseefinal.Heuristic.compareRoster;
import static caseefinal.Heuristic.nrNurses;
import static caseefinal.Heuristic.totalShifts;

/**
 *
 * @author degraeveha
 */
public class OverbodigeMethodes {
    
    public static int[][] MPS(int[][] currentRoster) {
        int[][] r1 = new int[nrNurses][totalShifts];
        int[][] bestRoster = currentRoster;
        int i1;
        // minimum = Double.POSITIVE_INFINITY;
        for (i1 = 0; i1 < 1000; i1++) {
            r1 = bestRoster;

            int[][] r2 = assignmentPMS();

            bestRoster = compareRoster(r1, r2);

        }
        return bestRoster;
//        for (int m = 0; m < nrNurses; m++) {
//            for (int n = 0; n < totalShifts; n++) {
//                if((n+1)%5==0 && newRoster[m][n-1]==0 && newRoster[m][n-2]==0 && newRoster[m][n-4]==0){
//                    newRoster[m][n]=1;
//                }}}
//                   
//                     //System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));}
//        return newRoster;
//    }
    }
       private static int[][] shifting(int[][] currentRoster, int iterations) {
        int[][] r1 = new int[nrNurses][totalShifts];
        int[][] bestRoster = currentRoster;
        int i1;
        // minimum = Double.POSITIVE_INFINITY;
        for (i1 = 0; i1 < iterations; i1++) {
            r1 = bestRoster;
            int[][] r2 = shiftRows(r1);

            bestRoster = Heuristic.compareRoster(r1, r2);

        }
        return bestRoster;

    }
    private static int[][] assignmentPMS() { //TABATA
        int[][] roster = new int[nrNurses][totalShifts];
        int i, j, n, s, min = 0, max = 1;
        int[] som = new int[totalShifts];

        for (i = 0; i < nrNurses; i++) {
            for (j = 0; j < totalShifts; j++) {
                roster[i][j] = (int) (2 * Math.random());

            }

        }

//        for (j = 0; j < totalShifts; j++) {
//            for (i = 0; i < nrNurses; i++) {
//                if ((j + 4) % 5 != 0 && (j + 1) % 5 != 0 &&) {
//                    roster[i][j] = min + (int) Math.round(Math.random());
//                }
//                if ((j + 4) % 5 == 0) {
//                    roster[i][j] = 0;
//                }
//            }
//        }
        return roster;
    }

    private static int[][] shiftRows(int[][] r) {
        int min = 0, max = nrNurses - 1;
        int[][] roster = new int[nrNurses][totalShifts];

        int rand1 = min + (int) (Math.random() * ((max - min) + 1));
        int rand2 = min + (int) (Math.random() * ((max - min) + 1));
        roster[rand2] = r[rand1];

        return roster;

    }
}
