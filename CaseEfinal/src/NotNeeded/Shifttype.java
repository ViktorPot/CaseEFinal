/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NotNeeded;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Viktor
 */
public class Shifttype {

    public String name;
    public int intValue; // 0 : Free, 1: Early, 2: Late, 3: Night

    public int duration = 9;
    public int startTime;
    //WAARDEN INLEZEN

    public Shifttype(String name, String depId) {
        this.name = name;
        this.duration = 9;
        if (name.contains("E")) {
            this.startTime = 6;
            this.intValue = 1;
        }
        if (name.contains("D")) {
            this.startTime = -1; // Niet mogelijk
            this.intValue = -1;
        }
        if (name.contains("L")) {
            if (depId.equalsIgnoreCase("C")) {
                this.startTime = 12; // 12 bij C
                this.intValue = 2;
            } else {
                this.startTime = 15;
                this.intValue = 2;
            }
        }
        if (name.contains("N")) {
            this.startTime = 21;
            this.intValue = 3;
        }
        if (name.contains("F")) {
            this.startTime = -1;
            this.intValue = 0;// KAN NIET mening?
        }
    }

    public Shifttype(String type) {
        this.name = type;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getType() {
        return name;
    }

    @Override
    public String toString() {
        return "Shifttype{" + "type=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Shifttype other = (Shifttype) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.duration != other.duration) {
            return false;
        }
        if (this.startTime != other.startTime) {
            return false;
        }
        return true;
    }

}
