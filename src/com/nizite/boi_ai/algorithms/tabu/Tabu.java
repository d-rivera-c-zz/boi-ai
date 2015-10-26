package com.nizite.boi_ai.algorithms.tabu;

import java.util.ArrayList;
import java.util.LinkedList;

import com.nizite.boi_ai.algorithms.Algorithm;
import com.nizite.boi_ai.representations.Atom;

/**
 * Basic tabu search implementation.
 * Doesn't change size of tabu list dynamically.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class Tabu extends Algorithm {

	protected LinkedList<Atom> _tabu;
	protected int _tabuSize;
	protected Atom _currentSolution;
	
	@Override
	public void setup(Object... setup) {
		_tabu = new LinkedList<Atom>();
		_tabuSize = (int) setup[0];
		_bestSolution = _representation.createAtom();
		_currentSolution = _bestSolution;
	}

	@Override
	/**
	 * Find neighbors and select best solution, but remove the solutions already in tabu list.
	 * If the current solution is better than the best solution, save it.
	 * Update tabu list with current solution to not use it again
	 */
	protected void iteration() {
		ArrayList<Atom> neighbors = _representation.getNeighbors(_currentSolution);
		
		// update tabu
		_tabu.add(_currentSolution);
		while (_tabu.size() > _tabuSize)
			_tabu.removeFirst();
		
		// remove tabu list == neighbors
		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = 0; j < _tabu.size(); j++) {
				if (_representation.atomToString(neighbors.get(i)).equals(
						_representation.atomToString(_tabu.get(j)))) {
					neighbors.remove(i);
					break;
				}
			}
		}
		
		Atom bestLocalSolution = null;
		//if no neighbors, explore and create a random atom
		if (neighbors.size() == 0)
			bestLocalSolution = _representation.createAtom();
		else
			bestLocalSolution = neighbors.get(0);
		
		for (int i = 0; i < neighbors.size(); i++) {
			// find solution best  = actual solution
			if (bestLocalSolution.getFitness() < neighbors.get(i).getFitness())
				bestLocalSolution = neighbors.get(i);
		}
		_currentSolution = bestLocalSolution;
		
		// if actual solution better -> best solution = actual
		if (_currentSolution.getFitness() > _bestSolution.getFitness())
			_bestSolution = _currentSolution;
	}
}
