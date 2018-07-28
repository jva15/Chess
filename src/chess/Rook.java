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
		ID=4;
		I_index_x=5;
		I_index_y=factionID;
	}
	public Rook(Node cell, int fac) {
		super(cell, fac);
		setupID();
		
	}

	//highlights cells in horizontal/vertical line from Rook piece
	public void highlight(boolean b) 
	{
		lineHighlighter(b);	
	}
	
	//sets attack risk in cells in horizontal/vertical lines from Rook piece
	public void setRange(boolean b)
	{
		lineAttack(b);
	}
	
}
