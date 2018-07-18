package src.chess;

import java.awt.event.ActionEvent;

public class Bishop extends Actor {

	public Bishop() {
		super();
		setupID();
	}

	public Bishop(int f) {
		super(f);
		setupID();
	}
	public Bishop(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=3;
		I_index_y=fac;
	}
	
	public void setupID() {
		I_index_x=ID=3;
		I_index_y=factionID;
	}
	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}

}
