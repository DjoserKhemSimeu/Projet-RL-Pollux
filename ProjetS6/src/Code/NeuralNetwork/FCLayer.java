package Code.NeuralNetwork;

import java.util.Random;

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
		weights=new Matrice(outputSize,inputSize);
		bias= new Matrice(outputSize, 1);
		Random r=new Random();
		for(int i=0;i<inputSize;i++) {
			for(int j=0;j<outputSize;j++) {
				//Warning 0.5 peut donner des valeur négative peut pas fonct avec relu
				weights.setValue(j,i,r.nextGaussian()*2/inputSize);
				
			}
			
		}
		for(int f=0;f<outputSize;f++) {
			bias.setValue(f,0,r.nextGaussian()*(2/inputSize));
		}
	}
	
	// def de la propagation vers l'avant
	public Matrice forwardPropagation (Matrice input) {
		super.input= input;
		/*System.out.println("/////////////////////////////////////////////////////");
		System.out.println(input);
		System.out.println("#########################");
		System.out.println(weights);
		System.out.println("#########################");*/
		super.output=weights.multiply(input).sumMatrice(bias);
	

		/*System.out.println(output);
		System.out.println("/////////////////////////////////////////////////////");*/
		return output;
	}
	
	// def de la propagation vers l'arriere
	public Matrice backwardPropagation (Matrice outputError, double learningRate) {
		//System.out.println("poids"+'\n'+weights);
		//System.out.println("input"+'\n'+super.input);
		Matrice inputError= weights.getMatriceTranspose().multiply(outputError);
		Matrice weightsError= outputError.multiply(super.input.getMatriceTranspose());
		//System.out.println("poidserr"+'\n'+weightsError);
		weights= weights.soustraction(weightsError.multiplyByK(learningRate));
		bias= bias.soustraction(outputError.multiplyByK(learningRate));
		//System.out.println("error size : "+inputError.getRows()+"x"+inputError.getColumns());
		return inputError;
				
	}
	
	public static void main (String []args) {
		Random r=new Random();
		System.out.println(r.nextGaussian()*2/5);
	}
	
}