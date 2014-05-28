package com.onedrinkaway.app;

import java.util.Arrays;

import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.Flavor;

/**
 * Sorts the attributes of a given drink alphabetically based on the flavor title that each attribute represents
 * @author Andrea
 *
 */
public class AttributeSort {
	
		/**
		 * Returns the attributes int[] of the given drink sorted alphabetically by flavor title
		 * @param drink the drink in which attributes to be sorted
		 * @return returns the attributes of the given drink sorted by flavor title
		 */
    public static int[] alphabetizeAttributes(Drink drink) {
		
		int[] attributes = drink.attributes;
		int[] alphabetized = new int[attributes.length];
		String[] flavors = Arrays.copyOf(Flavor.flavorsArr, Flavor.flavorsArr.length);
		Arrays.sort(flavors);
		for(int i = 0; i < flavors.length; i++){
			alphabetized[i] = attributes[Arrays.asList(Flavor.flavorsArr).indexOf(flavors[i])];
		}
		
		return alphabetized;
	}
    
  

	
}
