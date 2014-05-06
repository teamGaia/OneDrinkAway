package com.onedrinkaway.model;

import java.util.*;
import com.onedrinkaway.common.*;
import com.onedrinkaway.db.*;
import com.onedrinkaway.model.machinelearning.*;

public class DatabaseInterface {
  private static DrinkDB temp = new DrinkDB();
  private static MLModel machineLearner = new KNearestNeighborModel();
  
  public static List<Drink> getAllDrinks(){
    return temp.getAllDrinks();
  }
  
  public static List<Drink> getDrinks(Query query){
    List<Drink> drinks = temp.getDrinks(query);
    List<Drink> ratedDrinks = new ArrayList<Drink>(); // this will be changed to the new method
    for(int i = 0; i < drinks.length; i++){
      Drink d = drinks.get(i);
      if(ratedDrinks.contains(d)){
         drinks.remove(d);
         i--;
      }
    }
    
    machineLearner.train(ratedDrinks);
    
      
  }
  
  public static List<Drink> getFavorites(){
    return new ArrayList<Drink>();
  }
  
  public static List<Flavor> getFlavors(){
    return new ArrayList<Flavor>();
  }
  
  public static List<String> getCategories(){
    return new ArrayList<String>();
  }
  
  public static List<String> getIngredients(){
    return new ArrayList<String>();
  }
  
  public static void addFavorite(Drink favorite){
    
  }
  
  public static void removeFavorite(Drink oldFavorite){
  
  }
  
  public static List<Drink> getTrySomethingNewDrinks(){
    return new ArrayList<Drink>();
  }
}

