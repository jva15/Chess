package src.chess;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Actor extends JButton{//extend button
	int ID;
	int factionID;//0: player 1, 1 player 2
	Node currentcell;//keep this in mind when changing the Actors position.
	
	
	//image access info  
	int pixellength=27;//
	int pixelheight=38;//
	int I_index_x,I_index_y;//sprite selectors for the image file
	public static String imagefile="chess_pieces.png";
	public static BufferedImage image;
	public static boolean imageisset=false;
	
	//frame draw info
	int xoffset=0,yoffset=0;
	int xsize=140;
	int ysize=(int)(140*3.3);
	boolean highlighted;
	
	Actor(){
		if(!imageisset)
		{
			try {
				imageisset=true;
				image=ImageIO.read(new File(System.getProperty("user.dir")+"\\bin\\src\\chess\\" + imagefile));
			}catch(IOException e)
			{
				imageisset=false;
				System.out.println("can't load "+ System.getProperty("user.dir")+"\\bin\\src\\chess\\"+imagefile);}
		}
			I_index_x=I_index_y=0;
			highlighted=false;
	}
	//create actor and associate to the cell
	Actor(Node cell,int fac)
	{
		this();
		setCell(cell);
		setatoffset(cell.Virtualoffsetx,cell.Virtualoffsety);
		factionID=fac;
		cell.actor=this;

	}
	public void kill()
	{
		setCell(null);
		currentcell.actor=null;
	
	}
	//set offset to the lower left corner of sprite
	public void setatoffset(int ox, int oy) {
		this.xoffset=ox;
		this.yoffset=oy-ysize;
	}
	
	public void updatebycell()
	{
		setatoffset(currentcell.Virtualoffsetx,currentcell.Virtualoffsety);
	}
	
	public void setCell(Node cell) {
		currentcell=cell;
	}
	
	public void setoffset(int ox,int oy) {
		this.xoffset=ox;
		this.yoffset=oy;
	}
	
	
	public void Drawframe(Graphics2D g2d) {
		//Graphics2D g2d = (Graphics2D)g;
		
        g2d.drawImage(image,
			       xoffset,yoffset,xoffset+xsize, yoffset+ysize,//
			       pixellength*I_index_x, pixelheight*I_index_y, pixellength*I_index_x+pixellength, pixelheight*I_index_y+pixelheight,
			       null);
		//setBounds(xoffset,yoffset-ysize,xoffset+xsize,yoffset);
	
	}
	
	
public void actionPerformed(ActionEvent e) {
		
		
		
		
	}
	
	public void setsize(int xs,int ys) {
		this.xsize=xs;
		this.ysize=ys;
	}
	
	
		


}
