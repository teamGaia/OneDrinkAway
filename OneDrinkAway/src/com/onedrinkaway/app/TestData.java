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
	
	
	public static Map<String, Integer> interpretFlavors(Drink drink) {
		Map<String, Integer> flavors = new TreeMap<String, Integer>();
		int[] attributes = drink.attributes;
		flavors.put("Sweet", attributes[0] );
		flavors.put("Citrusy", attributes[1]);
		flavors.put("Bitter", attributes[2]);
		flavors.put("Herbal", attributes[3]);
		flavors.put("Minty", attributes[4]);
		flavors.put("Fruity", attributes[5]);
		flavors.put("Sour", attributes[6]);
		flavors.put("Boozy", attributes[7]);
		flavors.put("Spicy", attributes[8]);
		flavors.put("Salty", attributes[9]);
		flavors.put("Creamy", attributes[10]);
		
		return flavors;
	}
	
}
