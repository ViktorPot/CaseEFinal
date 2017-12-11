/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import java.util.ArrayList;
import java.util.Arrays;
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
    public Individual(int[][] roster) {
        this.roster = roster;
        this.OrigFitness = Heuristic.getFitness(roster);
    }

    public Individual(int imax, int jmax) {
        roster = new int[imax][jmax];
        for (int i = 0; i < imax; i++) {
            for (int j = 0; j < jmax; j++) {
                roster[i][j] = 0;

            }
        }
        this.OrigFitness = 0;
    }

    // Create a random individual
    public void generateIndividual() {

        //Genation methode
    }

    public int size() {
        return this.roster.length;
    }

    @Override
    public String toString() {
        return "Individual{" + "OrigFitness=" + OrigFitness + ", Roster=" + Arrays.toString(roster[0]) + '}';
    }

    public double getOrigFitness() {
        if (this.OrigFitness == 0) {
            return Heuristic.getFitness(this.roster);
        }
        return OrigFitness;
    }

    public void setRoster(int[][] roster) {
        this.roster = roster;
    }

    public int[][] getRoster() {
        return roster;
    }

}
