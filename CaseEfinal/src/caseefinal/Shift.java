/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Viktor
 */
public class Shift {

    public int day;

    public String shiftTypestr;
    public int startTime;
    public int length;
    public int shiftType;
    public int shiftIndex;//ZOALS WIJ ZE DEFINEN

    public Shift(int day, int tmp, int length, String dep) {
        this.day = day + 1;
        this.length = length;
        this.shiftIndex = tmp;

        switch (tmp) {
            case 0:
                this.shiftTypestr = "E";
                this.shiftType = 1;
                this.startTime = 6;
                break;
            case 1:
                this.shiftTypestr = "D";

                break;
            case 2:
                if (!dep.equalsIgnoreCase("C")) {
                    this.shiftTypestr = "L";
                    this.shiftType = 2;
                    this.startTime = 15;
                } else {
                    this.shiftTypestr = "L";
                    this.shiftType = 2;
                    this.startTime = 12;
                }

                break;
            case 3:
                this.shiftTypestr = "N";
                this.shiftType = 3;
                this.startTime = 21;
                break;
            case 4:
                this.shiftTypestr = "F";
                this.shiftType = 0;
                break;
        }
    }

    public int getDay() {
        return day;
    }

    public int getShiftType() {
        return shiftType;
    }

    public Shift(int day, int shiftType, int length, String dep, int decode) {
        this.day = day + 1;

        this.length = length;

        switch (shiftType) {
            case 1:
                this.shiftTypestr = "E";
                this.shiftIndex = 0;
                this.startTime = 6;
                break;
            case 2:
                if (!dep.equalsIgnoreCase("C")) {
                    this.shiftTypestr = "L";
                    this.shiftIndex = 2;
                    this.startTime = 15;
                } else {
                    this.shiftTypestr = "L";
                    this.shiftIndex = 2;
                    this.startTime = 12;
                }
                break;
            case 3:
                this.shiftTypestr = "N";
                this.shiftIndex = 3;
                this.startTime = 21;
                break;
            case 0:
                this.shiftTypestr = "F";
                this.shiftIndex = 4;
                break;
        }
    }

//    public Shift(int day, String dep, Shifttype shiftType) {
//        this.day = day;
//        this.dep = dep;
//        this.shiftType = shiftType;
//        this.reqNurses = 0;
//        this.nursesToStart = 0;
//    }
//
//    public int getDay() {
//        return day;
//    }
//
//    public String getDep() {
//        return dep;
//    }
//
//    public Shifttype getShiftType() {
//        return shiftType;
//    }
//
//    public int getReqNurses() {
//        return reqNurses;
//    }
//
//    public void setReqNurses(int reqNurses) {
//        this.reqNurses = reqNurses;
//    }
//
//    public void setShiftType(Shifttype shiftType) {
//        this.shiftType = shiftType;
//    }
//
//    public void setNursesToStart(int nursesToStart) {
//        this.nursesToStart = nursesToStart;
//    }
//
//    public int getNursesToStart() {
//        return nursesToStart;
//    }
}
