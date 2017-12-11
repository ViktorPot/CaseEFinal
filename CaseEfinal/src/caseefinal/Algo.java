/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caseefinal;

import static caseefinal.Heuristic.nrNurses;
import static caseefinal.Heuristic.totalShifts;
import java.util.ArrayList;

/**
 *
 * @author Viktor
 */
public class Algo {

    public static int[][] roster;
    public static int[] rosterType, nurseType;

    public static ArrayList<Individual> startPopulation;
    public static ArrayList<Individual> endPopulation;
    // SELECTION, MUTATION
// IN FORMAT OF BASIC CYCLICAL ROSTER
            /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 4;
    private static final boolean elitism = true;

    /* Public methods */
    // Evolve a population
    public static ArrayList<Individual> evolvePopulation(ArrayList<Individual> pop) { // STARTPOPULATION
        ArrayList<Individual> evolvePopulation = new ArrayList<>();

        // Keep our best individual
        if (elitism) {
            evolvePopulation.add(getFittest(pop));
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            evolvePopulation.add(newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < evolvePopulation.size(); i++) {
            mutate(evolvePopulation.get(i));
        }
    
        return evolvePopulation;
    }

    // Crossover individuals // FIXED
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(indiv1.getRoster().length, indiv1.getRoster()[0].length);
        // Loop through genes
        for (int i = 0; i < indiv1.getRoster().length; i++) {
            for (int j = 0; j < indiv1.getRoster()[0].length; j++) {
                // Crossover
                if (Math.random() <= uniformRate) {
                    newSol.getRoster()[i][j] = indiv1.getRoster()[i][j];
                } else {
                    newSol.getRoster()[i][j] = indiv2.getRoster()[i][j];
                }
            }
        }
        return newSol;
    }

    // Mutate an individual
    private static Individual mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.getRoster().length; i++) {
            for (int j = 0; j < indiv.getRoster()[0].length; j++) {
                if (Math.random() <= mutationRate) {
                    // Create random gene
                  indiv.getRoster()[i][j] = (int) (Math.random() *2);
                }
            }
        }
        return indiv;
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(ArrayList<Individual> pop) {
        // Create a tournament population
        ArrayList<Individual> tournament = new ArrayList<>();
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.add(pop.get(randomId));
        }
        // Get the fittest
        Individual fittest = getFittest(tournament);
        return fittest;
    }

    public static Individual getFittest(ArrayList<Individual> pop) {
        Individual fittest = pop.get(0);
        // Loop through individuals to find fittest
        for (Individual i : pop) {
            if (fittest.getOrigFitness() > i.getOrigFitness()) {
                fittest = i;
            }
        }
        return fittest;
    }

}
