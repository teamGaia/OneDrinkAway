/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.model.machinelearning;

import java.util.List;

import com.onedrinkaway.common.Drink;


public interface MLModel {
	/**
	 * @param trainingSet: List of training examples to create the model
	 **/ 
	public void train(List<Drink> trainingSet);
	
	/**
	 * @param sample: the Drink to predict the rating
	 * @return the predicted rating (0.0-5.0)
	 **/
	public double predictRating(Drink sample);
}
