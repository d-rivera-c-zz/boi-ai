package com.nizite.boi_ai.algorithms.genetic;

import java.util.ArrayList;
import java.util.Random;

import com.nizite.boi_ai.Config;
import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.representations.Atom;

/**
 * Genetic algorithm implementation.
 * Uses roulette wheel for selection (other options are tournament selection, ranked position, steady state).
 * Single point crossover (other options: uniform, shuffle, multi-point).
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class Genetic extends Algorithm {
	/**
	 * Size of the population to handle.
	 * The bigger the population, the more space to explore, but is more expensive to handle.
	 */
	private int _populationSize;
	
	/**
	 * Actual population, size of the array should be {@link Genetic#_populationSize}
	 */
	private Atom[] _population;
	
	/**
	 * Probability of mutating an Atom set by the user
	 */
	private double _mutation;
	
	/**
	 * Probability of crossover of two Atoms set by the user
	 */
	private double _crossover;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * Validates correct setup of the variables.
	 * 
	 * @throws Exception 
	 */
	@Override
	public void setup(Object... setup) throws Exception {
		_populationSize = (int) setup[0];
		
		if (_populationSize <= 0)
			throw new Exception("Population size needs to be bigger than zero");
		
		_population = new Atom[_populationSize];
		_mutation = (double) setup[1];
		_crossover = (double) setup[2];
		
		if (_mutation < 0 || _mutation > 1)
			throw new Exception("Mutation value needs to be a percentage (between 0 and 1 value)");
		if (_crossover < 0 || _crossover > 1)
			throw new Exception("Crossover value needs to be a percentage (between 0 and 1 value)");
	}
	
	/**
	 * Creates a new population based on the old one, mutating and crossing.
	 * Saves best solution.
	 */
	@Override
	protected void iteration() {
		// get the best solution so far
		for (int i = 0; i < _populationSize; i++) {
			if (_population[i].getFitness() < _bestSolution.getFitness()) {
				_bestSolution = _population[i];
			}
		}
		
		this.reproduction();
		this.crossover();
		this.mutation();
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */
	
	/**
	 * Create initial population and then run as usual.
	 */
	public void run() {
		this.initialPopulation();
		super.run();
	}
	
	/* *********************** */
	/* PROBLEM PERTINENT FUNCS */
	/* *********************** */

	/**
	 * Create a random new atom and set it as best solution, then create all other initial
	 * atoms for the initial population.
	 */
	private void initialPopulation() {
		_bestSolution = _representation.createAtom();
		for (int i = 0; i < _populationSize; i++) {
			_population[i] = _representation.createAtom();
		}
	}
	
	/**
	 * Get a new set of atoms based on the previous population.
	 * Implementing Roulette wheel selection.
	 * No new atoms are created here, since only old population have a probability of being selected
	 * for the new population.
	 * 
	 * Since we are minimizing the value, atoms with lower "fitness" value should have
	 * better probability of being selected, so if we do (1 - Fn/Ftotal) and sum it
	 * we'll be getting another "sum fitness" value that assigns bigger value to atoms with lower fitness value
	 * which is what we want. That "inverted sum fitness" value is the one used to normalize all.
	 * 
	 * New population is stored in {@link #_population}
	 */
	private void reproduction() {
		Atom[] newPopulation = new Atom[_populationSize];
		
		// avoid calculating anything if just one atom in the population
		if (_populationSize == 1) {
			newPopulation[0] = _population[0];
			return;
		}
		
		// sum all fitness to normalize values
		// Ex: with "fitness"  1,2,3,4 we get sumFitness = 10
		double sumFitness = 0.0;
		for (int i = 0; i < _populationSize; i++) {
			sumFitness += _population[i].getFitness();
		}

		// Ex: sumFitness = 10, invertedSumFitness = 0.9 + 0.8 + 0.7 + 0.6 = 3
		double invertedSumFitness = 0.0;
		for (int i = 0; i < _populationSize; i++) {
			invertedSumFitness += 1 - (_population[i].getFitness() / sumFitness);
		}
		
		// select new population based on cumulative fitness, lower values have more probability of being selected
		// Ex: for atom fitness = 1, probability of selection = (1 - 1/10) / 3 = 0.3
		// Ex: for atom fitness = 4, probability of selection = (1 - 4/10) / 3 = 0.2
		Random rn = new Random();
		for (int i = 0; i < _populationSize; i++) {
			double random = rn.nextDouble();
			double cumulativeFitness = 0.0;
			
			// keep adding "inverted fitness" until it reaches random value
			// Ex: loop 1 cumulativeFit = 0.3, random number 0.4
			//     loop 2 cumulativeFit = 0.56, random number 0.4, atom selected for next population
			for (int j = 0; j < _populationSize; j++) {
				cumulativeFitness += (1 - _population[j].getFitness() / sumFitness) / invertedSumFitness;
				if (random <= cumulativeFitness) {
					newPopulation[i] = _population[j];
					break;
				}
			}
		}
		
		// replace old population with new population
		_population = newPopulation;
	}
	
	/**
	 * Single point crossover (in the middle-ish).
	 * Result of crossover stored in {@link #_population}.
	 * 
	 * TODO: think about changing the partitioning from String representation to array of items representation,
	 *       like String[] digits = _representation.atomToString(_population[i]).split(Config.ATOM_SPLIT_CHAR, -1)
	 *       and then split and merge that array
	 * 
	 * Ex: Parent 1 = Part a1 + Part b1
	 *     Parent 2 = Part a2 + Part b2
	 *     Result: Child 1 = a1 + b2 ; Child 2 = a2 + b1
	 */
	private void crossover() {
		ArrayList<Atom> newPop = new ArrayList<Atom>();
		Random rn = new Random();
		
		Atom parent1 = null;
		for (int i = 0; i < _populationSize; i++) {
			// probability of crossover failed, copy atom as is
			if (rn.nextDouble() > _crossover) {
				newPop.add(_population[i]);
				continue;
			}
			
			// if only first parent found, continue looking for second
			if (parent1 == null) {
				parent1 = _population[i];
				continue;
			}

			// two parents found, crossing them
			String parent1S = _representation.atomToString(parent1);
			String parent2S = _representation.atomToString(_population[i]);
			
			// assume chromosomes are the same number of "bits", so same number of separators,
			// but could be in different positions for both parents since strings could have different lengths
			int separatorsQty = parent1S.length() - parent1S.replace(Config.ATOM_SPLIT_CHAR, "").length();
			int middle = separatorsQty / 2; //roughly the middle position of the atom splitting by SPLIT_CHAR

			// get the position of the middle separator for parent 1
		    int pos1 = parent1S.indexOf(Config.ATOM_SPLIT_CHAR, 0);
		    while (middle-- > 0 && pos1 != -1)
		        pos1 = parent1S.indexOf(Config.ATOM_SPLIT_CHAR, pos1+1);
		    
		    // get the position of the middle separator for parent 2
		    middle = separatorsQty / 2;
		    int pos2 = parent2S.indexOf(Config.ATOM_SPLIT_CHAR, 0);
		    while (middle-- > 0 && pos2 != -1)
		        pos2 = parent2S.indexOf(Config.ATOM_SPLIT_CHAR, pos2+1);

		    String child1 = parent1S.substring(0, pos1) + parent2S.substring(pos2, parent2S.length());
		    String child2 = parent2S.substring(0, pos2) + parent1S.substring(pos1, parent1S.length());
		    newPop.add(_representation.stringToAtom(child1));
		    newPop.add(_representation.stringToAtom(child2));
		    
			parent1 = null;
		}
		
		// in case last one in the population didn't find a partner, just add them without crossover
		if (parent1 != null) {
			newPop.add(parent1);
		}

		_population = newPop.toArray(new Atom[newPop.size()]);
	}
	
	/**
	 * For each bit calculate probability of mutation.
	 * If mutating, get allowed states of a bit and change to one randomly
	 */
	private void mutation() {
		Random rn = new Random();
		String[] problem = _representation.atomToString(_representation.getProblem()).split(Config.ATOM_SPLIT_CHAR, -1);
		
		for (int i = 0; i < _population.length; i++) {
			String[] bits = _representation.atomToString(_population[i]).split(Config.ATOM_SPLIT_CHAR, -1);

			// for each bit calculate probability
			String mutated = "";
			for (int j = 0; j < bits.length; j++) {
				if (rn.nextDouble() <= _mutation && problem[j].equals("")) {
					ArrayList<String> options = _representation.getAllowedStates(bits[j]);
					mutated += options.get(rn.nextInt(options.size()));
				} else {
					mutated += bits[j];
				}
				mutated += Config.ATOM_SPLIT_CHAR;
			}
			mutated = mutated.substring(0, mutated.length()-1);
			
			_population[i] = _representation.stringToAtom(mutated);
		}
	}
}
