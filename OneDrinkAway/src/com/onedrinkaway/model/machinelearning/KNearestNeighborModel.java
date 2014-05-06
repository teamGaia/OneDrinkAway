package com.onedrinkaway.machinelearning;


public class KNearestNeighborModel implements MLModel {
  private List<Drink> trainingSet;
  
  public void train(List<Drink> trainingSet) {
    this.trainingSet = trainingSet;
  }
  
  public double predictRating(Drink sample) {
    return 0.0
  }
}
