import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.lang.Math;
import java.util.*;

// NOTE: The board is size 30x19 with each square being 25 pixels

//TODO: ADD CASE FOR NO MORE POSITIONS FOR FOOD ON BOARD -> WIN?
public class Food {
  
  // Fields
  int xposn;
  int yposn;

  // Constants
  int ROW_SIZE = 30;
  int COL_SIZE = 19;
  int SQUARE_SIZE = 25;
  int X_ORIGIN = 25; 
  int Y_ORIGIN = 25;


	// Constructor for Player
  public Food() {

  	//Set xposn and yposn to garbage values until food generated
  	xposn = -1000;
  	yposn = -1000;

  }


  // Generate next food position
  public void generateFood(Vector<Vector<Integer>> posns, Vector<Vector<Integer>> walls) {
  	//Create list of possible spots for food
  	Vector<Integer> spots = new Vector<Integer>(0);
  	for (int i=0; i<COL_SIZE; i++) {
  		for (int j=0; j<ROW_SIZE; j++) {
  			spots.add(i*ROW_SIZE+j);
  		}
  	}

  	Collections.shuffle(spots);

	  Vector<Integer> posn = new Vector<Integer>(2);
	  do {

	  	posn.clear();
		  int num = spots.get(0);

	  	posn.add(num/COL_SIZE);
	  	posn.add(num%COL_SIZE);

	  	spots.remove(0);

	  } while (posns.contains(posn) || walls.contains(posn));

	  xposn = posn.get(0);
	  yposn = posn.get(1);

	}


	// Draw food
	public void drawFood(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.YELLOW); // make it yellow
    g2.setStroke(new BasicStroke(0)); // 0 pixel thick stroke
    Rectangle r = new Rectangle(X_ORIGIN+(xposn*SQUARE_SIZE),
    										  			Y_ORIGIN+(yposn*SQUARE_SIZE),
    										  			SQUARE_SIZE, 
    										  			SQUARE_SIZE);
    g2.fill(r);
    g2.draw(r);  // Player's Snake
	
	}


}
