import java.util.HashSet;


public class Node {
	private int depth;
	private int x;
	private int y;
	private int value;
	private HashSet<Node> children;

	public Node (int x, int y, int depth, int value) {

		children = new HashSet<Node>();
		this.x = x;
		this.y = y;
		this.value = value;
		this.depth = depth;
	}
	
	public int getX() {
		return x;
	}	

	public int getY() {
		return y;
	}	
	
	public int getDepth() {
		return depth;
	}	
	
	public int getValue() {
		return value;
	}
	
	public HashSet<Node> getChildren() {
		return children;
	}
	
	//Check if child exists, combine values, else create new
	//returns the target child
	public Node addChild(Node n) {
		for (Node c : children) {
			if (c.getX()==n.getX() && c.getY()==n.getX()) {
				value+= n.getValue();
				return c;
			}
		}
		children.add(n);
		return n;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ") v=" + value + " d=" + depth;
	}
	
}
