package src.chess;

import java.awt.event.ActionEvent;

public class Queen extends Actor {

	public Queen() {
		super();
		setupID();
	}
	public Queen(int f) {
		super(f);
		setupID();
	}

	public Queen(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=2;
		I_index_y=fac;
	}
	
	public void setupID() {
		I_index_x=ID=2;
		I_index_y=factionID;
	}
	
	public void highlight(boolean b) 
	{
		super.lineHighlighter(currentcell, b);
		super.diagonalHighlighter(currentcell, b);
	}
}
