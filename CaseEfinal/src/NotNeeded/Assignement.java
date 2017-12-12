/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NotNeeded;

import caseefinal.Nurse;
import caseefinal.Shift;
import java.util.ArrayList;

/**
 *
 * @author Viktor
 */
public class Assignement {

    public Nurse nurse; // Nurse related to Department
    public Shift Shift; // Shift related to Department!! Start shift van 5 dagen periode

    public boolean assigned; // Assignable
    // true or false

    public Assignement(Nurse nurse, Shift s) { // Direct volgende 5 dagen ook
        this.nurse = nurse;
        this.Shift = s;

        this.assigned = false;
    }

    public Assignement(Nurse nurse, Shift s, boolean b) { // Direct volgende 5 dagen ook
        this.nurse = nurse;
        this.Shift = s;

        this.assigned = b;
    }

    private void addWorkShift(Shift s) {

    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Shift getShift() {
        return Shift;
    }

}
