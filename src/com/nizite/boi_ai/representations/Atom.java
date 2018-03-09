package com.nizite.boi_ai.representations;

/**
 * Every Representation will know how to deal with its atoms
 * It can be a matrix or an object or a list, etc. Pertinent to each Rep.
 * Since fitness calculation depends on each Rep, that's not done here,
 * this is only used for storage
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class Atom {
	protected Object  _atom;
	protected Double _fitness;
	
	public Atom(Object atom) {
		this.set(atom);
		_fitness = null;
	}
	
	public Object get() {
		return _atom;
	};
	
	public void set(Object atom) {
		_atom = atom;
	};
	
	// TODO @todo probably makes sense to rename this since "fitness" implies that the bigger the better,
	// but we are usually trying to minimize the value of the objective function
	public Double getFitness() {
		return _fitness;
	}
	
	public void setFitness(double fitness) {
		_fitness = fitness;
	}
	
	// TODO @todo would maybe benefit of having a compare function inside the Atom structure
}
