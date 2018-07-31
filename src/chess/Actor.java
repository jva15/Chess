/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;

import java.awt.Graphics2D;

public abstract class Actor extends Sprite {//extend button
	
	public boolean firstMove = true;   //true if actor has not moved yet, false otherwise
	                                   //only needed for pawns(for enPassant & double moves on first turns), 
	                                   //and kings and rooks(for castling)
	public boolean enPassantRight = false, enPassantLeft = false; //only used for pawns
	int ID;//ID specific to the piece(by )
	int factionID;//0: player 1, 1 player 2
	Node currentcell;//keep this in mind when changing the Actors position.
	

	
	boolean highlighted;
	boolean set_to_a_cell=false;
	
	public Actor()
	{
		super();
		highlighted=false;
	}
	//create actor, but don't attach to a cell, nor render it.
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
		Active=true;
	}
	
	
	
	public void moveTo(Node newcell) {
		
		highlight(false);
		unsetCell();
		if (newcell.actor!=null)newcell.actor.kill();
		setCell(newcell);
		
	}
	public abstract void highlight(boolean b);  //abstract method will be defined in subclasses
	public abstract void setRange(boolean inrange);
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
	public void setCell(Node cell) {
		currentcell=cell;
		currentcell.actor=this;
		currentcell.occupied=true;
		set_to_a_cell=true;
		updatebycell();
	}
	//set offset to the lower left corner of sprite
	public void setatoffset(int ox, int oy) {
		this.xoffset=ox;
		this.yoffset=oy-(int)(ysize);
		
	}
	public void setoffset(int ox,int oy) {
		
		this.xoffset=ox;
		this.yoffset=oy;
	
	}
	public void setsize(int xs,int ys) {
		this.xsize=xs;
		this.ysize=ys;
	}
	public void Drawframe(Graphics2D g2d) {
		if(Active)
		{	
			if(set_to_a_cell) updatebycell();
			repaint();
		}
	}
	
	//update offset by cell
	public void updatebycell(int moveoffsetx,int moveoffsety)//not setup yet, but it will updates by cell, with an offset
	{
		
		int offx;
		if(currentcell.cellsize>xsize) offx=currentcell.Virtualoffsetx+(currentcell.cellsize/2-(xsize/2));
		else offx = currentcell.Virtualoffsetx-((xsize/2)-currentcell.cellsize/2);
		setatoffset(offx+moveoffsetx,currentcell.Virtualoffsety+moveoffsety);
		setBounds(offx,currentcell.Virtualoffsety-ysize,xsize,ysize);
		
	}
	//update offset by cell
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
		King a;
		for(int i=0;i<4;i++)
		{
			n = currentcell;
			while(n.adgNodes[i] != null)
			{
				n = n.adgNodes[i];                     //advance to next cell
				n.setAttackRisk(inrange, factionID);   //update attack risk of cell
				if(n.occupied)                         //if node is occupied, check for a king and break
				{
					if(n.actor.ID==1&&n.actor.factionID!=factionID)//if the piece is an enemy king
					{
						a=(King) n.actor;
						if(inrange)a.setthreat((i*2)%8);
						else a.setthreat(-1);
						
					}
					break;
				}
			}
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
		Node n; 
		King a;
				
		for(int i=0;i<4;i++)
		{
			n = currentcell; 
			while(n.adgNodes[i] != null && n.adgNodes[i].adgNodes[(i+1)%4] != null)
			{
				n = n.adgNodes[i].adgNodes[(i+1)%4];
				n.setAttackRisk(inrange, factionID);
				if(n.occupied)
				{
					if(n.actor.ID==1&&n.actor.factionID!=factionID)//if the piece is an enemy king
					{
						a=(King) n.actor;
						if(inrange)a.setthreat(((i*2)+1)%8);
						else a.setthreat(-1);
					}
					break;
				}
				
			}
		}
		
	}
	
}
