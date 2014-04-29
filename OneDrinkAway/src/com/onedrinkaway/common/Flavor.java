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
    public static final String sweet = "sweet";
    public static final String citrusy = "citrusy";
    public static final String bitter = "bitter";
    public static final String herbal = "herbal";
    public static final String minty = "minty";
    public static final String fruity = "fruity";
    public static final String sour = "sour";
    public static final String boosy = "boosy";
    public static final String spicy = "spicy";
    public static final String salty = "salty";
    public static final String creamy = "creamy";
    
    public static final String[] flavorsArr = {
        sweet, citrusy, bitter, herbal, minty, fruity,
        sour, boosy, spicy, salty, creamy
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
