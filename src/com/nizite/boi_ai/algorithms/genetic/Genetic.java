package com.nizite.boi_ai.algorithms.genetic;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Representation;

public class Genetic extends Algorithm {
	private int _populationSize;
	private double _selection;
	private double _mutation;
	private double _crossover;
	
	private Atom[] _population;

	@Override
	public void run() {
		for(int i = 0; i < this._iterations; i++) {
			this.iteration();
		}
	}

	@Override
	public Atom getBestSolution() {
		return this._bestSolution;
	}

	@Override
	public String getStats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void iteration() {
		for(int i = 0; i < this._populationSize; i++) {
			double fitness = this._population[i].getFitness();
			if (fitness < this._bestSolution.getFitness()) {
				this._bestSolution = this._population[i];
			}
		}
		this.initialPopulation();
	}

	@Override
	protected void setRepresentation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setBestSolution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setup(Object... setup) {
		this._populationSize = (int) setup[0];
		this._population = new Atom[this._populationSize];
		this._bestSolution = this._problem.getRepresentation().createAtom();
		this.initialPopulation();
		this._selection = (double) setup[1];
		this._mutation = (double) setup[2];
		this._crossover = (double) setup[3];
	}
	
	private void initialPopulation() {
		for (int i = 0; i < this._populationSize; i++) {
			this._population[i] = this._problem.getRepresentation().createAtom();
		}
	}

}
