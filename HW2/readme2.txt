Jingpeng Wu
CS6364 HW2
================================================================
To run jar from cmd line:
java -jar Astar.jar w h
w is the weight, ex .5 .25 or .75
h is the heuristic #, 0 for default, 1 for custom
================================================================
The data is stored using an object I created called a CustomGraph. 
It stores things in a simplistic manner using arrays and arraylists where there is a 
mapping from the name of a city to an index i. Then, there is an adjacency 2d array
and heuristic arrays using this index. The 2d array is like an adjacency matrix but 
instead of 1's and 0's, it has the real value of adjacent edges and 0 if there is no edge. 

The actual data of Romania is initialized through the AStar class where it is stored in an
instance of CustomGraph

The algorithm itself is just A*, where the evaluation function depends on the minimum weighted 
value to node n and the heuristic of the remaining value from n to the goal. 

The custom heuristic is the manhattan distance divided by two, which will always be an underestimate.

Results:

A*
At BUCHAREST, Distance traveled: 429, path: [ORADEA, SIBIU, RIMNICU_VILCEA, PITESTI, BUCHAREST], Nodes expanded: 4

W = .75
At BUCHAREST, Distance traveled: 461, path: [ORADEA, SIBIU, FAGARAS, BUCHAREST], Nodes expanded: 3

W = .25
Gets stuck between arad and zerind.