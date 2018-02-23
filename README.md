# boi-ai
Boi-ai is a simple boilerplate to test AI algorithms and problems. It has basic implementations for a number of NP-complete problems, and the algorithms designed to solve them. The representation of the problem can also be changed.
The main goal of this is to have a simple code that allows to test theories with little setup. For example, if I want to test that a new representation for the Nurse Scheduling Problem takes less time than another one, it's just a matter of setting those to representations, but the Problem and Algorithm to solve it should remain practically the same.

This is not supposed to be a masterpiece compilation of all representations and algorithm variations there are, just a quick and easy testing ground for new ideas. And I guess also a learning experience for AI beginners.

This code is meant to be played with, so it doesn't have a visual interface and relies on rudimentary config setups. Go to the docs to get basic assumptions and explanations of all problems and algorithms implemented.

## Get started

### Installation
Just download the project or make a clone of it.

### How to run it
For now the options are Eclipse or Ant.

###### Eclipse
Import the project, you have to build the classpath yourself, but it's just importing `jre` and the libraries in `lib`.  
Import the root folder `boi-ai`, make `src` the `Source folder`, add `jre` as a library.

###### Ant
- `ant` to compile sources and create docs
- `ant run` to run the program
- `ant test` to test (once there are real tests in place)


### Components
To prove an hypothesis in AI what I normally have to do is code all from scratch. Problem and algorithm. There is also a big part that is the representation used. For example, a sudoku could be represented by a square matrix, or a linked list, and the effect of using one or the other can be process time, memory, or other. A solution for a problem is effectively a combination of a representation and the implementation of the algorithm.
Therefore in this project, everything falls within one of those three objects:
- Problem
- Representation
- Algorithm

The `Core.java` file is the one that organizes what problem (sudoku or NSP?), representation (square matrix or list?) and algorithm (genetic or ants?) is going to be run together. To test, you can do something like


	public static void main(String[] args) throws NumberFormatException, Exception {
		String problem = FileReader.toString("examples/problems/sudoku/9x9easy.txt");
		Problem sudoku = new Sudoku();
		sudoku.config("SquareMatrix", "", "1, 2, 3");
		sudoku.setup(problem);

		// read config file and turn on solution
		int iterations = 100000;
		int population = 20;
		double mutation = 0.2;
		double crossover = 0.8;
		Algorithm algorithm = new Genetic();
		algorithm.config(iterations, null, null);
		algorithm.setProblem(sudoku);
		algorithm.setup(population, mutation, crossover);
		algorithm.run();
		Atom solution = algorithm.getBestSolution();
		System.out.println(sudoku.getRepresentation().humanize(solution));
		System.out.println(solution.getFitness());
		System.out.println(algorithm.getStats());
		
		// let solution run until it needs to stop (time, memory, iterations)
		
		System.out.println("Finished execution");
	}


#### Core
Sets what components are going to be used.
In charge of configuration of all variable aspects, like defining the basic data for the problem and the constraints that are going to be enforced.
This part is by far the most messy one, you have to delete and write code to test things.

#### Problem
Stores all data pertinent only to the problem. The representation and algorithm selected can have access to it. Defines what the objective function is (in words) and H & S constraints are (in words). Defines which constraints are going to be enforced (but not the math function with which to enforce).

#### Representation
Defines how the search space is constructed and how the problem variables are going to be used and implemented.
In here all constraints are translated to math functions to be implemented and quantified.

#### Solution
Picks up the representation for the problem and iterates over it. It's hard to make everything work for all algorithms, so some representations might not work well in combination, keep that in mind. 

### Setup
There's example files for each problem on the ` examples` folder.

# Docs
Maybe you need some context or info about a problem or algorithm. Go to the [docs folder](docs/en/index.md) :)