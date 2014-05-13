/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.db;

import java.util.ArrayList;
import java.util.List; 

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Query;

/**
 * Stores and manages data
 * 
 * @author John L. Wilson
 */

public class DrinkDb {
    
    /**
     * @return a list of all Ingredients
     */
    public static List<String> getIngredients() {

        return null;
    }
    
    /**
     * @return a list of all Drinks
     */
    public static List<Drink> getAllDrinks() {

        return null;
    }
    
    /**
     * @return a list of all Categories
     */
    public static List<String> getCategories() {
        
        return null;
    }
    
    /**
     * @return a list of all flavors
     */
    public static List<String> getFlavors() {
        
        return null;
    }
    
    /**
     * Searches for Drinks that match the given query
     * 
     * @return a list drinks that match the given Query
     */
    public static List<Drink> getDrinks(Query q) {
        
        return null;
    }
    
    /**
     * Adds a drink rating
     * 
     * @param drink the drink to be added
     * @param score the rating for the drink, must be [1-5] inclusive
     * @throws: IllegalArgumentException if the score is smaller than 1 or bigger than 5
     */
    public static void addRating(Drink drink, int score) {
        if (score < 1 || score > 5)
            throw new IllegalArgumentException("score must be [1-5] inclusive");
    }
    
    public static List<Drink> getRatedDrinks() {
        return new ArrayList<Drink>();
    }
    
    /**
     * Adds a drink to the user's favorites list
     * 
     * @param drink the drink to be added
     */
    public static void addFavorite(Drink drink) {
        
    }
    
    /**
     * Removes a drink from the user's favorites list
     * 
     * @param drink the drink to be removed
     */
    public static void removeFavorite(Drink drink) {
        
    }
    
    public static List<Drink> getFavorites() {
        return new ArrayList<Drink>();
    }
    
    /**
     * Gets a list of ingredients for the given drink
     * 
     * @param drink the drink to find the ingredients of
     */
    public static List<String> getIngredients(Drink drink) {
        List<String> result = new ArrayList<String>();
        
        return result;
    }
}
