package com.onedrinkaway.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Stores all data necessary for DrinkInfo page.
 * 
 * Some drinks may not have a garnish, source or instructions, thus these fields
 * may be null.
 * 
 * @author John L. Wilson
 */
public class DrinkInfo implements Serializable {
    
    private static final long serialVersionUID = -4663291903849832155L;
    /** unmodifiable list */
    public final List<String> ingredients;
    /** Garnish for this drink, may be null or "No Garnish" */
    public final String garnish;
    public final String instructions;
    public final int drinkId;
    
    /**
     * Sole Constructor
     * @param ingredients the list of ingredients
     * @param description the description of this drink
     * @param instructions the instructions for this drink
     * @param source the source of the description
     * @param drinkId the drinkId this DrinkInfo corresponds to
     */
    public DrinkInfo(List<String> ingredients, String garnish, String instructions, int drinkId) {
        this.ingredients = Collections.unmodifiableList(ingredients);
        this.garnish = garnish;
        this.instructions = instructions;
        this.drinkId = drinkId;
    }

}