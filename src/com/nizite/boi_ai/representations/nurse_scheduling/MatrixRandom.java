package com.nizite.boi_ai.representations.nurse_scheduling;

import java.util.ArrayList;
import java.util.Random;

import com.nizite.boi_ai.representations.Atom;
import com.nizite.boi_ai.representations.Lambda;
import com.nizite.boi_ai.representations.Representation;
import com.nizite.boi_ai.utils.Parser;

/**
 * Matrix of
 * 
 * day        1 | 2 | 3 | 4 | 5 | 6 | 7
 * shift 1
 * shift 2
 * shift 3
 * 
 * times x number of nurses.
 * Boolean 0 1 if nurse is schedule to work that day and shift
 * 
 * @author d-rivera-c
 */
public class MatrixRandom extends Representation {
	protected int _days;
	protected int _nurses;

	@Override
	public void setProblem(Object[] problem) throws Exception {
		_days = (int) problem[0];
		_nurses = (int) problem[1];
		// no initial representation of problem to start with
		// no dehuminize
	}

	@Override
	/**
	 * Minimize costs, that will mean minimize hard constraints broken.
	 * We start with a max "cost" of 1 unit per nurse per shift
	 */
	protected void setObjectiveFunction() {
		// TODO: I don't seem to be using this, delete?
	}

	@Override
	public Atom blankAtom() {
		return new Atom(new Boolean[_nurses][_days][3]);
	}

	@Override
	public Atom createAtom() {
		Boolean[][][] blank = (Boolean[][][]) this.blankAtom().get();
		
		// set all random
		Random rn = new Random();
		for (int i = 0; i < _nurses; i++) {
			for (int j = 0; j < _days; j++) {
				for (int k = 0; k < 3; k++) {
					blank[i][j][k] = rn.nextBoolean();
				}
			}
		}
		
		Atom atom = new Atom(blank);
		atom.setFitness(this.calculateFitness(atom));
		return atom;
	}

	@Override
	public double calculateFitness(Atom atom) {
		double totalScore = 0.0;

		for (Lambda constraint : _hard) {
			totalScore += constraint.calc(atom);
		}
		
		return totalScore;
	}
	
	/**
	 * TODO: filter by array
	 */
	@Override
	protected void setConstraints() {
		super.setConstraints();

		// All shifts must have at least 3 nurses
		_constraints.add((Atom a) -> {
			double total = 0.0;
			int subtotal = 0;
			Boolean[][][] matrix = (Boolean[][][]) a.get();
			
			for (int i = 0; i < _days; i++) {
				for (int j = 0; j < 3; j++) {
					for (int k = 0; k < _nurses; k++) {
						if (matrix[k][i][j]) {
							subtotal++;
						}
					}
					
					// add one for each shift with less than 3 nurses
					if (subtotal < 3) {
						total++;
					}
					subtotal = 0;
				}
			}
			
			return total;
		});
	}

	@Override
	public Atom stringToAtom(String representation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String atomToString(Atom atom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String humanize(Atom atom) {
		Boolean[][][] rep = (Boolean[][][]) atom.get();
		String representation = "";
		
		for (int i = 0; i < _nurses; i++) {
			representation += "Nurse " + (i+1) + "\r\n";
			
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < _days; k++) {
					if (rep[i][k][j]) {
						representation += "X";
					} else {
						representation += "-";
					}
					representation +=  " ";
				}
				representation += "\r\n";
			}
			
			representation += "\r\n\r\n";
		}
		
		return representation;
	}

	@Override
	public Atom dehumanize(String rep) throws Exception {
		return null;
	}

	@Override
	protected ArrayList<String> getStates(String avoidSelf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Atom> getNeighbors(Atom current) {
		ArrayList<Atom> neighbors = new ArrayList<Atom>();
		return neighbors;
	}


}
