package src.chess;

import java.awt.event.ActionEvent;

public class Pawn extends Actor {

	public Pawn() {
		super();
		ID=0;
		I_index_x=I_index_y=0;
		
	}
	public Pawn(Node cell,int faction) {
		super(cell,faction);
		I_index_y=(faction%2);
	}
	

	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}
}
