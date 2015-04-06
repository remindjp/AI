import java.util.ArrayList;
import java.util.Arrays;


public class CustomGraph { 
	private int size;
	// Like an adjacency matrix except instead of 1 or 0 for adjacent, it is 0 or the distance. 
	private int[][] adjDist;
	// straight line distances
	private int[] lineDist;
	// name at index i corresponds to i for the 2d-arrays
	
	// Angles used for custom heuristic
	private int[] angleDist;
	
	private ArrayList<String> names;
	public CustomGraph (int size) {
		this.size = size;
		this.adjDist = new int[size][size];
		this.lineDist = new int[size];
		this.names = new ArrayList<String>();
		this.angleDist = new int[size];
	}
	
	// Add a name to the names array
	public void addName(String name) {
		names.add(name);
	}
	
	// Adds a link symmetrically
	public void addLink(String name1, String name2, int distance) {
		adjDist[names.indexOf(name1)][names.indexOf(name2)] = distance;
		adjDist[names.indexOf(name2)][names.indexOf(name1)] = distance;
		
	}
	
	// Adds data about the distance to Bucharest 
	public void addDist(String name, int dist) {
		lineDist[names.indexOf(name)] = dist;
	}
	
	// Adds data about the angle to Bucharest 
	public void addAngle(String name, int angle) {
		angleDist[names.indexOf(name)] = angle;
	}
	
		
	public void printState() {
		System.out.println("Adjacency distance: ");
		for(int i =0; i < size; i ++) {
			for(int j =0; j < size; j ++) {
				System.out.print(adjDist[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Names: ");
		System.out.println(names);
		
		System.out.println("Direct distance: ");
		System.out.println(Arrays.toString(lineDist));
		
	}
	
	
	
	public int getSize() {
		return size;
	}
	
	public int[][] getAdjDist() {
		return adjDist;
	}
	
	public int[] getLineDist () {
		return lineDist;
	}
	
	public ArrayList<String> getNames() {
		return names;
	}
	
	public int[] getAngleDist() {
		return angleDist;
	}
}
