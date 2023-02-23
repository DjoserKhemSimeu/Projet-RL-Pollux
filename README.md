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
