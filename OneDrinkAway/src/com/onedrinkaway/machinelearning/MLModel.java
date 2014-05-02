/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.machinelearning;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface MLModel {
	/**
	 * @param trainingSet: List of training examples to create the model
	 **/ 
	// I changed List<Drink> to List<Instance>
	public void train(List<Instance> trainingSet);
	
	/**
	 * @param sample: the Drink to predict the rating
	 * @return the predicted rating (0.0-5.0)
	 **/
	public double predictRating(Drink sample);

	// I added this method declaration in order to remove errors from RunML
	public Map<String, Double> predict(Instance curIns) throws IOException;
}
