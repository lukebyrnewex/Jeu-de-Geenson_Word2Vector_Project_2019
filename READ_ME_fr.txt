READ_ME de JEU DE GEENSON
de Luke Byrne, Lilian Morgan et Heather Lloyd
Mars 2019 - mai 2019

** LIGNE DE COMMANDE **
On a appel� notre Main comme Game, donc alors il faut :
	java Game --w2v ../w2v_final3 --nbJoueurs 2 --nbTry 3 --k 5 --deMagique
mais �videmment sans les parties avec --, donc :
	java Game ../w2v_final3 2 3 5 deMagique

Quand m�me, les prochaines descriptions des variables de la ligne de commande sont
prises de la documentation du projet :

	- "w2v: indique le chemin vers le fichier de vecteurs de mots"
	- "nbJoueurs: indique le nombre de joueurs"
	- "nbTry: indique le nombre d'essais pour chaque tour"
	- "k: le nombre de k-r�ponses � retourner par le syst�me"
	- "deMagique: le jeu fonctionnera avec un d� magique"[1]

[1] On pr�cise qu'il faut, afin de faire fonctionner le d� magique, �crire le mot
"deMagique" (y compris le M au majuscule), sinon il va �tre un d� normal. Il est
�galement un d� normal au cas standard ; c'est � dire que si on n'�crit rien, comme :
	java Game ../w2v_final3 2 3 5
alors le d� est ainsi normal.

** SYNTAXE DES REQU�TES **
Pendant les tours de jeu, le jeu prend en entr�e des indices que vous devinez : ce sont
des String. � part ces String, il n'y a pas d'autres syntaxe � se souvenir.

** ARCHITECTURE DU PROGRAMME **
Ce jeu/programme comprend 7 classes dont les descriptions se trouvent dans le javadoc.
D'abord, on a une classe Vector qui d�signe la base du VectorList, notre structure de
donn�es qui contiennent les donn�es du fichier. Le Vector comprend le mot, ses vecteurs
et la dimension (le nombre des vecteurs). 

Le VectorList est une liste de ces Vector.

La classe VectorUtil n'est pas beaucoup utilis� hormis l'utilisation de "similarity", qui
trouve le cosinus des vecteurs (indispensable � ce jeu). Il contient toutefois les
fonctions que le projet demande : addition, soustraction, norm etc.

De plus, Player est �videmment le joueur, et Board le plateau du jeu et leurs m�thodes
associ�s. Le Board est hardcod� d'avoir une taille de 5 x 5.

Game contient le jeu (c'est-�-dire le main). Le fonctionnement g�n�ral de ce jeu est
de cr�er un tableau des Player, instantier le Board, et ensuite faire des tours de jeu
avec les joueurs jusqu'� ce que quelqu'un gagne. Le programme vous fournit un mot
al�atoire, et vous avez des essais (sp�cifies dans l'entr�e) de deviner trois mots
dont leurs similarit�s donnent le mot al�atoire. Il y a des cases sp�ciales (reculer par
trois �tapes, rouler le d� encore etc.).

** CHOIX D'IMPL�MENTATION **
On a choisi de cr��s des classes Vector et VectorList pour qu'on puisse utiliser
facilement la liste des vecteurs -- ils ont prouv� d'�tre tellement vite.

On a d�cid� d'utiliser un HashMap pour repr�senter la liste des Vector. Il nous a donn�
la vitesse de recherche qu'on utilise tr�s fr�quemment pendant la programme,
ainsi qu'une mani�re de stocker le tableau des vecteurs � c�t� du mot.

La taille du Board a �t� hardcod� parce que le jeu est (tr�s, tr�s...) difficile � gagner,
donc un plateau de jeu immense sera impossible. Le choix de mettre des indices sur
les tuiles du plateau, et de ne pas, par exemple, utiliser une classe Tile, �tait
un choix d'efficacit� et apr�s avoir supprim� une classe Tile du jeu, on a vu une grande
augmentation de vitesse du programme.

La cr�ation des petites m�thodes dans Game, comme initialise() et equalsRandomWord() �tait
un choix d'aider la lisibilit� du programme, et non pas d'efficacit�.

En ce qui concerne le Player, on n'a pas donn� l'option de leur donn� un nom ou d'autres
d�tails parce qu'on n'en a pas besoin - Player 1, 2 etc. fonctionnent mieux. On imprime
� chaque tour de jeu qui joue et combien d'�tapes jusqu'� la fin du jeu, donc le joueur n'aura
pas des probl�mes de m�moire.

Finalement, dans SimilarityUtil, la propension d'utiliser trois diff�rentes classes est de
diviser le probl�me de trouver les k-r�ponses facilement. Le premier, findAllSimilarities(),
trouvent toutes les similarit�s, findMeanOfSimilarities() trouvent la moyenne des trois
indices, et findClosestSimilarities() trouve les k-r�ponses les plus proches. Il faut
pr�ciser que l'utilisation d'un TreeMap dans findClosestSimilarities() �tait un choix
important, parce qu'un TreeMap peuvent ordonner les entiers facilement.

En plus, on utilise (Hash/Tree)Map<Double, String> quelque fois dans SimilarityUtil
quand on veut ordonner la moyenne des vecteurs, et (Hash/Tree)Map<String, Double> quand
on veut chercher les mots : il est beaucoup plus facile de chercher un mot avec
String/le mot comme cl� plut�t que faire une fonction de chercher dans les valeurs :
l'inverse est vrai avec les vecteurs comme cl� et non pas le mot.

** FIN **

Merci d'avoir lu ce ReadMe!