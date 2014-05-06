package com.onedrinkaway.model.machinelearning;

import java.util.*;
import com.onedrinkaway.common.*;

public class KNearestNeighborModel implements MLModel {
  private List<Drink> trainingSet;
  
  public void train(List<Drink> trainingSet) {
    this.trainingSet = trainingSet;
  }
  
  public double predictRating(Drink sample) {
    return 0.0
  }
}
