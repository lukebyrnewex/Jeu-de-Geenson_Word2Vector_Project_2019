import java.util.Random;
/**
 * The Board class defines the gameboard in use in the
 * game. It contains its size (which is fixed in Game),
 * whether the dice is magic and the board itself. Within it,
 * we can move the player around the board, check if
 * the player has one the game, etc. It is an integral
 * part of the program.
 *
 * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
 * @since   29-04-2019
 */
public class Board {
	// Instance variables
	int size;
	int[][] board;
	boolean magique;
	
	/**
	  * Constructor for Board, which initialises the
	  * size and whether or not the board is magic or not.
	  * Contained within is the true function for this
	  * initialisation, initialiseBoard(), which is
	  * found below.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   size = size of board
	  * @param   magique = if the die is magic or not
	  */
	public Board(int size, boolean magique) {
		this.size = size;
		this.magique = magique;
		board = new int[size][size];
		initialiseBoard(board);
	}
	
	/**
	  * initialiseBoard() takes in the board and initialises it.
	  * It does this by assigning to each "tile" in the board a
	  * number, each of which corresponds to a different type of
	  * event : for example, 1 = take turn etc. It takes into
	  * consideration the placements of the tiles (i.e. one 
	  * cannot have "move back three" if we haven't already
	  * gotten that far, and defines the start as always
	  * take a turn, and the end as 4 = winning. As its
	  * editing the constructor, it doesn't return the board
	  * but just edits it as is.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   board = the board
	  */
	private void initialiseBoard(int[][] board) {
		// Randomised tile events
		Random rand = new Random();
		int r = 0;
		
		// Loop through board, giving each tile a random event...
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// Start tile
				if (i == 0 && j == 0){
					board[i][j] = 1; 
				}
				
				// End tile
				else if (i == size - 1 && j == size - 1) {
					board[i][j] = 4; 
				}
				
				// Random events for all others...
				else {
					r = rand.nextInt(3);
					// ... except for first three, which can't have "move back three" event!
					while(r + 1 == 3 && i == 0 && j <= 2) {
						r = rand.nextInt(3);
					}
					board[i][j] = r + 1;
				}
			}
		}
	}
	
	/**
	  * move() furthers the player throughout the gameboard.
	  * It takes in the desired amount of steps and the player,
	  * and then assigns to that player its now current location.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   p = player
	  * @param   steps = how far the player must move (either forwards or backwards)
	  */
	public void move(Player p, int steps) {
		// Get coordinates of player...
		int[] currentPosition = p.getPos();
		
		// determine whether we must move to the next level, or if going backwards...
		if(currentPosition[0] + steps > size - 1 || steps < 0) {
			// if so, take the current i-coordinate...
			int i = currentPosition[0];
			
			// and if we're going backwards...
			if (i + steps < 0) {
				// set the position to there!
				currentPosition[0] = -((steps + i) % size);
				currentPosition[1] += Math.floor((double) (steps + i)/size);
			}
			// or if we're going forwards...
			else {
				// set the position to there!
				currentPosition[0] = (i + steps - size) % size;
				currentPosition[1] += (int) (i + steps) / size;
			}
		}
		// if not, add i-coordinate only!
		else {
			currentPosition[0] += steps;
		}
		
		// Update the players current coordinates
		p.move(currentPosition);
	}	
	
	/**
	  * winner() determines if the player has won or not, by
	  * checking its position against the "winning tile".
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   p = player
	  * @return  boolean whether you have won or not
	  */
	public boolean winner(Player p) {
		// Determine current position...
		int[] currentPosition = p.getPos();
		
		// If current coordinates are past the finish line...
		if(currentPosition[0] >= (size - 1) && currentPosition[1] >= (size - 1)) {
			return true;
		}
		
		// ... and if not, you haven't yet unfortunately!
		return false;
	}
	
	/**
	  * prompt() returns the description of the events which
	  * are embedded within the current tile. For example,
	  * if the tile is defined as type 1, then it returns
	  * a String saying "take your turn!". In the case of
	  * having the roll again or move backwards,
	  * this function performs that action and recursively
	  * calls prompt() until  you land on a tile where
	  * you must take your turn.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   p = player
	  * @return  the description of the current tile
	  */
	public String prompt(Player p) {
		// Get current position of player...
		int[] currentPosition = p.getPos();
		String positionString = "Current Player: Player " + p.getNumber() + " " + positionToString(currentPosition) + "\n";
		
		// If you haven't gone past the end of the board...
		if (!winner(p)) {
			// Switch to the current type of event the player finds themselves in!
			switch(board[currentPosition[0]][currentPosition[1]]) {
				// If you're on the starting block...
				case 0: return positionString + "\nCommencez !";
				
				// If you're *insert action to do here*...
				case 1: return positionString + "\nFaites votre tour de jeu !";
				
				// If you have to roll again... (and thus take another turn, recursively!)... 
				case 2: move(p, rollDice()); return positionString + "\nVous avez roulé le dé une autre fois. \n" + prompt(p);
				
				// If you have to move back three spaces...
				case 3: move(p, -3); return "\nVous avez reculé par 3 étapes. " + positionToString(p.getPos());
				
				// If you've won!
				case 4: return "\nFélicitations " + p.getNumber() + " - vous avez gagné(e) !";		
			}
		}
		
		// If we reach here, the player has won!
		return "Vous avez gagné !";
	}
	
	/**
	  * positionToString() returns a description of
	  * the current position taken in input.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   position = the position we want to print
	  * @return  the position in String form
	  */
	public String positionToString(int[] position) {
		return "\nPosition actuelle--\n\tX: " + position[0] + "\n\tY: " + position[1] + " ";		
	}
	
	/**
	  * getTileType() produces the type of the current tile
	  * on the board in integer form.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @param   p = player who is on the tile
	  * @return	 the type of tile
	  */
	public int getTileType(Player p) {
		int[] pos = p.getPos();
		return board[pos[0]][pos[1]];
	}
	
	/**
	  * rollDice() rolls the dice. Within, it sees whether
	  * the board is magic or not, and therefore picks
	  * the appropriate dice to use before rolling.
	  *
	  * @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	  * @since   29-04-2019
	  * @return	 the number on the die
	  */
	public int rollDice() {
		Random r = new Random();
		int roll;
		
		// If magic die...
		if(this.magique) {
			 roll = r.nextInt(7);
			 if (roll == 0) {
				 System.out.println("Restez où vous êtes !");
			 }
			 return r.nextInt(7);
		}
		return r.nextInt(6) + 1;
	}
}
