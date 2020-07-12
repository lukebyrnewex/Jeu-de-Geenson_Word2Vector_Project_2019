import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
* La classe Game est l'axe centrale de la programme.
* Il contient la seule méthode main() et donc est la seule
* classe éxécutable, et en plus contient dedans les sous-méthodes
* qui aident l'éxécution du jeu directement (par ex., initialise(), 
* takeGuesses(), etc.).
*
* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
* @since   26-04-2019
*/
public class Game {
	// Variables qui viennent du terminal
	private static int nbJoueurs;
	private static int nbTry;		
	private static int k;
	
	// La taille du plateau de jeu
	private static final int BOARD_SIZE = 5;
	
	/**
	* The class Game contains the only main function, as stated above.
	* This is the core of the games running.
	* It puts all the other functions found in the project to use in order
	* to create the basic logic game in the project.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @throws  IOException for file input
	* @param   args = input from terminal
	*/
	public static void main(String[] args) throws IOException {
		/* PARTIE UNE : ENTRÉE */
		String filename = args[0];
		
		// NOMBRE DE JOUEURS
		try {
			nbJoueurs = Integer.parseInt(args[1]);
			if(nbJoueurs < 2) {
				System.out.println("Infructueux. Le nombre de joueurs doit être plus grand que 2!");
				System.exit(1);
			}	
		}
		catch (NumberFormatException nfe) {
            System.out.println("L'argument \"nbJoueurs\" doit être un entier!");
            System.exit(1);
        }
		
		// NOMBRES D'ESSAIS
		try {
			nbTry = Integer.parseInt(args[2]);
			if(nbTry <= 0) {
				System.out.println("Infructueux. Le nombre d'essais doit être plus grand que 0!");
			}
		}
		catch (NumberFormatException nfe) {
            System.out.println("L'argument \"nbTry\" doit être un entier.");
            System.exit(1);
        }
		
		// NOMBRES DE RÉPONSES
		try {
			k = Integer.parseInt(args[3]);
			if(k <= 0) {
				System.out.println("Infructueux. Le nombre de réponses doit être plus grand que 0!");
			}
		}
		catch (NumberFormatException nfe) {
            System.out.println("L'argument \"k\" doit être un entier.");
            System.exit(1);
        }
		
		// DÉ MAGIQUE
		boolean magique = false;
		if(args.length > 4) { // si l'entrée contient le champ "deMagique"
			String deMagique = args[4];
			if(deMagique.equals("deMagique")) {
				magique = true;
			}
		}
		
		/* PARTIE 2 : LE JEU */
		VectorList v = initialise(filename);
		
		// Imprimer les détails du VectorList au début
		v.printVectorListDetails();
		System.out.println("---------------");		
		
		// Initialiser les joueurs et le plateau de jeu
		Player[] players = new Player[nbJoueurs];
		for (int i = 0; i < nbJoueurs; i++) {
			players[i] = new Player(i);
		}
		Board b = new Board(BOARD_SIZE, magique);
		
		// Variable qui définit l'indice dans "players" le joueur actuel
		int current = 0;
		
		// Jouer le jeu ! Il y a un "break" quand on trouve le gagnant
		while(true) {
			// Si le joueur tombe sur une tuile qui nécissite un tour de jeu
			if(b.getTileType(players[current]) == 1) {
				boolean turn = false; // si vous avez gagné ce tour de jeu
				do { // ... fait un tour d'aboard
					System.out.println(b.prompt(players[current]));
					System.out.println("You have " + stepsLeft(players[current]) + " steps to go!");
					turn = takeTurn(players[current], b, v);
					if(turn) {
						b.move(players[current], b.rollDice());
					}
				// ... jusqu'au point que vous avez gagné !
				} while(turn && b.getTileType(players[current]) == 1);
			}
			else {
				System.out.println(b.prompt(players[current]));
			}
			
			// Si vous avez gagné le tour ...
			if (b.winner(players[current])) {
				break;
			}
			
			// On va au prochain joueur après le tour !
			current++;
			
			// Si on trouve le dernier joueur, recommence au début !
			if(current >= players.length) {
				current = 0;
			}
		}
		
		// Si le boucle finit, alors on a trouvé notre gagnant !
		System.out.println("Félicitations Player " + current + " -- vous avez gagné(e) !");
	}
	
	/**
	* takeTurn() allows the user to take their turn! It takes in the
	* current player, the board, and the list of words and then
	* gives the player three chances to make the random word
	* appear in their list -- if it does, then you win that turn!
	* If not, you have to try again for nbTry amount of times.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   p = the current player
	* @param   b = the board
	* @param   v = the list of words and their associated vectors
	* @return  boolean saying you won the turn or not
	*/
	public static boolean takeTurn(Player p, Board b, VectorList v) {
		// Le système choisit un mot aléatoire du VectorList
		Vector randomWord = v.randomVector();
		System.out.println("Votre mot aléatoire est : " + randomWord.getName()
		+ "\nFaites trois suppositions pour trouver le mot aléatoire !");
		
		// Variables pour le boucle
		boolean turnSuccessful = false;
		int turnCounter = 0;
		
		// Imprimer les similarités les plus proches et prendre nbTry tours
		while(!turnSuccessful && turnCounter < nbTry) {
			if (turnCounter > 0) { System.out.println("Réesayez !"); }
			
			// Le système prend les trois suppositions du joueur actuel et crée des vecteurs
			Vector[] guesses = takeGuesses(randomWord, v);
			System.out.println("Indices : " + guesses[0].getName() + " / " +
					guesses[1].getName() + " / " + guesses[2].getName() + "\n");
			
			// Trouver les similarités les plus proches
			Map<Double, String> closestSimilarities = SimilarityUtil.findClosestSimilarities(guesses, v, k);
			
			// Les imprimer
			System.out.println("Turn " + (turnCounter + 1));
			
			// Traverser la liste des k-similarités et trouver si la supposition correcte est là
			for (Entry<Double, String> entry : closestSimilarities.entrySet()) {
				System.out.println(entry.getValue() + ", " + entry.getKey());
				if (entry.getValue().compareTo(randomWord.getName()) == 0) {
					//  ... et s'il est là, alors vous avez gagné ce tour !
					turnSuccessful = true;
				}
			}
			
			// Incrémenter le tour jusqu'au nbTry !
			turnCounter++;
		}
		
		// Imprimer les résultats
		if (turnSuccessful) { 
			System.out.println("\nFélicitations, vous avez deviné le mot aléatoire !");
			return true;
		}
		else {
			System.out.println("\nCommisérations, meilleure des chances la prochaine fois !");   
			return false;
		}
	}
	
	/**
	* takeGuesses() does just that - it takes the task of taking in
	* the user's three guesses as to what the "random word" in the game
	* might be. This randomWord is taken in as input to make sure that 
	* the user hasn't used that as one of their guesses - that, of course,
	* would be cheating. This function also checks to make sure the users'
	* guesses are actual words - this alone saves an enormous amount of
	* error handling. It returns, in a Vector array, the three guesses.
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   randomWord = for use in comparison and to prevent cheating
	* @param   v = used to see if word exists
	* @return  indices = the three guesses taken in from this function 
	*/
	private static Vector[] takeGuesses(Vector randomWord, VectorList v) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String[] guesses = new String[3];
		System.out.println();
		
		// Prints out the number of the guess and then allows you to take it,
			// adding it into the array of three guesses
		for (int i = 0; i < 3; i++) {
			System.out.println("Indice " + (i+1) + "?");
			guesses[i] = scan.nextLine();
			
			// If it's in the dictionary, you have to try again ...
			if (!isInDictionary(guesses[i], v)) {
				System.out.println("Ce mot-là n'est pas dans le dictionnaire. Réessayez !");
				guesses[i] = "";
				i--;
			}
			
			// ... or if it equals the random word itself, you have to try again ...
			else if (equalsRandomWord(guesses[i], randomWord.getName())) {
				System.out.println("Vous ne pouvez pas deviner le mot aléatoire soi-même. Réessayez !");
				guesses[i] = ""; 
				i--;
			}
			
			// or finally, if you've already guessed the word, you must try again ...
			else if (wordInArray(i, guesses)) {
				System.out.println("Vous ne pouvez pas deviner un mot que vous avez déjà deviné. Réessayez !");
				guesses[i] = "";
				i--;
			}
		}
		System.out.println();
		
		// Créer les vecteurs
		Vector indice1 = new Vector(guesses[0], v.vectorList.get(guesses[0]), v.getDimensions());
		Vector indice2 = new Vector(guesses[1], v.vectorList.get(guesses[1]), v.getDimensions());
		Vector indice3 = new Vector(guesses[2], v.vectorList.get(guesses[2]), v.getDimensions());
		Vector[] indices = {indice1, indice2, indice3};
		
		return indices;
	}
	
	/**
	* wordInArray() checks to see if the word has already been guessed
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   i = index of word in array
	* @param   a = array to check
	* @return  boolean saying whether word is there or not
	*/
	private static boolean wordInArray(int i, String[] a) {
		for(int j = 0; j < i; j++) {
			if(equalsRandomWord(a[i], a[j]))
				return true;
		}
		return false;
	}
	
	/**
	* isInDictionary() checks to see if the word is in the VectorList
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   word = word to check
	* @param   v = "dictionary"
	* @return  boolean saying whether word is in dictionary or not
	*/
	private static boolean isInDictionary(String word, VectorList v) {
		return v.vectorList.containsKey(word);
	}
	
	/**
	* equalsRandomWord checks if the first word equals the second.
	* It is a deprecated function in most instances, but helps with
	* readability and comprehension of the code 
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   w1 = word1
	* @param   w2 = word2
	* @return  boolean stating whether they're equal or not
	*/
	private static boolean equalsRandomWord(String w1, String w2) {
		if (w1.compareTo(w2) == 0) { return true; }	return false;
	}
	
	/**
	* initialise() is a tiny function to initialise the VectorList with
	* a filename. #work on this later for IOException, the reason for which
	* this function exists
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   filename = the name of file with list of words/vectors
	* @return  VectorList created from the file
	* @throws  IOException : if file doesn't exist
	*/
	private static VectorList initialise(String filename) throws IOException {
		return new VectorList(filename);
	}
	
	/**
	* stepsLeft() indicates how many more steps the player has to
	* take until they win the game!
	*
	* @author  Luke Byrne, Lilian Morgan, Heather Lloyd
	* @since   26-04-2019
	* @param   p = the current player
	* @return  how many steps are left
	*/
	private static int stepsLeft(Player p) {
		int[] position = p.getPos();
		return ((BOARD_SIZE - position[0]) * BOARD_SIZE) - position[1];
		
	}
}