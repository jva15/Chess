package src.chess;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//Bouncing Ball example
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.omg.IOP.ExceptionDetailMessage;

import javax.swing.*;

public class Game
{
	
	public Actor Actors[];
	// execute application
	public static void main( String args[] )
	{

	   JFrame frame = new JFrame( "Grid" );
	   frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   
	   
	   GridPanel bp = new GridPanel();
	  
	   
	   //
	   
	   frame.add( bp );
	   frame.setSize( 300*3, 300*3 ); // set frame size
	   frame.setVisible( true ); // display frame
	} // end main
	public void initActors()
	{
		//0: White Pawn
		//1: White Castle
		//2: White Knight
		//3: White Bishop
		//4: White King
		//5: White Queen
		//6: Black Pawn
		//7: Black Castle
		//8: Black Knight
		//9: Black Bishop
		//10: Black King
		//11: Black Queen
				
		
		
		
		
	}
	//public int[] PlaceActors()//place peaces
	//{
		
		
		//return format: 		
	/* 
	 * 
	 * */
	
	
	
	
	
	
	
	//}
}




class GridPanel extends JPanel implements ActionListener
{
	public Actor AllActors[];//list of all actor resources
	public Actor Actorlist[];//list of actors on the board
	
	
	private int delay = 10;
	protected Timer timer;
	//protected Timer highlight_animator;
	private int x = 0;//435;		// x position of grid
	private int y = 0;//685;		// y position of grid
	private int cellsize = 150;	// cell size on screen
	private int cellspace= 0;//15; // space between the cells
	private double rad = 40; // current view angle
	private double radd =0;// 0.1/2; // change in view angle
	private int gsize=8;//number of cells(this is then squared)

	int center=x+(cellsize+cellspace)*gsize/2-cellspace;
	int[] centerpoint=new int[2];;
	int[] tp =new int[2];//just a temporary pointer
	int focalpointx=x+(int)(((center-x)*2-(cellsize))/2);
	int focalpointy=y+(int)(((center-y)*2-(cellsize))/2);
	
	
	private Node cellgrid[][];  //actor grid
	
	
	
	private int dx = 0;		// reserved for player gridmovement
	private int dy = 0;		// increment amount (y coord)
	
	
	
	
	
	
	
	
	public GridPanel() 
	{
		
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	//get the mouse coords
		    	
		    	int[] mousepointer=new int[2];
		    	mousepointer[0]=e.getX();
		    	mousepointer[1]=(int)(e.getY()*3.3);//the calculation is grid based, so i morph the y so it simulates a reverted version of the grid(before the y shrink)
		    	centerpoint[0]=centerpoint[1]=center;//load the center point
		    	
		    	tp=rotoffc(centerpoint,mousepointer,Math.toRadians(rad));//rotate the mouse coords around the center point so that the grid is calculated as if straight.
		    	tp[0]=(int)(tp[0]/cellsize);//
		    	tp[1]=(int)(tp[1]/cellsize);//
		    	cellgrid[tp[0]%gsize][tp[1]%gsize].highlighted=true;//highlights the cell, but this is just a place holder
		    }
		    
		});
	   timer = new Timer(delay, this);
	   cellgrid=Init_NodeAr(cellgrid,gsize);//new Node[gsize][gsize];
	   timer.start();		// start the timer
	   
	  
	   
	}


	public void actionPerformed(ActionEvent e)
	// will run when the timer fires
	{
		
		repaint();
	}
	
	// draw Polygons and arcs
	public void paintComponent( Graphics g )
	{
	   super.paintComponent( g ); // call superclass's paintComponent 

	   
	   
	   //156 int[] xa=new int[4];
	   //157 int[] ya=new int[4];
	   //158 int xoffset,yoffset;
	   //159 int soffsety=(int)(-47*3.3);//for testing purposes; should be replaced by the actors value at time of application
	   //160 int soffsetx=0;//for testing purposes; should be replaced by the actors value at time of application
	   //161 int actorsizey=(int)(47*3.5);
	   
	   Graphics2D g2d = (Graphics2D)g;
	    //x=y=getHeight()/2;
	    //165int xpof,ypof;
	    dx=dy=0;
	    	    
		
		
		
		g2d.scale(1, 0.3);		
		g2d.rotate(Math.toRadians(rad),center,center);
		
		
		//draw the grids
		for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)	
		{
			//checkered
			int RoB=(i+j)%2;
			
			//highlighting
			if(cellgrid[i][j].highlighted==true) g2d.setColor(Color.YELLOW);
			else if(RoB==1)			g2d.setColor(Color.red);
			else 				g2d.setColor(Color.black);	

			g2d.draw(cellgrid[i][j].poly);
			g2d.fill(cellgrid[i][j].poly);
		}
		
		g2d.rotate(Math.toRadians(-rad),center,center);
		
		//draw the actors
		for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)
		   {
				
		   }
			
		x += dx;
		y += dy;
		rad +=radd;//TODO: Set user controlled once the final visualization is complete
		
		
	}
	
	
	
	public Node[][] Init_NodeAr(Node[][] nodeArr,int NAsize)
	{
		nodeArr=new Node[NAsize][NAsize];
		return Build_NodeArray(nodeArr);
	}
	public Node[][] Build_NodeArray(Node[][] nodeArr) 
	{
		for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
		{
			int pxoffset=x+i*(cellsize+cellspace);
			int pyoffset=y+j*(cellsize+cellspace);
			nodeArr[i][j]=new Node(pxoffset,pyoffset,cellsize,true);
		}
	
			
		for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
		{
			
			if(i!=0) nodeArr[i][j].adgNodes[3]=nodeArr[i-1][j];
			else nodeArr[i][j].adgNodes[3]=null;
			
			if(i!=nodeArr.length-1)nodeArr[i][j].adgNodes[1]=nodeArr[i+1][j];
			else nodeArr[i][j].adgNodes[1]=null;
			
			if(j!=0)nodeArr[i][j].adgNodes[2]=nodeArr[i][j-1];
			else nodeArr[i][j].adgNodes[2]=null;
			
			if(j!=nodeArr.length-1) nodeArr[i][j].adgNodes[0]=nodeArr[i][j+1];
			else nodeArr[i][j].adgNodes[0]=null;

			
		}
		return nodeArr;
	}
	/* ignor these.. fornow
	public void Rotate_NodeArray(Node[][] nodeArr) 
	{
		int xpof,ypof;
		for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
		{
			xpof=x+i*(cellsize+cellspace);
			ypof=y+j*(cellsize+cellspace);
			
			nodeArr[i][j].Virtualoffsetx=focalpointx+rotoffx(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
			nodeArr[i][j].Virtualoffsety=focalpointy+rotoffy(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
		}	
	}
	
	public static void nodeRender(Vector<Node> que,Graphics2D g2dn) 
	{
		
	}*/	
	
	
	//rotates a point around a center
	//cp: center point to rotate around, p: point to rotate around center point, and theta: degrees to rotate.
	public static int[] rotoffc(int[] cp,int[] p , double theta) {//returns the rotated point
		int[] np=new int[2];
		np[0]=cp[0]+ rotoffx((p[1]-cp[1]), (p[0]-cp[0]), theta);//calculates x
		np[1]=cp[1]+ rotoffy((p[1]-cp[1]), (p[0]-cp[0]), theta);
		return np;
	}
	
	//functions for rotating
	public static int rotoffx(int py, int px, double theta) {
		return (int)((double)py*Math.cos(theta)-(double)px*Math.sin(theta));
	}
	public static int rotoffy(int py, int px, double theta) {
		return (int)((double)px*Math.cos(theta)+(double)py*Math.sin(theta));
	}
	public static Color MySpectrum(int cindex) {
		//all colors
		if(cindex<225)
			return new Color(0,0,cindex%225 );
		else if(cindex>=225&&cindex<225*2)
			return new Color(0,cindex%225,225-(cindex%225) );
		else if((cindex>=225*2&&cindex<225*3))
			return new Color(cindex%225,225-(cindex%225),0);
		else return new Color(225,(cindex%225),(cindex%225));
	}

}
