package com.onedrinkaway.db;

/**
 * Helper for DrinkDb, singleton. Main data structure.
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.onedrinkaway.app.HomePage;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

public class DrinkData implements Serializable {
    
    private static DrinkData instance;
    
    private static Connection conn;
    private static Statement stmt;
    
    // When debug == true, doesn't save any data, must be used outside of Android
    private boolean debug = false;
    private DbUpdate updater;
    
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
     * Deserializes and populates singleton instance if found. Then updates from database
     * Asynchronously. Must be called within Android.
     */
    public static DrinkData getDrinkData() {
        // Run in non-main thread, allows asynchronous access
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        // Build and update instance
        if (instance == null) {
            if (!deserialize()) {
                instance = new DrinkData();
            }
            updateInstanceAsync(null);
        }
        return instance;
    }
    
    /**
     * Sets up DrinkData from database and return singleton instance, should work 
     * outside Android. Must call debug as well
     */
    public static DrinkData getDrinkDataDB(String password) {
        instance = new DrinkData();
        updateInstance(password);
        return instance;
    }
    
    /**
     * For testing
     * @param debug
     */
    public void setDebug() {
        debug = true;
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
        if (!debug)
            saveDrinkData();
    }
    
    /**
     * Adds a Drink to user's favorites list
     * @param D the Drink to add to favorites
     */
    public void addFavorite(Drink d) {
        favorites.add(d);
        if (!debug)
            saveDrinkData();
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
        if (!debug)
            saveDrinkData();
    }
    
    /**
     * Serializes DrinkData, saving it's current state
     */
    public void saveDrinkData() {
        try {
            FileOutputStream fos = HomePage.appContext.openFileOutput("drinkdata.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
            fos.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Pulls data from Database and updates instance
     */
    private static void updateInstanceAsync(String password) {
        instance.getUpdater();
        instance.updater.execute("");
    }
    
    private void getUpdater() {
        if (updater == null)
            updater = new DbUpdate();
    }
    
    /**
     * Gets average user rating for a drink
     */
    private static double getAvgRating(int drinkId) {
        Random r = new Random();
        return 3 + r.nextDouble() * 1.4;
    }
    
    /**
     * Attempts to deserialize DrinkData
     * @return true if successful, false if not
     */
    private static boolean deserialize() {
        try {
            InputStream is = HomePage.appContext.openFileInput("drinkdata.ser");
            ObjectInputStream in = new ObjectInputStream(is);
            instance = (DrinkData) in.readObject();
            in.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Adds given drink to "drinks" and "namesToDrinks"
     */
    private void addDrink(Drink d, DrinkInfo di) {
        drinks.add(d);
        namesToDrinks.put(d.name, d);
        info.put(d, di);
    }
    
    /**
     * Stores given ingredients, corresponding to given Drink
     * @param d the drink
     * @param ingr the ingredient without portions
     */
    private void addIngredient(Drink d, String ingredient) {
        ingredients.add(ingredient.trim());
        if (!drinkIngredients.containsKey(d)) {
            drinkIngredients.put(d, new HashSet<String>());
        }
        drinkIngredients.get(d).add(ingredient);
    }
    
    /**
     * Attempts to establish connection to database
     * @throws Exception if connection fails
     */
    private static void getConnection(String password) throws Exception {
        if (conn == null) {
            if (password == null) {
                AssetManager assets = HomePage.appContext.getAssets();
                InputStream pwd = assets.open("pwd.txt");
                Scanner sc = new Scanner(pwd);
                password = sc.next();
                sc.close();
            }
            String dbName = "onedrinkaway"; 
            String userName = "teamgaia"; 
            String hostname = "onedrinkaway.ctfs3q1wopmj.us-west-2.rds.amazonaws.com";
            String port = "3306";
            
            String jdbcUrl = "jdbc:mysql://" + hostname + ":"
            + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl);
            stmt = conn.createStatement();
        }
    }
    
    /**
     * Pulls data from Database and updates instance
     */
    private static void updateInstance(String password) {
        try {
            getConnection(password);
            String drinkSQL = "SELECT * FROM DRINK";
            ResultSet drinkRS = stmt.executeQuery(drinkSQL);
            while (drinkRS.next()) {
                // get basic info from Drinks
                String name = drinkRS.getString(2);
                if (!instance.namesToDrinks.containsKey(name)) {
                    int id = drinkRS.getInt(1);
                    String glass = drinkRS.getString(3);
                    String garnish = drinkRS.getString(4);
                    String description = drinkRS.getString(5);
                    String instructions = drinkRS.getString(6);
                    String source = drinkRS.getString(7);
                    int[] att = new int[11];
                    for (int i = 0; i < 11; i++) {
                        att[i] = drinkRS.getInt(i + 8);
                    }
                    double avg = getAvgRating(id);
                    // get categories
                    List<String> categoriesList = new ArrayList<String>();
                    String csql = "SELECT * FROM CATEGORY WHERE drinkid = " + id;
                    Statement cstmt = conn.createStatement();
                    ResultSet crs = cstmt.executeQuery(csql);
                    while (crs.next()) {
                        String category = crs.getString(2);
                        categoriesList.add(category);
                        instance.categories.add(category);
                    }
                    // finally have our drink
                    Drink d = new Drink(name, id, avg, att, categoriesList, glass);
                    List<String> ingr = new ArrayList<String>(); // ingredients list for drinkInfo
                    String isql = "SELECT * FROM INGREDIENT WHERE drinkid = " + id;
                    Statement istmt = conn.createStatement();
                    ResultSet irs = istmt.executeQuery(isql);
                    while (irs.next()) {
                        instance.addIngredient(d, irs.getString(2));
                        ingr.add(irs.getString(3));
                    }
                    DrinkInfo di = new DrinkInfo(ingr, description, garnish, instructions, source, d.id);
                    instance.addDrink(d, di);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private class DbUpdate extends AsyncTask<String, Void, Void> implements Serializable {
        
        private static final long serialVersionUID = -2632208847918637444L;

        @Override
        protected Void doInBackground(String... params) {
            updateInstance(null);
            return null;
        }

    }
    

}
