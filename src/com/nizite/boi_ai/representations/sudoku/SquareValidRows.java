package com.nizite.boi_ai.representations.sudoku;

import java.util.ArrayList;
import java.util.Collections;

import com.nizite.boi_ai.representations.Atom;

/**
 * Square n x n matrix
 * Enforced valid rows that don't break hard rules.
 * Doesn't enforce anything else.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class SquareValidRows extends SquareRandom {

	/**
	 * Sudoku matrix has valid rows (therefore motivating a constraint to be always valid, but doesn't care
	 * if it's cofigured by the user to be enforced).
	 */
	@Override
	public Atom createAtom() {
		Integer[][] newSquare = (Integer[][]) this.blankAtom().get();
		Integer[][] problem = (Integer[][]) _problem.get();
		
		//base arraylist with all numbers
		ArrayList<Integer> allNumbers = new ArrayList<Integer>();
		for (int i = 0; i < _size*_size; i++)
			allNumbers.add(i+1);
		
		// per each row, see what numbers are available
		for (int i = 0; i < _size*_size; i++) {
			ArrayList<Integer> available = new ArrayList<Integer>(allNumbers);
			for (int j = 0; j < _size*_size; j++)
				if (problem[i][j] != null)
					available.remove(problem[i][j]);
			
			// randomize and insert into empty spaces
			Collections.shuffle(available);
			for (int j = 0; j < _size*_size; j++)
			if (problem[i][j] != null && problem[i][j] != 0) {
				newSquare[i][j] = problem[i][j];
			} else {
				newSquare[i][j] = available.get(0);
				available.remove(0);
			}
		}
		
		// set fitness
		Atom atom = new Atom(newSquare);
		atom.setFitness(this.calculateFitness(atom));

		return atom;
	}
}
