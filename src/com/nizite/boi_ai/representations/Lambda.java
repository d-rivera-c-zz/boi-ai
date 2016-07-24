package com.nizite.boi_ai.representations;

/**
 * Lambda expression interface.
 * Used by (@link Representation) and (@link Atom) to calculate fitness
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public interface Lambda {
	double calc(Atom atom);
}
