/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Viktor
 */
public class Solution {

    public int[][] roster;
    public double fitness;
    public Department dep;

    public Solution(int[][] roster, double fitness, Department dep) {
        this.roster = roster;
        this.fitness = fitness;
        this.dep = dep;
    }

    public int[][] getRoster() {
        return roster;
    }

    public double getFitness() {
        return fitness;
    }

    public Department getDep() {
        return dep;
    }


}
