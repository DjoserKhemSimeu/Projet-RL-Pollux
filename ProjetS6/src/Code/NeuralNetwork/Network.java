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
	public Matrice predict(Matrice input) {
		for(Layer layer: layers) {
			input=layer.forwardPropagation(input);
		}
		return input;
	}

	// Fonction de perte MSE
	private static double loss(double cible, double pred) {
		return Math.pow(pred-cible,2);
	}

	// Dérivée de la fonction de perte
	private static double lossPrime(double cible, double pred) {
		return 2*(pred-cible)/6;
	}

	public void fit( ArrayList<Matrice>xTrain,ArrayList<Double> yTrain, int epochs, double learningRate) {
		int samples=xTrain.size();
		for(int i=0; i<epochs;i++) {
			double err=0;
			for(int j=0; j<samples;j++) {
				Matrice output=xTrain.get(j);
				System.out.println(output);
				for(Layer layer: layers) {

					output=layer.forwardPropagation(output);

				}
				System.out.println(output);
				double pred=output.argmax();
				double cible= yTrain.get(j);
				err+=loss(cible,pred);
				Matrice masque= new Matrice();
				masque.setLength(output.getRows(),output.getColumns());
				int iidx=0;
				double maxValue=Double.MIN_VALUE;
				for(int id=0;id<output.getColumns();id++) {
					if(maxValue<output.getValue(0,id)) {
						maxValue=output.getValue(0,id);
						iidx=id;
					}
					
				}
				masque.setValue(0,iidx,1.0);
				Matrice error=masque.multiplyByK(lossPrime(cible,pred));


				for(int idx=layers.size()-1;idx>=0;idx--) {
					if(layers.get(idx) instanceof ActivationLayer) {
						ActivationLayer l= (ActivationLayer)layers.get(idx);
						double d=output.getValue(0,0);
						error= l.backwardPropagation(d,learningRate);
					}else {
						error= layers.get(idx).backwardPropagation(error,learningRate);
					}
				}


			}
			err/=samples;
			System.out.println("epoch"+(i+1)+"/"+epochs+ "error="+ err);
		}
	}

}