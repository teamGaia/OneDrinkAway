package com.onedrinkaway.model;

import java.util.*;

public class DatabaseInterface {
  public static List<Drink> getAllDrinks(){
    return new ArrayList<Drink>();  
  }
  
  public static List<Drink> getDrinks(Query query){
    return new ArrayList<Drink>();
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
  
  public static List<String>> getIngredients(){
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

