/**
  * The Player class contains just that - the player. It is initialised
  * with the number in sequential order, and their current coordinates
  * on the board.
  *
  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
  * @since   29-04-2019
  */

public class Player {
	int number; // Player 1, 2 3 etc.
	int[] position = new int[2]; // Coordinates in board
	
	/**
	  * The Player constructor instantiates the player with its
	  * number and its position.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   number = the current number of the player
	  * 	in sequential order
	  */
	public Player(int number) {
		this.number = number;
		this.position[0] = 0; // X-coordinate
		this.position[1] = 0; // Y-coordinate
	}
	
	/**
	  * move() moves the player to the inputted position.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   newPos = coordinates of the position
	  * 	to move to
	  */
	public void move(int[] newPos) {
		position = newPos;
	}
	
	/**
	  * Getters of the Player class - number and position.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  */
	public int getNumber()  { return number;   }
	public int[] getPos()	{ return position; }
}
