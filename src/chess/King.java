/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;

import java.awt.event.ActionEvent;

public class King extends Actor {
	
	
	private int directionofthreat=-1; 
	
	public King() {
		super();
		setupID();
	}

	public King(int f) {
		super(f);
		setupID();
	}
	
	public King(Node cell, int fac) {
		super(cell, fac);
		I_index_x=1;
		I_index_y=fac;
	}
	public void setupID() {
		ID=1;
		I_index_y=factionID;
		I_index_x=1;
	}
	//sets the reversed direction
	public void setthreat(int incomingdir ) {
		if(incomingdir!=-1)directionofthreat=((incomingdir+4)%8);
		else directionofthreat=-1;
		}
	
	
	//needs to be aware that King cannot put itself in a capturable position -- this functionality not added yet
	public void highlight(boolean b) {
		Node n = currentcell; 
		n.highlighted = b; 
		if(n.adgNodes[1] != null)
			if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
				if(n.adgNodes[1].getAttackRisk(factionID) == 0)
					n.adgNodes[1].highlighted = b;
		if(n.adgNodes[3] != null)
			if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
				if(n.adgNodes[3].getAttackRisk(factionID) == 0)
					n.adgNodes[3].highlighted = b;
		if(n.adgNodes[0] != null)
		{
			n = n.adgNodes[0];
			if(!n.occupied || n.actor.factionID != this.factionID)
				if(n.getAttackRisk(factionID) == 0)
					n.highlighted = b; 
			if(n.adgNodes[1] != null)
				if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
					if(n.adgNodes[1].getAttackRisk(factionID) == 0)
						n.adgNodes[1].highlighted = b; 
			if(n.adgNodes[3] != null)
				if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
					if(n.adgNodes[3].getAttackRisk(factionID) == 0)
						n.adgNodes[3].highlighted = b;
		}
		n = currentcell;
		if(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			if(!n.occupied || n.actor.factionID != this.factionID)
				if(n.getAttackRisk(factionID) == 0)
					n.highlighted = b; 
			if(n.adgNodes[1] != null)
				if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
					if(n.adgNodes[1].getAttackRisk(factionID) == 0)
						n.adgNodes[1].highlighted = b; 
			if(n.adgNodes[3] != null)
				if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
					if(n.adgNodes[3].getAttackRisk(factionID) == 0)
						n.adgNodes[3].highlighted = b;
		}	
		n = currentcell;
		
		//castling support:
		if(firstMove)
		{
			while(n.adgNodes[1] != null)
			{
				n = n.adgNodes[1];
				if(n.adgNodes[1] == null)
				{
					if(n.occupied && n.actor.firstMove && n.actor.ID == 5 && n.getAttackRisk(factionID) == 0)
						n.highlighted = b;
				}
				else if(n.occupied || n.getAttackRisk(factionID) != 0)
					break;
			}
			n = currentcell;
			while(n.adgNodes[3] != null)
			{
				n = n.adgNodes[3];
				if(n.adgNodes[3] == null)
				{
					if(n.occupied && n.actor.firstMove && n.actor.ID == 5 && n.getAttackRisk(factionID) == 0)
						n.highlighted = b;
				}
				else if(n.occupied || n.getAttackRisk(factionID) != 0)
					break;
			}
		}
	}

	public void setRange(boolean inrange)
	{
		Node n = currentcell; 
		if(n.adgNodes[1] != null)
			n.adgNodes[1].setAttackRisk(inrange, factionID);
		if(n.adgNodes[3] != null)
			n.adgNodes[3].setAttackRisk(inrange, factionID);
		if(n.adgNodes[0] != null)
		{
			n = n.adgNodes[0];
			n.setAttackRisk(inrange, factionID);
			if(n.adgNodes[1] != null)
				n.adgNodes[1].setAttackRisk(inrange, factionID); 
			if(n.adgNodes[3] != null)
				n.adgNodes[3].setAttackRisk(inrange, factionID);
		}
		n = currentcell;
		if(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			n.setAttackRisk(inrange, factionID);
			if(n.adgNodes[1] != null)
				n.adgNodes[1].setAttackRisk(inrange, factionID); 
			if(n.adgNodes[3] != null)
				n.adgNodes[3].setAttackRisk(inrange, factionID);
		}
	}
	//if the king cant move
	public boolean isTrapped()
	{
		Node way;
		for(int i=0;i<8;i++)
		{
			way=this.currentcell.nodeindirection(i);
			if(way!=null)//its not an edge?	 
			{
				if(way.getAttackRisk(factionID)==0) //no one can attack me there
				{
					if(way.actor==null) return false;
					
					if(way.actor!=null&&way.actor.factionID==factionID) //no bumbling idiot to block the way
					{
						return false;
						//
					}
				}
			}
		}
		return true;

	}
	//if there is no piece that could take the hit nor attack the assailant
	public boolean cantcounter(Actor Piece)
	{
		
		
		Node cell;
		if(directionofthreat==-1) return false;//if the king isn't in check, return false
		boolean not_adjacent=false;
		do{	
			cell=currentcell.nodeindirection(directionofthreat); //get a cell in said directio
			if(cell!=null&&cell.getAttackRisk(factionID^1)>0)
			{	
				if(not_adjacent||(cell.getAttackRisk(factionID^1)>1))				
					return true; //if in that cell, theres an ally 
			}
			not_adjacent=true;
		}while(cell!=null||cell.occupied!=true);
			
		
		
		
		return false;
		
		
	}
	
	
	
	
	
	
}

