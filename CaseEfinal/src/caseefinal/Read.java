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
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Viktor
 */
public class Read { // READS ALL VALUES WE CAN READ IN

    public static int nrNurses, nrDays = 28, nrShifts = 5;
    public static int shiftLength = 9;

    public static ArrayList<Nurse> nurses = new ArrayList<>();
    public static ArrayList<Shift> shifts = new ArrayList<>();

    public static int[] cyclicalRosterTypes;   // IN
    public static int[] nurseTypes;            // CONFORM
    public static int nrType1;

    public static int[][] cyclicalRosterEncoded; // OMGEZET
    public static int[][] cyclicalRoster; // ZOALS IN EXCEL FILE

//    SHIFT ENCODING LIKE IN TABLE :
//    0 : EARLY
//            1 : DAY
//                    2: LATE
//                            3: NIGHT
//                                    4: FREE
    public static Department readAll(String fileName, String dep) {

        readShifts(dep);
        System.out.println(shifts.size());
        readNurses(fileName);
        nrNurses = nurses.size();

        System.out.println(Arrays.toString(nurses.get(0).getPrefGiven()));
        readCyclicalRoster(fileName);

        encode(cyclicalRoster); // ZET ONZE MANIER VAN SHIFT NUMMERING OM NAAR ALGEMEEN (ZOALS IN PREF)

        Department d = new Department(dep, nurses, shifts, nrNurses, cyclicalRosterEncoded, cyclicalRoster);

//        for (int i = 0; i < nrNurses; i++) {
//            System.out.println("Assignment" + (i + 1) + ": " + Arrays.toString(d.getCrEncoded()[i]));
//        }
        return d;
    }

    public static void readShifts(String dep) {
        for (int i = 0; i < nrDays; i++) {
            for (int j = 0; j < nrShifts; j++) {
                Shift s = new Shift(i, j, shiftLength, dep);
                shifts.add(s);
            }
        }
    }

    public static void readNurses(String fileName) { // nen file met gwn de code nurse en de empl rate en type
        String code = null;
        int type = 0;
        double emplRate = 0;
        int[] prefAll = new int[shifts.size()];

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                Row currentRow = rowIterator.next();

                Iterator<Cell> cellIterator1 = currentRow.iterator();

                prefAll = readNursePrefAll(fileName, cellIterator1);

                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    if (currentCell.getColumnIndex() == 0) {
                        code = currentCell.getStringCellValue();
                    }
                    if (currentCell.getColumnIndex() == 141) {
                        emplRate = currentCell.getNumericCellValue();
                    }
                    if (currentCell.getColumnIndex() == 142) {
                        type = (int) currentCell.getNumericCellValue();
                    }

                } // ADD NURSE
                Nurse n = new Nurse(code, type, prefAll, emplRate);
                nurses.add(n);

            }
        } catch (IOException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int[] readNursePrefAll(String fileName, Iterator<Cell> cellIterator) {
        int[] pref = new int[140];

        while (cellIterator.hasNext()) {
            Cell currentCell = cellIterator.next();

            if (currentCell.getColumnIndex() != 0 && currentCell.getCellTypeEnum() == CellType.NUMERIC && currentCell.getColumnIndex() <= 140) {
                pref[currentCell.getColumnIndex() - 1] = (int) currentCell.getNumericCellValue();

            }
        }

        return pref;
    }

    public static void readNumberOfNurses(String fileName) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));

            Sheet sheet = xssfWorkbook.getSheetAt(1);

            Iterator<Row> rowIterator = sheet.iterator();

            int currentRowIt = 0;

            if (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                if (currentRowIt == 0) {

                    Iterator<Cell> cellIterator = currentRow.iterator();

                    while (cellIterator.hasNext()) {

                        Cell currentCell = cellIterator.next();

                        if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                            nrNurses = (int) currentCell.getNumericCellValue();

                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Read.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void readCyclicalRoster(String fileName) {
        cyclicalRoster = new int[nrNurses][nrDays];
        cyclicalRosterTypes = new int[nrNurses];
        try {
            // Gehele Assignment inlezen           
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
            Sheet sheet = xssfWorkbook.getSheetAt(1);

            Iterator<Row> rowIterator = sheet.iterator();

            int currentRowIt = 0;

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                if (currentRow.getRowNum() != 0) {
                    //1: Early ,2: Late, 3: Night, 0: Free
                    Iterator<Cell> cellIterator = currentRow.iterator();

                    while (cellIterator.hasNext()) {
                        Cell currentCell = cellIterator.next();
                        if (currentCell.getCellTypeEnum() == CellType.NUMERIC && currentCell.getColumnIndex() < nrDays) {

                            cyclicalRoster[currentRow.getRowNum() - 1][currentCell.getColumnIndex()]
                                    = (int) currentCell.getNumericCellValue();

                        }
                        if (currentCell.getColumnIndex() == nrDays) {
                            cyclicalRosterTypes[currentRow.getRowNum() - 1] = (int) currentCell.getNumericCellValue();
                        }
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Read.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Read.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void encode(int[][] cr) {
        cyclicalRosterEncoded = new int[nrNurses][shifts.size()];
        for (int s = 0; s < shifts.size(); s++) {
            for (int n = 0; n < nrNurses; n++) {
                cyclicalRosterEncoded[n][s] = 0;
            }

        }

        for (int i = 0; i < nrNurses; i++) {
            for (int j = 0; j < nrDays; j++) {

                cyclicalRosterEncoded[i][typeToIndex(cr[i][j], j)] = 1;

            }
        }

    }

    private static int indexToType(int i) {
        switch (i) {
            case 0:
                return 1; //Early
            case 1:
                return 666; //Day DEVIL
            case 2:
                return 2; //Late
            case 3:
                return 3; //Night
            case 4:
                return 0; //Free
        }
        return 0;

    }

    private static int typeToIndex(int i, int j) { //j Day
        switch (i) {
            case 0:
                return 4 + 5 * j; //Free
            case 1:
                return 0 + 5 * j; //Early
            case 2:
                return 2 + 5 * j; //Late
            case 3:
                return 3 + 5 * j; //Night

        }
        return 666; //DEVIL

    }

    public static int[] readNursePrefCyclical(String fileName) {
        int[] pref = new int[(nrDays * nrShifts)];
        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(fileName)));
            Sheet sheet = xssfWorkbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                Row currentRow = rowIterator.next();

                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();

                    if (currentCell.getColumnIndex() != 0 && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        pref[currentCell.getColumnIndex() - 1] = (int) currentCell.getNumericCellValue();

                    }
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pref;

    }
}
