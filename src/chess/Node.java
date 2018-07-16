
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
		
		
	}
	
	public void setAttackRisk(boolean inrange,int facID)
	{
		/*TODO: use inrange to determine whether to increment or decriment attackrisk[facID]
		 * forexample:
		 * if attackrisk[0]=2; that means player 1 has 2 pieces that can hit that spot
		 * 
		 * */
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
	
	public void setpoly(int x, int y)
	{
		
		int pax[]=new int[4];
		int pay[]=new int[4];
		
		pax[0]=x;
		pay[0]=y;
		pax[1]=x+cellsize;
		pay[1]=y;
		pax[2]=x+cellsize;
		pay[2]=y+cellsize;
		pax[3]=x;
		pay[3]=y+cellsize;
			
		poly=new Polygon( pay , pax , 4);

	}

}

