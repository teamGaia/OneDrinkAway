package com.onedrinkaway.db;

/**
 * Helper for DrinkDb, singleton. Main data structure.
 * Attempts to deserialize itself, falling back to database if serialized image is not available.
 * If data connection fails, reads in data from text.
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
import android.os.StrictMode;

import com.onedrinkaway.app.HomePage;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

public class DrinkData implements Serializable {
    
    private static DrinkData instance;
    
    private static Connection conn;
    
    private static Statement stmt;
    
    // When debug == true, doesn't save any data, must be used outside of Android
    private boolean debug = false;
    
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (instance == null) {
            if (deserialize()) {
            } else if (buildFromDB()) {
            } else {
                buildFromFile();
            }
        }
        return instance;
    }
    
    /**
     * Sets up DrinkData from database and return singleton instance
     */
    public static DrinkData getDrinkDataDB(String password) {
        buildFromDB(password);
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
    
    private static boolean buildFromDB() {
        return buildFromDB(null);
    }
    
    private static boolean buildFromDB(String password) {
        try {
            getConnection(password);
            instance = new DrinkData();
            String sql = "SELECT * FROM DRINK";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // get basic info from Drinks
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String glass = rs.getString(3);
                String garnish = rs.getString(4);
                String description = rs.getString(5);
                String instructions = rs.getString(6);
                String source = rs.getString(7);
                int[] att = new int[11];
                for (int i = 0; i < 11; i++) {
                    att[i] = rs.getInt(i + 8);
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
                // now setup drink
                Drink d = new Drink(name, id, avg, att, categoriesList, glass);
                List<String> ingr = new ArrayList<String>(); // ingredients list for drinkInfo
                String isql = "SELECT * FROM INGREDIENT WHERE drinkid = " + id;
                Statement istmt = conn.createStatement();
                ResultSet irs = istmt.executeQuery(isql);
                while (irs.next()) {
                    instance.addIngredientTrimed(d, irs.getString(2));
                    ingr.add(irs.getString(3));
                }
                DrinkInfo di = new DrinkInfo(ingr, description, garnish, instructions, source, d.id);
                instance.info.put(d, di);
                instance.drinks.add(d);
                instance.namesToDrinks.put(d.name, d);
            }
            
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
     * Gets average user rating for a drink
     */
    private static double getAvgRating(int drinkId) {
        Random r = new Random();
        return 3 + r.nextDouble() * 1.4;
    }
    
    /**
     * Attempts to build DrinkData from files
     * @return true if successful, false if not
     */
    private static boolean buildFromFile() {
        try {
            AssetManager assets = HomePage.appContext.getAssets();
            InputStream drinkIs = assets.open("drinks.tsv");
            InputStream drinkInfoIs = assets.open("RecipesBeta.txt");
            getDrinkData(drinkIs, drinkInfoIs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
    private void addDrink(Drink d) {
        drinks.add(d);
        namesToDrinks.put(d.name, d);
    }
    
    /**
     * Stores given ingredients, corresponding to given Drink
     * @param d the drink
     * @param ingr the ingredient without portions
     */
    private void addIngredientTrimed(Drink d, String ingredient) {
        ingredients.add(ingredient.trim());
        if (!drinkIngredients.containsKey(d)) {
            drinkIngredients.put(d, new HashSet<String>());
        }
        drinkIngredients.get(d).add(ingredient);
    }
    
    /**
     * For testing purposes,resets singleton instance from given streams
     * Also enter debug mode, which doesn't write any data to disc
     */
    public static DrinkData getDrinkData(InputStream drinkIs, InputStream drinkInfoIs) {
        instance = new DrinkData();
        Scanner sc = new Scanner(drinkIs);
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
                instance.addDrink(d);
            }
        }
        sc.close();
        findDrinkInfo(drinkInfoIs);
        return instance;
    }
    
    /**
     * Parses the master list of drink info, searching for drinks that exist in drinkSet
     * This does not currently work on an Android device
     */
    private static void findDrinkInfo(InputStream is) {
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
                        instance.addIngredient(d, lines.get(i));
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
    }
    
    /**
     * Attempts to remove an unnecessary characters from an ingredient String, and adds it
     * to the set of unique ingredients
     */
    private void addIngredient(Drink d, String ingredient) {
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
        instance.addIngredientTrimed(d, ingredient);
    }
    
    



}
