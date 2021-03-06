/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import NotNeeded.Preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Viktor
 */
public class Heuristic {

    public static Department depUsed;
//Hallo!!
    public static int nrNurses = 32, nrDays = 28, nrShifts = 5, nrTypes = 2, totalShifts = 140, nrNurseType1;
    public static ArrayList<Nurse> nurses;
    public static int[] cyclicalRosterTypes;   // IN // WEGDOEN
    public static int[] nurseTypes;            // CONFORM
    public static int[] totalReq, type1Req;
    public static int[][] cyclicalRoster, cyclicalRosterEncoded; //Roster in format as cyclicalRoster 
    public static int maxWorkingDays = 20;
    public static int PEN_MINCOV = 200, PEN_MINCOV_TYPE_1 = 200,
            // PEN_NURSEDESIRED = 1, PEN_NURSEINDIFF = 5, PEN_NURSEAVERSION = 9,  in compliance with file
            // PEN_LEAVEDAY = 100, in compliance
            PEN_SURPLUS = 20, // 5 per dag genomen
            PEN_CYCLICALROSTER = 2,
            PEN_FAIRNESS = 50,
            WORKDAYSALLOWED = 5, // 5 shifts system ^^
            PEN_MAXSHIFTS = 100,
            PEN_CONSEC = 100,
            PEN_ASSIGNPERTYPE = 0,
            PEN_NONCYCLICAL = 10,
            PEN_SUCCESION = 100;

    public static int[][] roster1, roster2, roster3; // ROSTER SOLUTION

    public static int[][] roster, rosterExtra; // ROSTER SOLUTION

    public static ArrayList<Individual> population;

    // MINIMIZE FITNESS!!!
    public static int[][] heuristicArray(Department dep) {
        depUsed = dep;
        cyclicalRosterEncoded = dep.getCrEncoded(); // int (type) [nrNurses][Shifts]
        cyclicalRoster = dep.getCyclicalRoster();
        totalReq = dep.getTotalReq();
        totalShifts = dep.getTotalShifts();
        nrNurseType1 = dep.getNrType1();
        nurses = dep.getNurses();
        type1Req = dep.getType1Req();

        // SET NURSE REQ per SHIFT
        // ALLE WAARDEN TOEKENNEN
        //System.out.println("CYCLICAL FITNESS: " + getFitness(cyclicalRosterEncoded));
        //populatePopulation(nrShifts);
        getFitness(cyclicalRosterEncoded);
        roster1 = generateRosterMinCovPreference(dep);
        getFitness(roster1);
        //fixRoster(roster1);
//        getFitness(newRoster);
        //System.out.println(getFitness(roster1));
        //  int[][] rosterTest = (MPS(assignmentPMS()));
        //populatePopulation(20);
        //System.out.println("best roster " + getFitness(roster2));
        //  Solution sol = new Solution(r, nrNurses, dep);
        //GA.execute(population);
        return roster1;
    }

    public static ArrayList<Individual> heuristicPop(Department dep) {
        depUsed = dep;
        cyclicalRosterEncoded = dep.getCrEncoded(); // int (type) [nrNurses][Shifts]
        cyclicalRoster = dep.getCyclicalRoster();
        totalReq = dep.getTotalReq();
        totalShifts = dep.getTotalShifts();
        nrNurseType1 = dep.getNrType1();
        nurses = dep.getNurses();
        type1Req = dep.getType1Req();
        System.out.println("CYCLICAL FITNESS: " + getFitness(cyclicalRosterEncoded));
        ArrayList<Individual> pop = populatePopulation(20);
        return pop;
    }

    public static int countNrOfShiftsAssingedNurse(int[][] currentRoster, int nurse) {// telt voor een bepaalde nurse met een bepaald roster het aantal assingde shifts
        int nrOfShifts = 0;
        for (int i = 0; i < totalShifts; i++) {
            if (currentRoster[nurse][i] == 1) {
                nrOfShifts++;
            }
        }
        return nrOfShifts;
    }

    /**
     *
     * @param dep
     * @return
     */
    public static int[][] fixRoster(int[][] roster) {
        int[][] result = new int[nrNurses][totalShifts];
        for (int i = 0; i < nrNurses; i++) {
            for (int j = 0; j < totalShifts; j++) {
                result[i][j] = roster[i][j];
                if ((j + 4) % 5 == 0) {
                    result[i][j] = 0;
                }
                if ((j + 1) % 5 == 0 && (roster[i][j - 1] == 1 || roster[i][j - 2] == 1 || roster[i][j - 4] == 1)) {
                    result[i][j] = 0;
                }
                if ((j + 1) % 5 == 0 && roster[i][j - 1] == 0 && roster[i][j - 2] == 0 && roster[i][j - 4] == 0) {// ALS 1 bij E,L,N => Free = 0 EN OMGEKEERD
                    result[i][j] = 1;
                }
            }
            System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(result[i]));
        }
        return result;
    }

    public static ArrayList<Individual> populatePopulation(int Iterations) {
        ArrayList<Individual> population = new ArrayList<Individual>();

        for (int r = 0; r < 50; r++) {

            int[][] newRoster1 = new int[nrNurses][totalShifts]; //ADD CYCLICAL
            newRoster1 = depUsed.getCrEncoded();
            Individual ind = new Individual(newRoster1);
            population.add(ind);
        }
        //System.out.println("Value: )"+ ind.toString());}
        for (int i = 0; i < 1; i++) {//ADD PREFERENCES 50
            int[][] newRoster2 = new int[nrNurses][totalShifts];
            newRoster2 = generateRosterBasedOnPreference(depUsed);
            Individual ind2 = new Individual(newRoster2);
            population.add(ind2);
        }
        //System.out.println("Value: )"+ ind2.toString());}
//        for(int k=0; k<40;k++){// ADD ONE/DAY 20
//            int[][] newRoster3 = new int[nrNurses][totalShifts];
//            newRoster3=generateRosterOneShiftPerDay();
//            Individual ind3 = new Individual(newRoster3);
//            population.add(ind3);
//        }

        //System.out.println("Value: )"+ ind3.toString());
        //  System.out.println("Value: )" + ind.toString());
        return population;
    }

    public static int[][] generateRoster() {

        System.err.println(nrNurses);
        // assign to the cyclical roster
        int[][] newRoster = new int[nrNurses][totalShifts];
        for (int i = 0; i < nrNurses; i++) {

            for (int j = 0; j < totalShifts; j++) {
                if (j % 5 == 0 || (j + 3) % 5 == 0 || (j + 2) % 5 == 0) {
                    Random randomno = new Random();
                    newRoster[i][j] = randomno.nextInt(2);

                } else {
                    newRoster[i][j] = 0;
                }
            }
        }
        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {
                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }

            }
               //System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));}

            //System.out.println("FITNESS:  "+ Heuristic.getFitness(newRoster));
        }
        return newRoster;
    }  //Geneereert Random roster door invullen 0/1 op plaatsen E/L/N --> Free wordt obhv genereert

    public static int[][] generateRoster20Shifts() {
        int[][] newRoster = new int[nrNurses][totalShifts];
        for (int i = 0; i < nrNurses; i++) {
            int countShiftsNurse = 0;
            while (countShiftsNurse < 20) {
                Random rando = new Random();
                int shift = rando.nextInt(139);
                if (shift % 5 == 0 || (shift + 3) % 5 == 0 || (shift + 2) % 5 == 0) {
                    newRoster[i][shift] = 1;
                    countShiftsNurse++;
                }
                for (int j = 0; j < totalShifts; j++) {
                    if (newRoster[i][j] != 1) {
                        newRoster[i][j] = 0;
                    }
                }

            }
        }
        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {
                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }
            }

            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));
        }
        return newRoster;
    }

    public static int[][] generateRosterOneShiftPerDay2() {
        int[][] newRoster = new int[nrNurses][totalShifts];
        for (int i = 0; i < nrNurses; i++) {
            int countShiftDay = 0;
            for (int j = 0; j < totalShifts; j++) {

                //System.out.println(countShiftDay); 
                if (newRoster[i][j] > 0) {
//                     System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
                    countShiftDay++;
                    System.out.println("location : shift" + j + "nurse" + i);
                    System.out.println(countShiftDay);
                    if (countShiftDay == 2) {
                        newRoster[i][j] = 0;
                        //System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));

                    }
                }
            }
            System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
        }
        return newRoster;
    }

    // Zorg ervoor dat SOM E,L,N <1 error: early soms nog gevolgd door night...
    public static int[][] generateRosterSuccessionRule() {
        int[][] newRoster = generateRoster();
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 2) % 5; // NIGHT , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && newRoster[j][i] == 1) {//niet gevolgd door early
                    if ((i + 4) < totalShifts) {
                        newRoster[j][i + 2] = 0;
                        newRoster[j][i + 4] = 0;
                    }

                    //System.out.println("location : shift" + i + "nurse"  + j); 
                    if (i + 4 >= totalShifts) {
                        newRoster[j][0] = 0;
                        newRoster[j][2] = 0;
                    }
                    //System.out.println("location : shift" + i + "nurse"  + j); 
                }
            }
        }
        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {
                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }

            }
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));
        }
        return newRoster;
    }// Geen E,L na 

    public static int[][] generateRosterConsecutiveRule() {
        int countConsecNurse = 0;
        int[][] newRoster = generateRoster();
        for (int j = 0; j < nrNurses; j++) {
            countConsecNurse = 0;
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 1) % 5; //FREE
                if (k == 0 && newRoster[j][i] == 0) {
                    countConsecNurse++;
                    //System.out.println(countConsecNurse);
                    if (countConsecNurse > 5) {
                        newRoster[j][i - 1] = 0;
                        newRoster[j][i - 2] = 0;
                        newRoster[j][i - 4] = 0;
                        // System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
                if (k == 0 && newRoster[j][i] == 1) {
                    countConsecNurse = 0;
                }
//                    
            }
        }
        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {
                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }
            }
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));
        }
        return newRoster;
    } // Geen 5x on na elkaa

    public static int[][] generateRosterOneShiftPerDay() {
        int[][] newRoster = new int[nrNurses][totalShifts];
        for (int i = 0; i < nrNurses; i++) {
            for (int d = 0; d < totalShifts; d += 5) {
                int nrOfShifts = 0;
                while (nrOfShifts < 1) {
                    Random rando = new Random();
                    int shift = rando.nextInt(5);
                    if (shift % 5 == 0 || (shift + 3) % 5 == 0 || (shift + 2) % 5 == 0) {
                        newRoster[i][d + shift] = 1;
                        nrOfShifts++;
                    }
                }
            }
            //System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));  

        }
        for (int m = 0; m < nrNurses; m++) {
            for (int n = 0; n < totalShifts; n++) {
                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }
            }
        }

        //System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));}
        return newRoster;
    }
// genereert Roster met 28 dagen een shift

    public static int[][] generateRosterOneShiftPerDay20Shifts() {
        int[][] newRoster = generateRosterOneShiftPerDay();
        for (int i = 0; i < nrNurses; i++) {
            int countShiftsNurse = 0;
            countShiftsNurse += countNrOfShiftsAssingedNurse(newRoster, i);
            while (countShiftsNurse > 20) {
                Random rando = new Random();
                int shift = rando.nextInt(139);
                if (shift % 5 == 0 || (shift + 3) % 5 == 0 || (shift + 2) % 5 == 0) {
                    if (newRoster[i][shift] == 1) {
                        newRoster[i][shift] = 0;
                        countShiftsNurse--;
                    }
                }
            }
        }
        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {

                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;

                    //System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));}
                }

            }
        }
        return newRoster;
    }// genereert Roster met 20 dagen 1 shift

    public static int[][] generateRosterBasedOnPreference(Department d) {

        int[][] newRoster = new int[nrNurses][totalShifts];

        for (int i = 0; i < nrNurses; i++) {
            for (int j = 0; j < totalShifts; j++) {
                if (d.getNurses().get(i).getPrefGiven()[j] < 11) {
                    newRoster[i][j] = 1;
                } else {
                    newRoster[i][j] = 0;
                }
            }
        }

        for (int m = 0; m < nrNurses; m++) {

            for (int n = 0; n < totalShifts; n++) {

                if ((n + 1) % 5 == 0 && newRoster[m][n - 1] == 0 && newRoster[m][n - 2] == 0 && newRoster[m][n - 4] == 0) {
                    newRoster[m][n] = 1;
                }
            }
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));
        }

        return newRoster;
    }


    public static int[][] generateRosterMinCovPreference(Department d) {
        int[][] newRoster = new int[nrNurses][totalShifts];
        for (int i = 0; i < totalShifts; i++) {
            int countShifts = 0;
            while (countShifts < totalReq[i]) {
                Random rando = new Random();
                int nurse = rando.nextInt(nrNurses);
                if (i % 5 == 0 || (i + 3) % 5 == 0 || (i + 2) % 5 == 0) {
                    if (d.getNurses().get(nurse).getPrefGiven()[i] < 15) {
                        if (newRoster[nurse][i] == 0) {
                            newRoster[nurse][i] = 1;
                            countShifts++;
                            //System.out.println("count: " +countShifts);
                        }
                    }
                }
            }
        }
        newRoster = fixRoster(newRoster);
        for (int i = 0; i < nrNurses; i++) {
            //for (int s = 0; s < totalShifts; s++) {}
            //System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
        }
        return newRoster;
    }

    public static int[][] compareRoster(int[][] r1, int[][] r2) {
        if (getFitness(r1) <= getFitness(r2)) {
            return r1;

        } else {

            return r2;
        }
    }

    public static double getFitness(int[][] roster) {
        double fitness = 0;
        fitness += pen_PrefAll(roster);
        System.out.println("Preference: " + pen_PrefAll(roster));
        fitness += pen_MinCov(roster); // is per type berekend
        System.out.println("MinCov " + pen_MinCov(roster));
        fitness += pen_Surplus(roster, depUsed); // is per type berekend
        System.out.println("Surplus: " + pen_Surplus(roster, depUsed));
        fitness += pen_Fairness(roster, depUsed);
        System.out.println("Fairness: " + pen_Fairness(roster, depUsed));
        fitness += pen_consecShifts(roster, depUsed);
        System.out.println("consecShifts: " + pen_consecShifts(roster, depUsed));
        fitness += pen_succesionShiftsNight(roster, depUsed);
        System.out.println("Sucession Night: " + pen_succesionShiftsNight(roster, depUsed));
        fitness += pen_succesionShiftsLate(roster, depUsed);
        System.out.println("Sucession Late: " + pen_succesionShiftsLate(roster, depUsed));
        fitness += pen_succesionShiftsEarly(roster, depUsed);
        System.out.println("Sucession Early: " + pen_succesionShiftsEarly(roster, depUsed));
        fitness += pen_maxShifts(roster, depUsed);
        System.out.println("MaxShift: " + pen_maxShifts(roster, depUsed));
        System.out.println(Arrays.toString(count_sumDay(roster, depUsed)[0]));
        System.out.println("FITNESS: " + fitness);
        return fitness;

    }

    private static double pen_PrefAll(int[][] roster) {
        double pen = 0;
        for (int i = 0; i < nrNurses; i++) {
            for (int j = 0; j < totalShifts; j++) {
                if (roster[i][j] != 0) {
                    pen += depUsed.getNurses().get(i).getPrefGiven()[j];
                }
            }

        }
        //System.out.println("Pref :" + pen);
        return pen;
    }

    private static double pen_Surplus(int[][] roster, Department dep) {
        double penTotal = 0;
        int sumTotal[] = new int[totalShifts];
        int diffTotal[] = new int[totalShifts];

        for (int j = 0; j < totalShifts; j++) {
            for (int i = 0; i < nrNurses; i++) {
                if (roster[i][j] == 1 && (j + 1) % 5 != 0) {
                    sumTotal[j] += 1;
                }
            }
            diffTotal[j] = Math.max(0, sumTotal[j] - totalReq[j]);
            penTotal += diffTotal[j];
        }
        double pen = (penTotal * PEN_MINCOV);

//        System.out.println("MINCOV : " + pen);
        return pen;
    }

    private static double pen_MinCov(int[][] roster) {
        double pen1 = 0;
        double penTotal = 0;

        int sum1[] = new int[totalShifts];
        int sumTotal[] = new int[totalShifts];
        int diff1[] = new int[totalShifts];
        int diffTotal[] = new int[totalShifts];

        for (int j = 0; j < totalShifts; j++) {
            for (int i = 0; i < nrNurses; i++) {
                if (i < 6 && roster[i][j] == 1) {
                    sum1[j] += 1;
                    sumTotal[j] += 1;
                } else if (roster[i][j] == 1) {
                    sumTotal[j] += 1;
                }
            }
            diff1[j] = Math.max(0, type1Req[j] - sum1[j]);
            diffTotal[j] = Math.max(0, totalReq[j] - sumTotal[j]);
            pen1 += diff1[j];
            penTotal += diffTotal[j];
        }
        double pen = (pen1 * PEN_MINCOV_TYPE_1 + penTotal * PEN_MINCOV);

//        System.out.println("MINCOV : " + pen);
        return pen;
    }

    private static double pen_Fairness(int[][] roster, Department dep) { // 20 dagen max, 0 dagen minimum
        double pen = 0;
        int[] sum = new int[nrNurses];
        double[] empl = new double[nrNurses];
        ArrayList<Nurse> n = dep.getNurses();
        for (int i = 0; i < nrNurses; i++) {
            for (int j = 0; j < totalShifts; j++) {
                if (roster[i][j] != 0) {
                    sum[i] += 1;
                }
            }
            empl[i] = sum[i] / maxWorkingDays;
            pen += Math.abs(empl[i] - n.get(i).getEmplRate());
        }
        //System.out.println("PEN FAIRNESS : " + pen * PEN_FAIRNESS);
        return pen * PEN_FAIRNESS;
    }

    private static double pen_succesionShiftsNight(int[][] roster, Department dep) {//Nog LATE toevoegen!!
        int pen = 0;

        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 2) % 5; // NIGHT , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && roster[j][i] == 1) {//niet gevolgd door early // ALS NIGHT SHIFT =1
                    if ((i + 2) < totalShifts) { // Binnen bereik
                        if (roster[j][i + 2] == 1) {

                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 
                        }
                    }
                    if (i + 2 >= totalShifts && roster[j][0] == 1) {
                        pen += PEN_SUCCESION;
                        //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
            }
        }
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 2) % 5; // NIGHT 
                if (k == 0 && roster[j][i] == 1) {
                    if ((i + 4) < totalShifts) {//niet gevolgd door Late
                        if (roster[j][i + 4] == 1) {

                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 

                        }
                    }
                    if (i + 4 >= totalShifts && roster[j][2] == 1) {
                        pen += PEN_SUCCESION;
                        //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
            }
        }
//        System.out.println("SUCCESIVE SHIFTS " + pen);
        return pen;
    }

    private static double pen_succesionShiftsEarly(int[][] roster, Department dep) {//Nog LATE toevoegen!!
        int pen = 0;

        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i) % 5; // EARLY , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && roster[j][i] == 1) {//niet gevolgd door early
                    if ((i + 2) < totalShifts) {
                        if (roster[j][i + 2] == 1) {

                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 
                        }
                    }
//                    if (i + 2 >= totalShifts && roster[j][0] == 1) {
//                        pen += PEN_SUCCESION;
//                        //System.out.println("location : shift" + i + "nurse"  + j); 
//                    }
                }
            }
        }
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i) % 5; // Early 
                if (k == 0 && roster[j][i] == 1) {
                    if ((i + 3) < totalShifts) {//niet gevolgd door Late
                        if (roster[j][i + 3] == 1) {
                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 
                        }
                    }
//                    if (i + 4 >= totalShifts && roster[j][2] == 1) {
//                        pen += PEN_SUCCESION;
//                        //System.out.println("location : shift" + i + "nurse"  + j); 
//                    }
                }
            }
        }
//        System.out.println("SUCCESIVE SHIFTS " + pen);
        return pen;
    }

    private static double pen_succesionShiftsLate(int[][] roster, Department dep) {//Nog LATE toevoegen!!
        int pen = 0;

        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 3) % 5; // Late , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && roster[j][i] == 1) {//niet gevolgd door Night
                    if ((i + 1) < totalShifts) {
                        if (roster[j][i + 1] == 1) {

                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 
                        }
                    }
                    if (i + 1 >= totalShifts && roster[j][0] == 1) {
                        pen += PEN_SUCCESION;
                        //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
            }
        }
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 3) % 5; // LATE 
                if (k == 0 && roster[j][i] == 1) {
                    if ((i + 3) < totalShifts) {//niet gevolgd door Early
                        if (roster[j][i + 3] == 1) {
                            pen += PEN_SUCCESION;
                            //System.out.println("location : shift" + i + "nurse"  + j); 
                        }
                    }
                    if (i + 3 >= totalShifts && roster[j][0] == 1) {
                        pen += PEN_SUCCESION;
                        //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
            }
        }
//        System.out.println("SUCCESIVE SHIFTS " + pen);
        return pen;
    }

    private static double pen_consecShifts(int[][] roster, Department dep) {
        int nrOfTooMuchConsec = 0;
        int pen = 0;
        int countConsecNurse = 0;
        for (int j = 0; j < nrNurses; j++) {
            countConsecNurse = 0;
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 1) % 5; //FREE
                if (k == 0 && roster[j][i] == 0) {
                    countConsecNurse++;
                    //System.out.println(countConsecNurse);
                    if (countConsecNurse > 5) {
                        nrOfTooMuchConsec++;

                        //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }
                if (k == 0 && roster[j][i] == 1) {
                    countConsecNurse = 0;

                    //System.out.println("location : shift" + i + "nurse" + j);
                }
                if (k == 0 && roster[j][i] == 1) {
                    countConsecNurse = 0;
                }

//                     if (k == 0 && roster[j][i] == 0 && i== totalShifts-1){// Als op laatste dag monthly roster gewerkt wordt=> Kijken naar eerste dag vd maand.
//                         i=4;
//                         while(roster[j][i]==0){
//                             countConsecNurse++;
//                             i+=5;
//                             if (countConsecNurse > 5) {
//                              nrOfTooMuchConsec++;
//                             
//                         }
//                            
//                     } 
//                }
//            }}
            }
        }

        pen = nrOfTooMuchConsec * PEN_CONSEC;
//        System.out.println("CONSECUTIVE SHIFTS " + pen);
        return pen;
    }

    private static double pen_maxShifts(int[][] roster, Department dep) {
        int countShiftsNurse = 0;
        int countShiftsSurplus = 0;
        int pen = 0;
        for (int j = 0; j < nrNurses; j++) {
            countShiftsNurse = 0;
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 1) % 5;//Free shift
                if (k != 0 && roster[j][i] == 1) {
                    countShiftsNurse++;

                    if (countShiftsNurse > 20) {
                        countShiftsSurplus++;
                    }
                }
            }
        }
        pen = countShiftsSurplus * PEN_MAXSHIFTS;
//        System.out.println("MAX NR OF SHIFTS PENALTY: " + pen);
        return pen;
    }

    private static int[][] count_sumDay(int[][] roster, Department dep) {
        int[][] sum = new int[nrNurses][nrDays];
        for (int j = 0; j < totalShifts; j++) {
            for (int i = 0; i < nrNurses; i++) {

                double k = (j) % 5;

                if (k == 0) {
                    for (int s = 0; s < nrShifts -1; s++) {
                
                            sum[i][j / 5] += roster[i][j + s];
                        
                    }
                }

            }

        }
        return sum;
    }

    private static double pen_nonCyclical(int[][] roster, Department dep) {
        int pen = 0;
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                if (roster[j][i] != cyclicalRosterEncoded[j][i]) {

                    pen += PEN_NONCYCLICAL;
                }

            }
        }
//        System.out.println("PENALTY NONCYCLICAL: " + pen);
        return pen;
    }
}



