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
public class Individual {

    public int[][] roster;
    public double OrigFitness;

    //static int defaultGeneLength = 64;
    //private byte[] genes = new byte[defaultGeneLength];
    // Cache
    private double fitness = 0;

    public Individual(int[][] roster) {
        this.roster = roster;
        this.OrigFitness = Heuristic.getFitness(roster);
    }

    public Individual(int imax, int jmax) {
        for (int i = 0; i < imax; i++) {
            for (int j = 0; j < jmax; j++) {
                this.roster[i][j] = 0;

            }
        }
    }

    // Create a random individual
    

    public double getOrigFitness() {
        return OrigFitness;
    }

    public int size() {
        return this.roster.length;
    }

    public void setRoster(int[][] roster) {
        this.roster = roster;
    }

    public int[][] getRoster() {
        return roster;
    }

}
