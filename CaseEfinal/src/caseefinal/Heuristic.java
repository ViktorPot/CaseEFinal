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

    public static int nrNurses = 32, nrDays = 28, nrShifts = 5, nrTypes = 2, totalShifts=140, nrNurseType1;
    public static ArrayList<Nurse> nurses;
    public static int[] cyclicalRosterTypes;   // IN // WEGDOEN
    public static int[] nurseTypes;            // CONFORM
    public static int[] totalReq, type1Req;
    public static int[][] cyclicalRoster, cyclicalRosterEncoded; //Roster in format as cyclicalRoster 
    public static int maxWorkingDays = 20;
    public static int PEN_MINCOV = 200, PEN_MINCOV_TYPE_1 = 200,
            // PEN_NURSEDESIRED = 1, PEN_NURSEINDIFF = 5, PEN_NURSEAVERSION = 9,  in compliance with file
            // PEN_LEAVEDAY = 100, in compliance
            PEN_SURPLUS = 50, // 5 per dag genomen
            PEN_CYCLICALROSTER = 10,
            PEN_FAIRNESS = 50,
            WORKDAYSALLOWED = 5, // 5 shifts system ^^
            PEN_MAXSHIFTS = 100,
            PEN_CONSEC = 100,
            PEN_ASSIGNPERTYPE = 0,
            PEN_NONCYCLICAL = 10,
            PEN_SUCCESION = 100;

    public static int[][] roster1, roster2, roster3; // ROSTER SOLUTION

    public static int[][] roster, rosterExtra; // ROSTER SOLUTION

    // MINIMIZE FITNESS!!!
    public static int[][] heuristic(Department dep) {
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
//        System.out.println("Req: " + Arrays.toString(totalReq));
//        System.out.println("ReqType1: " + Arrays.toString(type1Req));

        //roster1 = assignCyclicalRoster(dep);
        System.out.println("CYCLICAL FITNESS: " + getFitness(roster1));
        //System.out.println(getFitness(roster1));
      //  int[][] rosterTest = (MPS(assignmentPMS()));
        int[][] roster2 = muteer(cyclicalRosterEncoded, 5000);
        System.out.println("best roster " + getFitness(roster2));
        //  Solution sol = new Solution(r, nrNurses, dep);
        return roster2;
    }

    /**
     *
     * @param dep
     * @return
     */
    public static int[][] generateRoster(){
   
       
        System.err.println(nrNurses);
        // assign to the cyclical roster
        int[][] newRoster = new int[nrNurses][totalShifts];
        for ( int i = 0; i < nrNurses; i++) {
            
            for (int j = 0; j < totalShifts; j++) {
                if(j%5 ==0 || (j+3)%5 ==0 || (j+2)%5 ==0){
                    Random randomno = new Random();
                     newRoster[i][j] = randomno.nextInt(2);
                     
                }
                else{newRoster[i][j]=0;     
    }}}
    for ( int m = 0; m < nrNurses; m++) {
            
            for (int n = 0; n < totalShifts; n++) {
                if((n+1)%5==0 && newRoster[m][n-1]==0 && newRoster[m][n-2]==0 && newRoster[m][n-4]==0){
                    newRoster[m][n]=1;
                }
                   
                     
                }
               //System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));}
   
        //System.out.println("FITNESS:  "+ Heuristic.getFitness(newRoster));
    }
            return newRoster;}  //Geneereert Random roster door invullen 0/1 op plaatsen E/L/N --> Free wordt obhv genereert
    public static int[][] generateRoster20Shifts(){
        int[][] newRoster = new int[nrNurses][totalShifts];
        for ( int i = 0; i < nrNurses; i++) {
             int countShiftsNurse=0;
           while(countShiftsNurse<20){
               Random rando= new Random();
               int shift= rando.nextInt(139);
               if(shift%5 ==0  || (shift+3)%5 ==0 || (shift+2)%5 ==0){
               newRoster[i][shift]=1;
               countShiftsNurse++;}
               for(int j=0;j<totalShifts;j++){
                   if(newRoster[i][j]!=1){
                       newRoster[i][j]=0;
                   }
               }
           
        }} 
        for ( int m = 0; m < nrNurses; m++) {
            
            for (int n = 0; n < totalShifts; n++) {
                if((n+1)%5==0 && newRoster[m][n-1]==0 && newRoster[m][n-2]==0 && newRoster[m][n-4]==0){
                    newRoster[m][n]=1;
                }
            }
            
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));  
        }
           return newRoster;
    }
    
    public static int[][] generateRosterOneShiftPerDay2(){
        int[][] newRoster = new int[nrNurses][totalShifts];
        for ( int i = 0; i < nrNurses; i++) { 
            int countShiftDay=0;      
            for (int j = 0; j < totalShifts; j++) {
                
                     //System.out.println(countShiftDay); 
                if(newRoster[i][j]>0 ){
//                     System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
                    countShiftDay++;
                    System.out.println("location : shift" + j + "nurse"  + i);
                    System.out.println(countShiftDay);
                    if(countShiftDay==2){
                        newRoster[i][j]=0;
                          //System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
                        
                        
                    }
                }
            } System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));  
        }
        return newRoster;
    }
     // Zorg ervoor dat SOM E,L,N <1 error: early soms nog gevolgd door night...
    public static int[][] generateRosterSuccessionRule(){
        int[][] newRoster = generateRoster();
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 2) % 5; // NIGHT , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && newRoster[j][i] == 1) {//niet gevolgd door early
                    if ((i + 4) < totalShifts) {
                        newRoster[j][i + 2] = 0;
                        newRoster[j][i + 4] = 0;} 

                            
                             //System.out.println("location : shift" + i + "nurse"  + j); 
                        
                    if (i + 4 >= totalShifts) {
                         newRoster[j][0] = 0;
                         newRoster[j][2] = 0;
                    } 
                         //System.out.println("location : shift" + i + "nurse"  + j); 
                    }
                }}
             for ( int m = 0; m < nrNurses; m++) {
            
            for (int n = 0; n < totalShifts; n++) {
                if((n+1)%5==0 && newRoster[m][n-1]==0 && newRoster[m][n-2]==0 && newRoster[m][n-4]==0){
                    newRoster[m][n]=1;
                }
    
                }
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));  
        }
        return newRoster;
    }// Geen E,L na 
    public static int[][] generateRosterConsecutiveRule(){
        int countConsecNurse = 0;
        int[][] newRoster = generateRoster();
        for (int j = 0; j < nrNurses; j++) {
            countConsecNurse=0;
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 1) % 5; //FREE
                if (k == 0 && newRoster[j][i] == 0) {
                    countConsecNurse++;
                    //System.out.println(countConsecNurse);
                    if (countConsecNurse > 5) {
                       newRoster[j][i-1]=0;
                       newRoster[j][i-2]=0;
                       newRoster[j][i-4]=0;
                        // System.out.println("location : shift" + i + "nurse"  + j); 
                    }}
                    if (k == 0 && newRoster[j][i] == 1){
                        countConsecNurse=0;
                    }
//                    
            }}
        for ( int m = 0; m < nrNurses; m++) {
            
            for (int n = 0; n < totalShifts; n++) {
                if((n+1)%5==0 && newRoster[m][n-1]==0 && newRoster[m][n-2]==0 && newRoster[m][n-4]==0){
                    newRoster[m][n]=1;
                }
                }
            System.out.println("Assignment" + (m + 1) + ": " + Arrays.toString(newRoster[m]));  
        }
        return newRoster;
    } // Geen 5x on na elkaar
    public static int[][] generateRosterOneShiftPerDay(){
        int[][] newRoster = generateRoster();
        for ( int i = 0; i < nrNurses; i++) { 
            int countShiftDay=0;
            for(int d =0;d<nrDays;d+=5){
            for (int j = d; j < d+5; d++) {
                 Random rando= new Random();
               int shift= rando.nextInt(139);
               if(shift%5 ==0  || (shift+3)%5 ==0 || (shift+2)%5 ==0){
               newRoster[i][shift]=1;
             
               }
                if(j%5==0){
                    countShiftDay=0;
                     //System.out.println(countShiftDay); 
                }
                if(newRoster[i][j]>0 ){
//                     System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
                    countShiftDay++;
                    System.out.println("location : shift" + j + "nurse"  + i);
                    System.out.println(countShiftDay);
                    if(countShiftDay==2){
                        newRoster[i][j]=0;
                          //System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));
                    }
                }
            } System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(newRoster[i]));  
        }
        
    }
        return newRoster;
    }
    
    
//    public static int[][] generateRosterMinCov(){
//        double pen1 = 0;
//        double penTotal = 0;
//
//        int sum1[] = new int[totalShifts];
//        int sumTotal[] = new int[totalShifts];
//        int diff1[] = new int[totalShifts];
//        int diffTotal[] = new int[totalShifts];
//
//        for (int j = 0; j < totalShifts; j++) {
//            for (int i = 0; i < nrNurses; i++) {
//                if (i < 6 && roster[i][j] == 1) {
//                    sum1[j] += 1;
//                    sumTotal[j] += 1;
//                } else if (roster[i][j] == 1) {
//                    sumTotal[j] += 1;
//                }
//            }
//            diff1[j] = Math.max(0, type1Req[j] - sum1[j]);
//            diffTotal[j] = Math.max(0, totalReq[j] - sumTotal[j]);
//            pen1 += diff1[j];
//            penTotal += diffTotal[j];
//        }
//        double pen = (pen1 * PEN_MINCOV_TYPE_1 + penTotal * PEN_MINCOV);
//
//        System.out.println("MINCOV : " + pen);
//
//        return pen;
//    }
    
            
       

   
        
    
    

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
    }

    private static int[][] muteer(int[][] roster, int iterations) {
        int[][] r1= roster;
        int[][] bestRoster = roster;
        int i1;
        // minimum = Double.POSITIVE_INFINITY;
        for (i1 = 0; i1 < 50000; i1++) {
            r1 = bestRoster;
            int[][] r2 = randomMutatie(r1);

            bestRoster = compareRoster(r1, r2);
        }
        return bestRoster;

    }

    private static int[][] randomMutatie(int[][] roster) {
        for(int t =0; t < 140; t++){
        int i = (int) Math.random() * nrNurses + 1;
        int j = (int) Math.random() * totalShifts + 1;
        roster[i][j] = (int) (2 * Math.random());
        }
        return roster;
    }

    private static int[][] shifting(int[][] currentRoster, int iterations) {
        int[][] r1 = new int[nrNurses][totalShifts];
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

    private static int[][] compareRoster(int[][] r1, int[][] r2) {
        if (getFitness(r1) <= getFitness(r2)) {
            return r1;

        } else {

            return r2;
        }
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

    public static double getFitness(int[][] roster) {
        double fitness = 0;
        fitness += pen_PrefAll(roster);

        fitness += pen_MinCov(roster); // is per type berekend
        fitness += pen_Surplus(roster, depUsed); // is per type berekend

        fitness += pen_Fairness(roster, depUsed);
        fitness += pen_consecShifts(roster, depUsed);
        fitness += pen_maxShifts(roster, depUsed);
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
        //  System.out.println("Pref :" + pen);
        return pen * 2;
    }

       private static double pen_Surplus(int[][] roster, Department dep) { // GEEFT NIET DE JUISTE WEER
        double pen1 = 0; // NIGHT , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
        double penTotal = 0;

        int sum1[] = new int[totalShifts];
        int sumTotal[] = new int[totalShifts];
        int diff1[] = new int[totalShifts];
        int diffTotal[] = new int[totalShifts];

        for (int j = 0; j < totalShifts; j++) {
            if ((j + 1) % 5 != 0 && j != totalShifts - 1) {
                for (int i = 0; i < nrNurses; i++) {
                    if (i < 6 && roster[i][j] == 1) {
                        sum1[j] += 1;
                        sumTotal[j] += 1;
                    } else if (roster[i][j] == 1) {
                        sumTotal[j] += 1;
                    }
                }
                diff1[j] = Math.max(0, sum1[j] - type1Req[j]);
                diffTotal[j] = Math.max(0, sumTotal[j] - totalReq[j]);
                pen1 += diff1[j];
                penTotal += diffTotal[j];
            } else {
                diff1[j] = 0;
                diffTotal[j] = 0;
            }
        }
        double pen = (pen1 * PEN_MINCOV_TYPE_1 + penTotal * PEN_MINCOV);
        System.out.println("SURPLUS : " + (penTotal * PEN_MINCOV));
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

        System.out.println("MINCOV : " + pen);

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
        System.out.println("PEN FAIRNESS : " + pen * PEN_FAIRNESS);
        return pen * PEN_FAIRNESS;
    }

    private static double pen_succesionShifts(int[][] roster, Department dep) {//Nog LATE toevoegen!!
        int pen = 0;

        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 2) % 5; // NIGHT , (i+3) % 5 LATE, (i+4) % 5 DAY , (i) % 5 EARLY, (i+1)% FREE
                if (k == 0 && roster[j][i] == 1) {//niet gevolgd door early
                    if ((i + 2) < totalShifts) {
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
        }for (int j = 0; j < nrNurses; j++) {
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
        System.out.println("SUCCESIVE SHIFTS " + pen);
        return pen;
    }

    private static double pen_consecShifts(int[][] roster, Department dep) {
        int nrOfTooMuchConsec = 0;
        int pen = 0;
        int countConsecNurse = 0;
        for (int j = 0; j < nrNurses; j++) {
            countConsecNurse=0;
            for (int i = 0; i < totalShifts; i++) {
                double k = (i + 1) % 5; //FREE
                if (k == 0 && roster[j][i] == 0) {
                    countConsecNurse++;
                    //System.out.println(countConsecNurse);
                    if (countConsecNurse > 5) {
                        nrOfTooMuchConsec++;
                         System.out.println("location : shift" + i + "nurse"  + j); 
                    }}
                    if (k == 0 && roster[j][i] == 1){
                        countConsecNurse=0;
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
            }}
        pen = nrOfTooMuchConsec * PEN_CONSEC;
        System.out.println("CONSECUTIVE SHIFTS " + pen);
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
        System.out.println("MAX NR OF SHIFTS PENALTY: " + pen);
        return pen;
    }

    private static double pen_nonCyclical(int[][] roster, Department dep) {
        int pen = 0;
        for (int j = 0; j < nrNurses; j++) {
            for (int i = 0; i < totalShifts; i++) {
              if (roster[j][i] != cyclicalRosterEncoded[j][i]){
                
                pen+=PEN_NONCYCLICAL;
            }
       
    }} System.out.println("PENALTY NONCYCLICAL: "+ pen);
        return pen;
    }
}


//         private static void encode(int[][] cr, int[] crt, String dep) {
//        cyclicalRoster = new int[nrNurses][shifts.size()];
//        for (int s = 0; s < shifts.size(); s++) {
//            for (int n = 0; n < nrNurses; n++) {
//                cyclicalRoster[n][s] = 0;
//            }
//
//        }
//
//        for (int i = 0; i < nrNurses; i++) {
//            for (int j = 0; j < nrDays; j++) {
//
//                cyclicalRoster[i][typeToIndex(cr[i][j], j)] = 1 * crt[i];
//
//            }
//        }
//
//    }
//
//    private static int indexToType(int i) {
//        switch (i) {
//            case 0:
//                return 1; //Early
//            case 1:
//                return 666; //Day DEVIL
//            case 2:
//                return 2; //Late
//            case 3:
//                return 3; //Night
//            case 4:
//                return 0; //Free
//        }
//        return 0;
//
//    }
//
//    private static int typeToIndex(int i, int j) { //j Day
//        switch (i) {
//            case 0:
//                return 4 + 5 * j; //Free
//            case 1:
//                return 0 + 5 * j; //Early
//            case 2:
//                return 2 + 5 * j; //Late
//            case 3:
//                return 3 + 5 * j; //Night
//
//        }
//        return 3;
//
//    }
