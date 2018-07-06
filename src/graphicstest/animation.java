package graphicstest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


/*to use:-
 * 1:create a frameclass
 * 2:use it to create animation object
 * 3:Set by passing in an array where for every 3 cells:
 *  cell1 is the frame (according to  the spritframes class you created) represented as a number
 *  cell2 is the row within the sprite sheet
 *  cell3 is the collumn within the sprite sheet
 *  pass this along with  an array number denoting the delay
 *    the delay que is used in case you have multiple animations with different delays
 *    iterations are activated whenever a frame number is negative
 * */
public class animation extends JPanel implements ActionListener {

	//actual 
	public ArrayList<Integer> animstream;
	public spriteFrames frames;
	
	/*TODO: use negatives to signify change*/
	public ArrayList<Integer> delay;//delay que
	private Timer timer;
	public int cdelay=0;
	
	public int iter=0;
	public int current_x;
	public int current_y;
	public int current_f;
	
	//metadata
	public int totalframes;
	
	
	//*important*
	//--
	public animation()
	{//TODO: set a default animation
		timer = new Timer(delay.get(cdelay), this);
		
	}
	public animation(spriteFrames theframes)
	{
		timer = new Timer(delay.get(cdelay), this);
		frames=theframes;
	}
	//for example anims:= {0,0,1,0,2,0}, 
	public void Set(int[] anims,int[] timers)
	{
		
		animstream=new ArrayList<Integer>();
		for(int i=0;i<(anims.length);i+=3)
		{
			animstream.add(anims[i]-1);//spritesheet
			animstream.add(anims[i+1]-1);//animation row
			animstream.add(anims[i+2]-1);//animation collumn
		}
		for(int t:timers)
		{
			delay.add(t);
		}
		cdelay=timers[0];
	}
	public animation add(int[] anims,int[] timers)
	{
		animation newanim=new animation(frames);
		
		newanim.animstream=new ArrayList<Integer>();
		
		for(int i=0;i<(anims.length);i+=3)
		{
			newanim.animstream.add(anims[i]-1);//spritesheet
			newanim.animstream.add(anims[i+1]-1);//animation row
			newanim.animstream.add(anims[i+2]-1);//animation collumn
		}
		for(int t:timers)
		{
			newanim.delay.add(t);
		}
		newanim.cdelay=this.cdelay;
		return newanim;
	}
	//--
	
	public void addFrames(spriteFrames frames)
	{}
	public animation add(animation anim)
	{
		animation newanim=new animation(this.frames);
		newanim.addFrames(anim.frames);

		newanim.animstream=new ArrayList<Integer>();
		for(int i=0;i<(this.animstream.size());i+=3)
		{
			newanim.animstream.add(this.animstream.get(i)-1);//spritesheet
			newanim.animstream.add(this.animstream.get(i+1)-1);//animation row
			newanim.animstream.add(this.animstream.get(i+2)-1);//animation collumn
		}
		for(int i=0;i<(anim.animstream.size());i+=3)
		{
			newanim.animstream.add(anim.animstream.get(i)-1);//spritesheet
			newanim.animstream.add(anim.animstream.get(i+1)-1);//animation row
			newanim.animstream.add(anim.animstream.get(i+2)-1);//animation collumn
		}
		
		for(int t:this.delay)
		{
			newanim.delay.add(t);
		}
		for(int t:anim.delay)
		{
			newanim.delay.add(t);
		}
		
		newanim.cdelay=this.cdelay;
		return newanim;
	}
	/*
	public animation insert(int index,animation anim)
	{}
	//public animation insert(int[] anims,int[] timers)
	//{}
	*/
	public void animate()//plays stored animation
	{
		timer.start();
		
	}
	public void actionPerformed(ActionEvent e)
	{
		current_f=this.animstream.get(3*iter);
		current_y=this.animstream.get(3*iter+1);
		current_x=this.animstream.get(3*iter+2);
		
		//repaint();
		if(3*iter==this.animstream.size())		
		{
			timer.stop();
			iter=-1;
		}
		iter+=1;
	}
	
	/*
	public void animatein(int seconds)//plays stored animation
	{
		
		for(int i=0;i<this.animstream ;i+=3)
		{
			if(this.animstream.get(i)<0)
			{
				(seconds*60000)
				
				
				
			}
		}
		
	}*/
	
	
}
