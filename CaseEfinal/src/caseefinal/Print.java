/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Viktor
 */
public class Print {

    public static void printRoster(int[][] roster, String filename) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        XSSFSheet sheet = workbook.createSheet("FITNESS = " + Heuristic.getFitness(roster));

        for (int i = 0; i < roster.length; i++) {

            Row row = sheet.createRow(i);

            for (int j = 0; j < roster[0].length; j++) {
                Cell cell = row.createCell(j);

                cell.setCellValue(roster[i][j]);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
