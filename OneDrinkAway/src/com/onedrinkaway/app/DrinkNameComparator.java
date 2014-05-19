package com.onedrinkaway.app;

import java.util.Comparator;

import com.onedrinkaway.model.Drink;


/**
 * Allows two drink objects to be compared by their drink String names
 * @author Andrea Martin
 *
 */
public class DrinkNameComparator implements Comparator<Drink> {
	
	/**
	 * @param d1 the first drink in the comparison
	 * @param d2 the second drink in the comparison
	 * @returns d1.name.compareTo(d2.name)
	 */
	public int compare(Drink d1, Drink d2) {
		return d1.name.compareTo(d2.name);
	}
}