/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.provider.Settings.Secure;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Query;

/**
 * Stores and manages data
 * 
 * @author John L. Wilson
 */

public class DrinkDb {
	
	public static final String ID = Secure.ANDROID_ID;
	
	private static DrinkData dd = new DrinkData();
    
    /**
     * @return a list contain all ingredients as Strings
     */
    public static List<String> getIngredients() {
        List<String> result = new ArrayList<String>();
        for (String s : dd.getIngredients())
            result.add(s);
        return result;
    }
    
    /**
     * @return a list of all Drinks
     */
    public static List<Drink> getAllDrinks() {
        return new ArrayList<Drink>(dd.getAllDrinks());
    }
    
    /**
     * @return a list of all Categories
     */
    public static List<String> getCategories() {
        return new ArrayList<String>(dd.getCategories());
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
     * Adds a drink to the user's favorites list
     * 
     * @param drink the drink to be added
     */
    public static void addFavorite(Drink drink, int score) {
        dd.addFavorite(drink);
        drink.addUserRating(score);
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
    
    /**
     * Gets all drinks rated by user
     * @return a List of all drinks rated by user
     */
    public static List<Drink> getRatedDrinks() {
    	
    	return null;
    }
    
    /**
     * Gets all drinks on users favorites list
     * @return a List of all drinks in users favorites list
     */
    public static List<Drink> getFavorites() {
    	
    	return null;
    }
    
    /**
     * Removes a drink from the user's favorites list
     * 
     * @param drink the drink to be removed
     */
    public static void removeFavorite(Drink drink) {
        
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