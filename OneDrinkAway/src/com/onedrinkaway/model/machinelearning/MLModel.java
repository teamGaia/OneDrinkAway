/**
 * Interface for machine learning algorithms to be used for 
 * classfying drinks. This interface allows machine learning 
 * algorithms to be easily swapped out for different algorithms 
 * as long as the new algorithm follows this interface.
 */

package com.onedrinkaway.model.machinelearning;

import java.util.List;

import com.onedrinkaway.model.Drink;


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
