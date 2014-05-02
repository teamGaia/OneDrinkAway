/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.db;

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
    public List<String> getIngredients() {

        return null;
    }
    
    /**
     * @return a list of all Drinks
     */
    public List<Drink> getAllDrinks() {

        return null;
    }
    
    /**
     * @return a list of all Categories
     */
    public List<String> getCategories() {
        
        return null;
    }
    
    /**
     * @return a list of all flavors
     */
    public List<String> getFlavors() {
        
        return null;
    }
    
    /**
     * Searches for Drinks that match the given query
     * 
     * @return a list drinks that match the given Query
     */
    public List<Drink> getDrinks(Query q) {
        
        return null;
    }
    
    /**
     * Adds a drink to the user's favorites list
     * 
     * @param drink the drink to be added
     */
    public void addFavorite(Drink drink) {
        
    }
    
    /**
     * Adds a drink rating
     * 
     * @param drink the drink to be added
     * @param score the rating for the drink, must be [1-5] inclusive
     */
    public void addRating(Drink drink, int score) {
        if (score < 1 || score > 5)
            throw new IllegalArgumentException("score must be [1-5] inclusive");
    }
    
    /**
     * Removes a drink from the user's favorites list
     * 
     * @param drink the drink to be removed
     */
    public void removeFavorite(Drink drink) {
        
    }
    
    
}
