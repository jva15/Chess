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
		I_index_x=ID=5;
		I_index_y=fac;
	}

	public void setupID() {
		I_index_x=ID=5;
		I_index_y=factionID;
	}
	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}
}
