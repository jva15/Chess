/*all code by Joseph Auguste and Matthew Klopfenstein*/

package src.chess;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//Bouncing Ball example
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

import org.omg.IOP.ExceptionDetailMessage;

import javax.swing.*;

public class Game
{ //
	protected Actor Actors[];
	static GameFrame gameframe;
	// execute application
	public static void main( String args[] )
	{
		   gameframe = new GameFrame();
	} // end main
}

 class publicdata {
	public static int height=1000;
	public static int width=1000;
	
	public static int getW() {return width;}
	public static int getH() {return height;}
	public static int getBaseSize() {return 1800;}
}