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
- `ant test` to test (once there are real tests in place

# Docs
Maybe you need some context or info about a problem or algorithm. Go to the [docs folder](docs/en/index.md) :)

# TODOs

0. Do the `TODOs` marked in code
1. Add logger to output more details about everything (for now forcefully using sysout
2. Add mvn for dependencies, or use ant(?)
3. Add GUI to select P-R-A
4. Implement tests
5. Implement linters?
6. Implement Template pattern instead of making classes abstract? https://en.wikipedia.org/wiki/Template_method_pattern