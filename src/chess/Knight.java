package src.chess;

import java.awt.event.ActionEvent;

public class Knight extends Actor {

	public Knight() {
		I_index_x=ID=5;
		
	}

	public Knight(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=5;
		I_index_y=fac;
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
}

