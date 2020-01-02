package InterFace;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import Map.GenerateRandomMap;
import ScheduleAlgorithm.AstarAlgorithm;
import ScheduleAlgorithm.Node;
import Vehicles.AutoAGV;
public class Scene extends JFrame {

	SceneGraph sg;
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JTextField jtf1,jtf2,jtf3,jtf4;
	JPanel jp;
	JButton jb2;
	
	ArrayList<JTextField> lis = new ArrayList<JTextField>(); 
	public Scene()
	{
		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		jl1 = new JLabel("开始位置");
		jl2 = new JLabel("SX=");
		jtf1 = new JTextField(5);
//		 jb = new JButton("start");
		 
		 jb2 = new JButton("AStar");
//		jtf1.setActionCommand("sx");
//		jtf1.addActionListener(sg);
		jl3 = new JLabel("SY=");
		jtf2 = new JTextField(5);
		jl4 = new JLabel("结束位置");
		jl5 = new JLabel("GX=");
		jtf3 = new JTextField(5);
		jl6 = new JLabel("GY=");
		jtf4 = new JTextField(5);
		lis.add(jtf1);
		lis.add(jtf2);
		lis.add(jtf3);
		lis.add(jtf4);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jtf1);
		jp.add(jl3);
		jp.add(jtf2);
		jp.add(jl4);
		jp.add(jl5);
		jp.add(jtf3);
		jp.add(jl6);
		jp.add(jtf4);
//		jp.add(jb);
		jp.add(jb2);
		sg = new SceneGraph(50,50,40,40,lis);
		Thread t = new Thread(sg);
		t.start();
//		jb.setActionCommand("s");
//		jb.addActionListener(sg);
		
		jb2.setActionCommand("a");
		jb2.addActionListener(sg);
		this.add(jp,new BorderLayout().NORTH);
		this.add(sg);
		
		this.addMouseListener(sg);
		
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		Scene s =new Scene();

	}

}

class SceneGraph extends JPanel implements MouseListener,ActionListener,Runnable
{
	AstarAlgorithm aa;
	Node start, end;
	Node parent;
	int m[][];
	Vector<Node> path;
	AutoAGV agv = null;
	Node path_start;
	GenerateRandomMap grm;
	int pos_x;
	int pos_y;
	int width;
	int height;
	ArrayList<JTextField> lis;
	int s_x = -1;
	int s_y = -1;
	int g_x = -1;
	int g_y = -1;
	public SceneGraph(int x, int y, int w, int h,ArrayList<JTextField> lis)
	{
		this.pos_x  = x;
		this.pos_y  = y;
		this.width  = w;
		this.height = h;
		this.lis    = lis;
		
		grm         = new GenerateRandomMap(10,10,0.1);
		m = grm.GenerateMap();
		
		aa = new AstarAlgorithm(m);
		aa.printM();
		path = new Vector<Node>();
//		this.setBackground(Color.YELLOW);
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		for(int k = 0; k <= m.length; k++)
		{
			g.drawLine(this.pos_x, this.pos_y+ k * this.height, this.pos_x + m.length * this.width, this.pos_y+ k * this.height);
			g.drawLine(this.pos_x + k * this.width, this.pos_y, this.pos_x + k * this.width, this.pos_y+ m[0].length * this.width);
		}
		for(int i = 0; i < m.length; i++)
		{
			
			for(int j = 0; j < m[0].length; j ++)
			{
				if(m[i][j]==1)
				{
					g.setColor(Color.BLACK);
					g.fillRect(this.pos_y + j * this.height, this.pos_x+ i * this.width, this.width, this.height);
				}
				
			}
		}
		
		if(s_x!=-1 && s_y != -1 && m[s_y][s_x]==0)
		{
			start = new Node(s_y,s_x);
			g.setColor(Color.GREEN);
			g.fillRect( this.pos_x+ s_x * this.width,  this.pos_y + s_y * this.height, this.width, this.height);
		}
		
		if(g_x!= -1 && g_y != -1 && m[g_y][g_x]==0)
		{
			end = new Node(g_y,g_x);
			g.setColor(Color.RED);
			g.fillRect(this.pos_x+ g_x * this.width, this.pos_y + g_y * this.height, this.width, this.height);
		}
		
		if(start != null && end != null)
		{
			parent = aa.findPath(start, end);
			//path_start = parent;
			//System.out.println(parent.x+" "+parent.y);
			parent = parent.parent;
			g.setColor(Color.BLUE);
			while(parent!=null)
			{
				//System.out.println(parent.x+" "+parent.y);
				
				if(parent.parent!=null)
				{

					g.fillRect(this.pos_y + parent.y * this.height,this.pos_x + parent.x * this.width,this.width, this.height);
				    
				}
				parent = parent.parent;
			}		
		}

		if(agv != null)
		{
			//System.out.println("AGV runing!!"+agv.getX()+";"+agv.getY());
			g.setColor(Color.ORANGE);
			g.fillOval( this.pos_y + agv.getY()* this.height, this.pos_x+ agv.getX() * this.width, this.width, this.height);
		}
		
		if(agv != null && agv.getX() == s_y && agv.getY() == s_x)
		{
			System.out.println("back,back!!!!!"+agv.getX()+"；"+agv.getY());
			m[path.get(path.size()-2).x][path.get(path.size()-2).y] = 1;
			m[path.get(path.size()-3).x][path.get(path.size()-3).y] = 1;
			Vector<Node> path1 = new Vector<Node>();
			AstarAlgorithm aa1 = new AstarAlgorithm(m);
			//aa.setNODES(m);
			aa1.printM();
			Node path_start1 = aa1.findPath(start, end);
			while(path_start1!=null)
			{
				Node next = path_start1.parent;
				if(next == null)
				{
					break;
				}
				if(next.x-path_start1.x == 1 && next.y-path_start1.y == 0)
				{
					next.direct = 3;
				}else if(next.x-path_start1.x == -1 && next.y-path_start1.y == 0)
				{
					next.direct = 1;
				}else if(next.y-path_start1.y == 1 && next.x-path_start1.x == 0)
				{
					next.direct = 0;
				}else if(next.y-path_start1.y == -1 && next.x-path_start1.x == 0)
				{
					next.direct = 2;
				}
				//System.out.println(next.x+";"+next.y +";"+next.direct);
				path1.add(0,next);
				path_start1 = path_start1.parent;
			}
			path1.remove(0);
			for(int i = 0; i < path1.size(); i++)
			{
				System.out.println("path:"+path1.get(i).x+";"+path1.get(i).y +";"+path1.get(i).direct);
			}
			
			agv.setPath(path1);
			agv.setX(path1.get(0).x);
			agv.setY(path1.get(0).y);
			agv.setDirection(path1.get(0).direct);
			//agv = new AutoAGV(path1.get(1).x,path1.get().y,path1);
			Thread t = new Thread(agv);
			t.start();
//			path.add(path_start);
		}
		

		
	}
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("x = "+arg0.getX()+" y = "+arg0.getY());
			
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand().equals("a"))
		{
			System.out.println(lis.get(0).getText()+","+lis.get(1).getText()+";"+lis.get(2).getText()+","+lis.get(3).getText());
			if(!lis.get(0).getText().isEmpty())
			{
				s_x = Math.round((Integer.parseInt(lis.get(0).getText()) - this.pos_x)/40);
			}else
			{
				s_x = -1;
			}
			if(!lis.get(1).getText().isEmpty())
			{
				s_y = Math.round((Integer.parseInt(lis.get(1).getText()) - this.pos_y-30)/40);
			}else
			{
				s_y = -1;
			}
			if(!lis.get(2).getText().isEmpty())
			{
				g_x = Math.round((Integer.parseInt(lis.get(2).getText()) - this.pos_x)/40);
			}else
			{
				g_x = -1;
			}
			if(!lis.get(3).getText().isEmpty())
			{
				g_y = Math.round((Integer.parseInt(lis.get(3).getText()) - this.pos_y-30)/40);
			}else
			{
				g_y = -1;
			}
			System.out.println(s_x+","+s_y+";"+g_x+","+g_y);
			if(s_x!=-1 && s_y != -1 && m[s_x][s_y]==0 && g_x!= -1 && g_y != -1 && m[g_x][g_y]==0)
			{
				start = new Node(s_y,s_x);
				end   = new Node(g_y,g_x);
			}
			if(start != null && end != null)
			{
				path_start = aa.findPath(start, end);
				while(path_start!=null)
				{
					Node next = path_start.parent;
					if(next == null)
					{
						break;
					}
					if(next.x-path_start.x == 1 && next.y-path_start.y == 0)
					{
						path_start.direct = 1;
					}else if(next.x-path_start.x == -1 && next.y-path_start.y == 0)
					{
						path_start.direct = 3;
					}else if(next.y-path_start.y == 1 && next.x-path_start.x == 0)
					{
						path_start.direct = 2;
					}else if(next.y-path_start.y == -1 && next.x-path_start.x == 0)
					{
						path_start.direct = 0;
					}
					System.out.println(path_start.x+";"+path_start.y +";"+path_start.direct);
					path.add(path_start);
					path_start = path_start.parent;
				}
//				path.add(path_start);
			}
			System.out.println("Path size is"+path.size());
			agv = new AutoAGV(path.get(0).x,path.get(0).y,path);
			Thread t = new Thread(agv);
			t.start();
		}
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.repaint();
	}
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.repaint();
		}
	}
}