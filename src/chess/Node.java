
package src.chess;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.*;

public class Node {
	
	public int xoffset,yoffset,Virtualoffsetx,Virtualoffsety;
	public int cellsize;
	public Polygon poly;
	public Actor actor;
	public Node[] adgNodes;
	public int visited;//
	public boolean occupied,highlighted;
	public int[] attackrisk;//counter for attackrisk
	int pax[]=new int[4];
	int pay[]=new int[4];
	int paz[]=new int[4];//height offset
	
	
	public Node() {
		// TODO Auto-generated constructor stub
		poly=null;
		actor=null;
		visited=0;
		occupied=false;
		adgNodes=new Node[4];
		xoffset=yoffset=0;
		cellsize=10;
		highlighted=false;
		attackrisk=new int[2];
		for(int i =0;i<4;i++) paz[i]=0;
		
	}
	
	public void setAttackRisk(boolean inrange,int facID)
	{
		if(inrange)
			attackrisk[facID]++;
		else
			attackrisk[facID]--;
	}
	
	public int getAttackRisk(int facID)
	{
		return attackrisk[facID];
	}
	
	public Node(int x,int y)
	{
		this();
		setoffset(x,y);
	}
	public void setoffset(int x, int y)
	{
		xoffset=x;
		yoffset=y;
	}
	
	public Node(int x,int y,int celsize,boolean p)
	{
		
		this(x,y);
		setcellsize(celsize);
		if(p!=false) setpoly(x,y);
		
	}
	public void setcellsize(int celsize)
	{
		cellsize=celsize;
	}
	
	
	//gets the cell ready for drawing
	public void setcell(int x, int y)
	{
		xoffset=x;
		yoffset=y;
		setpoly(x,y);
	}
	
	
	
	/*		0_1
	 * 		|_| for corners,
	 * 	    3 2
	 * */
	public void setpointheight(int corner,int pheight){

		//0=0&3
		//1=1&0
		//2=2&1
		//3=3&2
		
		
		
		
	}
	
	
	public void setpoly(int x, int y)
	{
		
		
		pax[0]=x;
		pay[0]=y+paz[0];
		pax[1]=x+cellsize;
		pay[1]=y+paz[1];
		pax[2]=x+cellsize;
		pay[2]=y+cellsize+paz[2];
		pax[3]=x;
		pay[3]=y+cellsize+paz[3];
			
		poly=new Polygon( pay , pax , 4);

	}

}

