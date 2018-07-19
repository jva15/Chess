package src.chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Timer;

public class Chara extends Actor {

	
	public Chara() {
		// TODO Auto-generated constructor stub
		 super();
		 pixellength=32;//
		 pixelheight=32;//
		 imagefileindex=1;//imagefileindex
		 I_index_x=0;
		 I_index_y=1;//sprite selectors
		 //int[] iar={1,1,0,1,1,1,1,1,2,1,1,3};
		 testanimation=new Animation( 500,new int[]{1,1,0,1,1,1,1,1,2,1,1,3});
		 setOpaque(false);	
	}
	
	
	Animation testanimation;
	public Chara(Node cell, int fac) {
		super(cell, fac);
		pixellength=32;//
		 pixelheight=32;//
		 imagefileindex=1;//imagefileindex
		 
		 I_index_x=0;
		 I_index_y=1;//sprite selectors
		 //int[] iar={1,1,0,1,1,1,1,1,2,1,1,3};
		 testanimation=new Animation( 500,new int[]{1,1,0,1,1,1,1,1,2,1,1,3});
		 setOpaque(false);
		 // TODO Auto-generated constructor stub
	}
	 	
	
	//a character has a-
	//
	//int[] baseframe;//by 3: the frame,the collum, and the row.
	boolean hasidle;boolean isidling;
	public void highlight(boolean True) {
		
	}
	//an animation has
	
	

}
