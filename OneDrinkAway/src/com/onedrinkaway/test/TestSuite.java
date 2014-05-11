package com.onedrinkaway.test;

/**
 * @author Yaohua Zhuo (Phoenix)
 */
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Drink.Category;
import com.onedrinkaway.common.Drink.Glass;
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
	//private List<Drink> getAllDrinksExpected;
	private List<String> getCategoritsExpected;
	private List<String> getFlavorsExpected;
	//private List<Drink> getDrinksExpected;
	private DrinkDb db;
	//</DrinkDb global>

	//<Query global>
	private Query que;
	//</Query global>

	//<Drink global>
	private List<String> getDrinkFlavorsExpected;
	private List<String> getDrinkCategoriesExpected;
	private List<String> getDrinkRedientsExpected;
	private List<String> getDrinkAttributesExpected;
	private Drink drink;
	//</Drink global>

	@Before
	public void buildTest() {
		//<for DrinkDb>
		db = new DrinkDb();
		String[] valIngredients = {};
		getIngredientsExpected = makeList(valIngredients);
		String[] valAllDrinks = {};
		//getAllDrinksExpected = makeDrinkList(valAllDrinks);
		String[] valCategories = {};
		getCategoritsExpected = makeList(valCategories);
		String[] valFlavors = {"glass", "sweet", "citrusy", "bitter", "herbal", "minty", "fruity", "sour", "boosy", "spicy", "salty", "creamy"};
		getFlavorsExpected = makeList(valFlavors);
		String[] valDrinks = {};
		//getDrinksExpected = makeDrinkList(valDrinks);
		//</for DrinkDb>

		//<for Query>
		que = new Query();
		//</for Query>

		//<for Drink>
		//drink = new Drink();
		String[] valDrinkFlavor = {};
		getDrinkFlavorsExpected = makeList(valDrinkFlavor);
		String[] valDrinkCategories = {};
		getDrinkCategoriesExpected = makeList(valDrinkCategories);
		String[] valDrinkRedients = {};
		getDrinkRedientsExpected = makeList(valDrinkRedients);
		String[] valDrinkAttributes = {};
		getDrinkAttributesExpected = makeList(valDrinkAttributes);
		int[] attr = {1, 2, 3};
		drink = new Drink("aaa", 111111, 0.9, attr, Category.SHAKEN, Glass.COCKTAIL, true);
		//</for Drink>
	}
	
	
	private Set<String> makeSet(String[] vals) {
		if(vals == null) {
			throw new IllegalArgumentException();
		}
		Set<String> ret = new HashSet<String>();
		for(String s : vals) {
			ret.add(s);
		}
		return ret;
	}
		
	private <T> List<T> makeList(T[] vals) {
		if(vals == null) {
			throw new IllegalArgumentException();
		}
		List<T> ret = new LinkedList<T>();
		for(T s : vals) {
			ret.add(s);
		}
		return ret;
	}
	
	private <T> boolean twoSetsEquals(Set<T> l1, Set<T> l2) {
		if(l1 == null || l2 == null || (l1.size() != l2.size())) {
			return false;
		}
		Iterator<T> i1 = l1.iterator();
		while(i1.hasNext()) {
			if(!l2.contains(i1.next())) {
				return false;
			}
		}
		return true;
	}
	
	private <T> boolean twoListsEquals(List<T> l1, List<T> l2) {
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
	/*
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
	*/
	
	//<================test DrinkDb=============>
	@Test
	public void testGetIngredientsReturnedValue() {
		List<String> res = db.getIngredients();
		assertTrue(twoListsEquals(res, getCategoritsExpected));
	}

	@Test
	public void testGetCategoriesReturnedValue() {
		List<String> res = db.getCategories();
		assertTrue(twoListsEquals(res, getIngredientsExpected));
	}

	@Test
	public void testGetFlavorsReturnedValue() {
		List<String> res = db.getFlavors();
		assertTrue(twoListsEquals(res, getFlavorsExpected));
	}
	
	/*
	@Test
	public void testGetDrinksReturnedValue() {
		//TODO: need to specify what drinks will be returned first
	}
	*/
	
	
	@Test
	public void testAddFavoriteMakeDrinkFavorite() {
		//TODO: class DrinkDb missing method getFavorite
	}
	
	
	@Test
	public void testAddRatingCanAddCorrectRatingToDrink() {
		for(int i = 1; i <= 5; i++) {
			db.addRating(drink, i);
			assertTrue(drink.rating == i);
		}
	}

	@Test(expected=IllegalArgumentException.class)
	public void testAddRatingWithWrongRatingThrowException() {
		for(int i = -10; i <= 10; i++) {
			if(i < 1 || i > 5) {
				boolean hasException = false;
				try {
					db.addRating(drink, i);
				} catch(Exception e) {
					hasException = true;
				}
				assertTrue(hasException);
			}			
		}
	}

	@Test
	public void testRemoveFavoriteRemovesTheDrinkFromFavoriteList() {
		//TODO: class DrinkDb missing method getFavorite
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

	@Test
	public void testTrainNoError() {

	}

	@Test
	public void testPredictNoError() {

	}

	@Test
	public void testPredictRatingNoError() {

	}
	//</test ML>

	//<test Drink>
	@Test
	public void testDrinkConstructor() {
		
	}

	@Test
	public void testGetIdNoError() {
		
	}

	@Test
	public void testConstructionAndToStringAreSame() {
		
	}

	@Test
	public void testGetRateAndSetRateAreSame() {
		
	}

	@Test
	public void testGetAttributesReturnValue() {
		
	}
	//</test Drink>
}