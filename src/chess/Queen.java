package src.chess;

public class Queen extends Actor {

	public Queen() {
		super();
		I_index_x=ID=2;
	}

	public Queen(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=2;
		I_index_y=fac;
	}
	

}
