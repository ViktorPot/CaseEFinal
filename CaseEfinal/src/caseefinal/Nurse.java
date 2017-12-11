/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Viktor
 */
public class Nurse {

    public String code;
    public int type; //(1,2)

    public int[] prefAll;
    public double emplRate;
    public int totalPref;
    // INLEZEN sheet 1

    public Nurse(String code, int type, int[] prefAll, double emplRate) {
        this.code = code;
        this.type = type;

        this.prefAll = prefAll;
        this.emplRate = emplRate;
    }

    public double getEmplRate() {
        return emplRate;
    }

    public int[] getPrefGiven() {
        return prefAll;
    }

  

//    public String getCyclicalPref() {
//        return cp;
//    }
//    public addPref(int score) {
//
//    }
    public String getNurseId() {
        return code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setEmplRate(double emplRate) {
        this.emplRate = emplRate;
    }

    @Override
    public String toString() {
        return "Nurse{" + "code=" + code + ", type=" + type + ", prefAll=" + Arrays.toString(prefAll) + ", emplRate=" + emplRate + ", totalPref=" + totalPref + '}';
    }

}
