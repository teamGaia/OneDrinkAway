package com.onedrinkaway.db;

/**
 * Helper for DrinkDb, singleton.
 */

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import android.content.res.AssetManager;

import com.onedrinkaway.app.HomePage;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

public class DrinkData implements Serializable {
    
    private static DrinkData instance;
    
    private static final long serialVersionUID = -8186058076202228351L;

    // hashSet of all drink objects
    private HashSet<Drink> drinks;
    // set of all favorites
    private HashSet<Drink> favorites;
    // set of all ingredients
    private HashSet<String> ingredients;
    // set of all categories
    private HashSet<String> categories;
    // maps drink names to drinks
    private HashMap<String, Drink> namesToDrinks;
    // maps DrinkId's to DrinkInfo
    private HashMap<Drink, DrinkInfo> info;
    // set of all drinks that have been rated
    private HashSet<Drink> ratedDrinks;
    // map of from Drink to Ingredients List (Ingredients do not have portions
    private HashMap<Drink, Set<String>> drinkIngredients;
    
    /**
     * Private Constructor
     */
    private DrinkData() {
        drinks = new HashSet<Drink>();
        ingredients = new HashSet<String>();
        categories = new HashSet<String>();
        namesToDrinks = new HashMap<String, Drink>();
        info = new HashMap<Drink, DrinkInfo>();
        ratedDrinks = new HashSet<Drink>();
        favorites = new HashSet<Drink>();
        drinkIngredients = new HashMap<Drink, Set<String>>();
    }
    
    /**
     * Deserializes and populates singleton instance. If serialized file is not found,
     * creates a new singleton instance and populates it with data from files found in assets,
     * drinks.tsv and Recipes.txt
     */
    public static DrinkData getDrinkData() {
        if (instance == null) {
            try { // attempt to deserialize DrinkData
                AssetManager assets = HomePage.appContext.getAssets();
                InputStream is = assets.open("drinkdata.ser");
                ObjectInputStream in = new ObjectInputStream(is);
                instance = (DrinkData) in.readObject();
                in.close();
                System.out.println("deserialized dd");
            } catch (FileNotFoundException fe) { // drinkdata.ser not found, build from file
                buildFromFile();
            } catch (Exception e) { // something went wrong
                e.printStackTrace();
            }
        }
        return instance;
    }
    
    /**
     * Returns the Drink corresponding to the given name
     */
    public Drink getDrink(String name) {
      if (namesToDrinks.containsKey(name))
        return namesToDrinks.get(name);
      throw new IllegalArgumentException("Invalid Drink Name");
    }
    
    /**
     * Gets the DrinkInfo for the given drink
     */
    public DrinkInfo getDrinkInfo(Drink d) {
      if (info.containsKey(d))
        return info.get(d);
      throw new IllegalArgumentException("Invalid Drink passed to GetDrinkInfo");
    }
    
    /**
     * @return a set of Ingredients for the given drink
     */
    public Set<String> getIngredients(Drink d) {
        return new HashSet<String>(drinkIngredients.get(d));
    }
    
    /**
     * Adds given rating to d, uploads rating to database
     */
    public void addRating(Drink d, int rating) {
        ratedDrinks.add(d);
        d.addUserRating(rating);
        // TODO: upload rating to database
    }
    
    /**
     * Adds a Drink to user's favorites list
     * @param D the Drink to add to favorites
     */
    public void addFavorite(Drink d) {
        favorites.add(d);
    }
    
    /**
     * @return a set of all distinct categories
     */
    public Set<String> getCategories() {
        return new HashSet<String>(categories);
    }
    
    /**
     * @return a set of all distinct ingredients
     */
    public Set<String> getIngredients() {
        return new HashSet<String>(ingredients);
    }
    
    /**
     * @return a set of all Drinks
     */
    public Set<Drink> getAllDrinks() {
        return new HashSet<Drink>(drinks);
    }
    
    /**
     * @return a set of all drink names
     */
    public Set<String> getDrinkNames() {
        return new HashSet<String>(namesToDrinks.keySet());
    }
    
    /**
     * @return Set of all drinks rated by user
     */
    public Set<Drink> getRatedDrinks() {
        return new HashSet<Drink>(ratedDrinks);
    }

    /**
     * @return Set of all Drinks in user's favorites list
     */
    public Set<Drink> getFavorites() {
        return new HashSet<Drink>(favorites);
    }

    /**
     * Removes a drink from favorites list
     * @param drink the Drink to remove
     */
    public void removeFavorite(Drink drink) {
        favorites.remove(drink);
    }
    
    /**
     * Builds and returns a DrinkData object from file
     */
    private static void buildFromFile() {
        instance = new DrinkData();
        try {
            AssetManager assets = HomePage.appContext.getAssets();
            InputStream is = assets.open("drinks.tsv");
            Scanner sc = new Scanner(is);
            sc.nextLine(); //throw away first line
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] tokens = s.split("\t");
                String complete = tokens[2];
                if (complete.equals("1")) {
                    // valid drink, add to list
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    // add categories
                    List<String> cat = new ArrayList<String>();
                    for (String c : tokens[3].split(", ")) {
                        cat.add(c);
                        instance.categories.add(c);
                    }
                    String glass = tokens[4];
                    // add attributes
                    int[] attributes = new int[11];
                    for (int i = 0; i < 11; i++) {
                        attributes[i] = Integer.parseInt(tokens[i + 5]);
                    }
                    double rating = getAvgRating(id);
                    Drink d = new Drink(name, id, rating, attributes, cat, glass);
                    instance.drinks.add(d);
                    instance.namesToDrinks.put(name, d);
                }
            }
            sc.close();
            findDrinkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Parses the master list of drink info, searching for drinks that exist in drinkSet
     * This does not currently work on an Android device
     */
    private static void findDrinkInfo() {
        try {
            AssetManager assets = HomePage.appContext.getAssets();
            InputStream is = assets.open("RecipesBeta.txt");
            Scanner sc = new Scanner(is);
            List<String> lines = new ArrayList<String>();
            String line = sc.nextLine();
            String genericDesc = "This is a really nice drink, we promise!";
            String genericCit = "Cloude Strife";
            while (sc.hasNext()) {
                if (line.equals("")) {
                    // found end of drink, process lines
                    String name = lines.get(0);
                    if (instance.namesToDrinks.containsKey(name)) {
                        // we have this drink, build a DrinkInfo for it
                        int len = lines.size();
                        String instructions = lines.get(len - 1);
                        String garnish = lines.get(len - 2).substring(9); // removes "Garnish: "
                        List<String> ingr = new ArrayList<String>();
                        // get the drink object for this DrinkInfo
                        Drink d = instance.namesToDrinks.get(name);
                        for (int i = 1; i < len - 2; i++) {
                            ingr.add(lines.get(i));
                            addIngredient(d, lines.get(i));
                        }
                        
                        DrinkInfo di = new DrinkInfo(ingr, genericDesc, garnish, instructions, genericCit, d.id);
                        instance.info.put(d, di);
                    }
                    lines.clear();
                } else {
                    lines.add(line);
                }
                line = sc.nextLine();
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Attempts to remove an unnecessary characters from an ingredient String, and adds it
     * to the set of unique ingredients
     */
    private static void addIngredient(Drink d, String ingredient) {
        // search for uppercase character
        int i = 0;
        while (!Character.isUpperCase(ingredient.charAt(i)))
            i++;
        // remove first part of String, getting rid of quantity
        ingredient = ingredient.substring(i);
        // remove optional if it is there
        if (ingredient.contains(" (Optional)")) {
            ingredient = ingredient.substring(0, ingredient.length() - 10);
        }
        // check for splash of / dash of etc
        if (ingredient.contains(" of "))
            ingredient = ingredient.split(" of ")[1];
        ingredient = ingredient.trim();
        // ingredient is finally ready to add
        instance.ingredients.add(ingredient.trim());
        if (!instance.drinkIngredients.containsKey(d)) {
            instance.drinkIngredients.put(d, new HashSet<String>());
        }
        instance.drinkIngredients.get(d).add(ingredient);
    }
    
    /**
     * Gets average user rating for a drink
     */
    private static double getAvgRating(int drinkId) {
        Random r = new Random();
        return 3 + r.nextDouble() * 1.4;
    }



}
