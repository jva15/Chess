package src.chess;

import javax.swing.JDialog;
import java.awt.Frame;
import java.awt.GridLayout; 
import javax.swing.JButton; 
import javax.swing.JLabel;
import java.awt.event.ActionEvent;

public class PawnProDialog extends JDialog
{
	//buttons to choose piece to promote pawn to: 
	JButton rook = new JButton("Rook");
	JButton knight = new JButton("Knight");
	JButton bishop = new JButton("Bishop");
	JButton queen = new JButton("Queen");
	
	Actor ptp; //pawn to promote
	
	public PawnProDialog(GameFrame owner, Actor piece)
	{
		super(owner, "Pawn Promotion");
		ptp = piece; 
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
		Node n = ptp.currentcell;
		ptp.kill();
		newPiece.setCell(n);
		//probably needs to add some stuff here
		this.dispose(); //I believe this will close the dialog box
	}
}
