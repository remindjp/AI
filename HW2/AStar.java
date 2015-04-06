// Jingpeng Wu CS6364 HW2
//A* Algorithm that uses the custom graph class to store data
//Has adjustable W value and a custom heuristic function

import java.util.ArrayList;


public class AStar {
	//Adjust this
	public static final double W=.5;
	
	private static CustomGraph g;
	
	
	public static void main(String[] args) {
		g = create();
		run(g, "ORADEA");
		
	}
	
	// initializes a new custom map with Romania data
	public static CustomGraph create() {
		CustomGraph g = new CustomGraph(20);
		
		//Add names
		String[] names = {"ORADEA","ZERIND","ARAD","TIMISOARA","LUGOJ","MEHADIA","DOBRETA",
				"SIBIU","RIMNICU_VILCEA","CRAIOVA","FAGARAS","PITESTI","GIURGIU","BUCHAREST",
				"NEAMT","URZICENI","IASI","VASLUI","HIRSOVA","EFORIE"};
		for(int i = 0; i < names.length; i++) {
			g.addName(names[i]);
		}
		
		//Add links
		g.addLink("ORADEA", "ZERIND", 71);
		g.addLink("ORADEA", "SIBIU", 151);
		g.addLink("ZERIND", "ARAD", 75);
		g.addLink("ARAD", "TIMISOARA", 118);
		g.addLink("ARAD", "SIBIU", 140);
		g.addLink("TIMISOARA", "LUGOJ", 111);
		g.addLink("LUGOJ", "MEHADIA", 70);
		g.addLink("MEHADIA", "DOBRETA", 75);
		g.addLink("DOBRETA", "CRAIOVA", 120);
		g.addLink("SIBIU", "FAGARAS", 99);
		g.addLink("SIBIU", "RIMNICU_VILCEA", 80);
		g.addLink("RIMNICU_VILCEA", "PITESTI", 97);
		g.addLink("RIMNICU_VILCEA", "CRAIOVA", 146);
		g.addLink("CRAIOVA", "PITESTI", 138);
		g.addLink("FAGARAS", "BUCHAREST", 211);
		g.addLink("PITESTI", "BUCHAREST", 101);
		g.addLink("GIURGIU", "BUCHAREST", 90);
		g.addLink("BUCHAREST", "URZICENI", 85);
		g.addLink("NEAMT", "IASI", 87);
		g.addLink("URZICENI", "VASLUI", 142);
		g.addLink("URZICENI", "HIRSOVA", 98);
		g.addLink("IASI", "VASLUI", 92);
		g.addLink("HIRSOVA", "EFORIE", 86);
		
		//Add straight distance to Bucharest
		g.addDist("ARAD", 366);
		g.addDist("BUCHAREST", 0);
		g.addDist("CRAIOVA", 160);
		g.addDist("DOBRETA", 242);
		g.addDist("EFORIE", 161);
		g.addDist("FAGARAS", 176);
		g.addDist("GIURGIU", 77);
		g.addDist("HIRSOVA", 151);
		g.addDist("IASI", 226);
		g.addDist("LUGOJ", 244);
		g.addDist("MEHADIA", 241);
		g.addDist("NEAMT", 234);
		g.addDist("ORADEA", 380);
		g.addDist("PITESTI", 100);
		g.addDist("RIMNICU_VILCEA", 193);
		g.addDist("SIBIU", 253);
		g.addDist("TIMISOARA", 329);
		g.addDist("URZICENI", 80);
		g.addDist("VASLUI", 199);
		g.addDist("ZERIND", 374);
		
		//angles for custom heuristic
		g.addAngle("ARAD", 117);
		g.addAngle("BUCHAREST", 360);
		g.addAngle("CRAIOVA", 74);
		g.addAngle("DOBRETA", 82);
		g.addAngle("EFORIE", 282);
		g.addAngle("FAGARAS", 142);
		g.addAngle("GIURGIU", 25);
		g.addAngle("HIRSOVA", 260);
		g.addAngle("IASI", 202);
		g.addAngle("LUGOJ", 102);
		g.addAngle("MEHADIA", 92);
		g.addAngle("NEAMT", 181);
		g.addAngle("ORADEA", 131);
		g.addAngle("PITESTI", 116);
		g.addAngle("RIMNICU_VILCEA", 115);
		g.addAngle("SIBIU", 123);
		g.addAngle("TIMISOARA", 105);
		g.addAngle("URZICENI", 247);
		g.addAngle("VASLUI", 222);
		g.addAngle("ZERIND", 125);
		
		return g;
	}
	
	// run the algorithm
	public static void run(CustomGraph g, String start) {
		// travel path
		ArrayList<String> path = new ArrayList<String>();
		// total real cost to travel up to the previous node
		int totalCost = 0;
		
		//String start = "ORADEA";

		path.add(start);
		String current = start;

		int[][] adj = g.getAdjDist();
		int[] line = g.getLineDist();
		ArrayList<String> names = g.getNames();
		while(!path.get(path.size() -1).equals("BUCHAREST")) {
			// If node is adjacent,  calculate value and store in values.
			System.out.println("At " + path.get(path.size() -1) + ", Distance traveled: " +
			totalCost + ", path: " + path + ", Nodes expanded: " + (path.size() - 1));
			
			double min = 99999.0;
			String next = "";
			int nextDist = -1;
			
			System.out.print("Options: ");
			// find the next destination
			for(int i = 0; i < g.getSize(); i ++) {
				int dist = adj[names.indexOf(current)][i];
				if(dist > 0) {
					double value = calculateValue(dist, totalCost, line[i]);
					//Custom heuristic
					//double value = totalCost + dist + customH(line[i], g.getAngleDist()[i]);
					if (min > value) {
						min = value;
						next = names.get(i);
						nextDist = dist;
					}
					System.out.print(names.get(i) + "=");
					System.out.print(value + " ");
				}
				
			}
			//traverse
			current = next;
			path.add(current);
			totalCost += nextDist;
			
			System.out.println();
			System.out.println("Next city is: " + next);
			System.out.println();
		}
		System.out.println("At " + path.get(path.size() -1) + ", Distance traveled: " + 
		totalCost + ", path: " + path + ", Nodes expanded: " + (path.size() - 1));
	}
	
	// calculates value, set value here
	public static double calculateValue(int dist, int totalCost, int h) {
		double fStar = (1.0 - W) * (double) (totalCost + dist);
		double hStar = W * (double) h;
		return fStar + hStar;
	}
	
	// returns half of the manhattan distance
	public static int customH(int angle, int dist) {
		double distD = (double) dist;
		double x = distD * Math.cos(Math.toRadians(angle));
		double y = distD * Math.sin(Math.toRadians(angle));
		// total number of adjacent nodes
		return (int) (Math.abs((.5)* ( x + y)));
	}
}
