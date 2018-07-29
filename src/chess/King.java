package src.chess;

import java.awt.event.ActionEvent;

public class King extends Actor {
	
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
}

