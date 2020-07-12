READ_ME de JEU DE GEENSON
de Luke Byrne, Lilian Morgan et Heather Lloyd
Mars 2019 - mai 2019

** LIGNE DE COMMANDE **
On a appelé notre Main comme Game, donc alors il faut :
	java Game --w2v ../w2v_final3 --nbJoueurs 2 --nbTry 3 --k 5 --deMagique
mais évidemment sans les parties avec --, donc :
	java Game ../w2v_final3 2 3 5 deMagique

Quand même, les prochaines descriptions des variables de la ligne de commande sont
prises de la documentation du projet :

	- "w2v: indique le chemin vers le fichier de vecteurs de mots"
	- "nbJoueurs: indique le nombre de joueurs"
	- "nbTry: indique le nombre d'essais pour chaque tour"
	- "k: le nombre de k-réponses à retourner par le système"
	- "deMagique: le jeu fonctionnera avec un dé magique"[1]

[1] On précise qu'il faut, afin de faire fonctionner le dé magique, écrire le mot
"deMagique" (y compris le M au majuscule), sinon il va être un dé normal. Il est
également un dé normal au cas standard ; c'est à dire que si on n'écrit rien, comme :
	java Game ../w2v_final3 2 3 5
alors le dé est ainsi normal.

** SYNTAXE DES REQUÊTES **
Pendant les tours de jeu, le jeu prend en entrée des indices que vous devinez : ce sont
des String. À part ces String, il n'y a pas d'autres syntaxe à se souvenir.

** ARCHITECTURE DU PROGRAMME **
Ce jeu/programme comprend 7 classes dont les descriptions se trouvent dans le javadoc.
D'abord, on a une classe Vector qui désigne la base du VectorList, notre structure de
données qui contiennent les données du fichier. Le Vector comprend le mot, ses vecteurs
et la dimension (le nombre des vecteurs). 

Le VectorList est une liste de ces Vector.

La classe VectorUtil n'est pas beaucoup utilisé hormis l'utilisation de "similarity", qui
trouve le cosinus des vecteurs (indispensable à ce jeu). Il contient toutefois les
fonctions que le projet demande : addition, soustraction, norm etc.

De plus, Player est évidemment le joueur, et Board le plateau du jeu et leurs méthodes
associés. Le Board est hardcodé d'avoir une taille de 5 x 5.

Game contient le jeu (c'est-à-dire le main). Le fonctionnement général de ce jeu est
de créer un tableau des Player, instantier le Board, et ensuite faire des tours de jeu
avec les joueurs jusqu'à ce que quelqu'un gagne. Le programme vous fournit un mot
aléatoire, et vous avez des essais (spécifies dans l'entrée) de deviner trois mots
dont leurs similarités donnent le mot aléatoire. Il y a des cases spéciales (reculer par
trois étapes, rouler le dé encore etc.).

** CHOIX D'IMPLÉMENTATION **
On a choisi de créés des classes Vector et VectorList pour qu'on puisse utiliser
facilement la liste des vecteurs -- ils ont prouvé d'être tellement vite.

On a décidé d'utiliser un HashMap pour représenter la liste des Vector. Il nous a donné
la vitesse de recherche qu'on utilise très fréquemment pendant la programme,
ainsi qu'une manière de stocker le tableau des vecteurs à côté du mot.

La taille du Board a été hardcodé parce que le jeu est (très, très...) difficile à gagner,
donc un plateau de jeu immense sera impossible. Le choix de mettre des indices sur
les tuiles du plateau, et de ne pas, par exemple, utiliser une classe Tile, était
un choix d'efficacité et après avoir supprimé une classe Tile du jeu, on a vu une grande
augmentation de vitesse du programme.

La création des petites méthodes dans Game, comme initialise() et equalsRandomWord() était
un choix d'aider la lisibilité du programme, et non pas d'efficacité.

En ce qui concerne le Player, on n'a pas donné l'option de leur donné un nom ou d'autres
détails parce qu'on n'en a pas besoin - Player 1, 2 etc. fonctionnent mieux. On imprime
à chaque tour de jeu qui joue et combien d'étapes jusqu'à la fin du jeu, donc le joueur n'aura
pas des problèmes de mémoire.

Finalement, dans SimilarityUtil, la propension d'utiliser trois différentes classes est de
diviser le problème de trouver les k-réponses facilement. Le premier, findAllSimilarities(),
trouvent toutes les similarités, findMeanOfSimilarities() trouvent la moyenne des trois
indices, et findClosestSimilarities() trouve les k-réponses les plus proches. Il faut
préciser que l'utilisation d'un TreeMap dans findClosestSimilarities() était un choix
important, parce qu'un TreeMap peuvent ordonner les entiers facilement.

En plus, on utilise (Hash/Tree)Map<Double, String> quelque fois dans SimilarityUtil
quand on veut ordonner la moyenne des vecteurs, et (Hash/Tree)Map<String, Double> quand
on veut chercher les mots : il est beaucoup plus facile de chercher un mot avec
String/le mot comme clé plutôt que faire une fonction de chercher dans les valeurs :
l'inverse est vrai avec les vecteurs comme clé et non pas le mot.

** FIN **

Merci d'avoir lu ce ReadMe!