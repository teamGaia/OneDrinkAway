package com.onedrinkaway.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.db.DrinkDb;
import com.onedrinkaway.model.machinelearning.KNearestNeighborModel;
import com.onedrinkaway.model.machinelearning.MLModel;

public class DatabaseInterface {
	private static MLModel machineLearner = new KNearestNeighborModel();

	/**
	 * @return all of the drinks in the database
	 * 
	 */
	public static Drink[] getAllDrinks() {
		Drink[] result = convertDrinkSetToArray(DrinkDb.getAllDrinks());
		Arrays.sort(result);
		return result;
	}

	/**
	 * @return String array containing names of each drink in the database.
	 */
	public static String[] getDrinkNames() {
		String[] result = convertStringSetToArray(DrinkDb.getDrinkNames());
		Arrays.sort(result);
		return result;
	}

	/**
	 * @param d
	 *            : the drink to be rated
	 * @param rating
	 *            : the rating that the user choose
	 */
	public static void addRating(Drink d, int rating) {
		DrinkDb.addRating(d, rating);
	}

	/**
	 * @return a list of drinks that the user has not rated, sorted by the
	 *         predicted rating (highest->lowest)
	 */
	public static Drink[] getTrySomethingNewDrinks() {
		Drink[] allDrinks = convertDrinkSetToArray(DrinkDb.getAllDrinks());
		Drink[] ratedDrinks = convertDrinkSetToArray(DrinkDb.getRatedDrinks());
		Drink[] unratedDrinks = getUnratedDrinks(allDrinks, ratedDrinks);

		return predictRatings(unratedDrinks, ratedDrinks);
	}

	/**
	 * @param query
	 *            : the query to filter the result drinks by
	 * @return a list of drinks unrated by the user, sorted by the predicted
	 *         rating (highest->lowest)
	 */
	public static Drink[] getDrinks(Query query) {
		//Set<Drink> drinks = new ArrayList<Drink>(DrinkDb.getAllDrinks());
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
				DrinkInfo di = DrinkDb.getDrinkInfo(d);
				List<String> drinkIngr = di.ingredients;
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

		// finally put all drinks in an array and sort
		Drink[] filteredDrinks = convertDrinkSetToArray(drinks);
		Drink[] ratedDrinks = convertDrinkSetToArray(DrinkDb.getRatedDrinks());
		Drink[] unratedDrinks = getUnratedDrinks(filteredDrinks, ratedDrinks);

		
		return predictRatings(unratedDrinks, ratedDrinks);
	}

	/**
	 * Returns the Drink corresponding to the given name
	 */
	public static Drink getDrink(String name) {
		return DrinkDb.getDrink(name);
	}

	/**
	 * @return a list of Drinks that the user has favorites
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
	 * @return a list of category names
	 */
	public static String[] getCategories() {
		String[] result = convertStringSetToArray(DrinkDb.getCategories());
		Arrays.sort(result);
		return result;
	}

	/**
	 * @return a list of possible ingredients
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
	 *            the Drink that the user favorited
	 */
	public static void addFavorite(Drink favorite) {
		DrinkDb.addFavorite(favorite);
	}

	/**
	 * Removes the passed Drink from the user's favorites list
	 * 
	 * @param oldFavorite
	 *            the Drink that the user no longer considers a favorite
	 */
	public static void removeFavorite(Drink oldFavorite) {
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
		return DrinkDb.getDrinkInfo(d);
	}

	/**
	 * filters out all of the Drinks that are in the second array from the first
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
			for (Drink rated : ratedDrinks) {
				if (!rated.equals(d)) {
					unratedDrinks.add(d);
				}
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

	private static Drink[] convertDrinkSetToArray(Set<Drink> drinks) {
		Drink[] result = new Drink[drinks.size()];
		int i = 0;
		for (Drink d : drinks) {
			result[i] = d;
			i++;
		}
		return result;
	}

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
