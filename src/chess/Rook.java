package src.chess;

import java.awt.event.ActionEvent;

public class Rook extends Actor {

	public Rook() {
		super();
		setupID();
	}
	
	public Rook(int f) {
		super(f);
		setupID();
	}

	public void setupID() {
		I_index_x=ID=4;
		I_index_y=factionID;
	}
	public Rook(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=4;
		I_index_y=fac;
		
	}

	public void highlight(boolean b) 
	{
		super.lineHighlighter(currentcell, b);	
	}
	
}
