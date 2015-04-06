//This class initializes the game
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Start {
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		System.out.println("Enable AI? (y/n) ");
		
		Queue<Point> pos = new LinkedList<Point>();
		pos.add(new Point(7,7));
		pos.add(new Point(3,3));
		pos.add(new Point(1,1));
		pos.add(new Point(4,2));
		pos.add(new Point(1,2));
		pos.add(new Point(2,4));
		
		pos = null;
		
		//can pass in a preloaded position for testing, will play if 
		//pos is not null
		
		BoardManager board = new BoardManager(19, new GoAI(19),
				s.next().equals("y"), pos);
		board.startGame();
		
	}
}
