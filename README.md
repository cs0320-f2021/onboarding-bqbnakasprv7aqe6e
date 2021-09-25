# README
### Project details

Name: Onboarding Project

Estimated time to completion: 20 hours

### Design choices 
This is a basic REPL that supports two functions: reading in star data and finding the nearest k stars to a given input star or location.

The command **stars <filename\>** may be used to load in star position information. If run multiple times, data will be processed anew with each iteration.

The k nearest stars computation may be prompted in one of two ways: **naive_neighbors \<k> \<x> \<y> <z\>** or **naive_neighbors \<k> <“name”>** where **k** is the number of nonnegative, integral number of nearest neighbors to find. For the first, **x**, **y**, and **z** represent the coordinates of the location from which to find the nearest neighbors. For the second, **name** is the name of a star, which must be given in quotes.

There are six classes: CSVParser, Main, MathBot, NaiveNeighbors, Star, and StarNotFoundException.
The REPL is run in the Main class. The CSVParser contains the method parseStars(), which is called in the Main
class when the command **stars** is input by the user. The NaiveNeighbors class contains all methods to calculate the nearest
neighbors to a given star. The Star class has fields corresponding to the data provided in the CSV files.

The algorithm to find the nearest neighboring stars, findNearestNeighbors, dynamically performs comparisons to build and ultimately return the closest stars in a list.

Star data is stored in an ArrayList for ease of access.
### Bugs 
No known bugs.

### Tests
There are three testing suites: MathBotTest, NaiveNeighbors, and StarTest. Each tests all methods within its respective class.

In addition, there are a number of system tests that check various combinations of inputs and data, as well as exceptions.


### How to Use
To build:
`mvn package`

To run:
`./run`

To start the server:
`./run --gui [--port=<port>]`

