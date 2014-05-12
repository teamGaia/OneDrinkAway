package com.onedrinkaway.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Drink.Category;
import com.onedrinkaway.common.Drink.Glass;

public class TestData {
	
	public static int[] attributes = {1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5};
	public static Drink whiskeySour =  new Drink("Whiskey Sour", 0, 3.2, attributes, Category.ON_THE_ROCKS, Glass.ROCKS, false);
	public static String[] whiskeySourIngredients = new String[]{"3/4 oz Fresh Lemon Juice", 
					"3/4 ox Simple syrup", "1 1/2 oz Whiskey"};
	public static String whiskeySourDescription = "Add all the ingredients to a shaker and fill with ice. Shake, and "
			+ "strain into a rocks glass filled with fresh ice. Garnish with a cherry and/or lemon wedge if desired.";
	
	
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
