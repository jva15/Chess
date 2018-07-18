package src.chess;

import java.awt.event.ActionEvent;

public class Pawn extends Actor {

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
		ID=0;
		I_index_x=0;
		I_index_y=factionID;
		
	}
	

	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}
}
