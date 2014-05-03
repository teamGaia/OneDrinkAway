package com.onedrinkaway.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.db.DrinkDb;

import junit.framework.TestCase;

public class TestSuite extends TestCase {
	
	//<DrinkDb global>
	private List<String> getIngredientsExpected;
	private List<Drink> getAllDrinksExpected;
	private List<String> getCategoritsExpected;
	private List<String> getFlavorsExpected;
	private List<Drink> getDrinksExpected;
	//</DrinkDb global>
	
	@Before
	public void buildTest() {
		//<for DrinkDb>
		DrinkDb db = new DrinkDb();
		String[] valIngredients = {};
		getIngredientsExpected = makeList(valIngredients);
		String[] valAllDrinks = {};
		getAllDrinksExpected = makeDrinkList(valAllDrinks);
		String[] valCategories = {};
		getCategoritsExpected = makeList(valCategories);
		String[] valFlavors = {};
		getFlavorsExpected = makeList(valFlavors);
		String[] valDrinks = {};
		getDrinksExpected = makeDrinkList(valDrinks);
		//</for DrinkDb>
		
		
	}
	
	private List<String> makeList(String[] vals) {
		if(vals == null) {
			throw new IllegalArgumentException();
		}
		List<String> ret = new LinkedList<String>();
		for(String s : vals) {
			ret.add(s);
		}
		return ret;
	}
	
	
	private <T> boolean twoListEquals(List<T> l1, List<T> l2) {
		if(l1 == null || l2 == null || (l1.size() != l2.size())) {
			return false;
		}
		Iterator<T> i1 = l1.iterator();
		Iterator<T> i2 = l2.iterator();
		while(i1.hasNext()) {
			if(!i1.next().toString().equals(i2.next().toString())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param vals: String representation of drinks
	 * @return: the list of drinks
	 */
	private List<Drink> makeDrinkList(String[] vals) {
		if(vals == null) {
			throw new IllegalArgumentException();
		}
		List<Drink> ret = new LinkedList<Drink>();
		for(String s : vals) {
			ret.add(new Drink(s));
		}
		return ret;
	}
	
	//<================test DrinkDb=============>
	@Test
	public void testGetIngredientsReturnedValue() {
		
	}
	
	
	@Test
	public void testGetCategoriesReturnedValue() {
		
	}
	
	@Test
	public void testGetFlavorsReturnedValue() {
		
	}
	
	@Test
	public void testGetDrinksReturnedValue() {
		
	}
	
	@Test
	public void testAddFavoriteMakeDrinkFavorite() {
		
	}
	
	@Test
	public void testAddRatingCanAddCorrectRatingToDrink() {
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddRatingWithWrongRatingThrowException() {
		
	}
	
	@Test
	public void testRemoveFavoriteRemovesTheDrinkFromFavoriteList() {
		
	}
	//<================test DrinkDb=============>
	
	//<================test >
}
