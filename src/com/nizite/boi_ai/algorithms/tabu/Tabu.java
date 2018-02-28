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
	/**
	 * The size of the tabu list
	 */
	protected int _tabuSize;
	
	/**
	 * Saved tabu list to avoid next time. The size should be {@link Tabu#_tabuSize}
	 */
	protected LinkedList<Atom> _tabu;
	
	/**
	 * Current solution from which we'll get neighbors
	 */
	protected Atom _currentSolution;
	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	/**
	 * Start an empty tabu list the size determined by the user
	 */
	@Override
	public void setup(Object... setup) {
		_tabu = new LinkedList<Atom>();
		_tabuSize = (int) setup[0];
	}

	/**
	 * Update tabu list with current solution as to not use it again.
	 * Find neighbors and select best solution, but remove the solutions already in tabu list.
	 * If the current solution is better than the best solution, save it.
	 * 
	 * TODO @todo check if this is implemented right
	 */
	@Override
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
		// if no neighbors, explore and create a random atom
		if (neighbors.size() == 0)
			bestLocalSolution = _representation.createAtom();
		else
			bestLocalSolution = neighbors.get(0);
		
		// find the best local solutions of neigh list
		for (int i = 0; i < neighbors.size(); i++) {
			if (bestLocalSolution.getFitness() > neighbors.get(i).getFitness())
				bestLocalSolution = neighbors.get(i);
		}
		_currentSolution = bestLocalSolution;
		
		// if current solution is better than the overall solution, update
		if (_currentSolution.getFitness() < _bestSolution.getFitness())
			_bestSolution = _currentSolution;
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */
	
	/**
	 * Put a random solution as the best one to have something to compare
	 */
	public void run() {
		_bestSolution = _representation.createAtom();
		_currentSolution = _bestSolution;
		super.run();
	}
}
