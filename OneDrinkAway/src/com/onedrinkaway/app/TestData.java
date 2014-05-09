package com.onedrinkaway.app;

import java.util.List;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Drink.Category;
import com.onedrinkaway.common.Drink.Glass;

public class TestData {
	
	
	public static Drink whiskeySour =  new Drink("Whiskey Sour", 0, 3.2, null, Category.ON_THE_ROCKS, Glass.ROCKS, false);
	public static String[] whiskeySourIngredients = new String[]{"3/4 oz Fresh Lemon Juice", 
					"3/4 ox Simple syrup", "1 1/2 oz Whiskey"};
	public static String whiskeySourDescription = "Add all the ingredients to a shaker and fill with ice. Shake, and "
			+ "strain into a rocks glass filled with fresh ice. Garnish with a cherry and/or lemon wedge if desired.";
	
}
