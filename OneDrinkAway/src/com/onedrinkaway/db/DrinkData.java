package com.onedrinkaway.db;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onedrinkaway.app.HomePage;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;

/**
 * Helper for DrinkDb, singleton, main data structure.
 * Stores all Drink, DrinkInfo and relevant data including categories, ingredients etc.
 * Loads itself from disc on startup, then updates from database.
 * When a favorite is added or removed, or a drink is rated, attempts to upload the data to the
 * database. If uploads fail, tries again when a rating or favorite is modified. After these
 * operations it writes its state to disc.
 */

public class DrinkData implements Serializable {
    
    private static DrinkData instance;
    
    // When debug == true, doesn't save any data, must be used outside of Android
    private boolean debug = false;
    
    // Timeout for uploads
    private static final long UPLOAD_TIMEOUT = 5000;
    // Pause between saving state
    private static final long SAVE_DELAY = 5000;
    // timestamp of last time data was saved to disc
    private static long LAST_SAVE;
    
    private static final long serialVersionUID = -8186058076202228351L;
    
    private String userId;
    
    private static String hostUrl = "http://54.200.252.24:8080/DrinkDbServer";
    private static String charset = "UTF-8";

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
    // queue of drinks needing to be updated in database
    private Queue<Drink> uploadQ;
    
    /**
     * Private Constructor
     */
    private DrinkData(String userId) {
        drinks = new HashSet<Drink>();
        ingredients = new HashSet<String>();
        categories = new HashSet<String>();
        namesToDrinks = new HashMap<String, Drink>();
        info = new HashMap<Drink, DrinkInfo>();
        ratedDrinks = new HashSet<Drink>();
        favorites = new HashSet<Drink>();
        drinkIngredients = new HashMap<Drink, Set<String>>();
        uploadQ = new LinkedList<Drink>();
        LAST_SAVE = 0;
        this.userId = userId;
    }
    
    /**
     * Deserializes and populates singleton instance if found. Then updates from database
     * Asynchronously. Must be called within Android.
     */
    public static DrinkData getDrinkData(String userId) {
        // Build and update instance
        if (instance == null) {
            if (!deserialize()) {
                instance = new DrinkData(userId);
            }
            instance.userId = userId;
            instance.updateInstanceAsync();
        }
        instance.userId = userId;
        return instance;
    }
    
    /**
     * Sets up DrinkData from database and return singleton instance, should work 
     * outside Android. Must call debug as well
     */
    public static DrinkData getDrinkDataDB(String userId) {
        instance = new DrinkData(userId);
        instance.userId = userId;
        updateInstance();
        return instance;
    }
    
    /**
     * For testing, debug implies we are running outside of Android
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
     * Adds given rating to d, uploads rating to database
     */
    public void addRating(Drink d, int rating) {
        ratedDrinks.add(d);
        d.addUserRating(rating);
        uploadQ.add(d);
        instance.updateDrinksAsync();
    }
    
    /**
     * Adds a Drink to user's favorites list
     * @param D the Drink to add to favorites
     */
    public void addFavorite(Drink d) {
        favorites.add(d);
        uploadQ.add(d);
        instance.updateDrinksAsync();
    }

    /**
     * Removes a drink from favorites list
     * @param d the Drink to remove
     */
    public void removeFavorite(Drink d) {
        favorites.remove(d);
        uploadQ.add(d);
        instance.updateDrinksAsync();
    }
    
    /**
     * Serializes DrinkData, saving it's current state, if not in debug mode.
     */
    private void saveDrinkData() {
        if (!debug) {
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
    }
    
    /**
     * Serializes DrinkData, saving it's current state to given outputstream
     */
    public void saveDrinkDataDebug(FileOutputStream fos) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
            fos.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
    
    /**
     * Attempts to deserialize DrinkData from Android internal memory. On a fresh install,
     * this file will not be found. In that case, it loads from the assets folder.
     * 
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
            try {
                AssetManager assets = HomePage.appContext.getAssets();
                InputStream is = assets.open("drinkdata.ser");
                ObjectInputStream in = new ObjectInputStream(is);
                instance = (DrinkData) in.readObject();
                in.close();
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
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
        ingredients.add(ingredient);
        if (!drinkIngredients.containsKey(d)) {
            drinkIngredients.put(d, new HashSet<String>());
        }
        drinkIngredients.get(d).add(ingredient);
    }
    
    /**
     * Searches for and returns a set of the users favorites found in database
     * @return a set of drinkids
     */
    private static Set<Integer> getFavoritesDb() {
        Set<Integer> result = new HashSet<Integer>();
        try {
            String url = hostUrl + "/getuserfavorites";

            String query = String.format("%s", URLEncoder.encode(instance.userId, charset));
            
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
            String data = reader.readLine();
            if (data.length() != 0) {
                String[] ids = data.split(",");
                for (String id : ids) {
                    result.add(Integer.parseInt(id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Searches for and returns users ratings from database
     * @return a map of drinkid->rating
     */
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Integer> getUserRatingsDb() {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        try {
            String url = hostUrl + "/getuserratings";

            String query = String.format("%s", URLEncoder.encode(instance.userId, charset));
            
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
            String data = reader.readLine();
            if (data.length() != 0) {
                String[] ids = data.split(",");
                for (String id : ids) {
                    String[] tokens = id.split(":");
                    result.put(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Searches for and returns users ratings from database
     * @return a map of drinkid->rating
     */
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Double> getAvgRatingsDb() {
        Map<Integer, Double> result = new HashMap<Integer, Double>();
        try {
            String url = hostUrl + "/getavgratings";
            
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
            String data = reader.readLine();
            if (data.length() != 0) {
                String[] ids = data.split(",");
                for (String id : ids) {
                    String[] tokens = id.split(":");
                    result.put(Integer.parseInt(tokens[0]), Double.parseDouble(tokens[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Uploads drink ratings and favorites, waiting to be uploaded
     * Tries for UPLOAD_TIMEOUT milliseconds, then gives up to try again later
     */
    private static void uploadDrinks() {
        try {
            long startTime = System.currentTimeMillis();
            long duration = 0;
            while (!instance.uploadQ.isEmpty() && duration < UPLOAD_TIMEOUT) {
                Drink d = instance.uploadQ.remove();
                try {
                    if (instance.favorites.contains(d)) {
                        String url = hostUrl + "/addfavorite";

                        String query = String.format("%s&%s", 
                             URLEncoder.encode(instance.userId, charset), 
                             URLEncoder.encode("" + d.id, charset));
                        
                        URLConnection connection = new URL(url + "?" + query).openConnection();
                        connection.setRequestProperty("Accept-Charset", charset);
                        connection.getInputStream();
                    }
                    if (instance.ratedDrinks.contains(d)) {
                        String url = hostUrl + "/adduserrating";

                        String query = String.format("%s&%s&%s", 
                                URLEncoder.encode(instance.userId, charset), 
                                URLEncoder.encode("" + d.id, charset),
                                URLEncoder.encode("" + d.getUserRating(), charset));
                        
                        URLConnection connection = new URL(url + "?" + query).openConnection();
                        connection.setRequestProperty("Accept-Charset", charset);
                        connection.getInputStream();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    instance.uploadQ.add(d);
                } finally {
                    duration = System.currentTimeMillis() - startTime;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    /**
     * Pulls data from Database and updates instance
     */
    private static void updateInstance() {
        try {
            String url = hostUrl + "/getalldrinks";
            InputStream response = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
            String data = reader.readLine();
            Gson gson = new Gson();
            // set of user favorited drinkids
            Set<Integer> favs = getFavoritesDb();
            // map of user rated drinks, drinkid->rating
            Map<Integer, Integer> ratings = getUserRatingsDb();
            HashMap<String, String> datamap = gson.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
            for (String key : datamap.keySet()) {
                Drink d = gson.fromJson(key, new TypeToken<Drink>() {}.getType());
                DrinkInfo di = gson.fromJson(datamap.get(key), new TypeToken<DrinkInfo>() {}.getType());
                if (!instance.namesToDrinks.containsKey(d.name)) {
                    instance.addDrink(d, di);
                    for (String ingredient : di.ingredients)
                        instance.addIngredient(d, stripPortions(ingredient));
                    for (String category : d.categories)
                        instance.categories.add(category);
                }
                if (favs.contains(d.id))
                    instance.favorites.add(d);
                if (ratings.containsKey(d.id)) {
                    Set<Drink> ratedDrinks = instance.ratedDrinks;
                    ratedDrinks.add(d);
                    d.addUserRating(ratings.get(d.id));
                }
            }
            updateRatings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates average user ratings on all drinks in dataset
     */
    private static void updateRatings() {
        Map<Integer, Double> ratings = getAvgRatingsDb();
        for (Drink d : instance.drinks) {
            d.setAvgRating(ratings.get(d.id));
        }
    }
    
    /**
     * Attempts to remove an unnecessary characters from an ingredient String, and adds it
     * to the set of unique ingredients
     */
    private static String stripPortions(String ingredient) {
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
        return ingredient;
    }
    
    /**
     * Updates drink info in database asynchronously
     */
    private void updateDrinksAsync() {
        if (!debug)
            new DbUpdate().execute("uploadDrinks");
    }
    
    /**
     * Pulls data from Database and updates instance
     */
    private void updateInstanceAsync() {
        if (!debug)
            new DbUpdate().execute("update");
    }
    
    /**
     * Asynchronous task manager, runs data connections in background.
     */
    private class DbUpdate extends AsyncTask<String, Void, Void> implements Serializable {
        
        private static final long serialVersionUID = -2632208847918637444L;

        /**
         * Runs update in background, then saves DrinkData to disc.
         * Param "update" updates entire DrinkData instance.
         * Param "uploadDrinks" attempts to send all uploadQ ratings and favorites to database
         */
        @Override
        protected Void doInBackground(String... params) {
            if (params[0].equals("update")) {
                updateInstance();
            } else if (params[0].equals("uploadDrinks")) {
                uploadDrinks();
            }
            if (System.currentTimeMillis() - LAST_SAVE > SAVE_DELAY) {
                instance.saveDrinkData();
                LAST_SAVE = System.currentTimeMillis();
            }
            return null;
        }
    }
    

}