package src.chess;

public class Bishop extends Actor {

	public Bishop() {
		I_index_x=ID=3;
		
	}

	public Bishop(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=3;
		I_index_y=fac;
	}

}
