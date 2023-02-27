package Code.Util;

import java.util.function.DoubleFunction;

/*
 * Class ActivationFunction qui réference l'ensemble des fonction d'activation possible
 * et utilisable sur le reseau de neurones
 */

public class ActivationFunction {
	
	public static DoubleFunction<Double> NORM = x ->  x;
	

	public static DoubleFunction<Double> SIGMOID = x -> 1 / (1 + Math.exp(-x));
	public static DoubleFunction<Double> SIGMOIDDER = x -> {
	    double s = SIGMOID.apply(x);
	    return s * (1 - s);
	};

	public static DoubleFunction<Double> TANH = Math::tanh;
	public static DoubleFunction<Double> TANHDER = x -> 1. - Math.pow(TANH.apply(x), 2);

	public static DoubleFunction<Double> RELU = x -> Math.max(0, x);
	public static DoubleFunction<Double> RELUDER = x -> {
	    if (x < 0) {
	        return 0.0;
	    }
	    return 1.0;
	};

	public static DoubleFunction<Double> SOFTPLUS = x -> Math.log(1 + Math.exp(x));

}
