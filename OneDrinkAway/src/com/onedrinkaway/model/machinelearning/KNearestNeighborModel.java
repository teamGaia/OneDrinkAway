/**
 * This class uses the K-Nearest Neighbor algorithm to  
 * determine the predicted rating for an arbitrary Drink 
 * given a list of Drinks that the user has rated
 * 
 * @author James Luey
 **/

package com.onedrinkaway.model.machinelearning;

import java.util.*;
import java.lang.Math;
import com.onedrinkaway.common.*;

public class KNearestNeighborModel implements MLModel {
  private List<Drink> trainingSet;
  
  /**
   * @param trainingSet: list of drinks that the user has rated
   **/
  public void train(List<Drink> trainingSet) {
    this.trainingSet = trainingSet;
  }
  
  /**
   * @param sample: the Drink to predict the rating based 
   * off of the drinks passed in during the train method
   * 
   * @return the predicted rating
   **/ 
  public double predictRating(Drink sample) {
    double rating = 0.0;
    for(Drink d : trainingSet){
      double absDiff = calculateAbsoluteDifference(d, sample);
      double signDiff = calculateSignedDifference(d, sample);
      rating += d.rating * absDiff / 5;
    }
    return rating / trainingSet.size();
  }
  
  /**
   * Calcualtes the unsigned distance between the two drinks' attributes'
   * @param a: the first drink to compare
   * @param b: the swcond drink to compare
   * @retrun the unsigned distance between the two drinks
   **/
  private double calculateAbsoluteDifference(Drink a, Drink b){
   double diff = 0.0;
   for(int i = 0; i < a.attributes.length; i++){
      diff += Math.pow(a.attributes[i] - b.attributes[i], 2);
   }
   return Math.sqrt(diff); 
  }
  
  /**
   * Calcualtes the signed distance between the two drinks' attributes'
   * @param a: the first drink to compare
   * @param b: the swcond drink to compare
   * @retrun the signed distance between the two drinks
   **/
  private double calculateSignedDifference(Drink a, Drink b){
   double diff = 0.0;
   for(int i = 0; i < a.attributes.length; i++){
     diff += a.attributes[i] - b.attributes[i];
   }
   return diff;
  }
}
