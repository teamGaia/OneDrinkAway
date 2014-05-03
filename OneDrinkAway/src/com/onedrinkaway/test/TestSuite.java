package com.onedrinkaway.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.db.DrinkDb;

import junit.framework.TestCase;

public class TestSuite extends TestCase {
	
	//<ML global>
	private final double PRECISION_THRES = 0.0;
	private final int ML_TIMEOUT = 100000;
	//</ML global>
	
	//<DrinkDb global>
	private List<String> getIngredientsExpected;
	private List<Drink> getAllDrinksExpected;
	private List<String> getCategoritsExpected;
	private List<String> getFlavorsExpected;
	private List<Drink> getDrinksExpected;
	private DrinkDb db;
	//</DrinkDb global>
	
	//<Query global>
	private Query que;
	
	//</Query global>
	
	@Before
	public void buildTest() {
		//<for DrinkDb>
		db = new DrinkDb();
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
		
		//<for Query>
		que = new Query();
		//</for Query>
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
		throw new IllegalArgumentException();
	}
	
	@Test
	public void testRemoveFavoriteRemovesTheDrinkFromFavoriteList() {
		
	}
	//<================test DrinkDb=============>
	
	//<================test Flavor==============>
	@Test
	public void testFlavorNormalConstructionAssignCorrectValues() {
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFlavorConstructionThrowErrors() {
		throw new IllegalArgumentException();
	}
	//</================test Flavor==============>
	
	//<test Query>
	@Test
	public void testGetNameReturnSetName() {
		
	}
	
	@Test
	public void testGetCategoryReturnSetCategory() {
		
	}
	
	@Test
	public void testGetIngredientsReturnAddedIngredients() {
		
	}
	
	@Test
	public void testGetFlavorReturnAddedFlavor() {
		
	}
	
	@Test
	public void testHasNameReturnsTrueAfterSetName() {
		
	}
	
	@Test
	public void testHasNameReturnsFalseBeforeSetName() {
		
	}
	
	@Test
	public void testHasCategoryReturnsTrueAfterAddCategory() {
		
	}
	
	@Test
	public void testHasCategoryReturnsFalseBeforeAddCategory() {
		
	}
	
	@Test
	public void testHasIngredientsReturnsTrueAfterAddIngredients() {
		
	}
	
	@Test
	public void testHasIngredientsReturnsFalseBeforeAddIngredients() {
		
	}
	
	
	@Test
	public void testHasFlavorsReturnsFalseBeforeAddFlavors() {
		
	}
	
	@Test
	public void testHasFlavorsReturnsTrueAfterAddFlavors() {
		
	}
	
	@Test
	public void testMethodsReturnsunmodifiableObjects() {
		//use try catch to test, don't import rule
	}
	//</test Query>
	
	
	//<test ML>
	@Test(timeout=ML_TIMEOUT)
	public void testMLRunningTime() {
		
	}
	
	@Test
	public void testAccuracy() {
		
	}
	
	public void testTrainNoError() {
		
	}
	
	public void testPredictNoError() {
		
	}
	
	public void testPredictRatingNoError() {
		
	}
	//</test ML>
	
}
