package src.chess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//DOTO: allow it to use multiple delays


public class spriteFrames {

	//image reading data
	int Totalframes=0;
	int[] pixelheight;//top,left corner of a frame
	int[] pixellength;//bottom, right corner of a frame
	public BufferedImage[] images;
	int defaultxsize;
	int defaultysize;
	
	
	int currentsprites,cx,cy;
		
	//int x,y,xsize,ysize;//location on screen
	
	public spriteFrames(String imagefile,int pixheight,int pixlength) {
		images= new BufferedImage[1];
		pixelheight= new int[1];//top,left corner of a frame
		pixellength= new int[1];
		try {
			images[0]=ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\graphicstest\\" + imagefile));
		}catch(IOException e)
		{System.out.println("can't load "+ System.getProperty("user.dir")+imagefile);}
			
		pixelheight[0]=pixheight;
		pixellength[0]=pixlength;
		currentsprites=cx=cy=0;
	}
	public void Setframe(int sprites,int col,int row){
		currentsprites=sprites;
		cx=col;
		cy=row;
	}
	public void Setframe(int col,int row){
		Setframe(currentsprites,col,row);
	}
	public void Drawframe(Graphics2D g2d,int x,int y) {
	
		Drawframe(g2d,x,y,defaultxsize,defaultysize);
	}
	public void Drawframe(Graphics2D g2d,int x,int y,int xsize, int ysize) {
		//Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(images[currentsprites],
			       x,y,xsize, ysize,//
			       pixellength[0]*cx, pixelheight[0]*cy, pixellength[0]*cx+pixellength[0], pixelheight[0]*cy+pixelheight[0],
			       null);
	}
}
