/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartScreen extends JPanel{

	BufferedImage image;
	String imagefile="Titlescreen.jpg";
	public StartScreen() {
		super();

		//setBounds(0,0,getParent().getWidth(),getParent().getHeight());
		try {
			image= ImageIO.read(ClassLoader.getSystemResourceAsStream(imagefile));
		}catch(IOException e)
		{System.out.println(imagefile);}
		
		
		
		
		
		
		repaint();
		
		
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		setBounds(0,0,getParent().getWidth(),getParent().getHeight());
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.drawImage(image,0,0,getParent().getWidth(),getParent().getHeight(),null);

		
	}
}
