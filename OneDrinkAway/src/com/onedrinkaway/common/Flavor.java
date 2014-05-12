/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 */

package com.onedrinkaway.common;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents a Flavor, and it's value.
 * 
 * To avoid exceptions, use static variables, ex:
 * 
 * new Flavor(Flavor.sweet, 3);
 * 
 * @author John L. Wilson
 *
 */

public class Flavor {
    
    // Valid flavors:
    public static final String SWEET = "Sweet";
    public static final String CITRUSY = "Citrusy";
    public static final String BITTER = "Bitter";
    public static final String HERBAL = "Herbal";
    public static final String MINTY = "Minty";
    public static final String FRUITY = "Fruity";
    public static final String SOUR = "Sour";
    public static final String BOOZY = "Boozy";
    public static final String SPICY = "Spicy";
    public static final String SALTY = "Salty";
    public static final String CREAMY = "Creamy";
    
    public static final String[] flavorsArr = {"Bitter", "Boozy", "Citrusy", "Creamy", 
											   "Fruity", "Herbal", "Minty", "Salty", "Sour",
											   "Spicy", "Sweet" };
    
    static final HashSet<String> flavors =
            new HashSet<String>(Arrays.asList(flavorsArr));

    public final String name;
    public final int value;
    
    /**
     * Constructor, name must be a valid flavor, value must be [0-5] inclusive
     * @param name
     * @param value
     * @throws IllegalArgumentException if name or value are invalid
     */
    public Flavor(String name, int value) {
        if (value < 0 || value > 5)
            throw new IllegalArgumentException("value must be [0-5] inclusive");
        else if (!flavors.contains(name))
            throw new IllegalArgumentException("name must be a valid flavor");
        this.name = name;
        this.value = value;
    }
    
    /**
     * @return true if the two Flavors have the same flavor name and false otherwise
     */
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof Flavor))
    		return false;
    	else {
    		Flavor other = (Flavor) o;
    		if (other.name == other.name)
    			return true;
    		else
    			return false;
    	}
    	
    }
    
    /**
     * @return a hashCode for this Flavor
     */
    @Override
    public int hashCode() {
    	return this.name.hashCode();
    }
    
}
