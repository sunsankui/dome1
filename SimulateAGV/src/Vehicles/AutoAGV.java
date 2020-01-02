package Vehicles;

import java.util.Vector;

import ScheduleAlgorithm.Node;

public class AutoAGV implements Runnable{

    public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	int x;
    int y;
    int speed = 1;
    public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	int direction=0;
    public Vector<Node> getPath() {
		return path;
	}
	public void setPath(Vector<Node> path) {
		this.path = path;
	}
	Vector<Node> path;
    public AutoAGV(int x, int y, Vector<Node> path)
    {
    	this.x    = x;
    	this.y    = y;
    	this.path = path;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void move()
	{
		switch(this.direction)
		{
		case 0:
			y -= speed;
			break;
		case 1:
			x += speed;
			break;
		case 2:
			y += speed;
			break;
		case 3:
			x -= speed;
			break;
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub

		for(int i = 0; i < path.size(); i++)
		{
			this.direction = path.get(i).direct;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			move();
		}
			
		
	}

}
