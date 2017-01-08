import javax.swing.*;
import javax.swing.Timer;
import javax.swing.JPanel;
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

public class Snake extends JComponent {

  // Object properties
  private int fps;
  private int speed;
  private int score;
  private Player p;
  private Food food;
  private Timer paintTimer;
  private Timer snakeTimer;   // Vector of positions of squares of the snake
  private Vector<Integer> keypresses; // Keeps track of keypresses 
  private Boolean paused;
  private Vector<Vector<Integer>> walls;

  // Constants
  int ROW_SIZE = 30;
  int COL_SIZE = 19;
  int SQUARE_SIZE = 25;
  int X_ORIGIN = 25; 
  int Y_ORIGIN = 25;
  int[] wallx = {2, 2, 3, 26, 27, 27, 2, 2, 3, 26, 27, 27, 13, 14, 15, 13, 14, 15};
  int[] wally = {4, 3, 3, 3, 3, 4, 15, 16, 16, 16, 16, 15, 7, 7, 7, 11, 11, 11};

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        runProgram(args);
      }
    });
  }


  public static void runProgram(String[] args) {

    JFrame f = new JFrame("Snake"); // jframe is the app window
    f.setResizable(false);// Non-resizable

    //Splash Screen
    SplashPanel sp = new SplashPanel(f, args);

    // Make the focus on the canvas (for KeyListener)
    sp.setFocusable(true);

    // Create window
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(800, 600); // window size... NOTE: 22px of height is taken up by bar at top
    f.setContentPane(sp); // add canvas to jframe
    f.setVisible(true); // show the window

  }


  public static void reRunProgram(JFrame f, String[] args) {
    //Splash Screen
    SplashPanel sp = new SplashPanel(f, args);

    // Make the focus on the canvas (for KeyListener)
    f.setContentPane(sp);
    f.getContentPane().revalidate(); 
    
    // Make the focus on the canvas (for KeyListener)
    sp.requestFocus();
    sp.setFocusable(true);

    // Create window
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(800, 600); // window size... NOTE: 22px of height is taken up by bar at top
    f.setContentPane(sp); // add canvas to jframe
    f.setVisible(true); // show the window

  }


  // Constructor for Game
  public Snake(JFrame f, String[] args) {
    fps = (args.length>0) ? Integer.parseInt(args[0]) : 30;
    speed = (args.length>1) ? Integer.parseInt(args[1]) : 5;
    
    // Set score to 0
    score = 0;

    // Not paused
    paused = false;
    
    // Initialize Player
    p = new Player();
    
    // Initialize Food
    food = new Food();
    
    // Initialize empty keypresses vector
    keypresses = new Vector<Integer>(0);

    //Initialize Walls
    walls = new Vector<Vector<Integer>>(0);

    for (int i=0; i<wallx.length; i++) {
      Vector<Integer> v = new Vector<Integer>(2);
      v.add(wallx[i]);
      v.add(wally[i]);
      walls.add(v);
    } 


    this.addKeyListener( new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          keypresses.add(0);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          keypresses.add(1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          keypresses.add(2);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          keypresses.add(3);
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
          reRunProgram(f, args); 
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
          if (paintTimer.isRunning()) {
            paused = true;
            repaint();
            paintTimer.stop();
            snakeTimer.stop();
          } else {
            paused = false;
            paintTimer.start();
            snakeTimer.start();
          }
        }
      }
    });

    // Set snake speed timer
    snakeTimer = new Timer(1000/(2*speed), new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        moveSnake();
      }
    });
    snakeTimer.start();

    // Set repaint timer
    paintTimer = new Timer(1000/fps, new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        repaint();
      }
    });
    paintTimer.start();

  }


  public void moveSnake() {

    // Get next keypress
    while (!keypresses.isEmpty()) {
      if (keypresses.get(0) == p.direction) {
        keypresses.remove(0);
        continue; 
      }
      if (p.direction == 0 && keypresses.get(0) != 1) {
        p.direction = keypresses.get(0);
        keypresses.remove(0);
        break;
      } else if (p.direction == 1 && keypresses.get(0) != 0) {
        p.direction = keypresses.get(0);
        keypresses.remove(0);
        break;
      } else if (p.direction == 2 && keypresses.get(0) != 3) {
        p.direction = keypresses.get(0);
        keypresses.remove(0);
        break;
      } else if (p.direction == 3 && keypresses.get(0) != 2) {
        p.direction = keypresses.get(0);
        keypresses.remove(0);
        break;
      } 
      keypresses.remove(0);
    }

    // Generate new position of the snake
    // Note: Must do this before checking if the food was eaten
    p.generateSnake();

    // Check if the food was eaten
    if (p.xposn == food.xposn && p.yposn == food.yposn) {
      score += 10;
      food.generateFood(p.posns, walls);
    // If food not generated then generate it
    // Remove last snake position if food was not eaten (else if and else cases)
    } else if (food.xposn == -1000 && food.yposn == -1000) {
      food.generateFood(p.posns, walls);
      p.posns.remove(0);
    } else {
      p.posns.remove(0);
    }

  }


  // Main paint function
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(10)); // 10 pixel thick stroke
    g2.setColor(Color.BLACK); // make it black
    g2.draw(new Rectangle(20, 20, getWidth()-40, getHeight()-93));  // draw border 

    // Walls
    for (int i=0; i<walls.size(); i++) {
      g2.setColor(Color.RED); // make it Red
      g2.setStroke(new BasicStroke(0)); // 0 pixel thick stroke
      Rectangle r = new Rectangle(X_ORIGIN+(walls.get(i).get(0)*SQUARE_SIZE),
                                  Y_ORIGIN+(walls.get(i).get(1)*SQUARE_SIZE),
                                  SQUARE_SIZE, 
                                  SQUARE_SIZE);
      g2.fill(r);
      g2.draw(r);  // Player's Snake
    }

    // Draw warp areas
    g2.setColor(g2.getBackground()); // make it the background colour
    g2.setStroke(new BasicStroke(10)); // 10 pixel thick stroke (same as border)
    g2.drawLine(20, 250, 20, 275);
    g2.drawLine(getWidth()-20, 250, getWidth()-20, 275);
    g2.drawLine(25+14*SQUARE_SIZE, 20, 25+15*SQUARE_SIZE, 20);
    g2.drawLine(25+14*SQUARE_SIZE, getHeight()-73, 25+15*SQUARE_SIZE, getHeight()-73);

    // Draw Food
    food.drawFood(g);

    // Draw score
    g2.setColor(Color.BLACK);
    g2.drawString("Score: " + score, 80, 550);
    g2.drawString("Speed: " + speed, 500, 540);
    g2.drawString("FPS: " + fps, 500, 560);

    // Check if snake went through warp
    if (p.xposn == -1 && p.yposn == 9) {
      p.xposn = 29;
      p.posns.lastElement().set(0,29);
    } else if (p.xposn == 30 && p.yposn == 9) {
      p.xposn = 0;
      p.posns.lastElement().set(0,0);
    }

    // Check if snake went through warp
    if (p.xposn == 14 && p.yposn == -1) {
      p.yposn = 18;
      p.posns.lastElement().set(1,18);
    } else if (p.xposn == 14 && p.yposn == 19) {
      p.yposn = 0;
      p.posns.lastElement().set(1,0);
    }

    // Draw Snake
    p.drawSnake(g);

    if (paused) {
      g2.setColor(Color.BLACK);
      g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 15)); 
      g2.drawString("Press p to unpause", 355, 270);
    }

    //Check if it ran into a barrier:
    Vector<Integer> v = new Vector<Integer>(2);
    v.add(p.xposn);
    v.add(p.yposn);

    if (p.yposn < 0 || p.yposn >= COL_SIZE ||
        p.xposn < 0 || p.xposn >= ROW_SIZE ||
        p.intoitself || walls.contains(v)) {
      paintTimer.stop();
      snakeTimer.stop();
      g2.setColor(Color.BLACK);
      g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 15)); 
      g2.drawString("Game Over :(", 355, 270);
      g2.drawString("Press r to restart", 340, 285);
    }


  }
}







