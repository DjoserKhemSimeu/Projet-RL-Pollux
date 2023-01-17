package Code.NeuralNetwork;

import Code.Util.Matrice;

/*
 * Définition de la class Layer représentant une couche de neurone,
 * elle est laissé abstraite car il faut différencier les traitement 
 * d'une couche d'activation ou d'une couche de poids et biais
 */
public abstract class Layer {
	
	//Attributs d'instances
	protected Matrice input;
	protected Matrice output;

	public Layer() {
		// TODO Auto-generated constructor stub
	}
	
	//Définiton de la methode de propagation vers l'avant pour la production d'une prediction
	public abstract Matrice forwardPropagation(Matrice output);
	
	//Définition de la methode de propagation vers l'arriere pour réajuster les valeur des pois et des biais
	public abstract Matrice backwardPropagation(Matrice ouputError, double learningRate);

}
