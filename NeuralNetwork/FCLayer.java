package Code.NeuralNetwork;

import Code.Util.Matrice;
/*
 * Définition de la class FCLayer représentant les couches de neurones
 * ayant des poids et des biais
 */
public class FCLayer extends Layer{
	
	//Attributs d'instance
	
	// utilisation de la classe Matrice pour les poids et les biais
	private Matrice weights;
	private Matrice bias;
	

	
	/*
	 * Constructeur de la classe qui prend en param un nb d'input et d'output
	 * pour intialiser les matrices en fonction avec des valeurs aléatoire entre
	 * -0.5 et 0.5
	 */
	public FCLayer(int inputSize, int outputSize ) {
		weights=new Matrice(inputSize,outputSize);
		bias= new Matrice(1, outputSize);
		for(int i=0;i<outputSize;i++) {
			for(int j=0;j<inputSize;j++) {
				//Warning 0.5 peut donner des valeur négative peut pas fonct avec relu
				weights.setValue(j,i,Math.random()-0.5);
				
			}
			bias.setValue(0,i,Math.random()-0.5);
		}
	}
	
	// def de la propagation vers l'avant
	public Matrice forwardPropagation (Matrice input) {
		this.input= input;
		this.output=weights.multiply(input).sumMatrice(bias);
		return output;
	}
	
	// def de la propagation vers l'arriere
	public Matrice backwardPropagation (Matrice outputError, double learningRate) {
		Matrice inputError= outputError.multiply(weights.getMatriceTranspose());
		Matrice weightsError= input.getMatriceTranspose().multiply(outputError);
		weights= weights.soustraction(weightsError.multiplyByK(learningRate));
		bias= bias.soustraction(outputError.multiplyByK(learningRate));
		return inputError;
				
	}

}
