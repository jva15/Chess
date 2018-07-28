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
{
	
	public Actor Actors[];
	static GameFrame gameframe;
	// execute application
	public static void main( String args[] )
	{
		   gameframe = new GameFrame();
	} // end main
	
	
}

 class publicdata {
	static int height=300*6;
	static int width=300*6;

}