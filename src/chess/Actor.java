package src.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Actor{
	int ID;
	int factionID;//may need this for i dentification of player ownership but as well as restricting movement of pawns
	Node Currentcell;//keep this in mind when changing the Actors position.
	
	
	
	int xoffset=0,yoffset=0;
	int xroffset=0,yroffset=0;
	
	int xsize,ysize;
	
	int velocityX,velocityY;//,velocityZ;
	
	
	//image reading data
	int Foffset1x=0,Foffset1y=0;//top,left corner of a frame
	int Foffset2x=0,Foffset2y=0;//bottom, right corner of a frame
	boolean staticobject=true;
	
	public static BufferedImage image;
	boolean highlighted;
	
	
	
	
	
	//private Timer timer;
	//int anim_interval=2000;
	//int current_animation=0;
	//int current_frame=0;
	//int[] animationinterval;//stores time it takes for transition
	//int[] anim_length;//stores lengths of animations.
	
	
	
	
	
	
	public void setsize(int xs,int ys) {
		this.xsize=xs;
		this.ysize=ys;
	}
	
	public void setoffset(int ox,int oy) {
		this.xoffset=ox;
		this.yoffset=oy;
	}
		


}
