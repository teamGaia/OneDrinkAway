/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.common;

import java.util.List;

public class Drink {
	public String name;
	private double rate;
	private int[] attributes;
	
	/**
	 * This is a constructor of the Drink object
	 * @effect: construct a Drink object with default value
	 */
	public Drink() {

	}
	
	/**
	 * This is a constructor of the Drink object
	 * 
	 * @effect: construct a Drink object represented by parameter
	 * @param info: the JSON representation of Drink object
	 */
	public Drink(String info) {
		
	}
	
	/**
	 * 
	 * @return: the id of the Drink object
	 */
	public int getId() {
		return 0;
	}
	
	/**
	 * 
	 * @return: the list all the flavors
	 */
	public List<String> getFlavors() {
		return null;
	}
	
	/**
	 * 
	 * @return: the category the drink belongs to
	 */
	public String getCategory() {
		return null;
	}
	
	/**
	 * 
	 * @return: the list of redients of the drink
	 */
	public List<String> getRedients() {
		return null;
	}
	
	/**
	 * 
	 * @return: all the attributes of the drink that used in machine learning
	 */
	public int[] getAttributes() {
		return null;
	}
	
	/**
	 * 
	 * @return: the rate of the drink
	 */
	public double getRate() {
		return 0;
	}
	
	/**
	 * @effect: set the rate of this drink
	 * @param rate: new rate of this drink
	 */
	public void setRate(double rate) {
		
	}
	
	/**
	 * @return: the JSON representation of Drink Object
	 */
	@Override
	public String toString() {
		return null;
	}
}
