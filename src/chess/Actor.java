package src.chess;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Actor extends JPanel implements ActionListener{//extend button
	int ID;
	int factionID;//0: player 1, 1 player 2
	Node currentcell;//keep this in mind when changing the Actors position.
	boolean Active;//TODO make it so Active is used to indicate whether or an item is rendered
	
	//image access info  
	int pixellength=27;//
	int pixelheight=38;//
	int I_index_x,I_index_y;//sprite selectors for the image file
	public static String imagefile="chess_pieces.png";
	public static BufferedImage image;
	public static boolean imageisset=false;
	
	//frame draw info
	int xoffset=0,yoffset=0;
	int xsize=70;
	int ysize=(int)(70*2);
	boolean highlighted;
	Rectangle Boundingbox;//cause setBounds took too much processing 
	
	
	
	Actor(){
		super();
		if(!imageisset)
		{
			try {
				imageisset=true;
				image=ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\chess\\" + imagefile));
			}catch(IOException e)
			{
				imageisset=false;
				System.out.println("can't load "+ System.getProperty("user.dir")+"\\src\\chess\\"+imagefile);}
		}
			I_index_x=I_index_y=0;
			highlighted=false;
		
			setVisible(true);
			
			
			//addActionListener(this);
			//setVisible(false);
	}
	//create actor and associate to the cell
	Actor(Node cell,int fac)
	{
		this();
		setCell(cell);
		setatoffset(cell.Virtualoffsetx,cell.Virtualoffsety);
		factionID=fac;
		cell.actor=this;
		Active=true;
	}
	
	public int GetY() {return yoffset;}
	//set offset to the lower left corner of sprite
	public void setatoffset(int ox, int oy) {
		this.xoffset=ox;
		this.yoffset=oy-ysize;
		
	}
	
	
	
	public void updatebycell()
	{
		int offx;
		if(currentcell.cellsize>xsize) offx=currentcell.Virtualoffsetx+(currentcell.cellsize/2-(xsize/2));
		else offx = currentcell.Virtualoffsetx-((xsize/2)-currentcell.cellsize/2);
		setatoffset(offx,currentcell.Virtualoffsety);
		setBounds(offx,currentcell.Virtualoffsety-ysize,xsize,ysize);
		
	}
	public void updatebycell(int moveoffsetx,int moveoffsety)//not setup yet, but it will updates by cell, with an offset
	{
		int offx;
		if(currentcell.cellsize>xsize) offx=currentcell.Virtualoffsetx+(currentcell.cellsize/2-(xsize/2));
		else offx = currentcell.Virtualoffsetx-((xsize/2)-currentcell.cellsize/2);
		setatoffset(offx+moveoffsetx,currentcell.Virtualoffsety+moveoffsety);
		setBounds(offx,currentcell.Virtualoffsety-ysize,xsize,ysize);
		
	}
	public void setCell(Node cell) {
		currentcell=cell;
		currentcell.actor=this;
		currentcell.occupied=true;
	}
	public void unsetCell()
	{
		currentcell.actor=null;
		currentcell=null;
		currentcell.occupied=false;
	}
	public void kill()//TODO: check if it works; delete the comment below if it no longer applies
	{
		/*end result should be that it is not being rendered on screen.
		*but the data will still be loaded, so it may need to be taken care of in gridpanel
		*
		*/
		unsetCell();
		Active=false;
		
	}
	public void setoffset(int ox,int oy) {
		
		this.xoffset=ox;
		this.yoffset=oy;
	
	}
	
	
	public void Drawframe(Graphics2D g2d) {
		if(Active)
		{	
			updatebycell();
			repaint();
		}
	}
	public void moveTo(Node newcell) {
		
		//setRange(false);
		//TODO: run function to dehighlight potential move here here.
		//unsetCell();
		//if (newcell.actor!=null)newcell.actor.kill();
		//setCell(newcell);
		//setRange(true);
		
	}
	
	public void setRange(boolean inrange)//uses the classmember factionId aswell
	{
		/*TODO: Impliment setRange for each of the subclasses of Actor
		* similar to highlight but instead of highlighting
		* it should call setAttackrisk(inrange,factionID) on the cell
		* remember not to let the piece setRange on itself
		* 
		* this would be a good oportunity to practice with iterators to avoid repeating code for both 
		* highlight and setRange
		*/
		
		
	}
	
	public void paintComponent(Graphics g){
		if(Active){
		super.paintComponent( g );
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(image,
			       0,0,xsize,ysize,//
				   //xoffset,yoffset,xoffset+xsize, yoffset+ysize,//
			       pixellength*I_index_x, pixelheight*I_index_y, pixellength*I_index_x+pixellength, pixelheight*I_index_y+pixelheight,
			       null);
		}
	}
	
	
	
public void actionPerformed(ActionEvent e) {
		
		System.out.println("Actor at "+xoffset+" & "+yoffset+" clicked");
		
		
	}
	
	public void setsize(int xs,int ys) {
		this.xsize=xs;
		this.ysize=ys;
	}
	
	
		


}
