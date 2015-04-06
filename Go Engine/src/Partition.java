import java.awt.Point;
import java.util.HashSet;

//A node representing the reduced game state of a micro 2rx2r grid

public class Partition {
	private int depth;
	private int iniX;
	private int iniY;
	private int radius;
	
	private int value;
	private Group[][] reducedBoard;
	
	public Partition(Group[][] reducedBoard, int radius, int iniX, int iniY, int depth) {
		this.depth = depth;
		
		this.reducedBoard = reducedBoard;
		this.radius = radius;
		this.iniX = iniX;
		this.iniY = iniY;
		
		displayState();
	}
	
	public int getDepth() {
		return depth;
	}
	
	
	public Group[][] getRB() {
		return reducedBoard;
	}
	
	
	public int getValue() {
		return value;
	}	
	
	public String toString() {
		//use to print board
		return "a";
	}
	
	//prints the miniboard
	public void displayState() {

		System.out.println("Board state @ depth " + depth);
		System.out.print("  ");
		for(int i = 0; i < 2 * radius + 1; i++) {
			if(iniY - radius + i < 10) System.out.print(" ");
			System.out.print(" " + (iniY - radius + i));
		}
		System.out.println();

		for(int i = 0; i < 2 * radius + 1; i++) {
			if(i < 10) System.out.print(" ");
			System.out.print(iniX - radius + i);
			for(int j = 0; j < 2 * radius + 1; j++) {
				System.out.print("  " + reducedBoard[i][j]); 
			}			
			System.out.println();
		}
	}
	
	public int getRadius() {
		return radius;
	}
	
	
//	public Group[][] reducedBoard(Group[][] board, int offX, int offY) {
//		Group[][] rB = new Group[2*radius + 1][2*radius + 1];
//		for (int i = 0; i < 2 * radius + 1; i++) { 
//			for (int j = 0; j < 2 * radius + 1; j++) { 
//				rB[i][j] = board[offX + i][offY + j].copy();
//			}				
//		}		
//		return rB;
//	}
//	//creates a partition with group duplication
//	public Partition createPart(Group[][] board, int x, int y, int depth) {
//		this.iniX = x;
//		this.iniY = y;
//
//		Group[][] rB = new Group[2*radius + 1][2*radius + 1];
//		
//		//not reduced
//		if (board.length != 2* radius + 1) {
//			//copies a non edge reduced board
//			if (iniX > radius && iniX < size - radius && iniY > radius 
//					&& iniY < size - radius) {
//				rB = reducedBoard(board, iniX - radius , iniY - radius );
//			}			
//		} else { //already reduced, just copy
//			rB = reducedBoard(board,0,0);
//		}
//		
//		return new Partition(rB, radius, iniX, iniY, depth);
//	}	
	
}
