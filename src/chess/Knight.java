package src.chess;

import java.awt.event.ActionEvent;

public class Knight extends Actor {

	public Knight() {
		super();
		setupID();
	}

	public Knight(int f) {
		super(f);
		setupID();
	}
	public Knight(Node cell, int fac) {
		super(cell, fac);
		setupID();
	}

	public void setupID() {
		I_index_x=4;
		ID=5;
		I_index_y=factionID;
	}
	
	public void highlight(boolean b) 
	{
		Node n = currentcell;
		n.highlighted = b; 
		if(n.adgNodes[0] != null)
		{
			 n = n.adgNodes[0];
			 if(n.adgNodes[0] != null)
			 {
				 n = n.adgNodes[0];
				 if(n.adgNodes[3] != null)
					 if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
						 n.adgNodes[3].highlighted = b;
				 if(n.adgNodes[1] != null)
					 if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
						 n.adgNodes[1].highlighted = b;
			 }
		}
		n = currentcell; 
		if(n.adgNodes[1] != null)
		{
			 n = n.adgNodes[1];
			 if(n.adgNodes[1] != null)
			 {
				 n = n.adgNodes[1];
				 if(n.adgNodes[0] != null)
					 if(!n.adgNodes[0].occupied || n.adgNodes[0].actor.factionID != this.factionID)
						 n.adgNodes[0].highlighted = b;
				 if(n.adgNodes[2] != null)
					 if(!n.adgNodes[2].occupied || n.adgNodes[2].actor.factionID != this.factionID) 
						 n.adgNodes[2].highlighted = b;
			 }
		}
		n = currentcell;
		if(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			if(n.adgNodes[2] != null)
			{
				n = n.adgNodes[2];
				if(n.adgNodes[1] != null)
					if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
						n.adgNodes[1].highlighted = b;
				if(n.adgNodes[3] != null)
					if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
						n.adgNodes[3].highlighted = b; 
			}
		}
		n = currentcell; 
		if(n.adgNodes[3] != null)
		{
			n = n.adgNodes[3];
			if(n.adgNodes[3] != null)
			{
				n = n.adgNodes[3];
				if(n.adgNodes[0] != null)
					if(!n.adgNodes[0].occupied || n.adgNodes[0].actor.factionID != this.factionID)
						n.adgNodes[0].highlighted = b;
				if(n.adgNodes[2] != null)
					if(!n.adgNodes[2].occupied || n.adgNodes[2].actor.factionID != this.factionID)
						n.adgNodes[2].highlighted = b; 
			}
		}
	}//end of highlight method for Knight actor class
	
	public void setRange(boolean inrange)
	{
		Node n = currentcell; 
		if(n.adgNodes[0] != null)
		{
			 n = n.adgNodes[0];
			 if(n.adgNodes[0] != null)
			 {
				 n = n.adgNodes[0];
				 if(n.adgNodes[3] != null)
					 n.adgNodes[3].setAttackRisk(inrange,factionID);
				 if(n.adgNodes[1] != null)
					 n.adgNodes[1].setAttackRisk(inrange, factionID);
			 }
		}
		n = currentcell; 
		if(n.adgNodes[1] != null)
		{
			 n = n.adgNodes[1];
			 if(n.adgNodes[1] != null)
			 {
				 n = n.adgNodes[1];
				 if(n.adgNodes[0] != null)
					 n.adgNodes[0].setAttackRisk(inrange, factionID);
				 if(n.adgNodes[2] != null)
					 n.adgNodes[2].setAttackRisk(inrange, factionID);
			 }
		}
		n = currentcell;
		if(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			if(n.adgNodes[2] != null)
			{
				n = n.adgNodes[2];
				if(n.adgNodes[1] != null)
					n.adgNodes[1].setAttackRisk(inrange, factionID);
				if(n.adgNodes[3] != null)
					n.adgNodes[3].setAttackRisk(inrange, factionID);
			}
		}
		n = currentcell; 
		if(n.adgNodes[3] != null)
		{
			n = n.adgNodes[3];
			if(n.adgNodes[3] != null)
			{
				n = n.adgNodes[3];
				if(n.adgNodes[0] != null)
					n.adgNodes[0].setAttackRisk(inrange, factionID);
				if(n.adgNodes[2] != null)
					n.adgNodes[2].setAttackRisk(inrange, factionID);
			}
		}
	}//end of setRange method for Knight actor class
}

