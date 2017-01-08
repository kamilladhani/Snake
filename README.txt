Snake 

Made for Macs

Made on: OSX Yosemite
JDK version: 1.8.0_91


Overall Design / Implementation:
 
There are 4 main classes/files: 
  Snake.java - Board/overall logic and main draw function. The board is a 30x19 board, with each square being 25 pixels.
  Player.java - Player/snake itself implemented using a vector of vectors of length 2 (an x and a y position for each inner vector which represent the coordinates of a square of the snake). 
  Food.java - Creates the food using a random number generator (but will not generate food where the snake and barriers are located)
  SplashPanel.java - Draws/creates splash panel 

Other important points:
 - The speed of the snake is determined by formula (1 sec)/(2*s) where s is the speed entered by command line
 - The score is shown at the bottom left of the screen and the fps and speed of the snake are shown at the bottom right 
 - The default arguments are 30 fps and a snake speed of 5.
 - 10 points are awarded for each food eaten

Controls: 
	- Use the arrow keys to move the snake
	- Use p to pause/unpause
	- Use r to restart the game (returns to the splash screen)
	- From the splash screen, click anywhere or press s to start
