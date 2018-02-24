package com.nizite.boi_ai.representations.sudoku;

import java.util.ArrayList;
import java.util.Random;

import com.nizite.boi_ai.Config;
import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Lambda;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;

/**
 * Square n x n matrix
 * Random numbers (< n^2) in every empty space.
 * Depends on penalizations to enforce constrains.
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class SquareRandom extends Representation {

	/**
	 * @see Sudoku#_size
	 */
	protected int _size;
	
	/**
	 * @see Sudoku#_square
	 */
	protected String _square;
	
	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	@Override
	public void setProblem(Object[] problem) throws Exception {
		_size = (int) problem[0];
		_square = (String) problem[1];
		_problem = this.dehumanize(_square);
	}

	/**
	 * Defines functions per each constraint.
	 * Adds "one point" per each constraint broken
	 * 
	 * TODO: filter by array
	 * TODO @todo should add one point per constraint broken in general or should it stay in each representation?
	 * 
	 * @see Sudoku#setConstraints
	 * @see Representation#setConstraints
	 */
	@Override
	protected void setConstraints() {
		_constraints = new ArrayList<Lambda>();

		// Each row must have all numbers 1-n^2
		_constraints.add((Atom a) -> {
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
		_constraints.add((Atom a) -> {
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
		_constraints.add((Atom a) -> {
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
	
	/**
	 * There's really no objective function to enforce here 
	 * other than minimize hard constraints, for which we penalize with 3 points for each hard constraint.
	 * 
	 * TODO @todo change this to take into consideration soft and hard constraints, with different penalizations
	 * TODO @todo this is super not used... should be used instead of fitness?
	 */
	@Override
	protected void setObjectiveFunction() {
/*		_objective = (Atom a) -> {
			double totalScore = Math.pow(_size, 4) * 3;
			return totalScore;
		};*/
	}
	
	/**
	 * Blank matrix the size of the full sudoku square
	 */
	@Override
	public Atom blankAtom() {
		return new Atom(new Integer[_size*_size][_size*_size]);
	}

	/**
	 * Puts initial problem numbers in place and adds random numbers to all other blank spaces
	 */
	@Override
	public Atom createAtom() {
		Integer[][] newSquare = (Integer[][]) this.blankAtom().get();
		Integer[][] problem = (Integer[][]) _problem.get();

		// set all random
		Random rn = new Random();
		for (int i = 0; i < _size*_size; i++) {
			for (int j = 0; j < _size*_size; j++) {
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

	/**
	 * TODO @todo check what to do with objective func and this
	 */
	@Override
	public double calculateFitness(Atom atom) {
		double totalScore = 0.0;
		for(Lambda constraint : _hard) {
			totalScore += constraint.calc(atom);
		}
		
		return totalScore;
	}

	/**
	 * Separated program-created string {@link Representation#stringToAtom} into pieces and
	 * create an Atom out of it.
	 * Any invalid value for sudoku (not natural numbers) are translated to null.
	 */
	@Override
	public Atom stringToAtom(String rep) {
		Integer[][] representation = (Integer[][]) this.blankAtom().get();
		
		String[] numbers = rep.split(Config.ATOM_SPLIT_CHAR);
		for (int i = 0; i < _size*_size; i++) {
			for (int j = 0; j < _size*_size; j++) {
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

	/**
	 * One line, separating each number by -
	 * Will leave empty if number is invalid
	 */
	@Override
	public String atomToString(Atom atom) {
		Integer[][] rep = (Integer[][]) atom.get();
		String representation = "";
		for (int i = 0; i < _size*_size; i++) {
			for (int j = 0; j < _size*_size; j++) {
				if (rep[i][j] != null) {
					representation += Integer.toString(rep[i][j]) + Config.ATOM_SPLIT_CHAR;
				} else {
					representation += Config.ATOM_SPLIT_CHAR;
				}
			}
		}
		
		// remove last "-"
		representation = representation.substring(0, representation.length()-1);
		
		return representation;
	}

	/**
	 * Converts the atom to a recognizable sudoku matrix separated by "-"  and "."
	 */
	@Override
	public String humanize(Atom atom) {
		Integer[][] rep = (Integer[][]) atom.get();
		String representation = "";
		for (int i = 0; i < _size*_size; i++) {
			for (int j = 0; j < _size*_size; j++) {
				representation += Parser.integerToString(rep[i][j]);
				
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
	public Atom dehumanize(String rep) throws Exception {
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
				
				atom += Config.ATOM_SPLIT_CHAR;
			}
		}
		atom = atom.substring(0, atom.length()-1);

		Atom atomObject = this.stringToAtom(atom);
		return atomObject;
	}

	@Override
	protected ArrayList<String> getStates(String avoidSelf) {
		ArrayList<String> states = new ArrayList<String>();
		
		// all numbers from 1 to n^2 are allowed
		for(int i = 1; i <= _size * _size; i++) {
			states.add(Integer.toString(i));
		}

		return states;
	}

	/**
	 * Pick one random row,
	 * pivot all numbers of the row,
	 * don't pivot the ones in the problem
	 */
	@Override
	public ArrayList<Atom> getNeighbors(Atom current) {
		ArrayList<Atom> neighbors = new ArrayList<Atom>();
		
		Random rn = new Random();
		int row = rn.nextInt(_size*_size);
		Integer[][] toPivot = (Integer[][]) current.get();
		Integer[][] problem = (Integer[][]) _problem.get();

		for (int i = 0; i < _size*_size; i++) {
			for (int j = i; j < _size*_size; j++) {
				// if [i][j] has number, don't move it
				if (problem[row][i] != null || problem[row][j] != null)
					continue;
				
				//copy
				Integer[][] copy = (Integer[][]) this.blankAtom().get();
				for(int k = 0; k < toPivot.length; k++)
					  for(int l = 0; l < toPivot[k].length; l++)
					    copy[k][l] = toPivot[k][l];

				int temp = copy[row][i];
				copy[row][i] = copy[row][j];
				copy[row][j] = temp;
				
				// replace matrix with items on _problem
				for (int x = 0; x < _size*_size; x++) {
					for (int y = 0; y < _size*_size; y++) {
						if (problem[x][y] != null && problem[x][y] != 0) {
							copy[x][y] = problem[x][y];
						}
					}
				}
				
				Atom atom = new Atom(copy);
				atom.setFitness(this.calculateFitness(atom));
				neighbors.add(atom);
			}
		}
		
		return neighbors;
	}
}