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
	
	public Actor Actors[];
	static GameFrame gameframe;
	// execute application
	public static void main( String args[] )
	{
		   gameframe = new GameFrame();
		   //gameframe.CallVictor(1);
	
	
	} // end main
	
	
}

 class publicdata {
	static int height=1000;
	static int width=1000;
	public static int getBaseSize() {return 1800;}
}