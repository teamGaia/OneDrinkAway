package com.onedrinkaway.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.onedrinkaway.common.Drink;


public class TestData {
	

    	public static int[] alphabetizeAttributes(Drink drink) {
		
		int[] attributes = drink.attributes;
		int[] alphabetized = new int[attributes.length];
		alphabetized[0] = attributes[7]; //Boozy
		alphabetized[1] = attributes[2]; //Bitter
		alphabetized[2] = attributes[1]; //Citrusy
		alphabetized[3] = attributes[10]; //Creamy
		alphabetized[4] = attributes[5]; //Fruity
		alphabetized[5] = attributes[3]; //Herbal
		alphabetized[6] = attributes[4]; //Minty
		alphabetized[7] = attributes[9]; //Salty
		alphabetized[8] = attributes[6]; //Sour
		alphabetized[9] = attributes[8]; //Spicy
		alphabetized[10] = attributes[0]; //Sweet
		
		
		return alphabetized;
	}
	
}
