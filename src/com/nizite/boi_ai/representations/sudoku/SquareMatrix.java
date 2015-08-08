package com.nizite.boi_ai.representations.sudoku;

import java.util.Random;

import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Lambda;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;

/**
 * 
 * @author experiments
 *
 */
public class SquareMatrix extends Representation {
	private int _size;
	private String _square;

	@Override
	public void setProblem(Object... problem) {
		_size = (int) problem[0];
		_square = (String) problem[1];
		
		_problem = this.stringToAtom(_square);
	}
	

	@Override
	public Atom blankAtom() {
		return new Atom(new Integer[this._size*this._size][this._size*this._size]);
	}

	@Override
	public Atom createAtom() {
		Integer[][] newSquare = (Integer[][]) this.blankAtom().get();
		Integer[][] problem = (Integer[][]) _problem.get();
		
		// set all random
		Random rn = new Random();
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				newSquare[i][j] = rn.nextInt(9) + 1;
				
				// replace matrix with items on _problem
				if (problem[i][j] != null && problem[i][j] != 0) {
					newSquare[i][j] = problem[i][j];
				}
			}
		}
		
		// set fitness
		Atom atom = new Atom(newSquare);
		atom.setFitness(this.calculateFitness(atom));
		
		return atom;
	}

	@Override
	public String atomToString(Atom atom) {
		Integer[][] rep = (Integer[][]) atom.get();
		String representation = "";
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				if (rep[i][j] != null) {
					representation += Integer.toString(rep[i][j]);
				}
			}
			representation += "\r\n";
		}
		
		return representation;
	}
	

	@Override
	public Atom stringToAtom(String rep) {
		Integer[][] representation = (Integer[][]) this.blankAtom().get();
		
		for(int i = 0; i < this._size*this._size; i++) {
			for(int j = 0; j < this._size*this._size; j++) {
				try {
					representation[i][j] = Parser.stringToInt(rep.charAt((i+1) * (j+1)) + "");
				} catch (Exception e) {
					representation[i][j] = null;
				}
			}
		}
		
		return new Atom(representation);
	}

	@Override
	public double calculateFitness(Atom atom) {
		double totalScore = _objective.calc(atom);
		for(Lambda constrain : _hard) {
			totalScore -= constrain.calc(atom);
		}
		
		return totalScore;
	}
	
	/**
	 * TODO: filter by array
	 */
	@Override
	protected void setHardConstraints(int[] hard) {
		super.setHardConstraints(hard);

		// Each row must have all numbers 1-n^2
		_hard.add((Atom a) -> {
			double total = 0.0;
			Integer[][] square = (Integer[][]) a.get();
			
			for(int i = 0; i < square.length; i++) {
				for(int x = 0; x < square.length; x++) {
					boolean found = false;
					for(int j = 0; j < square.length; j++) {
						if (square[i][j] == x) {
							found = true;
							continue;
						}
					}
					
					if (!found)
						total++;
				}
			}
			
			return total;
		});
		
		// Each column must have all numbers 1-n^2
		_hard.add((Atom a) -> {
			double total = 0.0;
			Integer[][] square = (Integer[][]) a.get();
			
			for(int i = 0; i < square.length; i++) {
				for(int x = 0; x < square.length; x++) {
					boolean found = false;
					for(int j = 0; j < square.length; j++) {
						if (square[j][i] == x) {
							found = true;
							continue;
						}
					}
					
					if (!found)
						total++;
				}
			}
			
			return total;
		});
		
		// Each n x n square must have all numbers 1-n^2
		_hard.add((Atom a) -> {
			double total = 0.0;
			Integer[][] square = (Integer[][]) a.get();
			
//TODO
			
			return total;
		});
	}

	@Override
	/**
	 * There's really no objective function to enforce here 
	 * other than minimoze hard contrains
	 */
	protected void setObjectiveFunction() {
		_objective = (Atom a) -> {
			double totalScore = Math.pow(_size, 4) * 3;
			return totalScore;
		};
	}
}
