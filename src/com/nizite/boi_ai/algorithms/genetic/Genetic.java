package com.nizite.boi_ai.algorithms.genetic;

import java.util.ArrayList;
import java.util.Random;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.representations.Atom;

//rulete wheel
// one point mutation
public class Genetic extends Algorithm {
	private int _populationSize;
	
	// TODO: doubles should be 0-1
	private double _mutation;
	private double _crossover;
	
	private Atom[] _population;

	@Override
	public void setup(Object... setup) {
		this._populationSize = (int) setup[0];
		this._population = new Atom[this._populationSize];
		this._bestSolution = this._problem.getRepresentation().createAtom();
		this._mutation = (double) setup[1];
		this._crossover = (double) setup[2];
	}
	
	public void run() {
		this.initialPopulation();
		super.run();
	}
	
	@Override
	protected void iteration() {
		Atom[] newPopulation = new Atom[_populationSize];
		double sumFitness = 0;
		for(int i = 0; i < this._populationSize; i++) {
			// selection
			sumFitness += _population[i].getFitness();

			//best solution
			if (_population[i].getFitness() < _bestSolution.getFitness()) {
				_bestSolution = _population[i];
			}
		}
		
		double secondFitness = 0.0;
		for(int i = 0; i < _populationSize; i++) {
			secondFitness += 1 - (_population[i].getFitness() / sumFitness);
		}
		
		Random rn = new Random();
		for(int i = 0; i < _populationSize; i++) {
			double random = rn.nextDouble();
			double previousFit = 0.0;
			for(int j = 0; j < _populationSize; j++) {
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
	

	private void initialPopulation() {
		for (int i = 0; i < this._populationSize; i++) {
			this._population[i] = this._problem.getRepresentation().createAtom();
		}
	}
	
	private void crossover() {
		ArrayList<Atom> newPop = new ArrayList<Atom>();
		Random rn = new Random();
		
		Atom parent1 = null;
		for(int i = 0; i < _populationSize; i++) {
			if (rn.nextDouble() <= _crossover) {
				if (parent1 == null) {
					parent1 = _population[i];
				} else {
					//crossover
					String parent1S = _representation.atomToString(parent1);
					int separators = parent1S.length() - parent1S.replace("-", "").length();
					int middle = separators / 2;

				    int pos1 = parent1S.indexOf("-", 0);
				    int fake = middle;
				    while (fake-- > 0 && pos1 != -1)
				        pos1 = parent1S.indexOf('-', pos1+1);
				    
				    String parent2S = _representation.atomToString(_population[i]);
				    int pos2 = parent2S.indexOf('-', 0);
				    while (middle-- > 0 && pos2 != -1)
				        pos2 = parent2S.indexOf('-', pos2+1);
				    
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
	 * 
	 */
	private void mutation() {
		Random rn = new Random();
		String[] problem = _representation.atomToString(_representation.getProblem()).split("-", -1);
		
		for (int i = 0; i < _population.length; i++) {
			String[] digits = _representation.atomToString(_population[i]).split("-", -1);

			String mutated = "";
			for (int j = 0; j < digits.length; j++) {
				if (rn.nextDouble() <= _mutation && problem[j].equals("")) {
					ArrayList<String> options = _representation.getAllowedStates(digits[j]);
					mutated += options.get(rn.nextInt(options.size()));
				} else {
					mutated += digits[j];
				}
				mutated += "-";
			}
			mutated = mutated.substring(0, mutated.length()-1);
			
			_population[i] = _representation.stringToAtom(mutated);
		}
	}
}
