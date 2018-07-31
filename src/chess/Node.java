/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;
import java.awt.*;

public class Node {
	
	public int xoffset,yoffset,Virtualoffsetx,Virtualoffsety;
	public int cellsize;
	public Polygon poly;
	public Actor actor;
	public Node[] adgNodes;
	public int visited;//
	public boolean occupied,highlighted;
	public int[] attackrisk;//counter for attackrisk
	public int[] gaurdchan;
	public Color color;
	Point[] points;
	
	
	
	
	
	
	int[] height=new int[4];//height offset
	
	
	public Node() {
		// TODO Auto-generated constructor stub
		points= new Point[4];
		poly=null;
		actor=null;
		visited=0;
		occupied=false;
		adgNodes=new Node[4];
		xoffset=yoffset=0;
		cellsize=150;
		highlighted=false;
		attackrisk=new int[2];
		for(int i =0 ; i<4;i++) height[i]=0;
		
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
		return attackrisk[facID^1];
	}
	
	public Node(int x,int y)
	{
		this();
		setoffset(x,y);
	}
	
	/*							  3
	 * node adgacancy list is  0     2
	 * 							  1
	 * 
	 * directions through this function are interpreted as
	 * 		7 6 5
	 * 		0   4
	 * 		1 2 3
	 */
	
	public Node nodeindirection(int dir)/*0-8*/
	{
		dir=dir%8;
		Node newway;
		newway=adgNodes[dir/2];
		if(dir%2==0) return newway;
		else if(newway!=null) {
			newway=newway.adgNodes[((dir/2)+1)%4];
		}
		return newway;
	}
	public void setoffset(int x, int y)
	{
		xoffset=x;
		yoffset=y;
	}
	//p is the faction
	public Node(int x,int y,int celsize,boolean p)
	{
		
		this(x,y);
		setcellsize(celsize);
		
		for(int i =0;i<4;i++) {height[i]=0;points[i]=new Point(0,0);}
		setpolypoints(x,y);
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
		setpolypoints(x,y);
	}
	
	
	
	/*	
	 * 
	 * 	0_3	0_3
	 * 	|_|	|_| for corners,
	 * 	1 2 1 2					  3
	 * 	0_3	0_3					2   0
	 * 	|_|	|_| for corners,	  1	
	 * 	1 2 1 2
	 * */
	//
	public void setpointheight(int corner,int pheight){
		
		height[corner]=-pheight;
		if(corner==0)
		{
			if(adgNodes[2]!=null) {adgNodes[2].height[3]=-pheight;
			if(adgNodes[2].adgNodes[3]!=null)adgNodes[2].adgNodes[3].height[2]=-pheight;}
			if(adgNodes[3]!=null)adgNodes[3].height[1]=-pheight;
		}
		else if(corner==1)
		{
			
			if(adgNodes[1]!=null) { adgNodes[1].height[0]=-pheight;
			
			if(adgNodes[1].adgNodes[2]!=null)adgNodes[1].adgNodes[2].height[3]=-pheight;
		}
			if(adgNodes[2]!=null)adgNodes[2].height[2]=-pheight;
		}
		else if(corner==2)
		{
			if(adgNodes[0]!=null) { adgNodes[0].height[1]=-pheight;
			
				if(adgNodes[0].adgNodes[1]!=null)adgNodes[0].adgNodes[1].height[0]=-pheight;
			}
			if(adgNodes[1]!=null)adgNodes[1].height[3]=-pheight;
		}
		else if(corner==3)
		{
			if(adgNodes[3]!=null) { adgNodes[3].height[2]=-pheight;
			
				if(adgNodes[3].adgNodes[0]!=null)adgNodes[3].adgNodes[0].height[1]=-pheight;
			}
			if(adgNodes[0]!=null)adgNodes[0].height[0]=-pheight;
		}
		
	
	}
	
	
	public void setpolypoints(int x, int y)
	{
		points[0].setLocation(x, y);
		points[1].setLocation(x+cellsize, y);		
		points[2].setLocation(x+cellsize, y+cellsize);		
		points[3].setLocation(x, y+cellsize);		
		
	}
	int[] xc=new int[4];
	int[] yc=new int[4];
	double[] dxc=new double[4];
	double[] dyc=new double[4];
	double[] dtyc=new double[4];
	double[] dtxc=new double[4];
	Point[] p= new Point[4];
	
	
	public Polygon getPoly(int tx,int ty, int[] center,double radians,double scale)
	{
		
		
		
		int[] xy=new int[2];
		
		for(int i=0;i<4;i++)
		{
			xy[0]=(int)(points[i].getX());
			xy[1]=(int)(points[i].getY());
			xy=rotoffc(center,xy,radians);
			xy[1]=(int)(xy[1]*scale);
			xc[i]=xy[0]+tx;
			yc[i]=xy[1]+ty+height[i];
					
		}
		
		
		poly=new Polygon(xc,yc,4);
		
		return poly;
	}
	
	public int[] rotoffc(int[] cp,int[] p , double theta) {//returns the rotated point
		int[] np=new int[2];
		np[0]=cp[0]+ rotoffx((p[1]-cp[1]), (p[0]-cp[0]), theta);//calculates x
		np[1]=cp[1]+ rotoffy((p[1]-cp[1]), (p[0]-cp[0]), theta);
		return np;
	}
	
	//functions for rotating
	public int rotoffx(int py, int px, double theta) {
		return (int)((double)py*Math.cos(theta)-(double)px*Math.sin(theta));
	}
	public int rotoffy(int py, int px, double theta) {
		return (int)((double)px*Math.cos(theta)+(double)py*Math.sin(theta));
	}
	

}

