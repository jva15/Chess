package src.chess;

import java.awt.event.ActionEvent;

public class King extends Actor {

	public King() {
		super();
		ID=1;
		I_index_x=1;
	}

	public King(Node cell, int fac) {
		super(cell, fac);
		I_index_x=1;
		I_index_y=fac;
	}
	
	//needs to be aware that King cannot put itself in a capturable position -- this functionality not added yet
	public void highlight(boolean b) {
		Node n = currentcell; 
		n.highlighted = true; 
		if(n.adgNodes[1] != null)
			if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
				n.adgNodes[1].highlighted = b;
		if(n.adgNodes[3] != null)
			if(!n.adgNodes[3].occupied || n.adgNodes[1].actor.factionID != this.factionID)
				n.adgNodes[3].highlighted = b;
		if(n.adgNodes[0] != null)
		{
			n = n.adgNodes[0];
			if(!n.occupied || n.actor.factionID != this.factionID)
				n.highlighted = b; 
			if(n.adgNodes[1] != null)
				if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
					n.adgNodes[1].highlighted = b; 
			if(n.adgNodes[3] != null)
				if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
					n.adgNodes[3].highlighted = b;
		}
		if(n.adgNodes[2] != null)
		{
			n = n.adgNodes[2];
			if(!n.occupied || n.actor.factionID != this.factionID)
				n.highlighted = b; 
			if(n.adgNodes[1] != null)
				if(!n.adgNodes[1].occupied || n.adgNodes[1].actor.factionID != this.factionID)
					n.adgNodes[1].highlighted = b; 
			if(n.adgNodes[3] != null)
				if(!n.adgNodes[3].occupied || n.adgNodes[3].actor.factionID != this.factionID)
					n.adgNodes[3].highlighted = b;
		}	
	}
}
