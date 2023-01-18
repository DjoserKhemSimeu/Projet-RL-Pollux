package Code.NeuralNetwork;

import java.util.ArrayList;
import java.util.function.DoubleFunction;

import Code.Util.Matrice;

/*
 * Class network qui représente les réseau de neurones dans sa globalité
 */
public class Network {
	
	
	//Attributs d'instance
	
	//Liste des couches de neurones
	private ArrayList<Layer>layers;
	
	
	//Fonction de perte et sa derive
	private DoubleFunction loss;
	private DoubleFunction lossPrime;
	
	
	

	public Network() {
		layers=new ArrayList<>();
	}
	
	//methode add qui ajoute une couche l au réseau
	public void add(Layer l) {
		layers.add(l);
	}
	
	//Methode use qui initialise la fonction de perte et sa dérivée
	public void use (DoubleFunction loss,DoubleFunction lossPrime) {
		this.loss=loss;
		this.lossPrime=lossPrime;
	}
	
	//Methode de prediction
	public ArrayList<Matrice> predict (ArrayList<Matrice>inputData){
		int samples=inputData.size();
		ArrayList<Matrice> result=new ArrayList<>();
		for(int i=0;i<samples;i++) {
			Matrice output=inputData.get(i);
			for(Layer layer: layers) {
				output=layer.forwardPropagation(output);
			}
			result.add(output);
		}
		return result;
	}
	
	// Fonction de perte MSE
	private static double loss(double cible, double pred) {
		return Math.pow(pred-cible,2);
	}
	
	// Dérivée de la fonction de perte
	private static double lossPrime(double cible, double pred) {
		return 2*(pred-cible);
	}
	
	public void fit( ArrayList<Matrice>xTrain,ArrayList<Matrice> yTrain, int epochs, double learningRate) {
		int samples=xTrain.size();
		for(int i=0; i<epochs;i++) {
			double err=0;
			for(int j=0; j<samples;j++) {
				Matrice output=xTrain.get(j);
				for(Layer layer: layers) {
					output=layer.forwardPropagation(output);
				}
				double pred=output.argmax();
				double cible= yTrain.get(j).getValue(yTrain.get(j).getValue(pred)[0],yTrain.get(j).getValue(pred)[1]);
				err+=loss(cible,pred);
				Matrice masque= new Matrice(new double[][] {{0,0,0}});
				masque.setValue(yTrain.get(j).getValue(pred)[0],yTrain.get(j).getValue(pred)[1],1);
				Matrice error=masque.multiplyByK(lossPrime(cible,pred));
				
				
				for(int idx=layers.size()-1;idx>=0;idx--) {
					error= layers.get(idx).backwardPropagation(error,learningRate);
				}
				
				
			}
			err/=samples;
			System.out.println("epoch"+(i+1)+"/"+epochs+ "error="+ err);
		}
	}

}
