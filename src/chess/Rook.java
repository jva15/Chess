package src.chess;

import java.awt.event.ActionEvent;

public class Rook extends Actor {

	public Rook() {
		I_index_x=ID=4;
		
	}

	public Rook(Node cell, int fac) {
		super(cell, fac);
		I_index_x=ID=4;
		I_index_y=fac;
	}

	//public void actionPerformed(ActionEvent e) {
	public void highlightTest() {
		currentcell.highlighted = true;
		rookHighlighter(currentcell.adgNodes[0], 0);
		rookHighlighter(currentcell.adgNodes[1], 1);
		rookHighlighter(currentcell.adgNodes[2], 2);
		rookHighlighter(currentcell.adgNodes[3], 3);
		
	}
	
	private void rookHighlighter(Node curCell, int i)
	{
		if(curCell == null)                                //if curCell == null, end of board has been reached
			return;
		else if(!curCell.occupied)                         //if curCell is not occupied, highlight and perform recursive call
		{
			curCell.highlighted = true;
			rookHighlighter(curCell.adgNodes[i], i);       //call again for adjacent node
		}
		else if(curCell.actor.factionID != this.factionID) //if curCell's actor is different faction, highlight
			curCell.highlighted = true;
	}
}
