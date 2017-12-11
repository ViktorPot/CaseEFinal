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

   

    // Create a random individual
    public void generateIndividual() {

            //Genation methode
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = Heuristic.getFitness(roster);
        }
        return fitness;
    }
    public int size() {
    return this.roster.length;
    }

    @Override
    public String toString() {
        return "Individual{" + "roster=" + Arrays.toString(roster[0]) + ", OrigFitness=" + OrigFitness + '}';
    }
    
    

}
