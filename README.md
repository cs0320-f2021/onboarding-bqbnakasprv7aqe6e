# README
### Project details

Name: Onboarding Project

Estimated time to completion: 20 hours

### Design choices 
This is a basic REPL that supports two functions: reading in star data and finding the nearest k stars to a given input star or location.

The command **stars <filename\>** may be used to load in star position information. If run multiple times, data will be processed anew with each iteration.

The k nearest stars computation may be prompted in one of two ways: **naive_neighbors \<k> \<x> \<y> <z\>** or **naive_neighbors \<k> <“name”>** where **k** is the number of nonnegative, integral number of nearest neighbors to find. For the first, **x**, **y**, and **z** represent the coordinates of the location from which to find the nearest neighbors. For the second, **name** is the name of a star, which must be given in quotes.  

-- high level design of your program

Explain the relationships between classes/interfaces.

If you don’t think a regular person could come up with the algorithm you wrote, explain how it works. Don’t be too specific, the lower level explanation should be in method/inline comments.

Discuss any specific data structures you used, why you created it, and other high level explanations.

### Bugs 
No known bugs.

### Tests 
-- Explain the testing suites that you implemented for your program and how each test ensures that a part of the program works.


### How to Use
To build:
`mvn package`

To run:
`./run`

To start the server:
`./run --gui [--port=<port>]`

