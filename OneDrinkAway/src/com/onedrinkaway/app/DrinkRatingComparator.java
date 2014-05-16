package com.onedrinkaway.app;
import java.util.Comparator;

import com.onedrinkaway.common.Drink;

/**
 * Compares two Drink objects by their rating intending to sort in descending order
 * @author Andrea Martin
 *
 */
public class DrinkRatingComparator implements Comparator<Drink> {
	
	/**
	 * @return the rating of d2 - the rating of d1
	 */
	public int compare(Drink d1, Drink d2) {
		double rating1 = d1.getRating();
		double rating2 = d2.getRating();
		if(rating2 > rating1) {
			return 1;
		} else if(rating2 < rating1) {
			return -1;
		} else {
			return 0;
		}
	}
}
