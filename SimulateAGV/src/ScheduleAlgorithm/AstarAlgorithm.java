package ScheduleAlgorithm;
import java.util.ArrayList;
import java.util.List;

public class AstarAlgorithm {

	   public int[][] getNODES() {
		return NODES;
	}

	public void setNODES(int[][] nODES) {
		NODES = nODES;
	}
	public int[][] NODES;
    public static final int STEP = 10;
//       Node start,end;
    public AstarAlgorithm(int[][]map)
    {
	    this.NODES = map;
//    	   this.start = start;
//    	   this.end   = end;
    }
	public void printM()
	{
		for(int i = 0; i < this.NODES.length;i++)
		{
			for(int j = 0; j < this.NODES[0].length; j++)
			{
				System.out.print(NODES[i][j]+" ");
			}
			System.out.println();
		}
	}
	    private ArrayList<Node> openList = new ArrayList<Node>();
	    private ArrayList<Node> closeList = new ArrayList<Node>();
	    
	    //从openlist中寻找f最小的节点作为下一步
	    public Node findMinFNodeInOpneList() {
	        Node tempNode = openList.get(0);
	        for (Node node : openList) {
	            if (node.F < tempNode.F) {
	                tempNode = node;
	            }
	        }
	        return tempNode;
	    }
	 
	    public ArrayList<Node> findNeighborNodes(Node currentNode) {
	        ArrayList<Node> arrayList = new ArrayList<Node>();
	        // 只考虑上下左右，不考虑斜对角
	        int topX = currentNode.x;
	        int topY = currentNode.y - 1;
	        if (canReach(topX, topY) && !exists(closeList, topX, topY)) {
	            arrayList.add(new Node(topX, topY));
	        }
	        
	        int bottomX = currentNode.x;
	        int bottomY = currentNode.y + 1;
	        if (canReach(bottomX, bottomY) && !exists(closeList, bottomX, bottomY)) {
	            arrayList.add(new Node(bottomX, bottomY));
	        }
	        int leftX = currentNode.x - 1;
	        int leftY = currentNode.y;
	        if (canReach(leftX, leftY) && !exists(closeList, leftX, leftY)) {
	            arrayList.add(new Node(leftX, leftY));
	        }
	        int rightX = currentNode.x + 1;
	        int rightY = currentNode.y;
	        if (canReach(rightX, rightY) && !exists(closeList, rightX, rightY)) {
	            arrayList.add(new Node(rightX, rightY));
	        }
//	        int right_topX = currentNode.x + 1;
//	        int right_topY = currentNode.y-1;
//	        if (canReach(right_topX, right_topY) && !exists(closeList, right_topX, right_topY)) {
//	            arrayList.add(new Node(right_topX, right_topY));
//	        }
//	        int right_bottomX = currentNode.x + 1;
//	        int right_bottomY = currentNode.y+1;
//	        if (canReach(right_bottomX, right_bottomY) && !exists(closeList, right_bottomX, right_bottomY)) {
//	            arrayList.add(new Node(right_bottomX, right_bottomY));
//	        }
//	        int left_bottomX = currentNode.x - 1;
//	        int left_bottomY = currentNode.y + 1;
//	        if (canReach(left_bottomX, left_bottomY) && !exists(closeList, left_bottomX, left_bottomY)) {
//	            arrayList.add(new Node(left_bottomX, left_bottomY));
//	        }
//	        int left_topX = currentNode.x - 1;
//	        int left_topY = currentNode.y - 1;
//	        if (canReach(left_topX, left_topY) && !exists(closeList, left_topX, left_topY)) {
//	            arrayList.add(new Node(left_topX, left_topY));
//	        }
	        return arrayList;
	    }
	 
	    public boolean canReach(int x, int y) {
	        if (x >= 0 && x < NODES.length && y >= 0 && y < NODES[0].length) {
	            return NODES[x][y] == 0;
	        }
	        return false;
	    }
	 
	    public Node findPath(Node startNode, Node endNode) {
	 
	        // 把起点加入 open list
	        openList.add(startNode);
	 
	        while (openList.size() > 0) {
	            // 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点
	            Node currentNode = findMinFNodeInOpneList();
	            // 从open list中移除
	            openList.remove(currentNode);
	            // 把这个节点移到 close list
	            closeList.add(currentNode);
	 
	            ArrayList<Node> neighborNodes = findNeighborNodes(currentNode);
	            for (Node node : neighborNodes) {
	                if (exists(openList, node)) {
	                    foundPoint(currentNode, node);
	                } else {
	                    notFoundPoint(currentNode, endNode, node);
	                }
	            }
	            if (find(openList, endNode) != null) {
	                return find(openList, endNode);
	            }
	        }
	 
	        return find(openList, endNode);
	    }
	 
	    private void foundPoint(Node tempStart, Node node) {
	        int G = calcG(tempStart, node);
	        if (G < node.G) {
	            node.parent = tempStart;
	            node.G = G;
	            node.calcF();
	        }
	    }
	 
	    private void notFoundPoint(Node tempStart, Node end, Node node) {
	        node.parent = tempStart;
	        node.G = calcG(tempStart, node);
	        node.H = calcH(end, node);
	        node.calcF();
	        openList.add(node);
	    }
	 
	    private int calcG(Node start, Node node) {
	        int G = STEP;
	        int parentG = node.parent != null ? node.parent.G : 0;
	        return G + parentG;
	    }
	 
	    private int calcH(Node end, Node node) {
	        int step = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	        return step * STEP;
	    }
	    public static Node find(List<Node> nodes, Node point) {
	        for (Node n : nodes)
	            if ((n.x == point.x) && (n.y == point.y)) {
	                return n;
	            }
	        return null;
	    }
	 
	    public static boolean exists(List<Node> nodes, Node node) {
	        for (Node n : nodes) {
	            if ((n.x == node.x) && (n.y == node.y)) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
	    public static boolean exists(List<Node> nodes, int x, int y) {
	        for (Node n : nodes) {
	            if ((n.x == x) && (n.y == y)) {
	                return true;
	            }
	        }
	        return false;
	    }
}
