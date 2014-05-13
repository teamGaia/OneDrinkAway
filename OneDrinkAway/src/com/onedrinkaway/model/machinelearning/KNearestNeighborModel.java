package com.onedrinkaway.model.machinelearning;

import java.util.*;
import java.lang.Math;
import com.onedrinkaway.common.*;

public class KNearestNeighborModel implements MLModel {
  private List<Drink> trainingSet;
  
  public void train(List<Drink> trainingSet) {
    this.trainingSet = trainingSet;
  }
  
  public double predictRating(Drink sample) {
    double rating = 0.0;
    for(Drink d : trainingSet){
      double absDiff = calculateAbsoluteDifference(d, sample);
      double signDiff = calculateSignedDifference(d, sample);
      rating += d.rating * absDiff / 5;
    }
    return rating / trainingSet.size();
  }
  
  private double calculateAbsoluteDifference(Drink a, Drink b){
   double diff = 0.0;
   for(int i = 0; i < a.attributes.length; i++){
      diff += Math.pow(a.attributes[i] - b.attributes[i], 2);
   }
   return Math.sqrt(diff); 
  }
  
  private double calculateSignedDifference(Drink a, Drink b){
   double diff = 0.0;
   for(int i = 0; i < a.attributes.length; i++){
     diff += a.attributes[i] - b.attributes[i];
   }
   return diff;
  }
}
