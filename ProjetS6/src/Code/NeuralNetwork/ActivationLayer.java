package Code.NeuralNetwork;

import java.util.function.DoubleFunction;

import Code.Util.Matrice;

/*
 * Class définissant les couches d'activation
 */
public class ActivationLayer extends Layer{

	public DoubleFunction<Double> activation;
	public DoubleFunction<Double> activationPrime;
	
	
	public ActivationLayer(DoubleFunction<Double> activation, DoubleFunction<Double> activationPrime) {
		this.activation=activation;
		this.activationPrime=activationPrime;
		
		
	}
	public static final Matrice activation(DoubleFunction<Double> activation, Matrice output) {
		Matrice res= new Matrice (output.getMatrice());
		for(int i=0; i<res.getRows();i++) {
			for(int j=0;j<res.getColumns();j++) {
				res.setValue(i,j,activation.apply(res.getValue(i,j)));
			}
		}
		return res;

	}
	
	public Matrice forwardPropagation(Matrice input) {
		this.input=input;
		this.output=activation(activation,input);
		return output;
	}
	public Matrice backwardPropagation(double outputError,double learningRate) {
		return activation(activationPrime,input).multiplyByK(outputError);
		//Warning: les code utilis * peut etre pas une matrice
		
	}
	@Override
	public Matrice backwardPropagation(Matrice outputError, double learningRate) {
		// TODO Auto-generated method stub
		/*System.out.println("input size : "+input.getRows()+"x"+input.getColumns());
		System.out.println("error size : "+outputError.getRows()+"x"+outputError.getColumns());*/
		return activation(activationPrime,input).multSca(outputError);
	}

}