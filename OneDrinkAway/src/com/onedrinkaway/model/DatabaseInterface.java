package com.onedrinkaway.model;

import java.util.*;
import com.onedrinkaway.common.*;
import com.onedrinkaway.db.*;
import com.onedrinkaway.model.machinelearning.*;

public class DatabaseInterface {
  private static MLModel machineLearner = new KNearestNeighborModel();
  
  /**
   * @return all of the drinks in the database
   *
   */
  public static List<Drink> getAllDrinks(){
    return DrinkDb.getAllDrinks();
  }
  
  /**
   * @param d: the drink to be rated
   * @param rating: the rating that the user choose
   */
  public static void addRating(Drink d, int rating){
	  DrinkDb.addRating(d, rating);
  }
  
  /**
   * @return a list of drinks that the user has not rated, 
   *         sorted by the predicted rating (highest->lowest)
   */
  public static List<Drink> getTrySomethingNewDrinks(){
    List<Drink> allDrinks = DrinkDb.getAllDrinks();
    List<Drink> ratedDrinks = DrinkDb.getRatedDrinks();
    List<Drink> unratedDrinks = getUnratedDrinks(allDrinks, ratedDrinks);
    
    return predictRatings(unratedDrinks, ratedDrinks);
  }
  
  /**
   * @param query: the query to filter the result drinks by
   * @return a list of drinks unrated by the user, 
   *         sorted by the predicted rating (highest->lowest)
   */
  public static List<Drink> getDrinks(Query query){
    List<Drink> filteredDrinks = DrinkDb.getDrinks(query);
    List<Drink> ratedDrinks = DrinkDb.getRatedDrinks(); // this will be changed to the new method
    List<Drink> unratedDrinks = getUnratedDrinks(filteredDrinks, ratedDrinks);
    
    
    return predictRatings(unratedDrinks, ratedDrinks);
  }
  
  private static List<Drink> getUnratedDrinks(List<Drink> allDrinks, List<Drink> ratedDrinks){
    List<Drink> unratedDrinks = new ArrayList<Drink>();
    for(int i = 0; i < allDrinks.size(); i++){
      Drink d = allDrinks.get(i);
      if(!ratedDrinks.contains(d)){
         unratedDrinks.add(d);
      }
    }
    return unratedDrinks;
  }
  
  private static List<Drink> predictRatings(List<Drink> unratedDrinks, List<Drink> ratedDrinks){
  
    machineLearner.train(ratedDrinks);
    Map<Double, List<Drink>> ratings = new TreeMap<Double, List<Drink>>();
    for(Drink d : unratedDrinks){
      double rating = machineLearner.predictRating(d);
      if (!ratings.containsKey(rating)){
         ratings.put(rating, new ArrayList<Drink>());
      }
      ratings.get(rating).add(d);
    }
    
    return new ArrayList<Drink>();
  }
  
  /**
   * @return a list of Drinks that the user has favorites
   */
  public static List<Drink> getFavorites(){
    return DrinkDb.getFavorites();
  }
  
  /**
   * @return a list of category names
   */
  public static List<String> getCategories(){
    return DrinkDb.getCategories();
  }
  
  /**
   * @return a list of possible ingredients
   */
  public static List<String> getIngredients(){
    return DrinkDb.getIngredients();
  }
  
  /**
   * Adds the passed Drink to the user's favorites list
   * @param favorite the Drink that the user favorited
   */
  public static void addFavorite(Drink favorite){
    DrinkDb.addFavorite(favorite);
  }
  
  /**
   * Removes the passed Drink from the user's favorites list
   * @param oldFavorite the Drink that the user 
   *              no longer considers a favorite
   */
  public static void removeFavorite(Drink oldFavorite){
    DrinkDb.removeFavorite(oldFavorite);
  }
}

