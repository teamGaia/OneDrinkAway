package com.onedrinkaway.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.db.DrinkDb;
import com.onedrinkaway.model.machinelearning.KNearestNeighborModel;
import com.onedrinkaway.model.machinelearning.MLModel;

public class DatabaseInterface {
  private static MLModel machineLearner = new KNearestNeighborModel();
  
  /**
   * @return all of the drinks in the database
   *
   */
  public static Drink[] getAllDrinks(){
    Set<Drink> drinks = DrinkDb.getAllDrinks();
    Drink[] result = new Drink[drinks.size()];
    int i = 0;
    for (Drink d : drinks) {
      result[i] = d;
      i++;
    }
    Arrays.sort(result);
    return result;
  }
  
  /**
   * @return String array containing names of each drink in the database.
   */
  public static String[] getDrinkNames() {
	  Set<String> names = DrinkDb.getDrinkNames();
      String[] result = new String[names.size()];
      int i = 0;
      for (String s : names) {
          result[i] = s;
          i++;
      }
      Arrays.sort(result);
      return result;
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
  public static Drink[] getTrySomethingNewDrinks(){
    List<Drink> allDrinks = new ArrayList<Drink>(DrinkDb.getAllDrinks());
    List<Drink> ratedDrinks = new ArrayList<Drink>(DrinkDb.getRatedDrinks());
    List<Drink> unratedDrinks = getUnratedDrinks(allDrinks, ratedDrinks);
    
    return getAllDrinks();
  }
  
  /**
   * @param query: the query to filter the result drinks by
   * @return a list of drinks unrated by the user, 
   *         sorted by the predicted rating (highest->lowest)
   */
  public static Drink[] getDrinks(Query query){
    List<Drink> drinks = new ArrayList<Drink>(DrinkDb.getAllDrinks());
    Iterator<Drink> iter;
    if (query.hasCategory()) {  // iterate and filter by category
      iter = drinks.iterator();
      while (iter.hasNext()) {
        Drink d = iter.next();
        if (!d.categories.contains(query.getCategory()))
          iter.remove();
      }
    }
    if (query.hasIngredients()) { // iterate and filter by ingredients
      iter = drinks.iterator();
      while (iter.hasNext()) {
        Drink d = iter.next();
        DrinkInfo di = DrinkDb.getDrinkInfo(d);
        List<String> drinkIngr = di.ingredients;
        List<String> queryIngr = query.getIngredients();
        if (!drinkIngr.containsAll(queryIngr))
          iter.remove();
      }
    }
    if (query.hasName()) { // iterate and filter by name
      iter = drinks.iterator();
      while (iter.hasNext()) {
        Drink d = iter.next();
        if (!d.name.contains(query.getName()))
          iter.remove();
      }
    }
    if (query.hasFlavors()) {
      // Machine Learning time
    }
    // more code here
    
    
    // finally put all drinks in an array and sort
    Drink[] result = new Drink[drinks.size()];
    int i = 0;
    for (Drink d : drinks) {
      result[i] = d;
      i++;
    }
    Arrays.sort(result); // change this maybe
    return result;
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
   * Returns the Drink corresponding to the given name
   */
  public static Drink getDrink(String name) {
      return DrinkDb.getDrink(name);
  }
  
  /**
   * @return a list of Drinks that the user has favorites
   */
  public static Drink[] getFavorites(){
    Set<Drink> favs = DrinkDb.getFavorites();
    Drink[] result = new Drink[favs.size()];
    int i = 0;
    for (Drink d : favs) {
      result[i] = d;
      i++;
    }
    Arrays.sort(result);
    return result;
  }
  
  /**
   * @return a list of category names
   */
  public static String[] getCategories(){
    Set<String> cat = DrinkDb.getCategories();
    String[] result = new String[cat.size()];
    int i = 0;
    for (String s : cat) {
      result[i] = s;
      i++;
    }
    Arrays.sort(result);
    return result;
  }
  
  /**
   * @return a list of possible ingredients
   */
  public static String[] getIngredients(){
    Set<String> ingr = DrinkDb.getIngredients();
    String[] result = new String[ingr.size()];
    int i = 0;
    for (String s : ingr) {
      result[i] = s;
      i++;
    }
    Arrays.sort(result);
    return result;
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
  
  /**
   * Finds DrinkInfo for a given drink
   * @param d the Drink to search for
   * @return the DrinkInfo for d
   */
  public static DrinkInfo getDrinkInfo(Drink d) {
      return DrinkDb.getDrinkInfo(d);
  }
  
}

