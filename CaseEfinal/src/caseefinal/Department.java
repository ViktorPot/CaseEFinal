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
public class Department {

    public String name; // ID department
    public ArrayList<Nurse> nurses;
    public Nurse[] nursesArray;

    public ArrayList<Shift> shifts;
    public int totalShifts;
    public int nrNurses, nrTypes, nrdays, nrType1;
    public int[][] crEncoded; // GEEFT OP ELKE PLAATS de type nurse weer voor shifts
    public int[] totalReq, type1Req;
    public int[][] cyclicalRoster;

//    public Department(String depId) {
//        this.name = depId;
//        this.nurses = new ArrayList<>();
//        this.shifts = new ArrayList<>();;
//    }
    public Department(String name, ArrayList<Nurse> nurses, ArrayList<Shift> shifts,
            int nrNurses, int[][] crEncoded, int[][] cr) {
        this.name = name;
        this.nurses = nurses;
        this.shifts = shifts;
        this.nrNurses = nrNurses;
        this.nrTypes = 2;
        this.nrdays = 28;
        this.totalShifts = shifts.size();
        this.cyclicalRoster = cr;

        this.crEncoded = crEncoded;
        this.totalReq = this.calcReq(crEncoded);
        this.nrType1 = this.countType1(nurses);
        this.type1Req = this.makeType1Req(shifts.size());

    }

    @Override
    public String toString() {
        return "Department{" + "depId=" + name + ", nurses=" + nurses + ", shifts=" + shifts + '}';
    }

    public int[] calcReq(int[][] cr) {
        totalReq = new int[140];

        for (int j = 0; j < totalShifts; j++) {
            double k = (j + 1) % 5;

            for (int i = 0; i < nrNurses; i++) {
                if (cr[i][j] == 1) {
                    totalReq[j] += 1;
                    if (k == 0 && j != 0) {
                        totalReq[j] = 0;
                    }
                }

            }
        }
        return totalReq;
    }

    private int countType1(ArrayList<Nurse> nurses) {
        int result = 0;
        for (int i = 0; i < nurses.size(); i++) {
            if (nurses.get(i).getType() == 1) {
                result++;
            }

        }
        return result;
    }

    private int[] makeType1Req(int s) {
        int[] result = new int[s];
        for (int i = 0; i < s; i++) {
            if ( i < s - 4 && (i + 1) % 5 != 0
                    && (i + 4) % 5 != 0 ) {
                result[i] = 1;

            }
        }

    
    return result ;
}

public String getName() {
        return name;
    }

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public Nurse[] getNursesArray() {
        return nursesArray;
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }

    public int getTotalShifts() {
        return totalShifts;
    }

    public int getNrNurses() {
        return nrNurses;
    }

    public int getNrTypes() {
        return nrTypes;
    }

    public int getNrdays() {
        return nrdays;
    }

    public int[][] getCrEncoded() {
        return crEncoded;
    }

    public int[] getReq() {
        return totalReq;
    }

    public int[][] getCyclicalRoster() {
        return cyclicalRoster;
    }

    public int getNrType1() {
        return nrType1;
    }

    public int[] getTotalReq() {
        return totalReq;
    }

    public int[] getType1Req() {
        return type1Req;
    }

}
