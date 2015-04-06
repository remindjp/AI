import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// This class maintains the board state and checks for legality
public class BoardManager {
	private GoAI ai;
	private boolean aiEnabled;
	private Queue<Point> pos;
	
	public static final String WHITE = "o";
	public static final String BLACK = "x";
	public static final String LIBERTY = "L";
	public static final String EMPTY = ".";
	
	public static final String KO = "K";
	private int size;
	private Group[][] board; //Group[][] instead of Point[][] b/c efficiency
	private Scanner s;
	private HashSet<Group> activeGroups;
	private int whiteCaps;
	private int blackCaps;
	private int koX;
	private int koY;
	
	public BoardManager(int size, GoAI ai, boolean aiEnabled, 
			Queue<Point> pos) {
		this.pos = pos;
		this.ai = ai;
		this.aiEnabled = aiEnabled;
		
		this.koX = -1;
		this.koY = -1;
		this.whiteCaps = 0;
		this.blackCaps = 0;
		activeGroups = new HashSet<Group>();
		
		this.size = size;
		this.board = new Group[this.size][this.size];
		for(int i =0; i < this.size; i++) {
			for(int j =0; j < this.size; j++) {
				this.board[i][j] = new Group(i, j, 0);
			}			
		}		
		this.s = new Scanner(System.in);
	}
	
	//set up pos and do turns
	public void startGame() {
		int z = 1;
		if(pos != null) {
			while(!pos.isEmpty()) {
				Point p = pos.remove();
				int x = (int) p.getX();
				int y = (int) p.getY();
				
				place(new Group(x, y, z%2 + 1), x, y);
				z++;
			}
		}
		debugPrint();
		turnLoop(z);
	}
	
	public void turnLoop(int z) {

		//dummy to enter second loop
		int x = -1;
		int y = -1;
		Group newGroup = new Group(x, y, 0);
		
		while(true) {
			//prints board with space padding to align
			System.out.print("  ");
			for(int i =0; i < size; i++) {
				if(i < 10) System.out.print(" ");
				System.out.print(" " + i);
			}
			System.out.println();
			
			for(int i =0; i < size; i++) {
				if(i < 10) System.out.print(" ");
				System.out.print(i);
				for(int j =0; j < size; j++) {
					System.out.print("  " + board[i][j]); 
				}			
				System.out.println();
			}
			//second legal check loop
			while(!isLegal(newGroup) || (x==koX && y==koY)) {

				System.out.println();
				
				if(aiEnabled && z%2==0) {
					Group[][] nB = new Group[size][size];
					for (int c = 0 ; c < size; c ++) {
						for (int v = 0 ; v < size; v ++) {
							nB[c][v] = board[c][v].copy();
							}							
						}
					
					ai.initMonteCarlo(nB, null);
					ai.simulate(5000);

					x = ai.aiX();
					y = ai.aiY();
					
				} else {
					//Asks for next move
					System.out.println("x Coordinate of new move? ");
					x= s.nextInt();		
					System.out.println("y Coordinate of new move? ");
					y= s.nextInt();	
					//sends move to AI and a surrounding area
				}
				
				newGroup = new Group(x, y, z%2 + 1);
			}	
			//represents a move
			z++;
			place(newGroup, x, y);
			
			//debug
			//debugPrint();
		}
	}
	
	//Actual modification after move legality is ok
	public void place(Group newGroup, int x, int y) {
		activeGroups.add(newGroup);
		board[x][y] = newGroup;
		
		//reset kos
		if(koX != -1 && koY != -1) board[koX][koY].setString("L");
		koX = -1;
		koY = -1;
				
		setLibs(newGroup);
	}
	
	
	//Immediate action after playing a piece. Finds the new liberties and interacts
	//with adjacent groups as needed.
	public void setLibs(Group g) {
		int gX =g.getX();
		int gY= g.getY();
		for (Group other : getAdjacent(gX, gY)) {
			
			// liberty
			if (other.toString().equals(EMPTY) || other.toString().equals(LIBERTY)
					|| other.toString().equals(KO)) {
				g.getLiberties().add(new Point(other.getX(), other.getY()));
				other.setString(LIBERTY);
			} else { 
				// Since we just placed this piece, remove this piece from all adjacent
				// groups' liberties.
				HashSet<Point> temp = new HashSet<Point>();
				temp.add(new Point(gX, gY));
				other.getLiberties().removeAll(temp);
				
				// Same color so combine
				if(g.toString().equals(other.toString())) {
					union(g, other);
				//different color,  maybe capture
				} else {
					captureCheck(gX, gY, other);
				}
			}
		}
	}
	
	//checks legality without modifying state
	public boolean isLegal(Group g) {
		int x = g.getX();
		int y = g.getY();
		//valid bounds and empty space
		if(x < 0 || x > 18 || y < 0 || y > 18) return false;
		if(!(board[x][y].toString().equals(EMPTY) ||
				board[x][y].toString().equals(LIBERTY))) return false;
		
		//returns if any adjacent group indicates possible legality, liberty or capture.
		for (Group other : getAdjacent(x, y)) {
			// has empty space so this move is always safe
			if(other.toString().equals(EMPTY)||other.toString().equals(LIBERTY)) return true;
			if(isEnemy(g.toString(), other.toString())) {
				if(other.getLiberties().size()==1) return true; // captures enemy
			} else { //links with an ally that must have >=2 liberties (-1 for this)
				if(other.getLiberties().size()>=2) return true;
			}
		}
		return false;
	}
	
	//combines two groups of the same color
	public void union(Group g, Group other) {
		g.getPieces().addAll(other.getPieces());
		g.getLiberties().addAll(other.getLiberties());
	
		//update Board to reference each piece to this group
		//and remove old group
		for (Point piece : other.getPieces()) {
			board[(int) piece.getX()][(int) piece.getY()] = g;
		}
		activeGroups.remove(other);		
	}
	
	//returns a set of immediately adjacent groups
	public HashSet<Group> getAdjacent(int x, int y) {
		HashSet<Group> adj = new HashSet<Group>();
		if(x + 1 < size) adj.add(board[x+1][y]);
		if(x - 1 >= 0) adj.add(board[x-1][y]);
		if(y + 1 < size) adj.add(board[x][y+1]);
		if(y - 1 >= 0) adj.add(board[x][y-1]);
		return adj;
	}
	
	//Captures the group as needed (if it has 0 liberties). Also handles ko
	public void captureCheck(int gX, int gY, Group target) {
		String enemy = "";
		if(target.getLiberties().isEmpty()) {
			HashSet<Point> pieces = target.getPieces();
			//Increase captured count
			if(target.toString().equals(BLACK)) {
				whiteCaps+=pieces.size();
				enemy = WHITE;
			} else {
				blackCaps+=pieces.size();
				enemy = BLACK;
			}
			
			//Remove pieces and group from board and add liberties to touching
			//enemy groups
			int x = -1;
			int y = -1;
			for(Point p : pieces) {
				
				boolean isEdge = false;
				x = (int) p.getX();
				y = (int) p.getY();
				for (Group potential : getAdjacent(x,y)) {
					//if enemy, add this point as a liberty
					if(isEnemy(potential.toString(), target.toString())){
						potential.getLiberties().add(new Point(x,y));
						isEdge = true;
					}
				}
				Group temp = new Group(x, y, 0);
				if(isEdge) { 
					temp.setString(LIBERTY);
				}
				board[x][y] = temp;
			}
			activeGroups.remove(target);

			//if only 1 piece then p = potentialKo, must iterate to get element
			if(pieces.size()==1) {
				if(board[gX][gY].getLiberties().size()==1) {
					// just capped a sole piece and you have 1 liberty -> ko 
					koX = x;
					koY = y;
					board[x][y].setString("K");
					
				}
			}
		}
	}
	//checks if opppsite colors given two strings
	public boolean isEnemy(String s1, String s2) {
		return (s1.equals(WHITE) || s1.equals(BLACK)) && 
				(s2.equals(WHITE) || s2.equals(BLACK)) &&
				(!s1.equals(s2));
	}
	
	public void debugPrint() {
		for (Group g : activeGroups) {
			System.out.print(g + " : ");
			System.out.println("pieces: " + g.getPieces());
			System.out.println("liberties: " + g.getLiberties());
			System.out.println();
		}
	}
}
