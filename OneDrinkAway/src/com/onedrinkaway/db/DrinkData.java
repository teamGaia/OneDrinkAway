package com.onedrinkaway.db;

/**
 * Helper for DrinkDb
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import com.onedrinkaway.common.Drink;

public class DrinkData {
    
    // list of all drink objects
    private ArrayList<Drink> drinkList;
    // hashSet of all drink objects
    private HashSet<Drink> drinkSet;
    // favorites
    private HashSet<Drink> favorites;
    //
    private HashSet<String> ingredients;
    
    /**
     * Sole Constructor
     */
    public DrinkData() {
        buildFromFile();
    }
    
    /**
     * Builds this DrinkData from file
     */
    private void buildFromFile() {
        drinkList = new ArrayList<Drink>();
        try {
            Scanner sc = new Scanner(new File("Drinks.tsv"));
            sc.nextLine(); //throw away first line
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] tokens = s.split("\t");
                String complete = tokens[2];
                if (complete.equals("1")) {
                    // valid drink, add to list
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String category = tokens[3];
                    String glass = tokens[4];
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
