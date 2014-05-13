/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a user issued query, stores a name, category, list of flavors and
 * a list of ingredients. Some fields may be null, and the lists may be empty.
 * 
 * Ex: A query for drinks with two ingredients would contain a list with two ingredients,
 * with name and category set to null and flavors an empty list.
 * 
 * @author John L. Wilson
 *
 */

public class Query {
    
    private String name;
    private String category;
    private List<String> ingredients;
    private List<Flavor> flavors;
    
    /**
     * Constructor, initializes an empty query
     */
    public Query() {
        ingredients = new ArrayList<String>();
        flavors = new ArrayList<Flavor>();
    }
    
    /**'
     * Sets the name in this query
     * @param name the name to be searched for
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**'
     * Sets the category in this query
     * @param category the category to be searched for
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Adds an ingredient to this query
     * @param ing the ingredient to be searched for
     */
    public void add(String ing) {
        ingredients.add(ing);
    }
    
    /**
     * Adds a flavor to this query
     * @param fv the flavor to be searched for
     */
    public void add(Flavor fv) {		
    	if (!flavors.contains(fv))  // add this new flavor
			flavors.add(fv);
		else {  // remove the old flavor and replace it with this new value
			int index = flavors.indexOf(fv);
			flavors.remove(index);
			flavors.add(fv);
		}
    }
    
    /**
     * @return true if this query contains a name
     */
    public boolean hasName() {
        return name != null;
    }
    
    /**
     * @return true if this query contains a category
     */
    public boolean hasCategory() {
        return category != null;
    }
    
    /**
     * @return true if this query has ingredients
     */
    public boolean hasIngredients() {
        return ingredients.size() != 0;
    }
    
    /**
     * @return true if this query has flavors
     */
    public boolean hasFlavors() {
        return flavors.size() != 0;
    }
    
    /**
     * @return the name in this Query
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the category in this Query
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @return an unmodifiable list of the ingredients in this query
     */
    public List<String> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }
    
    /**
     * @return an unmodifiable list of the flavors in this query
     */
    public List<Flavor> getFlavors() {
        return Collections.unmodifiableList(flavors);
    }
}
