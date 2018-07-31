/*all code by Joseph Auguste and Matthew Klopfenstein*/
package src.chess;

import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class VictorScreen extends Sprite {
	
	
	public VictorScreen(int player) {
		//get the bounds
		super();
		pixellength=893;
		pixelheight=94;
		
		//get the index
		imagefileindex=2;
		I_index_y=player;
		I_index_x=0;
		
		xsize=publicdata.width/2;   //pixellength;
		ysize=publicdata.height/10;
		xoffset=publicdata.width/2;
		yoffset=publicdata.height/2;
		Active=true;
		setOpaque(false);
		setVisible(true);
		
		repaint();
	}


}
