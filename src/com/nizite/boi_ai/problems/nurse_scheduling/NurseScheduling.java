package com.nizite.boi_ai.problems.nurse_scheduling;

import com.nizite.boi_ai.problems.Problem;
import com.nizite.boi_ai.utils.Parser;

/**
 * Basic implementation of the problem.
 * Obj: minimizing total costs while maximizing nurses preferences and requests, and equally distribute workload between nurses
 * Assumption:
 *  - the week has 7 days
 *  - the day has three shifts
 * TODO @todo These can be made into variables later.
 *
 * Variables: 
 *  - Time period (days)
 *  - Number of nurses available
 * 
 * Takes into account few constraints:
 *  - demand for each shift (how many nurses do we need in one shift)
 *  - Number of shifts per day a nurse is allowed to work
 *  - Number of consecutive shifts a nurse is allowed to work
 *  - Nurse specialization (shifts that can be assign for each particular nurse)
 *  - Maximum number of consecutive days of work
 *  - Minimum amount of rest time between two shifts
 *  - Isolated days of work or days off
 *  - Nurses preference
 *  - Minimum / maximum number of shifts assign to a nurse in a week 
 * While these constraints can be coded to accept variables, the first implementation of this will assume
 * the variable elements of it will be hardcoded. Only a handful of this possible constraints are implemented here.
 * 
 * TODO @todo unhardcode variable elements of constraints if there's a need to
 * 
 * @author d-rivera-c
 * @version 0.1
 */
public class NurseScheduling extends Problem {

	protected int _days;
	protected int _numberOfNursesAvailable;

	/* *********************** */
	/*      DEFINED FUNCS      */
	/* *********************** */

	@Override
	public void setup(String setup) throws NumberFormatException, Exception {
		String lines[] = setup.split("\\r?\\n");
		
		this.setPeriod(Parser.stringToInt(lines[0]));
		this.setNumberOfNurses(Parser.stringToInt(lines[1]));
	}

	@Override
	public Object[] getInfo() {
		Object[] info = new Object[2];
		info[0] = _days;
		info[1] = _numberOfNursesAvailable;
		return info;
	}
	
	/* *********************** */
	/*     OVERLOAD FUNCS      */
	/* *********************** */

	@Override
	protected void setConstraints() {
		super.setConstraints();
		_constraints.add("All shifts must have at least 3 nurses");
		_constraints.add("A nurse is allowed to work a maximum of 1 shift per day");
		_constraints.add("A nurse is allowed to work a maximum of 1 consecutive shift per day");
		_constraints.add("A nurse can work no more than 7 days in a row");
		_constraints.add("A nurse can rest for a maximum of 3 days in a row");
		_constraints.add("A nurse can have no less than 2 consecutive work days");
	}


	@Override
	protected void setObjectiveFunction() {
		super.setObjectiveFunction();
		_objectiveFunction = "Minimizing total costs while maximizing nurses preferences and requests, "
							+ "and equally distribute workload between nurses";
	}

	/* *********************** */
	/* PROBLEM PERTINENT FUNCS */
	/* *********************** */

	private void setPeriod(int period) throws Exception {
		if (period <= 0) {
			throw new Exception("Invalid period set for problem");
		}
		_days = period;
	}
	
	private void setNumberOfNurses(int numberOfNurses) throws Exception {
		if (numberOfNurses <= 0) {
			throw new Exception("Invalid quantity of nurses available");
		}
		_numberOfNursesAvailable = numberOfNurses;
	}
	
	/* *********************** */
	/*        GETTERS          */
	/* *********************** */
	
	/**
	 * @see NurseScheduling#_days
	 */
	public int getDays() {
		return _days;
	}

	/**
	 * @see NurseScheduling#_numberOfNursesAvailable
	 */
	public int getNumberOfNursesAvailable() {
		return _numberOfNursesAvailable;
	}
}
