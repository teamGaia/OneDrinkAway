package com.onedrinkaway.common;

/*
import foo.Drink.Category;
import foo.Drink.Glass;
*/

/**
 * Represents a Drink, stores a name, rating, flavor attributes
 * 
 * @author John L. Wilson
 *
 */

public class Drink {
  public final String name;
  public final int id;
  public final double rating;
  /**
   * Flavors, all [0,5]. Each index corresponds to a flavor:
   * [0] : SWEET
   * [1] : CITRUSY
   * [2] : BITTER
   * [3] : HERBAL
   * [4] : MINTY
   * [5] : FRUITY
   * [6] : SOUR
   * [7] : BOOSY
   * [8] : SPICY
   * [9] : SALTY
   * [10] : CREAMY
   */
  public final int[] attributes;
  public final Category category;
  public final Glass glass;
  public final boolean rated;
  
  /**
   * Sole Constructor
   * @param name the name of this drink
   * @param id the unique id of this drink
   * @param rating the average user rating of this drink, 
   * @param attributes
   * @param c the Category of this drink
   * @param g the Glass of this drink
   */
  public Drink(String name, int id, double rating, int[] attributes,
               Category category, Glass glass, boolean rated) {
    this.name = name;
    this.id = id;
    this.rating = rating;
    this.attributes = attributes;
    this.category = category;
    this.glass = glass;
    this.rated = rated;
  }
  
  /**
   * Valid Categories 
   */
  public enum Category {
    SHAKEN, STIRRED, SHOOTER, FROZEN, ON_THE_ROCKS, HOT
  }
  
  /**
   * Valid Glasses
   */
  public enum Glass {
    HIGHBALL, MARTINI, SHOT, COLLINS, COCKTAIL, PINT, MUG, TALL, ROCKS
  }
  
  public boolean equals(Object obj){
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if(!(obj instanceof Drink))
      return false;
    return id == ((Drink)obj).id;
  }
}