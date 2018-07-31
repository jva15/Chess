/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;

import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.GridLayout; 
import javax.swing.JButton; 
import javax.swing.JLabel;

import src.chess.GameFrame.GridPanel;

import java.awt.event.ActionEvent;
import java.util.LinkedList; 

public class PawnProDialog extends JDialog
{
	//buttons to choose piece to promote pawn to: 
	JButton rook = new JButton("Rook");
	JButton knight = new JButton("Knight");
	JButton bishop = new JButton("Bishop");
	JButton queen = new JButton("Queen");
	
	private Actor ptp; //pawn to promote
	private LinkedList<Actor> pieces;
	private GridPanel gridcopy;
	
	public PawnProDialog(GameFrame owner, Actor piece, LinkedList<Actor> pieces,GridPanel GP)
	{
		super(owner, "Pawn Promotion");
		gridcopy=GP;
		ptp = piece; 
		this.pieces = pieces;
		setLayout(new GridLayout(5,1));
		add(new JLabel("Promote pawn to: "));
		buttonSetup();
		add(knight);
		add(bishop);
		add(rook);
		add(queen);
	}
	
	private void buttonSetup()
	{
		knight.addActionListener((ActionEvent e) -> {
			replacePawn(new Knight(ptp.factionID));
		});
		
		rook.addActionListener((ActionEvent e) -> {
			replacePawn(new Rook(ptp.factionID));
		});
		
		bishop.addActionListener((ActionEvent e) -> {
			replacePawn(new Bishop(ptp.factionID));
		});
		
		queen.addActionListener((ActionEvent e) -> {
			replacePawn(new Queen(ptp.factionID));
		});
	}
	
	//still needs some work, I'm not sure how to add pieces to board
	private void replacePawn(Actor newPiece)
	{
		Node n= ptp.currentcell;
		ptp.unsetCell();
		newPiece.setCell(n);
		newPiece.Active=true;
		pieces.add(newPiece);
		
		
		
		pieces.remove(ptp);
		gridcopy.remove(ptp);
		gridcopy.add(newPiece);
		
		this.dispose();
	}
}
