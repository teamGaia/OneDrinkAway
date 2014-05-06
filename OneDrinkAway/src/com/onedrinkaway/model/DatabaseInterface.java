package com.onedrinkaway.model;

import java.util.*;
import com.onedrinkaway.common.*;
import com.onedrinkaway.db.*;
import com.onedrinkaway.model.machinelearning.*;

public class DatabaseInterface {
  private static DrinkDb temp = new DrinkDb();
  private static MLModel machineLearner = new KNearestNeighborModel();
  
  public static List<Drink> getAllDrinks(){
    return temp.getAllDrinks();
  }
  
  public static List<Drink> getDrinks(Query query){
    List<Drink> drinks = temp.getDrinks(query);
    List<Drink> ratedDrinks = new ArrayList<Drink>(); // this will be changed to the new method
    for(int i = 0; i < drinks.size(); i++){
      Drink d = drinks.get(i);
      if(ratedDrinks.contains(d)){
         drinks.remove(d);
         i--;
      }
    }
    
    machineLearner.train(ratedDrinks);
    Map<Double, List<Drink>> ratings = new TreeMap<Double, List<Drink>>();
    for(Drink d : drinks){
      double rating = machineLearner.predictRating(d);
      if (!ratings.containsKey(rating)){
         ratings.put(rating, new ArrayList<Drink>());
      }
      ratings.get(rating).add(d);
    }
    
    return null;
  }
  
  public static List<Drink> getFavorites(){
    return new ArrayList<Drink>();//temp.getFavorites();
  }
  
  public static List<String> getFlavors(){
    return temp.getFlavors();
  }
  
  public static List<String> getCategories(){
    return temp.getCategories();
  }
  
  public static List<String> getIngredients(){
    return temp.getIngredients();
  }
  
  public static void addFavorite(Drink favorite){
    temp.addFavorite(favorite);
  }
  
  public static void removeFavorite(Drink oldFavorite){
  
  }
  
  public static List<Drink> getTrySomethingNewDrinks(){
    return new ArrayList<Drink>();
  }
}

