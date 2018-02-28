package com.nizite.boi_ai.algorithms.genetic;

import java.util.ArrayList;
import java.util.Random;

import com.nizite.boi_ai.Config;
import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.representations.Atom;

/**
 * Genetic algorithm implementation.
 * Uses rulette wheel for selection and one point mutation.
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
	 * TODO @todo should be between 0 and 1
	 */
	private double _mutation;
	
	/**
	 * TODO @todo should be between 0 and 1
	 */
	private double _crossover;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * TODO @todo validate correct value of vars
	 */
	@Override
	public void setup(Object... setup) {
		_populationSize = (int) setup[0];
		_population = new Atom[_populationSize];
		_mutation = (double) setup[1];
		_crossover = (double) setup[2];
	}
	
	/**
	 * Creates a new population based on the old one, mutating and crossing
	 */
	@Override
	protected void iteration() {
		Atom[] newPopulation = new Atom[_populationSize];
		double sumFitness = 0;
		
		// sum all fitness and get the best solution so far
		for (int i = 0; i < _populationSize; i++) {
			// selection
			sumFitness += _population[i].getFitness();

			//best solution, the smaller the better
			if (_population[i].getFitness() < _bestSolution.getFitness()) {
				_bestSolution = _population[i];
			}
		}
		
		// TODO @todo check this, not sure if this calculation is done right
		double secondFitness = 0.0;
		for (int i = 0; i < _populationSize; i++) {
			secondFitness += 1 - (_population[i].getFitness() / sumFitness);
		}
		
		Random rn = new Random();
		for (int i = 0; i < _populationSize; i++) {
			double random = rn.nextDouble();
			double previousFit = 0.0;
			for (int j = 0; j < _populationSize; j++) {
				previousFit += (1 - _population[j].getFitness() / sumFitness) / secondFitness;
				if (random <= previousFit) {
					newPopulation[i] = _population[j];
					break;
				}
			}
		}
		
		if (_populationSize == 1) {
			newPopulation[0] = _population[0];
		}
		
		_population = newPopulation;

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
	 * TODO @todo check this
	 */
	private void crossover() {
		ArrayList<Atom> newPop = new ArrayList<Atom>();
		Random rn = new Random();
		
		Atom parent1 = null;
		for (int i = 0; i < _populationSize; i++) {
			if (rn.nextDouble() <= _crossover) {
				if (parent1 == null) {
					parent1 = _population[i];
				} else {
					//crossover
					String parent1S = _representation.atomToString(parent1);
					int separators = parent1S.length() - parent1S.replace(Config.ATOM_SPLIT_CHAR, "").length();
					int middle = separators / 2;

				    int pos1 = parent1S.indexOf(Config.ATOM_SPLIT_CHAR, 0);
				    int fake = middle;
				    while (fake-- > 0 && pos1 != -1)
				        pos1 = parent1S.indexOf(Config.ATOM_SPLIT_CHAR, pos1+1);
				    
				    String parent2S = _representation.atomToString(_population[i]);
				    int pos2 = parent2S.indexOf(Config.ATOM_SPLIT_CHAR, 0);
				    while (middle-- > 0 && pos2 != -1)
				        pos2 = parent2S.indexOf(Config.ATOM_SPLIT_CHAR, pos2+1);
				    
				    String child1 = parent1S.substring(0, pos1) + parent2S.substring(pos1, parent2S.length());
				    String child2 = parent2S.substring(0, pos2) + parent1S.substring(pos2, parent1S.length());
				    
				    newPop.add(_representation.stringToAtom(child1));
				    newPop.add(_representation.stringToAtom(child2));
				    
					parent1 = null;
				}
			} else {
				// copy as is
				newPop.add(_population[i]);
			}
		}
		
		// in case last one didn't find a partner
		if (parent1 != null) {
			newPop.add(parent1);
		}

		_population = newPop.toArray(new Atom[newPop.size()]);
	}
	
	/**
	 * TODO @todo check this
	 */
	private void mutation() {
		Random rn = new Random();
		String[] problem = _representation.atomToString(_representation.getProblem()).split(Config.ATOM_SPLIT_CHAR, -1);
		
		for (int i = 0; i < _population.length; i++) {
			String[] digits = _representation.atomToString(_population[i]).split(Config.ATOM_SPLIT_CHAR, -1);

			String mutated = "";
			for (int j = 0; j < digits.length; j++) {
				if (rn.nextDouble() <= _mutation && problem[j].equals("")) {
					ArrayList<String> options = _representation.getAllowedStates(digits[j]);
					mutated += options.get(rn.nextInt(options.size()));
				} else {
					mutated += digits[j];
				}
				mutated += Config.ATOM_SPLIT_CHAR;
			}
			mutated = mutated.substring(0, mutated.length()-1);
			
			_population[i] = _representation.stringToAtom(mutated);
		}
	}
}
