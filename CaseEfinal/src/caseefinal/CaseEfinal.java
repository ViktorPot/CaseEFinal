/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Viktor
 */
public class CaseEfinal {

    static int[][] roster;
    public static int benchmark = 80000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("ENTER DEP LETTER (A,B,C,D)");
        Department d = Read.readAll("files/depA.xlsx", "A");
       
              
//Heuristic.generateRoster();
        //Heuristic.generateRoster20Shifts();
        //roster = Heuristic.heuristic(d);
//roster=Heuristic.generateRosterOneShiftPerDay20Shifts();
         Heuristic.heuristicArray(d);
        //Print.printRoster(roster, "files/FormatDepA.xlsx");

        // Print.printRoster(roster, "files/FormatDepA.xlsx");
//        ArrayList<Individual> myPop = Heuristic.heuristicPop(d);
//        int generationCount = 0;
//        for (int i = 0; i < 250; i++) { // cyclical roster pref
//
//            generationCount++;
//            System.out.println("Generation: " + generationCount + " Fittest: " + Algo.getFittest(myPop));
//            myPop = Algo.evolvePopulation(myPop);
//            for (Individual j : myPop) {
//                System.out.println(j);
//            }
//        }
//        System.out.println("done");
//        roster = Algo.getFittest(myPop).getRoster();
//        Print.printRoster(roster, "files/FormatDepA.xlsx");
//    }
//
    }
}
