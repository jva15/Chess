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
	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}

}
