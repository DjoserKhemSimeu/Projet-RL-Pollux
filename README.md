# Introduction
## Contexte
L'objectif principale de ce est de développer le projet [Pollux ](https://github.com/DjoserKhemSimeu/Pollux-Project/wiki) en implémentant un système d’apprentissage par renforcement dans le programme. Afin d’accélérer l'apprentissage, un environnement d'apprentissage doit être conçu et intégrer au programme.

## Index

# Etat de l'art

Dans le contexte de l'apprentissage par renforcement, les travaux Yann Bouteiller Edouard Gèze avec le [MISTlab ](https://mistlab.ca/), sur un système d'apprentissage par renforcement optimiser pour le jeux [TrackMania ] (https://github.com/trackmania-rl/tmrl) m'ont fortement inspiréer dans le developpement du projet.

Les algorithmes d'apprentissage par renforcement s'appuyent sur des termes clef : etats, action, recompense, politique, transition, agent, environnement.

![image](https://github.com/trackmania-rl/tmrl/raw/master/readme/img/mrp.png)
Dans l'image ci-dessus, l'agent est représenter par le bonhomme bâton, cet agent evolue dans un environnement. L'agent est capable (dans notre cas) d'observer partiellement son environnement, une observation représente un ètat de l'environnement. Il est possible pour l'agent d'agir sur son environnement  via un panel d'action. l'agent à un objectif dans un environnement, il doit atteindre un un but précis, l'environnement vas attribuer une récompense (positive ou négative) en fonction d'un état observer et action éfectuée, afin de valoriser les actions rapprochant l'agents de son objectif.
une transition est représenter par 5 valeurs: un état initial, une action éffectuer, un état resultat de l'action effectuer ainsi d'une récompense obtenue.

Les travaux fait sur TrackMania cité précédement m'ont orienté sur l'utilisation d'une forme d'apprentissage par renforcement nommé le deep Q Learning


## Le Q Learning

Le Q learning est une forme d'apprentissage par renforcement qui s'appuie  sur un fonction Q : 

![image](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/Capture%20d'%C3%A9cran%20du%202023-02-23%2015.47.23.png)

La fonction Q détermine en fonction d'un état et d'une action le maximum de récompense que l'agent peut espérer obtenir à la suite de l'action a dans l'état s. L'apprentissage d'une fonction Q se fait via la recolte de récompense par l'exploitation ou l'exploration de son environnement. Si vous souhaitez avoir plus d'information au sujet du Q Learning allez voir l'article d'[OpenAI ](https://spinningup.openai.com/en/latest/spinningup/rl_intro.html)  qui traite de ce sujet.

## Le deep Q Learning

Le deep Q Learning est de forme de Q Learning qui utlise un réseau de neurones en guise de fonction Q (DQN: Deep Q Network). Le DQN prend en entrée un etat, en sortie, nous allons obtenir pour chaque action le maximum de récompense que l'agent peut esperer obtenir.

![image](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/DQN.png)

L'apprentissage d'un DQN se fait comme cela :
- L'algorithme récupère un lots de transition (s, a, r, s') via l'exploration et l'exploitation
- le DQN produit une prediction pour chaque transition avec en input l'etat initiale s 
- nous deffinisons les cibles de chaque transition par la formule de la fonction Q
- les paramètre du reseau sont corriger par retropropagtion de l'erreur via la methode de descente du gradient. 

pour plus d'information, je vous renvoi à l'article d'[OpenAI ](https://spinningup.openai.com/en/latest/algorithms/ddpg.html#pseudocode) 

## Le DQN

Afin de construire le DQN, je me suis appuyer sur les travaux de Omar Aflak sur la [programation d'un reseau de neurone en python ](https://medium.com/france-school-of-ai/math%C3%A9matiques-des-r%C3%A9seaux-de-neurones-code-python-613d8e83541).

Etant donné que java est un language orienté objet, le reseau de neurone est définis comme un agencement d'objet de plusieur classes:
- la class [Layer ](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/ProjetS6/src/Code/NeuralNetwork/Layer.java)est une classe abstraite definisant une couche du réseau de neurone, elle possède en attribut un input et un output. Les methode applicable sur cette classe sont la forwardPropagation et la backwardPropagation
- la classe [FCLayer ](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/ProjetS6/src/Code/NeuralNetwork/FCLayer.java)hérite de la classe Layer est définis une couche du réseau de neurones appliquant la somme pondéré par les poids des input pour obtenir l'output, elle possède en attribut une matrice de poids de dimension input size * output size  ainis qu'une matrice de biais de taille output size*1.
- la classe [ActivationLayer ](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/ProjetS6/src/Code/NeuralNetwork/ActivationLayer.java)hérite de la classe Layer, elle definis une couche d'activation du réseau de neurone, elle possède en attributs une fonction d'activation.
- la classe [Network ](https://github.com/DjoserKhemSimeu/Projet-RL-Pollux/blob/main/ProjetS6/src/Code/NeuralNetwork/Network.java) définis le réseau en lui meme, elole possède en attribut une collection de Layers représentant les couches successive du réseau de neurone. La methode fit prend en parametre un jeu de donnée d'entrée et les ettiquettes correspondante, et permet d'entrainer récursivement le réseau  via la methode de rétropopagation de chaque couche.
