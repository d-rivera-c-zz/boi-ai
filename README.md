# boi-ai
boilerplaite for ai algorithms


#Flow
Problems
-------
Core -> config
               -> sets implemented soft, hard, and rep (empty)
Core -> setup
               -> setproblem --> instanciate unique problem variables
                  rep.setStuff      --> fill in rep object with implemented soft, hard, problem

Repres
--------
Problem -> setRep -> 
                     set soft hard and objective -> define the functions for each, should be static
Problem -> setsoft
           sethard -> instanciated
           setproblem -> def of problem
           set space --> figure out way to show space rep
           
Solution
-----------
Core -> start up solution
Core -> config
Core -> setPRoblem (in solution)
Core -> run
Core -> wait for it to finish


#Plans
Core
-------
Config -> basic config stuff, like how much to log, how long to run, when to fail, where are the files,
          what problem and solution to use
Puppetmaster -> Controller of the basic and main functions
Logger


Problems
--------
Setup -> basic problem setup, normally taken from a file (num of cols, num of resources, space)
Soft restrictions
Hard restrictions
Function objective(?)



Solutions
--------
Config -> set up time running, numb of iterations, 
Type of function to use (penilize soft restrictions or no admit them)
Init mini solutions: ants, crmosoms, bees
Basically: run a cicle, deliver results
Save best solution
Check time pass or iterations pass


Representations
-----------
way to represent the problem, is not link to a problem itself or an algorithm

Examples
--------
1 to 3 examples for problems


Docs
----------