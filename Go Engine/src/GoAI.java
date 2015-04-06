import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class GoAI {
	private Node root;
	private Group[][] nB;
	private int size;
	private int x;
	private int y;
	
	public GoAI (int size){
		this.size = size;
		x = -1;
		y = -1;
		root = new Node(0, 0 , 0, 0); //x, y wont matter, depth = 0 check for root;
	}
	
	public void initMonteCarlo(Group[][] nB, Queue<Point> pos) {
		this.nB = nB;

	}
	
	public void simulate(int times) {
		root = new Node(0, 0 , 0, 0); //x, y wont matter, depth = 0 check for root;
		for(int i = 0; i < times; i++) {
			//clone
			Group[][] board = new Group[size][size];
			
			for (int c = 0 ; c < size; c ++) {
				for (int v = 0 ; v < size; v ++) {
					board[c][v] = nB[c][v].copy();
				}							
			}
			//Used to create new nodes
			Queue<Integer> xList = new LinkedList<Integer>();
			Queue<Integer> yList = new LinkedList<Integer>();
			
//			for(int k = 0; k < nB.length; k++) {
//				for(int j = 0; j < nB.length; j++) {
//					System.out.print(board[k][j] + " ");
//				}
//				System.out.println();
//			}
			
			Simulator s = new Simulator(board, size, null, xList, yList);
			s.startGame();
			//retrieve coordinates and build tree
			int nextX = 0;
			int nextY = 0;
			
			Node current = root;
			Node newN = null;
			while(!xList.isEmpty()) {
	
				nextX = xList.remove();
				nextY = yList.remove();		
				newN = new Node(nextX, nextY, current.getDepth() + 1, s.getValue());
				
				current.addChild(newN); //check to see if it exists or we can add
				current = newN;
			}
			//System.out.println(s.getValue());
			//printTree();  //debug
		}
		
		//find best move
		int max = -9999;
		Node best = null;
		for(Node c : root.getChildren()) {
			int v = c.getValue();
			if(v > max) {
				max = v;
				best = c;
			}
		}
		x = best.getX();
		y = best.getY();
		
	}
	
	public int aiX() {
		return x;
	}
	
	public int aiY() {
		return y;
	}
	
	
	public void printTree() {
		printNode(root);
	}
	
	//prints branches
	public void printNode(Node n) {
		for(Node child : n.getChildren()) {
			if(!child.getChildren().isEmpty()) {
				for(int i = 0 ; i < n.getDepth(); i++) {
					System.out.print("-");
				}
				System.out.println(n.toString());
				printNode(child);
			} else {
				System.out.println(n.toString());
			}
		}
	}
}
