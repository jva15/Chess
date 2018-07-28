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

	
	
	
	
public abstract class Actor extends JPanel implements ActionListener{//extend button
	
	public static int TotalImages;
	public static String[] imagefilelist= {"chess_pieces.png","test_character.png"};
	public static BufferedImage[] imageset;
	public static boolean imageisset=false;
	int ID;
	int factionID;//0: player 1, 1 player 2
	Node currentcell;//keep this in mind when changing the Actors position.
	boolean Active;//

	
	//image access info  
	int pixellength=27;//
	int pixelheight=39;//
	int imagefileindex=0;//imagefileindex
	int I_index_x,I_index_y;//sprite selectors
	public String imagefile="chess_pieces.png";
	
	
	
	//frame draw info
	int ratio=1;
	int xoffset=0,yoffset=0;
	int xsize=70;
	int ysize=(int)(70*2);
	boolean highlighted;
	Rectangle Boundingbox;//cause setBounds took too much processing 
	boolean set_to_a_cell=false;
	
	public Actor()
	{
		super();
		setOpaque(false);
		//resizing
		ysize= (int)((((double)ysize)/(300*6))*publicdata.height);
		xsize= (int)((((double)xsize)/(300*6))*publicdata.width);

		if(!imageisset)
		{
			TotalImages=imagefilelist.length;
			imageset= new BufferedImage[TotalImages];
			
			for(int i=0;i<TotalImages;i++)
			{
				
				imagefile=imagefilelist[i];
				try {
					imageisset=true;
					imageset[i]=ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\chess\\" + imagefile));
					
				}catch(IOException e)
				{
					imageisset=false;
					System.out.println("can't load "+ System.getProperty("user.dir")+"\\src\\chess\\"+imagefile);}
			}
		}
			I_index_x=I_index_y=0;
			highlighted=false;
		
			setVisible(true);
			
			
			//addActionListener(this);
			//setVisible(false);
	}
	//create actor and associate to the cell
	public Actor(int fac)
	{
		this();
		factionID=fac;
		Active=false;
		set_to_a_cell=false;
	}
	
	Actor(Node cell,int fac)
	{
		this(fac);
		setCell(cell);
		//setatoffset(cell.Virtualoffsetx,cell.Virtualoffsety);
		Active=true;
	}
	
	
	
	public int GetY() {return yoffset;}
	//set offset to the lower left corner of sprite
	public void setatoffset(int ox, int oy) {
		this.xoffset=ox;
		this.yoffset=oy-(int)(ysize);
		
	}
	
	
	
	public void updatebycell()
	{
		if(Active&&set_to_a_cell)
		if (currentcell!=null);
		{
			int offx;
			if(currentcell.cellsize>xsize) offx=currentcell.Virtualoffsetx+(currentcell.cellsize/2-(xsize/2));
			else offx = currentcell.Virtualoffsetx-((xsize/2)-currentcell.cellsize/2);
			setatoffset(offx,currentcell.Virtualoffsety);
			setBounds(offx,currentcell.Virtualoffsety-ysize,xsize,ysize);
		}
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
		set_to_a_cell=true;
		updatebycell();
	}
	public void unsetCell()
	{
		currentcell.actor=null;
		currentcell.occupied=false;
		currentcell=null;
		set_to_a_cell=false;
		
	}
	public void kill()//TODO: check if it works; delete the comment below if it no longer applies
	{
		/*end result should be that it is not being rendered on screen.
		*but the data will still be loaded, so it may need to be taken care of in gridpanel
		*
		*/
		unsetCell();
		Active=false;
		set_to_a_cell=false;
		
	}
	public void setoffset(int ox,int oy) {
		
		this.xoffset=ox;
		this.yoffset=oy;
	
	}
	
	
	public void Drawframe(Graphics2D g2d) {
		if(Active)
		{	
			if(set_to_a_cell) updatebycell();
			repaint();
		}
	}
	public void moveTo(Node newcell) {
		
		//setRange(false);
		highlight(false);
		unsetCell();
		if (newcell.actor!=null)newcell.actor.kill();
		setCell(newcell);
		//setRange(true);
		
	}
	
	public abstract void setRange(boolean inrange); //uses the classmember factionId aswell

		/*TODO: Impliment setRange for each of the subclasses of Actor
		* similar to highlight but instead of highlighting
		* it should call setAttackrisk(inrange,factionID) on the cell
		* remember not to let the piece setRange on itself
		* 
		* this would be a good opportunity to practice with iterators to avoid repeating code for both 
		* highlight and setRange
		*/
		
		

	
	public void paintComponent(Graphics g){
		if(Active){
		super.paintComponent( g );
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(imageset[imagefileindex],
			       0,0,xsize,ysize,//
				   //xoffset,yoffset,xoffset+xsize, yoffset+ysize,//
			       pixellength*I_index_x, pixelheight*I_index_y, pixellength*I_index_x+pixellength, pixelheight*I_index_y+pixelheight,
			       null);
		}
	}
	
	public abstract void highlight(boolean b);  //abstract method will be defined in subclasses
	
	//function highlights or unhighlights cells vertically/horizontally in all directions, starting from currentcell
    //and ending at end of board/other piece
	protected void lineHighlighter(boolean b)
	{ 
		Node n = currentcell; 
		n.highlighted = b; 
		while(n.adgNodes[0] != null)
		{
			n = n.adgNodes[0];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{	
				if(n.actor.factionID != this.factionID)	
					n.highlighted = b;
				break;
			}
		}
		n = currentcell; 
		while(n.adgNodes[1] != null)
		{
			n = n.adgNodes[1];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{	
				if(n.actor.factionID != this.factionID)	
					n.highlighted = b;
				break;
			}
		}
		n = currentcell;
		while(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{	
				if(n.actor.factionID != this.factionID)	
					n.highlighted = b;
				break;
			}
		}
		n = currentcell;
		while(n.adgNodes[3] != null)
		{
			n = n.adgNodes[3];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{	
				if(n.actor.factionID != this.factionID)	
					n.highlighted = b;
				break;
			}
		}
	}
	
	//sets attack risk of cells in horizontal/vertical line of piece:
	protected void lineAttack(boolean inrange)
	{
		Node n = currentcell; 
		while(n.adgNodes[0] != null)
		{
			n = n.adgNodes[0];                     //advance to next cell
			n.setAttackRisk(inrange, factionID);   //update attack risk of cell
			if(n.occupied)                         //if node is occupied, break
				break;
		}
		n = currentcell; 
		while(n.adgNodes[1] != null)
		{
			n = n.adgNodes[1];                     //advance to next cell
			n.setAttackRisk(inrange, factionID);   //update attack risk of cell
			if(n.occupied)                         //if node is occupied, break
				break;
		}
		n = currentcell;
		while(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];                     //advance to next cell
			n.setAttackRisk(inrange, factionID);   //update attack risk of cell
			if(n.occupied)                         //if node is occupied, break
				break;
		}
		n = currentcell;
		while(n.adgNodes[3] != null)
		{
			n = n.adgNodes[3];                     //advance to next cell
			n.setAttackRisk(inrange, factionID);   //update attack risk of cell
			if(n.occupied)                         //if node is occupied, break
				break;
		}
	}
	
	//function highlights or unhighlights cells diagonally in all directions, starting at currentcell
	//and ending at end of board/other piece
	protected void diagonalHighlighter(boolean b) 
	{
		Node n = currentcell; 
		n.highlighted = b; 
		while(n.adgNodes[1] != null && n.adgNodes[1].adgNodes[0] != null)
		{
			n = n.adgNodes[1].adgNodes[0];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{
				if(n.actor.factionID != this.factionID)
					n.highlighted = b;
				break;
			}
		}
		n = currentcell; 
		while(n.adgNodes[1] != null && n.adgNodes[1].adgNodes[2] != null)
		{
			n = n.adgNodes[1].adgNodes[2];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{
				if(n.actor.factionID != this.factionID)
					n.highlighted = b;
				break;
			}
		}	
		n = currentcell; 
		while(n.adgNodes[3] != null && n.adgNodes[3].adgNodes[0] != null)
		{
			n = n.adgNodes[3].adgNodes[0];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{
				if(n.actor.factionID != this.factionID)
					n.highlighted = b;
				break;
			}	
		}
		n = currentcell; 
		while(n.adgNodes[3] != null && n.adgNodes[3].adgNodes[2] != null)
		{
			n = n.adgNodes[3].adgNodes[2];
			if(!n.occupied)
				n.highlighted = b;
			else 
			{
				if(n.actor.factionID != this.factionID)
					n.highlighted = b;
				break;
			}
		}	
	}
	
	//sets attack risk for cells in diagonal range from piece:
	protected void diagAttack(boolean inrange)
	{
		Node n = currentcell; 
		while(n.adgNodes[1] != null && n.adgNodes[1].adgNodes[0] != null)
		{
			n = n.adgNodes[1].adgNodes[0];
			n.setAttackRisk(inrange, factionID);
			if(n.occupied)
				break;
		}
		n = currentcell; 
		while(n.adgNodes[1] != null && n.adgNodes[1].adgNodes[2] != null)
		{
			n = n.adgNodes[1].adgNodes[2];
			n.setAttackRisk(inrange, factionID);
			if(n.occupied)
				break;
		}	
		n = currentcell; 
		while(n.adgNodes[3] != null && n.adgNodes[3].adgNodes[0] != null)
		{
			n = n.adgNodes[3].adgNodes[0];
			n.setAttackRisk(inrange, factionID);
			if(n.occupied)
				break;
		}
		n = currentcell; 
		while(n.adgNodes[3] != null && n.adgNodes[3].adgNodes[2] != null)
		{
			n = n.adgNodes[3].adgNodes[2];
			n.setAttackRisk(inrange, factionID);
			if(n.occupied)
				break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setsize(int xs,int ys) {
		this.xsize=xs;
		this.ysize=ys;
	}
	
	
		


}
