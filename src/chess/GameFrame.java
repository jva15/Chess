package src.chess;


import java.util.Random;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.chess.GameFrame.GridPanel;

class GameFrame extends JFrame
{
	Random random;
	GridPanel GP;
	StartScreen Startscreen;
	JPanel Mainmenu;
	int initialxclick;
	//plarestuff
	boolean kingsintrouble[] = new boolean[2];
	boolean thekingisdead[]=new boolean[2];
	boolean WarMode=false;
	int placementoffset=0;
	King kings[]=new King[2];
	static int gaurdchannels=0;
	String players[]=new String[2];
	public boolean inputenabled=false;
	int currentturn =0;
	JMenuBar menubar;
	JMenu firstmenu;
	JMenuItem newgame;
	JMenuItem Warmode;
	JLabel turnlabel;
	Timer timer=new Timer(10,new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			repaint();
		}
	});
	private JMenu secondmenu;
	private JMenuItem tostart;
	private JMenuItem todesktop;
	
	
	JPanel tempanel;
	GameFrame()
	{
		super("Chess");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		random=new Random();
		players[0]="Player 1's turn";
		players[1]="Player 2's turn";
		turnlabel=new JLabel(players[0]);
		turnlabel.setVerticalTextPosition(JLabel.BOTTOM);
		turnlabel.setHorizontalAlignment(JLabel.LEFT);
		turnlabel.setVisible(true);
		turnlabel.setSize(300, 300);
		
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		for(int i=0;i<2;i++)
		{
			kingsintrouble[i]=thekingisdead[i]=false;	
		}
		
		menubar=new JMenuBar();
		firstmenu=new JMenu("New");
		newgame=new JMenuItem("New Game");
		Warmode=new JMenuItem("War Mode");
		
		secondmenu=new JMenu("Quit");
		todesktop=new JMenuItem("Quit to Desktop");
		tostart=new JMenuItem("Quit to StartScreen");
		
		
		
		setJMenuBar(menubar);
		
		menubar.add(firstmenu);
		menubar.add(secondmenu);
		
		secondmenu.add(todesktop);
		secondmenu.add(tostart);
		
		firstmenu.add(newgame);
		firstmenu.add(Warmode);
		
		
		
		newgame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				getContentPane().removeAll();
				LoadGrid(false);
			}
		});
		
		Warmode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				getContentPane().removeAll();
				LoadGrid(true);
			}
		});
		
		tostart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getContentPane().removeAll();
				getContentPane().add(new StartScreen());
				repaint();
				setVisible(true);
			}
		});
		
		todesktop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getContentPane().removeAll();
				System.exit(0);
			}
		});
		
		setSize(publicdata.width,publicdata.height);
		
		LoadStartScreen();
		setVisible(true);
		timer.start();
	}
	
	

	public void LoadStartScreen(){
		Startscreen=new StartScreen();
		add(Startscreen);
		repaint();
	}
	
		
	public void LoadGrid(boolean THIS_IS_WAAAAR)
	{
		GP=new GridPanel(THIS_IS_WAAAAR);
		add(GP);
		repaint();
	}

	protected void callCheck(boolean theplayersincheck)
	{
		if(theplayersincheck)turnlabel.setText(players[currentturn]+"\nCheck!!");
		else turnlabel.setText(players[currentturn]+"'s turn");	
	}
	
	protected void callCheckMate(int playernum)
	{
		add(new VictorScreen(playernum^1));
		repaint();
	}
	
	public void pawnPromotion(Actor propawn)
	{
		PawnProDialog ppd = new PawnProDialog(this, propawn);
	}
	
	public void displayMoveError()
	{
		JOptionPane.showMessageDialog(this, "Move is not valid: King is put in check");
	}


	class GridPanel extends JLayeredPane implements ActionListener
	{
		
		//public Actor[] Actors;//list of actors
		public LinkedList<Actor> Actors;
		private int delay = 10;//delay for the FPS timer
		protected Timer timer; //FPS timer
		private int x =0;//base offset	don't touch
		private int y = 0;//base offset don't touch
		public int transx=300;
		public int transy=400;
		
		public double gridratio=1; // not ready yet
		private int cellsize = (int)(150*gridratio);	// cell size on screen
		private int cellspace= 0;//15; // space between the cells
		private double rad = 315; // current view angle
		private double radd = 0.0; // change in view angle
		private int gsize=8;//number of cells(this is then squared)
	
		int[] centerpoint=new int[2];//same as above just in point form
		int[] tp =new int[2];//just a temporary pointer
		int focalpointx;//used to store the center point of actors to be rotated
		int focalpointy;
	
		//Movement variables
		Node[] kingPos = new Node[2];   //keeps track of current king position at every time 
		Actor selected_piece = null;    
		Node selected_cell = null; 
		Actor lastpiece=null;
		Node enPassantCell = null;      //stores location of cell where a potential en passant can occur
		int kingAtkRisk;                //stores the current attack risk of the king's cell
		boolean kingIsInCheck = false;
		
		private Node cellgrid[][];  //cell grid
		private int dx = 0;		// reserved for player gridmovement
		private int dy = 0;		// increment amount (y coord)
		
		boolean selfrotatemode;
		int targetradius;
		
		
		int Highlightvalue=255;
		int dHighlightvalue=1;
		Point[] graphingpoints = new Point[4];
		
		
		
		public GridPanel(boolean crazymode)
		{
			this();
			
			if(crazymode)WarMode=crazymode;
				
				//TODO: add additional actors
			init();	
		}
		
		int transx1,transy1;
		public void init() {
			if(WarMode)
			{
				
				placementoffset=gsize;
				gsize=8*2;
			}
			else
			{
				gsize=8;
				placementoffset=0;
				
			}
			
			selected_piece = null;    
			selected_cell = null; 
			lastpiece=null;
			enPassantCell = null;      //stores location of cell where a potential en passant can occur
			currentturn=0;
			
			selfrotatemode=false;
			
			transy1=(int)((double)transy/(publicdata.getBaseSize())*publicdata.height);
			transx1=(int)((double)transx/(publicdata.getBaseSize())*publicdata.width);
			transx=transx1;
			transy=transy1;
			
			cellsize=(int)((double)cellsize/(publicdata.getBaseSize())*publicdata.width);
			
			//restag
			centerpoint[0]=x+((cellsize+cellspace)*gsize)/2-cellspace;//
			centerpoint[1]=y+((cellsize+cellspace)*gsize)/2-cellspace;//
			focalpointx=x+(int)(((centerpoint[0]-x)*2-(cellsize))/2);//used to store the center point of actors to be rotated
			focalpointy=y+(int)(((centerpoint[1]-y)*2-(cellsize))/2);//
			
			
			setSize(publicdata.width, publicdata.height);
			
			timer = new Timer(delay, this);//refresh timer
			cellgrid=Init_NodeAr(cellgrid,gsize);   
			timer.setCoalesce(true);
			timer.start();		// start the timer
			Actors=new LinkedList<Actor>();
			Populate_board();	 
			update();
			setLayer(turnlabel,300);
			add(turnlabel);
			inputenabled=true;
		}
		
		
		
		public GridPanel() 
		{
			
			
			
			
			addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
					if((e.getModifiers()&InputEvent.BUTTON3_MASK)==InputEvent.BUTTON3_MASK)
					{
						double rd=((double)(e.getXOnScreen()-initialxclick))/500;
						System.out.println(e.getXOnScreen());
						System.out.println(initialxclick);
						radd=rd;
								//Math.abs(rd) < 1 ? rd : (rd/Math.abs(rd))*1;
						
						
						
					}
				}
			});
			
			//this is where selection of items occures
			addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	// TODO Auto-generated method stub
			    	super.mouseReleased(e);
			    	radd=0;
			    }
				
			    @Override
			    public void mousePressed(MouseEvent e) {
			    	// TODO Auto-generated method stub
			    	super.mousePressed(e);
			    	initialxclick=e.getXOnScreen();
			    }
			    
			    
				@Override
			    public void mouseClicked(MouseEvent e) {
			    	//get the mouse coords
					
					if((e.getModifiers()&InputEvent.BUTTON3_MASK)==InputEvent.BUTTON3_MASK)
			    	{
						
			    	}else
			    	{
			    		Actor Piece;
				    	Node cell = null;
				    	int[] mousepointer=new int[2];
				    	
				    	if(inputenabled)
				    	{
					     	inputenabled=false;
					    	mousepointer[0]=e.getX();
					    	mousepointer[1]=(e.getY());//the calculation is grid based, so i morph the y so it simulates a reverted version of the grid(before the y shrink)
					    	System.out.println(e.getX()+"  "+e.getY());
					    	System.out.println(getComponentAt(mousepointer[0],(int)(mousepointer[1])).getClass().getName());
					    	
					    	if(getComponentAt(mousepointer[0],(mousepointer[1])).getClass().getName().equals("src.chess.GameFrame$GridPanel"))
					    	{//you selected the grid panel
					    		mousepointer[1]=(int)((mousepointer[1]-transy)/0.3);
					    		mousepointer[0]-= transx;
						    	tp=rotoffc(centerpoint,mousepointer,Math.toRadians(rad));//rotate the mouse coords around the center point so that the grid is calculated as if straight.
						    	tp[0]=(int)((tp[0])/cellsize);//
						    	tp[1]=(int)((tp[1])/cellsize);//
						    	if(tp[0]>=0&&tp[0]<gsize&&tp[1]>=0&&tp[1]<gsize)
						    		cell=cellgrid[tp[0]][tp[1]];
					    	}
					    	else if(getComponentAt(mousepointer[0],mousepointer[1]).getClass().getName().equals("javax.swing.JLabel")) {}
					    	
					    	else { //actor selected
					    		
					    		Piece=(Actor)getComponentAt(mousepointer[0],mousepointer[1]);
				    			
					    		cell = Piece.currentcell;	
					    		
					    	}
					    	
					    	if(cell != null)  //if a cell/actor was clicked
					    	{
					    		if(cell.occupied && cell.actor.factionID == currentturn)  //if player selected their own piece
					    		{
					    			if(selected_piece!=null&&selected_piece.factionID == currentturn )
					    				unselectpiece();
					    			if(selected_piece!=cell.actor)
					    				select(cell.actor);    
					    		}                                     //will run highlight function for actor on cell
					    		else if(cell.highlighted)                                 //else if player chose cell to move to 
					    		{	
					    			selected_piece = selected_cell.actor; 
					    			if(cell.occupied && cell.actor.ID == 4 && selected_piece.ID == 1 && selected_piece.factionID == cell.actor.factionID)
					    				castling(cell);
					    			else
					    			{
					    				if(testMove(selected_piece, cell))
					    				{
					    					displayMoveError();
					    					return;
					    				}
					    				selected_piece.moveTo(cell);           //move piece to cell
					    				lastpiece=selected_piece;
					    				//if(piece is king or rook, we need to keep track of whether it is first move or not)
					    				//for castling purposes
					    				if(selected_piece.ID == 4)
					    					selected_piece.firstMove = false;
					    				else if(selected_piece.ID== 1)
					    				{
					    					selected_piece.firstMove = false;
					    					kingPos[currentturn] = selected_piece.currentcell;
					    				}
					    			}
					    			
					    			pawnCheck();                               //performs functions related only to the pawn
					    			endturn();
					    			CheckKing();
					    			
					    			
					    			
					    		}
					    	}
					    	inputenabled=true;
				    	}
				    	}	
				    }
				    
				});
			   init();
		   
		}
	
	/*-------------Selection-functions------------------*/
		public boolean testMove(Actor piece, Node c)
		{
			Node presentcell = piece.currentcell;
			Node tempcell = new Node();
			boolean killedanActor=false;
			
			
			int oldAtkRisk = kingPos[currentturn].getAttackRisk(currentturn);
			int newAtkRisk = 0;
			for(Actor a : Actors)
				a.setRange(false);
			
			//phantom movement -- may need to be updated to ensure pieces do not actually move on the board
			if(c.actor != null)
			{
				c.actor.moveTo(tempcell);
				killedanActor=true;
			}
			piece.moveTo(c);
			
			
			//calculate what attack risk would be after move
			for(Actor a : Actors)
				a.setRange(true);
			newAtkRisk = kingPos[currentturn].getAttackRisk(currentturn);
			
			//set everything back to normal: 
			for(Actor a : Actors)
				a.setRange(false);      
			
			piece.moveTo(presentcell);
			
			
			if(killedanActor)	tempcell.actor.moveTo(c);
			
			for(Actor a : Actors)
				a.setRange(true);
			//if this move put the king in danger, it is invalid and cannot be taken
			return (newAtkRisk > oldAtkRisk);
		}
		
		public void castling(Node c)
		{
			c.actor.firstMove = false;
			selected_piece.firstMove = false;
			
			int left, right;
			if(currentturn == 0)
			{
				left = 1;
				right = 3;
			}
			else
			{
				left = 3;
				right = 1;
			}
			
			//kingside castling
			if(selected_cell.adgNodes[right].adgNodes[right].adgNodes[right] == c)
			{
				selected_piece.moveTo(selected_cell.adgNodes[right].adgNodes[right]);
				c.actor.moveTo(c.adgNodes[left].adgNodes[left]);
			}
			else  //queenside castling
			{
				selected_piece.moveTo(selected_cell.adgNodes[left].adgNodes[left]);
				c.actor.moveTo(c.adgNodes[right].adgNodes[right].adgNodes[right]);
			}
			kingPos[currentturn] = selected_piece.currentcell;   //update position of king
		}
		
		//if pawn was moved, firstMove is set to false & checks for enPassant conditions
		//important to note this is called AFTER selected_piece is moved to new cell
		public void pawnCheck()
		{
			if(selected_piece.ID == 0)                  //if piece is pawn
			{ 
				int dir, rdir;                          //direction needed for checks(rdir = reverse direction)
				if(currentturn == 0)
				{	
					dir = 0;
					rdir = 2;
				}
				else
				{	
					dir = 2;
					rdir = 0; 
				}
				
				//if(pawn has reached end of board, it must be promoted)
				if(selected_piece.currentcell.adgNodes[dir] == null)
					pawnPromotion(selected_piece);
				//if pawn has captured the opponent's pawn through en passant
				else if(enPassantCell != null && selected_piece.currentcell.adgNodes[rdir].occupied && selected_piece.currentcell.adgNodes[rdir] == enPassantCell)
				{ 
					selected_piece.enPassantLeft = selected_piece.enPassantRight = false;
					selected_piece.currentcell.adgNodes[rdir].actor.kill(); 
			    }
				if(selected_piece.firstMove)            //if this is pawn's first move
				{
					selected_piece.firstMove = false; 
					//if(piece was moved two spaces forward) 
					if(selected_cell.adgNodes[dir].adgNodes[dir] == selected_piece.currentcell)
					{
						if(selected_piece.currentcell.adgNodes[1] != null && 
						   selected_piece.currentcell.adgNodes[1].occupied && 
						   selected_piece.currentcell.adgNodes[1].actor.factionID != currentturn &&
						   selected_piece.currentcell.adgNodes[1].actor.ID == 0)
						{
							if(currentturn == 0)
								selected_piece.currentcell.adgNodes[1].actor.enPassantRight = true;
							else 
								selected_piece.currentcell.adgNodes[1].actor.enPassantLeft = true; 
						}
						if(selected_piece.currentcell.adgNodes[3] != null && 
						   selected_piece.currentcell.adgNodes[3].occupied && 
						   selected_piece.currentcell.adgNodes[3].actor.factionID != currentturn &&
						   selected_piece.currentcell.adgNodes[3].actor.ID == 0)
						{
							if(currentturn == 0)
								selected_piece.currentcell.adgNodes[3].actor.enPassantLeft = true;
							else 
								selected_piece.currentcell.adgNodes[3].actor.enPassantRight = true; 
						}
						enPassantCell = selected_piece.currentcell;
						return; 
					}
				}
				enPassantCell = null; 
			}
		}
		
		public void movehighlight(Actor Piece,boolean highlighted)
		{
			Piece.highlight(highlighted);
		}
		//selection functions
		protected void unselectpiece() {
			if(selected_piece!=null)movehighlight(selected_piece,false);
			selected_piece=null;
			selected_cell=null;
		}
		protected void select(Actor piece) {
			if(selected_piece!=null)
				unselectpiece();
			movehighlight(piece,true);
			selected_piece=piece;
			selected_cell=piece.currentcell;
		}
		protected void select(Node piece) {
			if(selected_cell != null)         //if player had previously selected an item 
				unselectpiece();              //de-highlight the previous selection 
			movehighlight(piece.actor,true);
			selected_cell = piece; 
			selected_piece = piece.actor;
		}
		//----------------end game functions
		
		//checks to see if the current king is in check or checkmate and react accordingly 
		public void CheckKing()
		{
			if(kingisincheck())
			{
				callCheck(true);
				if(checkmate())
				{
					callCheckMate(currentturn);
				}
			}else {
				callCheck(false);
			}
		}
		
		//checks the board to see if the king is in check and update the trouble status
		protected boolean kingisincheck()
		{
			if(kings[currentturn]!=null&&kings[currentturn].currentcell!=null) {
				
				if(kings[currentturn].currentcell.getAttackRisk(currentturn)>0)
					kingsintrouble[currentturn]=true;
				else kingsintrouble[currentturn]=false;
			}
			
			return kingsintrouble[currentturn];
			
		}
		
		protected boolean checkmate() {
			if(kingsintrouble[currentturn])
			{
				if(kingcantmove()&&kingcantblock())
				{
					return true;
				}
			}
			return false;
		}
		
		protected boolean kingcantmove()
		{
			
			return kings[currentturn].isTrapped();
		}
		protected boolean kingcantblock()
		{
			if(kings[currentturn].currentcell.getAttackRisk(currentturn)>1 )
			{//if the king has more than 1 piece targeting him, its over.
				System.out.println("victor by ultimate gank");
				return true;
			}
			else { 
				System.out.println("victor by no counter");
				return kings[currentturn].cantcounter(lastpiece);
			}
		}
		
		
		
		protected void endturn() {
			for(int i=0;i<Actors.size();i++)
				if(Actors.get(i).currentcell==null)
				{
					Actors.remove(i);
				}
			update();
			
			
			currentturn=(currentturn+1)%2;
			turnlabel.setText(players[currentturn]);
			unselectpiece();
			
			kingAtkRisk = kingPos[currentturn].getAttackRisk(currentturn);
			if(kingPos[currentturn].getAttackRisk(currentturn) > 0)
				kingIsInCheck = true;
			
			rotatebyturn();
		}
		
		
		
		protected void update()
		{
			//clear the grid
			for(int i=0;i<cellgrid.length;i++)for(int j=0;j<cellgrid.length;j++)
			{cellgrid[i][j].attackrisk[0]=cellgrid[i][j].attackrisk[1]=0; }
			//update the ranges
			for(Actor A: Actors)
			{
				
				A.setRange(true);
			
			}
		}
		
		
		
	/*-------------Rendering------------------*/
	
		public void actionPerformed(ActionEvent e)
		// will run when the timer fires
		{
			timer.stop();
			repaint();
			timer.restart();
		}
		
		private void rotatebyturn()
	    {
	    
		    
		    if(currentturn==0) 	targetradius=315;
		    else targetradius=135;
		    inputenabled=false;
		    selfrotatemode=true;
		    radd=3;
	    }
		
		// draws the stuff
		public void paintComponent( Graphics g )
		{
			
		   super.paintComponent( g ); // call superclass's paintComponent 
		   Rotate_NodeArray(cellgrid);// update the virtual offsets
		   Graphics2D g2d = (Graphics2D)g;
		   publicdata.height=getParent().getHeight();
		   publicdata.width=getParent().getWidth();
		   
		   int tr=(int) (targetradius-radd);
		   
		   if(selfrotatemode&& ((tr <= rad)&&(rad<=targetradius)))
		   {
			   radd=0;
			   inputenabled=true;
			   selfrotatemode=false;
		   }
		   
		   
		    dx=dy=0;
		    
		    
		    
		    
		    
		    
		    
		    
		    //does the highlight glow effect
			if(Highlightvalue>=255||Highlightvalue<=0) dHighlightvalue*=-1;
			Highlightvalue+=dHighlightvalue;
			
			int rdx=1,rdy=1;
			int rstx=0,rsty=0;
			boolean ended=true;
			double temprad=(rad+180)%360;
	//TODO: FIX HERE
			if (temprad<90)					{rsty=gsize-1;rstx=gsize-1;
				if(temprad<=45) 					{rdx=1;rdy=-1;}else{rdx=-1;rdy=1;}}
			else if (temprad>=90&&temprad<180)	{rsty=gsize-1;rstx=0;
				if(temprad<=90+45+180) 				{rdx=1;rdy=1;}else{rdx=-1;rdy=-1;}}
			else if (temprad>=180&&temprad<270)	{rsty=0;rstx=0;
				if(temprad<=180+45) 				{rdx=-1;rdy=1;}else{rdx=1;rdy=-1;}}
			else						{rsty=0;rstx=gsize-1;
				if(temprad<=270+45)				{rdx=-1;rdy=-1;}else{rdx=1;rdy=1;}}
			
			int stx=rstx;
			int sty=rsty;
			int overcount=0;
			boolean overload=false;
			
			for(int count=0,i=rstx,j=rsty; count<(gsize*gsize);count++, i+=rdx,j+=rdy)
			{
				//radd=0.1;
				int nexti=i+rdx;
				int nextj=j+rdy;
				int nextrx=rstx+(rdx*-1);
				int nextry=rsty+(rdy*-1);
				//radd=0.1;
				//System.out.print("starts: "+rstx+" "+rsty+" || ");
				
				
				 if(!(i<gsize&&i>=0)) {
					if((nextrx<gsize&&nextrx>=0)) 
					{rstx=nextrx;
					i=rstx;
					j=sty;
					}
					else {
						overload=true;

					}
				}else if(!(j<gsize&&j>=0)) {
					if((nextry<gsize&&nextry>=0)) 
					{
					rsty=nextry;
					j=rsty;
					i=stx;
					}else
					{
						overload=true;
						
					}
				}
				else {overload=false;}
				if(overload)
				{
					if(temprad<=180) {
						if(rdx==1&&rdy==-1||rdx==-1&&rdy==-1)
						{
							i= rstx;
							j= sty+rdy*(++overcount);
						}
						else if(rdx==-1&&rdy==1||rdx==1&&rdy==1)
						{
							j= rsty;
							i=stx+rdx*(++overcount);	
						}
					}else
					{
						if(rdx==-1&&rdy==1||rdx==1&&rdy==1)
						{
							i= rstx;
							j= sty+rdy*(++overcount);
						}
						else if(rdx==1&&rdy==-1||rdx==-1&&rdy==-1)
						{
							j= rsty;
							i=stx+rdx*(++overcount);	
						}
						
					}
					
					
				}
					
				
				int tempcolor;
				 if(cellgrid[i][j].getAttackRisk(currentturn)>0) {
					 tempcolor=225;
					 
				 }
				 else tempcolor=0;
				
				//System.out.println(i+" "+j);
				
				//color stuff
				//checkered
				int RoB=(i+j)%2;
				if(cellgrid[i][j].highlighted==true) //cell is highlighted
				{
					if(RoB==1)g2d.setColor(new Color(255,Highlightvalue%255,0));//highlighting
					else g2d.setColor(new Color(Highlightvalue%255,Highlightvalue%255,0));
				}
				else //cell is not highlighted
				{
					if(RoB==1)			g2d.setColor(new Color(255,0,tempcolor));
					else 				g2d.setColor(new Color(0,0,tempcolor));	
					
					
					
				}
				
				Polygon poly= cellgrid[i][j].getPoly(transx,transy,centerpoint, Math.toRadians(rad), 0.3);

				
				
				
				g2d.draw(poly);
				g2d.fill(poly);
				
				
				
				
				
			}
			Actor ractor;
			for(int i=0;i<Actors.size();i++)
			{
				
				ractor=Actors.get(i);
				setLayer(ractor, ractor.yoffset);
				ractor.Drawframe(g2d);//draw the actor
				
			}
			
			rad +=radd;//TODO: Set radd to be user controlled once the final visualization is complete
			rad=rad%360;
			
		}
		
		
		
		
	
	/*-------------initialization------------------*/
	
		
		//creates the node array
		public Node[][] Init_NodeAr(Node[][] nodeArr,int NAsize)
		{
			nodeArr=new Node[NAsize][NAsize];
			return Build_NodeArray(nodeArr);
		}
		
		public Node[][] Build_NodeArray(Node[][] nodeArr) 
		{
			//creates node
			for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
			{
				int pxoffset=x+i*(cellsize+cellspace);
				int pyoffset=y+j*(cellsize+cellspace);
				nodeArr[i][j]=new Node(pxoffset,pyoffset,cellsize,true);
			}
		
				
			//links the nodes together
			for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
			{
				
				if(i!=0)nodeArr[i][j].adgNodes[3]=nodeArr[i-1][j];
				else nodeArr[i][j].adgNodes[3]=null;
				
				if(i!=nodeArr.length-1)nodeArr[i][j].adgNodes[1]=nodeArr[i+1][j];
				else nodeArr[i][j].adgNodes[1]=null;
				
				if(j!=0)nodeArr[i][j].adgNodes[2]=nodeArr[i][j-1];
				else nodeArr[i][j].adgNodes[2]=null;
				
				if(j!=nodeArr.length-1) nodeArr[i][j].adgNodes[0]=nodeArr[i][j+1];
				else nodeArr[i][j].adgNodes[0]=null;
			}
			//nodeArr[4][4].setpointheight(1, 10);
			return nodeArr;
			
		}
		
		
		
		
		private void pieceloader(String id, int fac,int x,int y)
		{
			if(x>=0&&x<gsize&&y>=0&&y<gsize)
			{
				switch(id)
				{
					
				case "pawn":
						addPiece(new Pawn(fac),x,y);
						break;
				case "king":
						kings[fac]=new King(fac);
						addPiece(kings[fac],x,y);
						kingPos[fac] = kings[fac].currentcell;
						break;
				case "queen":
					addPiece(new Queen(fac),x,y);
					break;
				case "bishop":
					addPiece(new Bishop(fac),x,y);
					break;
				case "knight":
					addPiece(new Knight(fac),x,y);
					break;
				case "rook":
					addPiece(new Rook(fac),x,y);
					break;
					
				default:break;
				
				}
					
			
			}
		}
		
		private void Populate_board() {
			//order placed affects 		
			for(int i =0;i<(8+placementoffset);i++) addPiece(new Pawn(0),i,1);//white pawns
			for(int i =0;i<(8+placementoffset);i++) addPiece(new Pawn(1),i,placementoffset+6);//black pawns
			
			kings[0]=new King(0);
			kings[1]=new King(1);
			addPiece(new Rook(0),0,0);
			addPiece(new Rook(0),7,0);
			addPiece(new Knight(0),1,0);//white Knight1
			addPiece(new Knight(0),6,0);//white Knight2
			addPiece(new Bishop(0),2,0);//white Bishop1
			addPiece(new Bishop(0),5,0);//white Bishop2
			addPiece(kings[0],4,0);//white King
			kingPos[0] = kings[0].currentcell;
			addPiece(new Queen(0),3,0);//white Queen
			
			
			//addPiece(new Rook(1),5,4);
			
		
			addPiece(new Rook(1),0,placementoffset+7);//black rook1
			addPiece(new Rook(1),7,placementoffset+7);//black rook2
			addPiece(new Knight(1),1,placementoffset+7);;//black Knight1
			addPiece(new Knight(1),6,placementoffset+7);;//black Knight2
			addPiece(new Bishop(1),2,placementoffset+7);//black Bishop1
			addPiece(new Bishop(1),5,placementoffset+7);//black Bishop2
			addPiece(kings[1],4,placementoffset+7);//black King
			kingPos[1] = kings[1].currentcell;
			addPiece(new Queen(1),3,placementoffset+7);

			if(WarMode)
			{
				addPiece(new Rook(0),placementoffset+0,0);
				addPiece(new Rook(0),placementoffset+7,0);
				addPiece(new Knight(0),placementoffset+1,0);//white Knight1
				addPiece(new Knight(0),placementoffset+6,0);//white Knight2
				addPiece(new Bishop(0),placementoffset+2,0);//white Bishop1
				addPiece(new Bishop(0),placementoffset+5,0);//white Bishop2
				addPiece(new Queen(0),placementoffset+4,0);//white Queen
				
				addPiece(new Rook(1),placementoffset+0,placementoffset+7);//black rook1
				addPiece(new Rook(1),placementoffset+7,placementoffset+7);//black rook2
				addPiece(new Knight(1),placementoffset+1,placementoffset+7);;//black Knight1
				addPiece(new Knight(1),placementoffset+6,placementoffset+7);;//black Knight2
				addPiece(new Bishop(1),placementoffset+2,placementoffset+7);//black Bishop1
				addPiece(new Bishop(1),placementoffset+5,placementoffset+7);//black Bishop2
				addPiece(new Queen(1),placementoffset+3,placementoffset+7);
				
			}	
				
			//chara.testanimation.setloop(true);
		}
		
		
		
		
		public void addPiece(Actor new_actor) {
			for(int i=0;i<cellgrid.length;i++)for(int j=0;j<cellgrid.length;j++)
				if(cellgrid[i][j].occupied==false)	
					{
						System.out.println(i+" "+j);
						addPiece(new_actor,i,j);
						//i=j=cellgrid.length;
					}
		}
		
		
		public void addPiece(Actor new_actor, int gridx,int gridy)
		{
			new_actor.setCell(cellgrid[gridx][gridy]);
			new_actor.Active=true;
			add(new_actor);
			Actors.add(new_actor);
		
		}
		
		//if you want to add actor directly to the screen
		public void addActor(Actor new_actor,int screenx,int screeny) {
			add(new_actor);
			Actors.add(new_actor);
			new_actor.xoffset=screenx;
			new_actor.yoffset=screeny;
			//further improvement note: impliment this Actor class so you can use it frome actor class
		}
		
	
	/*-------------Rotation manipulation------------------*/
	
		public void Rotate_NodeArray(Node[][] nodeArr) 
		{
			int xpof,ypof;
			
			for(int i=0;i<nodeArr.length;i++)for(int j=0;j<nodeArr.length;j++)
			{
				xpof=x+i*(cellsize+cellspace);
				ypof=y+j*(cellsize+cellspace);
				
				nodeArr[i][j].Virtualoffsetx=transx+focalpointx+rotoffx(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad));
				nodeArr[i][j].Virtualoffsety=(int)(transy)+(int)(((focalpointy+rotoffy(ypof-focalpointy,xpof-focalpointx,Math.toRadians(rad))+(int)(cellsize/2)))*0.3);

				//nodeArr[i][j].Virtualoffsetx=(int)((double)nodeArr[i][j].Virtualoffsetx/(publicdata.getBaseSize())*publicdata.width); //restag
				//nodeArr[i][j].Virtualoffsety=(int)((double)nodeArr[i][j].Virtualoffsety/(publicdata.getBaseSize())*publicdata.height); //restag
				
			}	
			
		}
		
		
		
		//rotates a point around a center
		//cp: center point to rotate around, p: point to rotate around center point, and theta: degrees to rotate.
		public int[] rotoffc(int[] cp,int[] p , double theta) {//returns the rotated point
			int[] np=new int[2];
			np[0]=cp[0]+ rotoffx((p[1]-cp[1]), (p[0]-cp[0]), theta);//calculates x
			np[1]=cp[1]+ rotoffy((p[1]-cp[1]), (p[0]-cp[0]), theta);
			return np;
		}
		
		//functions for rotating
		public int rotoffx(int py, int px, double theta) {
			return (int)((double)py*Math.cos(theta)-(double)px*Math.sin(theta));
		}
		public int rotoffy(int py, int px, double theta) {
			return (int)((double)px*Math.cos(theta)+(double)py*Math.sin(theta));
		}
		//public Color radiallight()
		
		
		public Color MySpectrum(int cindex) {
			//all colors
			if(cindex<225)
				return new Color(0,0,cindex%225 );
			else if(cindex>=225&&cindex<225*2)
				return new Color(0,cindex%225,225-(cindex%225) );
			else if((cindex>=225*2&&cindex<225*3))
				return new Color(cindex%225,225-(cindex%225),0);
			else return new Color(225,(cindex%225),(cindex%225));
		}
	
	}
}