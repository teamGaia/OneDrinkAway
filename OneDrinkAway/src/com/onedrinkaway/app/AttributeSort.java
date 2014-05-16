package com.onedrinkaway.app;

import com.onedrinkaway.common.Drink;

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
		alphabetized[0] = attributes[7]; //Boozy
		alphabetized[1] = attributes[2]; //Bitter
		alphabetized[2] = attributes[1]; //Citrusy
		alphabetized[3] = attributes[10]; //Creamy
		alphabetized[4] = attributes[5]; //Fruity
		alphabetized[5] = attributes[3]; //Herbal
		alphabetized[6] = attributes[4]; //Minty
		alphabetized[7] = attributes[9]; //Salty
		alphabetized[8] = attributes[6]; //Sour
		alphabetized[9] = attributes[8]; //Spicy
		alphabetized[10] = attributes[0]; //Sweet
		
		
		return alphabetized;
	}
    
  

	
}
