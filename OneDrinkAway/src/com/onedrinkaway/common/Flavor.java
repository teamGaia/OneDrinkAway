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
    public static final String SWEET = "sweet";
    public static final String CITRUSY = "citrusy";
    public static final String BITTER = "bitter";
    public static final String HERBAL = "herbal";
    public static final String MINTY = "minty";
    public static final String FRUITY = "fruity";
    public static final String SOUR = "sour";
    public static final String BOOZY = "boozy";
    public static final String SPICY = "spicy";
    public static final String SALTY = "salty";
    public static final String CREAMY = "creamy";
    
    public static final String[] flavorsArr = {
        "sweet", "citrusy", "bitter", "herbal", "minty", "fruity",
        "sour", "boozy", "spicy", "salty", "creamy"
    };
    public static final HashSet<String> flavors =
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
    
}
