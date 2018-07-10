package src.chess;

public class King extends Actor {

	public King() {
		super();
		ID=1;
		I_index_x=1;
	}

	public King(Node cell, int fac) {
		super(cell, fac);
		I_index_x=1;
		I_index_y=fac;
	}

}
