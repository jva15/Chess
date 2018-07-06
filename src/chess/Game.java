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
	private int x = 435;		// x position of grid
	private int y = 685;		// y position of grid
	private int cellsize = 150;	// cell size on screen
	private int cellspace= 0;//15; // space between the cells
	private double rad = 40; // current view angle
	private double radd =0;// 0.1/2; // change in view angle
	private int gsize=8;//number of cells(this is then squared)

	int center=x+(cellsize+cellspace)*gsize/2-cellspace;
	int focalpointx=x+(int)(((center-x)*2-(cellsize))/2);
	int focalpointy=y+(int)(((center-y)*2-(cellsize))/2);
	
	
	private Polygon boxgrid[][];  //tile grid
	private Node cellgrid[][];  //actor grid
	
	
	
	private int dx = 0;		// reserved for player gridmovement
	private int dy = 0;		// increment amount (y coord)
	
	
	
	
	
	
	
	
	public GridPanel() 
	{
		//TODO: set to work for standalones
	   /*
		blockfile= new File(System.getProperty("user.dir")+"\\src\\graphicstest\\" + "block.png");
	   try {
	   image=ImageIO.read(blockfile);
	   }catch(IOException e){System.out.println(System.getProperty("user.dir")+"fuck");}
	*/
	   timer = new Timer(delay, this);
	   boxgrid=new Polygon[gsize][gsize];
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
	   int[] xa=new int[4];
	   int[] ya=new int[4];
	   int xoffset,yoffset;

	   int soffsety=(int)(-47*3.3);//for testing purposes; should be replaced by the actors value at time of application
	   int soffsetx=0;//for testing purposes; should be replaced by the actors value at time of application
	   int actorsizey=(int)(47*3.5);
	   
	   Graphics2D g2d = (Graphics2D)g;
	    //x=y=getHeight()/2;
	    int xpof,ypof;
	    dx=dy=0;
	    	    
	    
		/*for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)
		   {
			
				xoffset=x+i*(cellsize+cellspace);
				yoffset=y+j*(cellsize+cellspace);
				xa[0]=xoffset;
				ya[0]=yoffset;
				xa[1]=xoffset+cellsize;
				ya[1]=yoffset;
				xa[2]=xoffset+cellsize;
				ya[2]=yoffset+cellsize;
				xa[3]=xoffset;
				ya[3]=yoffset+cellsize;
				
				boxgrid[i][j]=new Polygon( xa , ya , 4);

		   
		   
		   }*/
		
		//prerender actors
		
		/*for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)
		   {
				//if(actorgrid[i][j].actor!=null) {
				xpof=x+i*(cellsize+cellspace);
				ypof=y+j*(cellsize+cellspace);
				
				
				//TODO:Actor , have actor sprite height replace baseoffset
				xoffset=focalpointx+soffsetx+rotoffx(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
				yoffset=focalpointy+soffsety+rotoffy(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
				//xoffset=focalpointx+actorgrid[i][j].actor.xsize*-1+rotoffx(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
				//yoffset=focalpointy+actorgrid[i][j].actor.ysize*-1+rotoffy(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
				//actorgrid[i][j].actor.setroffset(xoffset,yoffset);
				
				
				
				
				xa[0]=xoffset;
				ya[0]=(int)(yoffset);
				xa[1]=xoffset+cellsize;
				ya[1]=(int)(yoffset);
				xa[2]=xoffset+cellsize;
				ya[2]=(int)(yoffset)+(int)(actorsizey);
				xa[3]=xoffset;
				ya[3]=(int)(yoffset)+(int)(actorsizey);
				
				
				//actorgrid[i][j]=new Polygon( xa , ya , 4);
				//}
		   }*/
		
		g2d.scale(1, 0.3);		
		
		g2d.rotate(Math.toRadians(rad),center,center);
		
		for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)
			
		{
			int RoB=(i+j)%2;
			
			if(RoB==1)			g2d.setColor(Color.red);
			else 				g2d.setColor(Color.black);
			
			
			/*all colors
			int cindex=(i*gsize+j);
			if(cindex<225)
				g2d.setColor(new Color(0,0,(i*gsize+j)%225 ));
			else if(cindex>=225&&cindex<225*2)
				g2d.setColor(new Color(0,(i*gsize+j)%225,225-((i*gsize+j)%225) ));
			else if((cindex>=225*2&&cindex<225*3))
				g2d.setColor(new Color((i*gsize+j)%225,225-((i*gsize+j)%225),0));
			else g2d.setColor(new Color(225,(cindex%225),(cindex%225)));
			*/
			
			g2d.draw(cellgrid[i][j].poly);
			g2d.fill(cellgrid[i][j].poly);
		}
		
		g2d.rotate(Math.toRadians(-rad),center,center);
		
		//draw the actors
		for(int i=0;i<gsize;i++)for(int j =0;j<gsize;j++)
		   {
				//actorgrid[i][j].render(g2d);
				//g2d.setColor(Color.black);g2d.draw(actorgrid[i][j]);
				//g2d.setColor(Color.red);g2d.fill(actorgrid[i][j]);
				
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
	
	public static void nodeRender(Vector<Node> que,Graphics2D g2dn) {
		
		
	}	
	public static int rotoffx(int py, int px, double theta) {
		//return cx+((px-cx)* (int)Math.cos(theta));
		return (int)((double)py*Math.cos(theta)-(double)px*Math.sin(theta));
		
		
	}	
	public static int rotoffy(int py, int px, double theta) {
		return (int)((double)px*Math.cos(theta)+(double)py*Math.sin(theta));
		
		
		//return cy+((py-cy)* (int)Math.sin(theta));
		
	}	
		
	

}
