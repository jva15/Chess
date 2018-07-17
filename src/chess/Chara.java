package src.chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Chara extends Actor {

	public Chara() {
		// TODO Auto-generated constructor stub
	
		 pixellength=27;//
		 pixelheight=38;//
		 imagefileindex=1;//imagefileindex
		 I_index_x=0;
		 I_index_y=1;//sprite selectors
		 //int[] iar={1,1,0,1,1,1,1,1,2,1,1,3};
		 testanimation=new Animation( 500,new int[]{1,1,0,1,1,1,1,1,2,1,1,3});
	}
	
	
	
	Animation testanimation;
	public Chara(Node cell, int fac) {
		super(cell, fac);
		// TODO Auto-generated constructor stub
	}
	 	
	
	//a character has a-
	//
	//int[] baseframe;//by 3: the frame,the collum, and the row.
	boolean hasidle;boolean isidling;
	
	//an animation has
	public class Animation
	{
		Timer frametimer;
		boolean playing;
		int animindex=0;
		int framedelay;
		int[] framelist;
		int length; //to see if finished/for looping
		boolean loop;
		Animation(int delay, int[] frames)
		{
			playing=false;
			framedelay=delay;
			framelist=new int[frames.length];
			for(int i=0;i< frames.length;i++)framelist[i]=frames[i];
			
			length=(int)(frames.length/3);
			frametimer= new Timer(framedelay,new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					imagefileindex= framelist[animindex*3] ;
					I_index_y= framelist[animindex*3+1];
					I_index_x= framelist[animindex*3+2];
					animindex++;
					if(animindex==length)
					{
						animindex=0;
						if(!loop) {stop();}
					}	
				}
			} ) ;
		}
		public void start(){
			playing=true;
			frametimer.start();
		}
		public void stop(){
			playing=false;
			frametimer.stop();
		}
		public void reset(){
			frametimer.restart();
			animindex=0;
		}
		void setdelay(int delay){
			framedelay=delay;
			frametimer.setDelay(delay);
		}
		void setloop(boolean lp){
			loop=lp;
		}
	
		//override()
		
		
		
	}
	/*    
	WALK: frames 1,2,3,4, cycle
    JUMP: frame 5 for "jump preparation", frame 6 for moving upwards, frame 7 for moving downards and frame 8 for landing
    HIT: frames 9,10,9
    SLASH: frames 11,12,13 (you might use them in the order 12,11,12,13 if you want an extra "preparation" frame before the actual slash)
    PUNCH: 14,12 (again, you might use them in the order 12,14,12)
*/
	

}
