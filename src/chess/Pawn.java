package src.chess;

import java.awt.event.ActionEvent;

public class Pawn extends Actor {
	
	public boolean firstMove = true;   //true if pawn has not moved yet, false otherwise
	private int direction;              //pawns cannot move backward
	int ID=0;
	
	
	public Pawn() {
		super();
		setupID();
	}
	public Pawn(int f) {
		super(f);
		setupID();
	}
	public Pawn(Node cell,int faction) {
		super(cell,faction);
		//I_index_y=(faction%2);
		setupID();
	}
	
	public void setupID(){
		
		if(factionID == 0)
			direction = 0;
		else
			direction = 2;
		
		I_index_x=0;
		I_index_y=factionID;
		
	}
	
	//needs to add functionality for en passant: 
	public void highlight(boolean b) 
	{
		Node n = currentcell;
		n.highlighted = b;
		if(n.adgNodes[direction] != null)
		{
			n = n.adgNodes[direction];
			if(!n.occupied)
				n.highlighted = b;
			if(n.adgNodes[1] != null)
				if(n.adgNodes[1].occupied && n.adgNodes[1].actor.factionID != this.factionID)
					n.adgNodes[1].highlighted = b;
			if(n.adgNodes[3] != null)
				if(n.adgNodes[3].occupied && n.adgNodes[2].actor.factionID != this.factionID)
					n.adgNodes[3].highlighted = b;
			if(firstMove)
				if(!n.occupied && !n.adgNodes[direction].occupied)
					n.adgNodes[direction].highlighted = b;
		}	
	}
	
	public void setRange(boolean inrange)
	{
		Node n = currentcell; 
		n = n.adgNodes[direction];
		if(n.adgNodes[1] != null)
			n.adgNodes[1].setAttackRisk(inrange, factionID);
		if(n.adgNodes[3] != null)
			n.adgNodes[3].setAttackRisk(inrange, factionID);
	}	
}
