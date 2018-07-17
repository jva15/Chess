package src.chess;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//Bouncing Ball example
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
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
	   
	   
	   GridPanel gp = new GridPanel();
	  
	   
	   //TODO: add splash screen panel
	   //frame.add(sp);
	   
	   frame.add( gp );
	   frame.setSize( 300*6, 300*6 ); // set frame size
	   frame.setVisible( true ); // display frame
	} // end main
	
	
}




class GridPanel extends JLayeredPane implements ActionListener
{
	public Actor[] Actors;//list of actors
	
	private int delay = 10;//delay for the FPS timer
	protected Timer timer; //FPS timer
	private final int x =0;//base offset	don't touch
	private final int y = 0;//base offset don't touch
	public static int transx=300;
	public static int transy=400;
	
	
	public static double gridratio=1; // not ready yet
	private int cellsize = (int)(150*gridratio);	// cell size on screen
	private int cellspace= 0;//15; // space between the cells
	private double rad = 0; // current view angle
	private double radd =0.1; // change in view angle
	private int gsize=8;//number of cells(this is then squared)

	int[] centerpoint=new int[2];//same as above just in point form
	int[] tp =new int[2];//just a temporary pointer
	int focalpointx;//used to store the center point of actors to be rotated
	int focalpointy;

	static Actor selected_piece = null;
	int state;//not yet implimented
			//0 nothing should be selectable
			//1 no item selected, only the pieces can be selected
			//2 item selected, only tiles can be selected
	
	
	private Node cellgrid[][];  //cell grid
	private int dx = 0;		// reserved for player gridmovement
	private int dy = 0;		// increment amount (y coord)
	
	public static boolean inputenabled=true;
	int currentturn =0;
	
	
	
	public GridPanel() 
	{
		centerpoint[0]=x+((cellsize+cellspace)*gsize)/2-cellspace;//
		centerpoint[1]=y+((cellsize+cellspace)*gsize)/2-cellspace;//
		focalpointx=x+(int)(((centerpoint[0]-x)*2-(cellsize))/2);//used to store the center point of actors to be rotated
		focalpointy=y+(int)(((centerpoint[1]-y)*2-(cellsize))/2);//
		
		
		
		//this is where selection of items occures
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	//get the mouse coords
		    	
		    	Actor Piece;
		    	Node cell;
		    	int[] mousepointer=new int[2];
		    	
		    	if(inputenabled)
		    	{
			    	inputenabled=false;
			    	mousepointer[0]=e.getX();
			    	mousepointer[1]=(int)((e.getY()));//the calculation is grid based, so i morph the y so it simulates a reverted version of the grid(before the y shrink)
			    	System.out.println(e.getX()+"  "+e.getY());
			    	System.out.println(getComponentAt(mousepointer[0],(int)(mousepointer[1])).getClass().getName());
			    	
			    	if((getComponentAt(mousepointer[0],(int)(mousepointer[1])).getClass().getName().equals("src.chess.GridPanel")))
			    	{//you selected the grid panel
			    		mousepointer[1]=(int)((mousepointer[1]-transy)/0.3);
			    		mousepointer[0]-= transx;
				    	tp=rotoffc(centerpoint,mousepointer,Math.toRadians(rad));//rotate the mouse coords around the center point so that the grid is calculated as if straight.
				    	tp[0]=(int)((tp[0])/cellsize);//
				    	tp[1]=(int)((tp[1])/cellsize);//
				    	if(tp[0]>=0&&tp[0]<gsize&&tp[1]>=0&&tp[1]<gsize)
				    	{
				    		cell=cellgrid[tp[0]][tp[1]];
				    		cell.highlighted=true;
				    		
				    		if(selected_piece!=null)
				    		{
					    		
					    		int factID=selected_piece.factionID;
					    		if(factID==currentturn&&//if your previouspiece is selected
					    		   cell.highlighted==true)//highlighted
					    		{
					    			selected_piece.moveTo(cell);
					    			switchturns();
					    		}
					    		
				    		}
				    	}
				    	
			    	}
			    	else //actor selected
			    	{

		    			Piece=(Actor)getComponentAt(mousepointer[0],(int)(mousepointer[1]));
		    			Piece.currentcell.highlighted=true;
		    			if(Piece.factionID==currentturn) select(Piece);
				    	else if(Piece.currentcell.highlighted) //piece is not yours
		    			{
				    		
		    				if(selected_piece!=null)
		    				{
		    					//can't unselect before a move otherwise, selected piece can't call moveTo()
		    					movehighlight(selected_piece, false);
		    					selected_piece.moveTo(Piece.currentcell);
		    					selected_piece=null;
		    					switchturns();
		    				}
		    				//otherwise do nothing
		    			}
			    	}
			    	inputenabled=true;
		    	}	
		    }
		    
		});
	   timer = new Timer(delay, this);//refresh timer
	   cellgrid=Init_NodeAr(cellgrid,gsize);
	   
	   timer.start();		// start the timer
	   Actors=new Actor[8*4];
	   Populate_board();
	  
	   
	   
	   
	}

/*-------------Selection-functions------------------*/
	
	public void movehighlight(Actor Piece,boolean highlighted)
	{
		//TODO call the Actor Highlight function	
	}
	//selection functions
	protected void unselectpiece() {
		movehighlight(selected_piece,false);
		selected_piece=null;
	}


	protected void select(Actor piece) {
		if(selected_piece!=null)unselectpiece();
		movehighlight(piece,true);
		selected_piece=piece;
	}
	protected void switchturns() {
		//currentturn=(currentturn+1)%2;
	}


/*-------------Rendering------------------*/

	public void actionPerformed(ActionEvent e)
	// will run when the timer fires
	{
		repaint();
	}
	
	// draws the stuff
	public void paintComponent( Graphics g )
	{
	   super.paintComponent( g ); // call superclass's paintComponent 
	   Rotate_NodeArray(cellgrid);// update the virtual offsets
	   
	   Graphics2D g2d = (Graphics2D)g;
	    //x=y=getHeight()/2;
	    dx=dy=0;
	    	    
	    //perform the transformation for the 3d effect
	    g2d.translate(transx, transy);
		g2d.scale(1, 0.3);		
		g2d.rotate(Math.toRadians(rad),centerpoint[0],centerpoint[1]);
		
		
		//draw the grids
		for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)	
		{
			//checkered
			int RoB=(i+j)%2;
			
			if(cellgrid[i][j].highlighted==true) g2d.setColor(Color.YELLOW);//highlighting
			else if(RoB==1)			g2d.setColor(Color.red);
			else 				g2d.setColor(Color.black);	
			Polygon poly= cellgrid[i][j].poly;
			
			//poly.translate(transx, transy);
			g2d.draw(poly);
			g2d.fill(poly);
			//poly.translate(-transx, -transy);
		}

		//unrotate it since Actors use raw coords to store post rotation
		
		g2d.rotate(Math.toRadians(-rad),centerpoint[0],centerpoint[1]);
		g2d.scale(1, 1/0.3);		
		g2d.translate(-transx, -transy);
		
		
		//draw actors
		for(int i=0;i<Actors.length;i++)
		{
			
			Actors[i].updatebycell();//snap the actor to the new coords
			setLayer(Actors[i], Actors[i].yoffset);
			Actors[i].Drawframe(g2d);//draw the actor
			
		}
		
		rad +=radd;//TODO: Set radd to be user controlled once the final visualization is complete
		rad=rad%360;
		
	}
	
	
	
	

/*-------------initialization------------------*/

	
	//creates the node array
	public Node[][] Init_NodeAr(Node[][] nodeArr,int NAsize)
	{
		nodeArr=new Node[NAsize][NAsize];
		return Build_NodeArray(nodeArr);
	}
	
	public Node[][] Build_NodeArray(Node[][] nodeArr) 
	{
		//creates node
		for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
		{
			int pxoffset=x+i*(cellsize+cellspace);
			int pyoffset=y+j*(cellsize+cellspace);
			nodeArr[i][j]=new Node(pxoffset,pyoffset,cellsize,true);
		}
	
			
		//links the nodes together
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
	//sets the pieces on the board
	public void Populate_board() {
		//order placed affects 
		
		//add white pieces
		Actors[0]=new Rook(cellgrid[0][0],0);//white rook1
		Actors[1]=new Rook(cellgrid[7][0],0);//white rook2
		Actors[2]=new Knight(cellgrid[1][0],0);//white Knight1
		Actors[3]=new Knight(cellgrid[6][0],0);//white Knight2
		Actors[4]=new Bishop(cellgrid[2][0],0);//white Bishop1
		Actors[5]=new Bishop(cellgrid[5][0],0);//white Bishop2
		Actors[6]=new King(cellgrid[3][0],0);//white King
		Actors[7]=new Queen(cellgrid[4][0],0);//white Queen
		for(int i =0;i<8;i++) Actors[i+8]=new Pawn(cellgrid[i][1],0);//white pawns
		
		//add black pieces
		for(int i =0;i<8;i++) Actors[i+16]=new Pawn(cellgrid[i][6],1);//black pawns		
		Actors[24]=new Rook(cellgrid[0][7],1);//black rook1
		Actors[25]=new Rook(cellgrid[7][7],1);//black rook2
		Actors[26]=new Knight(cellgrid[1][7],1);//black Knight1
		Actors[27]=new Knight(cellgrid[6][7],1);//black Knight2
		Actors[28]=new Bishop(cellgrid[2][7],1);//black Bishop1
		Actors[29]=new Bishop(cellgrid[5][7],1);//black Bishop2
		Actors[30]=new King(cellgrid[4][7],1);//black King
		Actors[31]=new Queen(cellgrid[3][7],1);//black Queen
		for(int i=0;i<8*4;i++) add(Actors[i]);
		
		
		
	}
	
	

/*-------------Rotation manipulation------------------*/

	public void Rotate_NodeArray(Node[][] nodeArr) 
	{
		int xpof,ypof;
		
		for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
		{
			xpof=x+i*(cellsize+cellspace);
			ypof=y+j*(cellsize+cellspace);
			
			nodeArr[i][j].Virtualoffsetx=transx+focalpointx+rotoffx(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
			nodeArr[i][j].Virtualoffsety=(int)(transy)+(int)(((focalpointy+rotoffy(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad))+(int)(cellsize/2)))*0.3);
		}	
		
	}
	
	
	
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