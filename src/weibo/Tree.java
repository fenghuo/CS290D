package weibo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tree extends JFrame {

	private HashMap<Long, Node> nodes;
	private HashMap<Long, Long> uids;
	public Node root = null;
	private int xF=10;
	private int yF=100;
	private Random ran=new Random();
	public Integer[]serials=null;
	public int lineCount=0;

	public Tree(String name) {
		super("Draw A Circle In JFrame");  
		 
		nodes = new HashMap<Long, Node>();
		uids = new HashMap<Long, Long>();
		
		  
		 //Set default close operation for JFrame  
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		  
		 //Set JFrame size  
		 setSize(1000,800);  
		  
		 //Make JFrame visible   
		 //setVisible(true); 
		 
		utils.loadJson(this,name);

	}
	

	public Tree(File file,int time) {
		//super("Draw A Circle In JFrame");  
		 
		nodes = new HashMap<Long, Node>();
		uids = new HashMap<Long, Long>();
		
		  
		 //Set default close operation for JFrame  
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		  
		 //Set JFrame size  
		 setSize(1000,800);  
		  
		 //Make JFrame visible   
		 //setVisible(true); 
		 
		utils.loadJson(this,file,time);

	}
	public Tree() {
		super("Draw A Circle In JFrame");  
		 
		nodes = new HashMap<Long, Node>();
		uids = new HashMap<Long, Long>();
		
		  
		 //Set default close operation for JFrame  
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		  
		 //Set JFrame size  
		 setSize(1000,800);  
		  
		 //Make JFrame visible   
		 //setVisible(true); 

	}

	public Tree(String name1, int time) {
		// TODO Auto-generated constructor stub

		super("Draw A Circle In JFrame");  
		 
		nodes = new HashMap<Long, Node>();
		uids = new HashMap<Long, Long>();
		
		  
		 //Set default close operation for JFrame  
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		  
		 //Set JFrame size  
		 setSize(1000,800);  
		  
		 //Make JFrame visible   
		 //setVisible(true); 
		 

		utils.loadJson(this,name1);
	}


	public void createTree(ArrayList<long[]>data)
	{
		for(long[]d:data)
			addToTree(d[0],d[1],d[2],d[3]);
	}
	
	public void addToTree(String uid, String parent, String time, String id) {
		addToTree(Long.parseLong(uid), Long.parseLong(parent),
				Long.parseLong(time), Long.parseLong(id));
	}

	public void addToTree(long uid, long parent, long time, long id) {

		uids.put(id, uid);
		
		if (nodes.containsKey(uid))
			return;
		
		if (root == null)
		{
			root = new Node(uid, 0, time,null);
			nodes.put(uid, root);
		}
		else {
			if (uids.containsKey(parent)) {

				long puid = uids.get(parent);
				Node node = new Node(uid, puid, time,nodes.get(puid));
				if(nodes.get(puid)==null)
					return;
				nodes.get(puid).addChild(node);
				nodes.put(uid, node);
			} else
				System.err.println("Missing Parent:" + parent);
		}
	}

	public void sort()
	{
		sort(root);
	}
	public void sort(Node node)
	{
		node.sort();
	}

	public void addToNewTree(Tree tree,Node node)
	{
		if(node==null)
			return;
		addToNewTree(tree, node.parent);
		if(tree.nodes.containsKey(node.uid))
		{
			return;
		}
		Node n=new Node(node.uid, node.puid, node.time, tree.nodes.get(node.puid));
		tree.nodes.put(node.uid, n);
		if(tree.root==null)
			tree.root=n;
		else
			tree.nodes.get(node.puid).addChild(n);
	}
	
	public Tree sample(int n)
	{
		if(this.root.size<=n)
			return this;
		
		Tree tree=new Tree();
	
		Object[]data=nodes.keySet().toArray();
		
		for(int i=0;i<n;i++)
		{
			int k=ran.nextInt(data.length);
			Object key=data[k];
			addToNewTree(tree,nodes.get(key));
		}
		
		return tree;
	}

	public void serial(Node node,LinkedList<Integer> list)
	{
		if(node==null)
			return;
		list.add(node.level);
		for(Node n:node.children)
			serial(n,list);
	}
	
	public Integer[] serial()
	{
		LinkedList<Integer> list=new LinkedList<Integer>();
		
		serial(root,list);
		
		serials=list.toArray(new Integer[list.size()]);
		
		return serials;
		
	}

	public void printSerial()
	{
		if(serials==null)
			return;
		for(Integer i:serials)
			System.out.print(i+",")	;	

		System.out.println();
	}
	
	void draw(Graphics g,Node root,int count[])
	{
		if(root==null)
			return;
		
		//System.out.println(root.depth);

		int d=root.level;
		count[d]++;
		g.drawLine(count[d]*xF, d*yF, count[d-1]*xF, (d-1)*yF);
		//System.out.println("draw:"+count[d]*xF+","+ d*yF+","+count[d-1]*xF+","+(d-1)*yF);
		g.drawOval(count[d]*xF, d*yF, 10, 10);
		for(Node n:root.children)
			draw(g,n,count);
	}
	
	public void show2()
	{
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	
	public void paint(Graphics g)  
	{  
		if(root==null)
			return;
		
		super.paint(g);  
		
		int count[]=new int[1000];
		
		g.drawOval(0, 0, 10, 10);
		
		draw(g,root,count);
	
	}  

	
	public class Node extends Object {
		long uid;
		long puid;
		long time;
		public ArrayList<Node> children;
		Node parent;
		int size;
		int depth;
		int level=1;
		
		public Node(long uid, long puid, long time,Node parent) {
			this.uid = uid;
			this.puid = puid;
			this.time = time;
			children = new ArrayList<Node>();
			size=0;
			depth=0;
			this.parent=parent;
			if(parent!=null)
				level=parent.level+1;
		}

		public void addChild(Node node) {
			children.add(node);
		}
		
		public void sort()
		{
			size=1;
			int maxD=0;
			Comparator<Node> com=new Comparator<Node>(){

				@Override
				public int compare(Node o1, Node o2) {
					if(o1.depth==o2.depth)
						return -o1.size+o2.size;
					else
						return -o1.depth+o2.depth;
				}
			};
			
			for(int i=0;i<children.size();i++)
			{
				children.get(i).sort();
				size+=children.get(i).size;
				maxD=Math.max(maxD, children.get(i).depth);
			}
			
			Collections.sort(children,com);
			
			depth=maxD+1;
		}
	}
}
