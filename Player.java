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
import java.util.*;

// NOTE: The board is size 30x19 with each square being 25 pixels

public class Player {
  
  // Fields
  int direction; // 0 = up, 1 = down, 2 = left, 3 = right
  int xposn; // x position - head of snake
  int yposn; // y position - head of snake
  Vector<Vector<Integer>> posns;   // Vector of positions of squares of the snake
  Boolean intoitself;

  // Constants
  int ROW_SIZE = 30;
  int COL_SIZE = 19;
  int SQUARE_SIZE = 25;
  int X_ORIGIN = 25; 
  int Y_ORIGIN = 25;

  // Constructor for Player
  public Player() {
  	// Snake start off at 2,2
  	xposn = 2;
  	yposn = 2; 
  	int xposn2 = 1;
  	int yposn2 = 2; 
  	direction = 3; // Direciton starts off as right
  	intoitself = false;
  

  	Vector<Integer> posn = new Vector<Integer>(2);
  	posn.add(xposn);
  	posn.add(yposn);

  	Vector<Integer> posn2 = new Vector<Integer>(2);
  	posn2.add(xposn2);
  	posn2.add(yposn2);

  	posns = new Vector<Vector<Integer>>(0);
  	posns.add(posn2);
  	posns.add(posn);

  }


  public void generateSnake() {

    // Calculate new position
    if (direction == 0 && yposn >= 0) {
      yposn--;
    } else if (direction == 1 && yposn < COL_SIZE) {
      yposn++;
    } else if (direction == 2 && xposn >= 0) {
      xposn--;
    } else if (direction == 3 && xposn < ROW_SIZE) {
      xposn++;
    }

    // Add new posn to position Vector
    Vector<Integer> posn = new Vector<Integer>(2);
    posn.add(xposn);
    posn.add(yposn);

    if (posns.contains(posn)) {
    	intoitself = true;
    }

    posns.add(posn);

  }

  // custom graphics drawing 
  public void drawSnake(Graphics g) {
    Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(5)); // 0 pixel thick stroke

    // Draw snake
    for (int i=0; i<posns.size(); i++) {
	    Rectangle r = new Rectangle(X_ORIGIN+(posns.get(i).get(0)*SQUARE_SIZE),
	    										  			Y_ORIGIN+(posns.get(i).get(1)*SQUARE_SIZE),
	    										  			SQUARE_SIZE, 
	    										  			SQUARE_SIZE);
    	g2.setColor(Color.BLUE);
    	g2.fill(r); 
    	g2.setColor(Color.BLACK); // make the outline black
    	g2.draw(r);
	  } 

  }

}
