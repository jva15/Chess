import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class animation implements ActionListener
{
	//
	Timer frametimer;//
	boolean playing;
	int animindex=0;
	int framedelay;
	int[] framelist;//data in here is formatted by the following
	/*
	 * {Spritesheet,row,collumn}
	 * */
	int length; //to see if finished/for looping
	boolean loop;
	static final int SEC=1000;
	
	
	public animation() {
		//super(SEC,null);
	}
	
	
	
	public animation(int delay, int[] frames)
	{
		frametimer= new Timer(delay, this);
		playing=false;
		framedelay=delay;
		framelist=new int[frames.length];
		for(int i=0;i< frames.length;i++)framelist[i]=frames[i];
		
		length=(int)(frames.length/3);
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
	
	@Override
	public void start(){
		super.start();
		playing=true;
	}
	@Override
	public void stop(){
		super.stop();
		playing=false;
	}
	@Override
	public void restart(){
		super.restart();
		animindex=0;
	}
	@Override
	public void setDelay(int delay){
		super.setDelay(delay);
		framedelay=delay;
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