/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    
    private static DrinkData dd = DrinkData.getDrinkData();
    
    /**
     * Returns the Drink corresponding to the given name
     */
    public static Drink getDrink(String name) {
        return dd.getDrink(name);
    }
    
    /**
     * Saves DrinkData object to file, currently broken in Android
     */
    public static void saveDrinkData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("drinkdata.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dd);
            out.close();
            fileOut.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Finds DrinkInfo for a given drink
     * @param d the Drink to search for
     * @return the DrinkInfo for d
     */
    public static DrinkInfo getDrinkInfo(Drink d) {
        return dd.getDrinkInfo(d);
    }
    
    /**
     * @return a set of Ingredients for the given drink
     */
    public static Set<String> getIngredients(Drink d) {
        return dd.getIngredients(d);
    }
    
    /**
     * @return an array containing all drink names found in database
     */
    public static Set<String> getDrinkNames() {
        return dd.getDrinkNames();
    }
    
    /**
     * @return a list contain all ingredients as Strings
     */
    public static Set<String> getIngredients() {
        return dd.getIngredients();
    }
    
    /**
     * @return a list of all Drinks
     */
    public static Set<Drink> getAllDrinks() {
        return dd.getAllDrinks();
    }
    
    /**
     * @return a list of all Categories
     */
    public static Set<String> getCategories() {
        return dd.getCategories();
    }
    
    /**
     * Adds a drink to the user's favorites list
     * 
     * @param drink the drink to be added
     */
    public static void addFavorite(Drink drink) {
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
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("score must be [1-5] inclusive");
        dd.addRating(drink, rating);
    }
    
    /**
     * Gets all drinks rated by user
     * @return a List of all drinks rated by user
     */
    public static Set<Drink> getRatedDrinks() {
        return dd.getRatedDrinks();
    }
    
    /**
     * Gets all drinks on users favorites list
     * @return a List of all drinks in users favorites list
     */
    public static Set<Drink> getFavorites() {
        return dd.getFavorites();
    }
    
    /**
     * Removes a drink from the user's favorites list
     * 
     * @param drink the drink to be removed
     */
    public static void removeFavorite(Drink drink) {
        dd.removeFavorite(drink);
    }
    
    /**
     * For testing purposes.
     * Allows tests to setup DrinkData outside of Android environment
     */
    public static void setDrinkData(DrinkData newD) {
        dd = newD;
    }
}