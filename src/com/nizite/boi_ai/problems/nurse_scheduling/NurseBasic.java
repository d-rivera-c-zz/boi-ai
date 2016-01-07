package com.nizite.boi_ai.problems.nurse_scheduling;

import java.util.ArrayList;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.utils.Parser;

/**
 * Basic implementation of the problem.
 * Obj: minimizing total costs while maximizing nurses preferences and requests, and equally distribute workload between nurses
 * Variables: 
 *  - Time period (days)
 *  - Number of shifts
 * Takes into account few constrains:
 *  - Number of shifts per day a nurse is allowed to work
 * 	- Demand for each shift (how many nurses do we need in one shift)
 *  - Nurse specialization (shifts that can be assign for each particular nurse)
 *  - Maximum number of consecutive days of work
 *  - Minimum amount of rest time between two shifts
 *  - Isolated days of work or days off
 *  - Nurses preference
 *  - Minimum / maximum number of shifts assign to a nurse in a week 
 * @author d-rivera-c
 * @version 0.1
 */
public class NurseBasic extends Problem {
	
	protected int _days;
	protected int _numShifts;
	protected int _numNurses;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */
	
	@Override
	public void setup(String setup) throws NumberFormatException, Exception {
		this.setProblem(setup);
		
	}

	@Override
	protected void setProblem(String problem) throws Exception {
		String lines[] = problem.split("\\r?\\n");
		
		this.setPeriod(Parser.stringToInt(lines[0]));
		this.setShifts(Parser.stringToInt(lines[1]));
		this.setNurses(Parser.stringToInt(lines[2]));
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */

	@Override
	/**
	 * Direct the class load to all representations specifically for Nurse Scheduling problem
	 * Called by (@link #config())
	 * @param rep
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void setRepresentation(String rep) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		rep = "com.nizite.boi_ai.representations.nurse_scheduling."+rep;
		super.setRepresentation(rep);
	}


	@Override
	/**
	 * Some restrictions on this problem might be considered either soft or hard constrains,
	 * it's up to the selection of "implemented soft constraints" to determine if it will be seen as H or S
	 */
	protected void setSoftConstraints() {
		this._soft = new ArrayList<String>();
		_soft.add("A nurse can have no less than 2 consecutive work days");
	}

	@Override
	/**
	 * All sudoku constraints are hard.
	 * If not all are enforced (@link #setImplementedHard(int[] hard)
	 * the solution will not be what's expected for the problem
	 */
	protected void setHardConstraints() {
		super.setHardConstraints();
		this._hard.add("A nurse is allowed to work a maximum of 1 shift per day");
		this._hard.add("Each shift must have at least 2 nurses");
		this._hard.add("A nurse can work no more than 3 days in a row");
		this._hard.add("A nurse can rest for a maximum of 3 days in a row");
	}


	@Override
	protected void setObjectiveFunction() {
		super.setObjectiveFunction();
		this._objectiveFunction = "minimizing total costs while maximizing nurses preferences and requests, and equally distribute workload between nurses";
	}
	
	/* *********************** */
	/* PROBLEM PERTINENT FUNCS */
	/* *********************** */

	private void setPeriod(int period) throws Exception {
		if(period <= 0) {
			throw new Exception("Invalid period set for problem");
		}
		_days = period;
	}
	
	private void setShifts(int shifts) throws Exception {
		if(shifts <= 0) {
			throw new Exception("Invalid number of shifts set for problem");
		}
		_numShifts = shifts;
	}
	
	private void setNurses(int nurses) throws Exception {
		if (nurses <= 0)
			throw new Exception("Invalid number of nurses for problem");
		
		_numNurses = nurses;
	}
}
