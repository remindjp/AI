//This class keeps track of groups on the board which are formed by points
import java.awt.Point;
import java.util.HashSet;

public class Group {

	public static final String WHITE = "o";
	public static final String BLACK = "x";
	private String display;
	private HashSet<Point> pieces;
	private HashSet<Point> liberties;
	private int groupX;
	private int groupY;
	private int type;
	//used for cloning

	public Group(int x, int y, int type, HashSet<Point> pieces,
			HashSet<Point> liberties, String display) {
		this.type = type;
		this.groupX = x;
		this.groupY = y;
		if(pieces !=null) this.pieces = new HashSet<Point>(pieces);
		if(liberties !=null) this.liberties = new HashSet<Point>(liberties);
		this.display = display;
	}
	
	// maintains legal state on creation
	//type; 0 = empty intersection , 1 = 'o', 2 = 'x'
	public Group(int x, int y, int type) {
		this.type = type;
		this.groupX = x;
		this.groupY = y;
		
		//empty, do nothing
		if (type==0) {
			this.display = ".";
			return;
		}
		
		//black or white, add initial piece
		if (type==1){
			this.display = WHITE;
		} else {
			this.display = BLACK;
		}
		this.pieces = new HashSet<Point>();
		pieces.add(new Point(this.groupX,this.groupY));

		this.liberties = new HashSet<Point>();
		
	}
	
	public Group copy() {
		return new Group(groupX, groupY, type, pieces,
			liberties, display);
	}
	public String toString() {
		return display;
	}
	
	public void setString(String s) {
		display = s;
	}
	
	public HashSet<Point> getPieces() {
		return pieces;
	}
	
	public HashSet<Point> getLiberties() {
		return liberties;
	}	
	
	public int getX() {
		return groupX;
	}	
	
	public int getY() {
		return groupY;
	}
	
	public int getType() {
		return type;
	}
}
