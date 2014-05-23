/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.db;

import java.util.Set;

import android.provider.Settings.Secure;

import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

/**
 * Stores and manages data
 * 
 * @author John L. Wilson
 */

public class DrinkDb {
    
    public static final String ID = Secure.ANDROID_ID;
    private static DrinkData dd = null;
    // true if dd has been loaded, false if not
    private static boolean open;
    
    public static void open() {
        // Run in non-main thread, allows asynchronous access
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        dd = DrinkData.getDrinkData();
        open = true;
    }
    
    /**
     * Returns the Drink corresponding to the given name
     */
    public static Drink getDrink(String name) {
        if (!open)
            open();
        return dd.getDrink(name);
    }
    
    /**
     * Finds DrinkInfo for a given drink
     * @param d the Drink to search for
     * @return the DrinkInfo for d
     */
    public static DrinkInfo getDrinkInfo(Drink d) {
        if (!open)
            open();
        return dd.getDrinkInfo(d);
    }
    
    /**
     * @return a set of Ingredients for the given drink
     */
    public static Set<String> getIngredients(Drink d) {
        if (!open)
            open();
        return dd.getIngredients(d);
    }
    
    /**
     * @return an array containing all drink names found in database
     */
    public static Set<String> getDrinkNames() {
        if (!open)
            open();
        return dd.getDrinkNames();
    }
    
    /**
     * @return a list contain all ingredients as Strings
     */
    public static Set<String> getIngredients() {
        if (!open)
            open();
        return dd.getIngredients();
    }
    
    /**
     * @return a list of all Drinks
     */
    public static Set<Drink> getAllDrinks() {
        if (!open)
            open();
        return dd.getAllDrinks();
    }
    
    /**
     * @return a list of all Categories
     */
    public static Set<String> getCategories() {
        if (!open)
            open();
        return dd.getCategories();
    }
    
    /**
     * Adds a drink to the user's favorites list
     * 
     * @param drink the drink to be added
     */
    public static void addFavorite(Drink drink) {
        if (!open)
            open();
        dd.addFavorite(drink);
        
    }
    
    /**
     * Adds a drink rating
     * 
     * @param drink the drink to be added
     * @param rating the rating for the drink, must be [1-5] inclusive
     * @throws: IllegalArgumentException if the score is smaller than 1 or bigger than 5
     */
    public static void addRating(Drink drink, int rating) {
        if (!open)
            open();
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("score must be [1-5] inclusive");
        dd.addRating(drink, rating);
        
    }
    
    /**
     * Gets all drinks rated by user
     * @return a List of all drinks rated by user
     */
    public static Set<Drink> getRatedDrinks() {
        if (!open)
            open();
        return dd.getRatedDrinks();
    }
    
    /**
     * Gets all drinks on users favorites list
     * @return a List of all drinks in users favorites list
     */
    public static Set<Drink> getFavorites() {
        if (!open)
            open();
        return dd.getFavorites();
    }
    
    /**
     * Removes a drink from the user's favorites list
     * 
     * @param drink the drink to be removed
     */
    public static void removeFavorite(Drink drink) {
        if (!open)
            open();
        dd.removeFavorite(drink);
        
    }
    
    /**
     * For testing purposes.
     * Allows tests to setup DrinkData outside of Android environment
     */
    public static void setDrinkData(DrinkData newD) {
        open = true;
        dd = newD;
        dd.setDebug();
    }
}