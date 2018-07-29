package src.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Sprite extends JPanel{
	
	
	public static int TotalImages;
	public final static String[] imagefilelist= {"chess_pieces.png","Titlescreen.jpg","Winner.png"};
	public static BufferedImage[] imageset;
	public static boolean imageisset=false;
	
	
	//image access info
	int pixellength=27;//default
	int pixelheight=39;//
	int imagefileindex=0;//imagefileindex
	int I_index_x,I_index_y;//sprite selectors
	int xpicoffset=0,ypicoffset=0;
	public String imagefile;
	
	//drawing info
	int xoffset=0,yoffset=0;
	int xsize=70;
	int ysize=(int)(70*2);
	boolean Active;//
	
	
	public Sprite()
	{
		super();
		setOpaque(false);
		//resizing
		ysize= (int)((((double)ysize)/(300*6))*publicdata.height);
		xsize= (int)((((double)xsize)/(300*6))*publicdata.width);

		initialize_images();
		I_index_x=I_index_y=0;
		
		
		setVisible(true);
		
	}
	
	public void initialize_images() 
	{
		if(!imageisset)
		{
			TotalImages=imagefilelist.length;
			imageset= new BufferedImage[TotalImages];
			for(int i=0;i<TotalImages;i++)
			{
				
				imagefile=imagefilelist[i];
				try {
					
					imageisset=true;
					imageset[i]=ImageIO.read(ClassLoader.getSystemResourceAsStream(imagefile));
					
				}catch(IOException e)
				{
					imageisset=false;
					System.out.println("can't load "+ System.getProperty("user.dir")+"\\src\\chess\\"+imagefile);}
			}
		}
	}
	
	
	
	
	public void paintComponent(Graphics g){
		if(Active){
		super.paintComponent( g );
		Graphics2D g2d = (Graphics2D)g;
		setBounds(xoffset,yoffset,xsize,ysize);
		g2d.drawImage(imageset[imagefileindex],
			       0,0,xsize,ysize,//
				   pixellength*I_index_x+xpicoffset, pixelheight*I_index_y+ypicoffset, pixellength*I_index_x+pixellength, pixelheight*I_index_y+pixelheight,
			       null);
		
		}
		
		
		
	}
	public void setoffset(int ox,int oy) {
		
		this.xoffset=ox;
		this.yoffset=oy;
	
	}
	public int GetYoff() {return yoffset;}
	public int GetXoff() {return xoffset;}
	
	//set offset to the lower left corner of sprite
	public void setatoffset(int ox, int oy) {
		this.xoffset=ox;
		this.yoffset=oy-(int)(ysize);
		
	}

}
