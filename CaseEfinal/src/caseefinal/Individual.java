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
  

    public Individual(int[][] roster) {
        this.roster = roster;
        this.OrigFitness = Heuristic.getFitness(roster);
   
    }

    public int[][] getRoster() {
        return roster;
    }

    public double getOrigFitness() {
        return OrigFitness;
    }

   


}
