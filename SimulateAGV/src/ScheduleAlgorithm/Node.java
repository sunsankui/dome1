package ScheduleAlgorithm;
import javax.swing.JFrame;


public class Node {
           
    public int x;
    public int y;
    public int direct;

    public int F;
    public int G;
    public int H;
    
	public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }



    public void calcF() {
        this.F = this.G + this.H;
    }

    public Node parent;
}
