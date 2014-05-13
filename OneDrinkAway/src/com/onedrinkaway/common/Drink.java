package com.onedrinkaway.common;

import java.util.List;

import com.onedrinkaway.db.DrinkDb;

/**
 * Represents a Drink, stores a name, ratings, flavor attributes
 * 
 * @author John L. Wilson, someone else...
 *
 */

public class Drink implements Comparable<Drink> {
  public final String name;
  public final int id;
  /** Average user rating */
  private double avgRating;
  /** Predicted user rating, set to -1 if it has not been set by ML */
  public double predictedRating;
  /** user rating, set to -1 if the user has not rated this Drink */
  private int userRating;
  /**
   * Flavors, all [0,5]. Each index corresponds to a flavor:
   * [0] : SWEET
   * [1] : CITRUSY
   * [2] : BITTER
   * [3] : HERBAL
   * [4] : MINTY
   * [5] : FRUITY
   * [6] : SOUR
   * [7] : BOOZY
   * [8] : SPICY
   * [9] : SALTY
   * [10] : CREAMY
   */
  public final int[] attributes;
  public final List<String> categories;
  public final String glass;
  
  /**
   * Sole Constructor
   * @param name the name of this drink
   * @param id the unique id of this drink
   * @param avgRating the average user rating of this drink, 
   * @param attributes
   * @param category the Category of this drink
   * @param glass the Glass of this drink
   */
  public Drink(String name, int id, double avgRating, int[] attributes,
               List<String> categories, String glass) {
    this.name = name;
    this.id = id;
    this.avgRating = avgRating;
    this.attributes = attributes;
    this.categories = categories;
    this.glass = glass;
    predictedRating = -1;
    userRating = -1;
  }
  
  /**
   * Adds a user rating to this Drink
   * 
   * @param score the rating to add
   */
  public void addUserRating(int score) {
      userRating = score;
      DrinkDb.addRating(this, score);
  }
  
  /**
   * Gets average user rating for this drink
   */
  public double getAvgRating() {
      return avgRating;
  }
  
  /**
   * Gets the user rating for this drink, returns -1 if drink has not been rated
   */
  public int getUserRating() {
      return userRating;
  }
  
  /**
   * Gets the rating for this Drink. Returns the rating with highest precedence.
   * Precedence: userRating > predictedRating > avgRating
   * @return the rating for this Drink
   */
  public double getRating() {
	  if (userRating > 0) // check if user rating is set
		  return (double) userRating;
	  else if (predictedRating > 0) // check if predRating is set
		  return predictedRating;
	  else
		  return avgRating;
  }
  
  /**
   * hashCode for this Drink
   */
  public int hashCode() {
      return id;
  }
  
  /**
   * Returns true if obj equals this, false if not
   */
  public boolean equals(Object obj){
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if(!(obj instanceof Drink))
      return false;
    return id == ((Drink)obj).id;
  }

  /**
   * Returns 1 if this is greater than other, -1 if this is less, 0 if equal
   */
  @Override
  public int compareTo(Drink other) {
      if (getRating() - other.getRating() > 0)
          return 1;
      else if (getRating() - other.getRating() < 0)
          return -1;
      else
          return 0;
  }
}