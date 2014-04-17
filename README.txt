------------------------------------------------------
Catherine Morgan
Game of Life
Vassar College
CS 102, Fall 2012

Description written by Professor Tom Ellman
------------------------------------------------------
Play “Life”, invented by the mathematician John Conway. 

The program simulates and animates the birth, life and death of a population of individuals inhabiting a square grid. The program begins by presenting the user with an empty grid. The user has the following options: Press the “Randomize” button to generate an initial population that is randomly distributed over the cells of the grid; Click on an individual cell to create or destroy life in the designated 
cell; Press the “Next” button to process one generation of the simulation; Press the “Start” button to commence a simulation that runs for multiple generations, until the user presses the “Stop” button; Press the “Clear” button to kill all life in the grid; Press the “Exit” button to quit. During the simulation, the program 
simulates the birth and death of objects in the grid, according to the following rules: 

• Birth: If a dead cell is surrounded by exactly three living cells, it becomes alive in the next generation. Otherwise, it remains dead in the next generation. 
• Life: If living cell is surrounded by either two or three other living cells, it is happy, and therefore 
remains alive in the next generation. 
• Death: If a living cell is surrounded by less than two, or more than three living cells, it dies of 
loneliness or overcrowding, and is therefore dead in the next generation. 
