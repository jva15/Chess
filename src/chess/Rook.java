package src.chess;

public class Rook extends Actor {

	public Rook() {
		I_index_x=ID=4;
		
	}

	public Rook(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=4;
		I_index_y=fac;
	}

}
