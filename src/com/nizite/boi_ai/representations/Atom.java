package com.nizite.boi_ai.representations;

/**
 * Every Representation will know how to deal with it's atoms
 * It can be a matrix or an object or a list, etc. Pertinent to each Rep
 * @author experiments
 *
 */
public abstract class Atom {
	protected Object _atom;
	protected int    _fitness;
	
	public int getFitness() {
		return _fitness;
	}
	
	public Object getAtom() {
		return _atom;
	};
	
	public void setAtom(Object atom) {
		_atom = atom;
	};
}
