/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NotNeeded;

import caseefinal.Shift;
import java.util.Objects;

/**
 *
 * @author Viktor
 */
public class Preference { // Readmethode

    public double score;
    public Shift shift;
    //public int day;

    public Preference(double score, Shift shift) {
        this.score = score;
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "Preference{" + "score=" + score + ", shift=" + shift + '}' + "\t";
    }

    public double getScore() {
        return score;
    }

    public Shift getShift() {
        return shift;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.shift);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Preference other = (Preference) obj;
        if (!Objects.equals(this.shift, other.shift)) {
            return false;
        }
        return true;
    }

}
