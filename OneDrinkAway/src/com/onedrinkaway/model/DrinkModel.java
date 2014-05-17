package com.onedrinkaway.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.provider.Settings.Secure;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.db.DrinkDb;
import com.onedrinkaway.model.machinelearning.KNearestNeighborModel;
import com.onedrinkaway.model.machinelearning.MLModel;

/**
 * DatabaseInterface communicates with the machine learning, database and UI and passes information between
 * the three. DatabaseInterface also makes search queries and sends the results to the UI
 *
 */
public class DrinkModel {
	private static MLModel machineLearner = new KNearestNeighborModel();
	
	private static Drink[] results = null;
	
	/**
	 * Gets the contents of results. If results is null, returns an empty array
	 * @return an Array of results
	 */
	public static Drink[] getResults() {
	    if (results == null)
	        return new Drink[0];
	    else
	        return results;
	}

	/**
	 * Returns all of the drinks in the database, sorted by their 
	 * 	  user rating, then predicted rating, then average rating
	 * @return all of the drinks in the database
	 */
	public static Drink[] getAllDrinks() {
		Drink[] result = convertDrinkSetToArray(DrinkDb.getAllDrinks());
		Arrays.sort(result);
		return result;
	}

	/**
	 * Returns an alphabetically sorted array of the names of all of the drinks in the database
	 * @return String array containing names of each drink in the database.
	 */
	public static String[] getDrinkNames() {
		String[] result = convertStringSetToArray(DrinkDb.getDrinkNames());
		Arrays.sort(result);
		return result;
	}

	/**
	 * Adds rating to the given drink
	 * @param d
	 *            : the drink to be rated
	 * @param rating
	 *            : the rating that the user choose
	 */
	public static void addRating(Drink d, int rating) {
		DrinkDb.addRating(d, rating);
	}

	/**
	 * Return a list of drink that the user has not rated, sorted by the predicted rating: 
	 * highest to lowest
	 * @return a list of drinks that the user has not rated, sorted by the
	 *         predicted rating (highest->lowest)
	 */
	public static void findTrySomethingNewDrinks() {
		Drink[] allDrinks = convertDrinkSetToArray(DrinkDb.getAllDrinks());
		Drink[] ratedDrinks = convertDrinkSetToArray(DrinkDb.getRatedDrinks());
		Drink[] unratedDrinks = getUnratedDrinks(allDrinks, ratedDrinks);
		
		results = predictRatings(unratedDrinks, ratedDrinks);
	}

	/**
	 * Searches for drinks constrained by a query.
	 * To access the results, call getResults()
	 * @param query
	 *            : the query to filter the result drinks by
	 * @return false if the results array is empty, true if not
	 */
	public static boolean searchForDrinks(Query query) {
		Set<Drink> drinks = DrinkDb.getAllDrinks();
		Iterator<Drink> iter;
		if (query.hasCategory()) { // iterate and filter by category
			iter = drinks.iterator();
			while (iter.hasNext()) {
				Drink d = iter.next();
				if (!d.categories.contains(query.getCategory()))
					iter.remove();
			}
		}
		if (query.hasIngredients()) { // iterate and filter by ingredients
			iter = drinks.iterator();
			while (iter.hasNext()) {
				Drink d = iter.next();
				Set<String> drinkIngr = DrinkDb.getIngredients(d);
				List<String> queryIngr = query.getIngredients();
				if (!drinkIngr.containsAll(queryIngr))
					iter.remove();
			}
		}
		if (query.hasName()) { // iterate and filter by name
			iter = drinks.iterator();
			while (iter.hasNext()) {
				Drink d = iter.next();
				if (!d.name.contains(query.getName()))
					iter.remove();
			}
		}
		if (query.hasFlavors()) {
			// Machine Learning time
		}
		// more code here, set predicted ratings or whatever

		// Get everything into arrays
		Drink[] filteredDrinks = convertDrinkSetToArray(drinks);
		Drink[] ratedDrinks = convertDrinkSetToArray(DrinkDb.getRatedDrinks());
		// Get all of the unrated drinks then set the predicted ratings
		Drink[] unratedDrinks = getUnratedDrinks(filteredDrinks, ratedDrinks);
		results = predictRatings(unratedDrinks, ratedDrinks);
		return results.length > 0;
	}

	/**
	 * Returns the Drink corresponding to the given name
	 */
	public static Drink getDrink(String name) {
		return DrinkDb.getDrink(name);
	}

	/**
	 * @return a list of Drinks that the user has favorites or null if the favorites list is empty
	 */
	public static Drink[] getFavorites() {
		Set<Drink> favorites = DrinkDb.getFavorites();
		if(favorites != null) {
			Drink[] result = convertDrinkSetToArray(DrinkDb.getFavorites());
			Arrays.sort(result);
			return result;
		} else {
			return null;
		}
			
	}

	/**
	 * Returns an alphabetically sorted list of drink category names
	 * @return a list of category names
	 */
	public static String[] getCategories() {
		String[] result = convertStringSetToArray(DrinkDb.getCategories());
		Arrays.sort(result);
		return result;
	}

	/**
	 * Returns a list of all ingredients used in drinks
	 * @return a String[] of all ingredients used in drinks
	 */
	public static String[] getIngredients() {
		String[] result = convertStringSetToArray(DrinkDb.getIngredients());
		Arrays.sort(result);
		return result;
	}
	/**
	 * Adds the passed Drink to the user's favorites list
	 * 
	 * @param favorite
	 *            the Drink that the user has added to their favorite drink list
	 */
	public static void addFavorite(Drink favorite) {
		DrinkDb.addFavorite(favorite);
	}

	/**
	 * Removes the passed Drink from the user's favorites list
	 * 
	 * @param oldFavorite
	 *            the Drink that the user has removed from their favorite drink list
	 */
	public static void removeFavorite(Drink oldFavorite) {
		if(oldFavorite != null) 
			DrinkDb.removeFavorite(oldFavorite);
	}

	/**
	 * Finds DrinkInfo for a given drink
	 * 
	 * @param d
	 *            the Drink to search for
	 * @return the DrinkInfo for d
	 */
	public static DrinkInfo getDrinkInfo(Drink d) {
		if(d != null) 
			return DrinkDb.getDrinkInfo(d);
		return null;
	
	}

	/**
	 * Filters out all of the Drinks that are in the second array from the first
	 * Returns an array of unrated drinks
	 * 
	 * @param allDrinks
	 *            array of drinks to filter
	 * @param ratedDrinks
	 *            array of drinks to filter the first array by
	 * @return an array that is the subtraction of ratedDrinks from allDrinks
	 */
	private static Drink[] getUnratedDrinks(Drink[] allDrinks,
										Drink[] ratedDrinks) {
		List<Drink> unratedDrinks = new ArrayList<Drink>();
		for (Drink d : allDrinks) {
			boolean flag = false;
			for (Drink rated : ratedDrinks) {
				if (rated.equals(d)) {
					flag = true;
				}
			}
			if(!flag){
					unratedDrinks.add(d);
			}
		}
		return unratedDrinks.toArray(new Drink[unratedDrinks.size()]);
	}

	/**
	 * Predicts the user rating for each unrated drink
	 * 
	 * @param unratedDrinks
	 *            list of drinks to predict the rating
	 * @param ratedDrinks
	 *            list of drinks that the user has rated
	 * @return a list of drinks that is the same as the unrated drink list but
	 *         is SORTED by the predicted rating for the drinks, highest ->
	 *         lowest
	 */
	private static Drink[] predictRatings(Drink[] unratedDrinks,
			Drink[] ratedDrinks) {
		machineLearner.train(new ArrayList<Drink>(Arrays.asList(ratedDrinks)));
		for (Drink d : unratedDrinks) {
			double rating = machineLearner.predictRating(d);
			d.predictedRating = rating;
		}
		Drink[] predictedDrinks = unratedDrinks.clone();
		Arrays.sort(predictedDrinks);
		return predictedDrinks;
	}

	/**
	 * Converts the given set of drinks to an array
	 * @param drinks the set of drinks to be converted to an array
	 * @return Drink[] composed of the drinks originally stored in the given drinks set
	 */
	private static Drink[] convertDrinkSetToArray(Set<Drink> drinks) {
		Drink[] result = new Drink[drinks.size()];
		int i = 0;
		for (Drink d : drinks) {
			result[i] = d;
			i++;
		}
		return result;
	}

	/**
	 * Converts the given set of strings to an array of strings
	 * @param strings set of strings to be converted
	 * @return String[] composed of all the strings in the original set
	 */
	private static String[] convertStringSetToArray(Set<String> strings) {
		String[] result = new String[strings.size()];
		int i = 0;
		for (String s : strings) {
			result[i] = s;
			i++;
		}
		return result;
	}
}
