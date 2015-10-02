package com.nizite.boi_ai.representations.sudoku;

import java.util.ArrayList;
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
	public void setProblem(Object... problem) throws Exception {
		_size = (int) problem[0];
		_square = (String) problem[1];
		_problem = this.stringToAtom(this.dehumanize(_square));
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
	/**
	 * One line, separating each number by -
	 * Will fill in with 1 if number is invalid
	 */
	public String atomToString(Atom atom) {
		Integer[][] rep = (Integer[][]) atom.get();
		String representation = "";
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				if (rep[i][j] != null) {
					representation += Integer.toString(rep[i][j]) + "-";
				} else {
					representation += "-";
				}
			}
		}
		
		// remove last -
		representation = representation.substring(0, representation.length()-1);
		
		return representation;
	}
	

	@Override
	/**
	 * 
	 */
	public Atom stringToAtom(String rep) {
		Integer[][] representation = (Integer[][]) this.blankAtom().get();
		
		String[] numbers = rep.split("-");
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				try {
					representation[i][j] = Parser.stringToInt(numbers[i*(_size*_size) + j]);
				} catch (Exception e) {
					representation[i][j] = null;
				}
			}
		}

		Atom repA = new Atom(representation);
		repA.setFitness(this.calculateFitness(repA));
		return repA;
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
				for(int x = 1; x <= square.length; x++) {
					boolean found = false;
					for(int j = 0; j < square.length; j++) {
						if (square[i][j] != null && square[i][j] == x) {
							found = true;
							break;
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
				for(int x = 1; x <= square.length; x++) {
					boolean found = false;
					for(int j = 0; j < square.length; j++) {
						if (square[j][i] != null && square[j][i] == x) {
							found = true;
							break;
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
			
			int row = 0;
			int col = 0;
			for(int loops = 0; loops < square.length; loops++) {
				for(int x = 1; x <= square.length; x++) {
					boolean found = false;
					for(int i = row; i < row + _size; i++) {
						for(int j = col; j < col + _size; j++) {
							if (square[i][j] != null && square[i][j] == x) {
								found = true;
								continue;
							}
						}
					}
					
					if (!found)
						total++;
				}
				
				col += _size;
				if (col >= square.length) {
					col = 0;
					row += _size;
				}
			}
			
			return total;
		});
	}

	@Override
	/**
	 * There's really no objective function to enforce here 
	 * other than minimize hard constrains
	 */
	protected void setObjectiveFunction() {
		_objective = (Atom a) -> {
			double totalScore = Math.pow(_size, 4) * 3;
			return totalScore;
		};
	}


	@Override
	public String humanize(Atom atom) {
		Integer[][] rep = (Integer[][]) atom.get();
		String representation = "";
		for (int i = 0; i < this._size*this._size; i++) {
			for (int j = 0; j < this._size*this._size; j++) {
				if (rep[i][j] != null) {
					representation += Integer.toString(rep[i][j]);
				}
				
				if(((j+1) % _size) == 0)
					representation += ".";
				else
					representation += "-";
			}
			representation += "\r\n";
			if(((i+1) % _size) == 0) {
				for(int k = 0; k < (_size*_size)+_size; k++) {
					representation += ".";
				}
				representation += "\r\n";
			}
		}
		
		return representation;
	}

	@Override
	public String dehumanize(String rep) throws Exception {
		String atom = "";
		String lines[] = rep.split("\\r?\\n");
		
		// size of the square not set correctly
		if(lines.length != (_size*_size))
			throw new Exception("Size of the problem is incorrect");
		
		for (int i = 0; i < lines.length; i++) {
			String digits[] = lines[i].split("[.-]", -1);
			for (String digit: digits) {
				try {
					digit = digit.trim();
					Parser.stringToInt(digit);
					atom += digit;
				} catch (Exception e) {}
				
				atom += "-";
			}
		}
		atom = atom.substring(0, atom.length()-1);

		return atom;
	}

	@Override
	protected ArrayList<String> getStates(String avoidSelf) {
		ArrayList<String> states = new ArrayList<String>();
		
		// all numbers from 1 to n^2 are allowed
		for(int i = 1; i <= _size * _size; i++) {
			states.add(Integer.toString(i));
		}
		
		if (avoidSelf != null) {
			while(states.remove(avoidSelf));
		}
		if (states.size() == 0) {
			return this.getAllowedStates(null);
		}

		return states;
	}


	@Override
	public ArrayList<Atom> neighbors() {
		// TODO Auto-generated method stub
		return null;
	}
}
