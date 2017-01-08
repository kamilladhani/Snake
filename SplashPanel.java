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

class SplashPanel extends JPanel {

  public SplashPanel(JFrame f, String[] args) {
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        // Instantiate Snake Class
        Snake canvas = new Snake(f, args);
        f.setContentPane(canvas);
        f.getContentPane().revalidate(); 
        
        // Make the focus on the canvas (for KeyListener)
        canvas.requestFocus();
        canvas.setFocusable(true);
      }
    });

    this.addKeyListener( new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
          // Instantiate Snake Class
          Snake canvas = new Snake(f,args);
          f.setContentPane(canvas);
          f.getContentPane().revalidate(); 
          
          // Make the focus on the canvas (for KeyListener)
          canvas.requestFocus();
          canvas.setFocusable(true);
        }
      }
    });
  }


  // Main paint function
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(10)); // 10 pixel thick stroke
    g2.setColor(Color.BLACK); // make it black
    g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 50)); 
    g2.drawString("Snake", 330, 100); 
    g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 20)); 
    g2.drawString("Use the arrow keys to move the snake", 220, 220); 
    g2.drawString("Eat the yellow food to get points!", 250, 245);
    g2.drawString("Avoid the red barriers!", 300, 270);
    g2.drawString("Other controls:", 340, 320); 
    g2.drawString(" - p to pause/unpause", 300, 340); 
    g2.drawString(" - r to restart the game", 300, 360);
    g2.drawString("Click anywhere or press s to start game", 220, 430); 

    g2.setFont(new Font(g2.getFont().getFontName(), Font.PLAIN, 15)); 
    g2.drawString("Kamil Ladhani", 355, 500); 


  }

}