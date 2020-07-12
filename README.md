# Jeu-de-Geenson_Word2Vector_Project_2019
A French game in Java using word2vector, where a random word, such as "computer" is shown to the user, and the user inputs three words which,
when their vector similarities are calculated, should have in the 10 most similar words the random word.
For example, with "computer" as the random word, by inputting "screen", "technology" and "calculation",
the random word "computer" should appear. It is played on a simulated game board with players and dice, and the first to get to the end,
by correctly guessing three words which make the random word appear in the similarity list, wins. The user gets three tries at each round.
The project description, along with the READ_ME in French and English, is provided in this repository.

READ_ME de JEU DE GEENSON
by Luke Byrne, Lilian Morgan and Heather Lloyd
March 2019-May 2019

Foreword: this project was completed entirely in French, and the original READ_ME
too, so it is important to keep in mind that some parts may seem bizarre, 
such as the input "deMagique" instead of"magicDie", for example.

** COMMAND LINE **
We named our Main "Game", so the following is used for the command line:
	java Game --w2v ../w2v_final3 --nbPlayers 2 --nbTry 3 --k 5 --deMagique
and thus without the input descriptions, it would be:
	java Game ../w2v_final3 2 3 5 deMagique

The command line variable descriptions are taken directly from the project documentation :

	- "w2v: Indicates the path to the word vector file."
	- "Players: Indicates the number of players."
	- "nbTry: Indicates the number of tries for each lap."
	- "k: the number of k-responses to be returned by the system"
	- "deMagic: The game will work with a magic dice"[1]

[1] In order to make the magic die work, one must write the word
"deMagique" (including the capital "M"), otherwise it'll be a normal die. It is
also a normal die in the standard case; that is, if you don't write anything down, like:
	java game ../w2v_final3 2 3 5

** QUERY SYNTAX **
During the game rounds, the game takes clues that you guess: these are
Strings. Other than these Strings, there's no other query syntax to remember.

** PROGRAM ARCHITECTURE **
This game/program consists of 7 classes whose descriptions can be found in the javadoc.
Firstly, we have a Vector class which designates the basis of the VectorList, our data 
structure of choice which contains the data in the file. The Vector includes the word, 
its vectors and the dimension (the number of vectors). 

The VectorList is a list of these Vectors.

The VectorUtil class is not much used except for the use of "similarity", which
finds the cosine of the vectors (essential for this game). It too contains additional
functions that the project requires: (vector) addition, subtraction, norm etc.

In addition, Player is the player, and Board is the board of the game and their associated
methods. The Board is hardcoded to be 5 x 5 in size.

Game contains the game (i.e. the Main). The general operation of this game is
to create a Player Board, instantiate the Board, and then play the game
with the players until someone wins. The program provides you with a random,
and you have a certain amount of tries (specified in the input) to guess three words
which, after having calculated their similarities, will have in the output that same word.
There are also special squares (move back by three steps, roll the dice again etc.).

** IMPLEMENTATION CHOICES **
We have chosen to create Vector and VectorList classes so that we can easily vector list
-- they've proven to be very fast.

We decided to use a HashMap to represent the Vector list. It gave us
the search speed required for the program,
as well as a way to store the vector array next to the word.

The size of the Board has been hardcoded because the game is (very, very...) hard to win,
so a huge game board would simply be impossible. The choice to put clues on
the tiles on the board, and not, for example, to use a Tile class, was
a choice of efficiency, and after removing a Tile class from the game, we saw a great
increase in speed in the program.

The creation of the small methods in Game, such as initialized() and equalsRandomWord() were
a choice to help the readability of the program, not effectiveness.

With respect to the Player class, the option of giving them a name or other
details was not given as they weren't needed - Player 1, 2 etc. works fine.
At each turn the player number is printed and how many steps they need until 
they win the game.

Finally, in SimilarityUtil, the propensity to use three different classes was to
divide the problem of finding the k-responses easily. The first one, findAllSimilarities(),
finds all the similarities, findMeanOfSimilarities() finds the average of the three
hints, and findClosestSimilarities() finds the closest k-responses. It should be
specified that using a TreeMap in findClosestSimilarities() was an important choice,
because a TreeMap can order integers easily.

In addition, we sometimes use (Hash/Tree)Map<Double, String> in SimilarityUtil
when we want to order the average of the vectors, and (Hash/Tree)Map<String, Double> when
you want to search for words: it is much easier to search for a word with
String/the word as a key rather than making a search function with the values:
the reverse is true with vectors as key and not the word.

** END **

Thank you for reading this ReadMe!
© 2020 GitHub, Inc.
