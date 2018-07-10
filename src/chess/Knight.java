package src.chess;

public class Knight extends Actor {

	public Knight() {
		I_index_x=ID=5;
		
	}

	public Knight(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=5;
		I_index_y=fac;
	}

}
