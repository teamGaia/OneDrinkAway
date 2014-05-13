package com.onedrinkaway.db;

/**
 * Helper for DrinkDb
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;

public class DrinkData {
    
    // list of all drink objects
    private ArrayList<Drink> drinkList;
    // hashSet of all drink objects
    private HashSet<Drink> drinkSet;
    // set of all favorites
    private HashSet<Drink> favorites;
    // set of all ingredients
    private HashSet<String> ingredients;
    // maps drink names to drinks
    private HashMap<String, Drink> namesToDrinks;
    // maps DrinkId's to DrinkInfo
    private HashMap<Integer, DrinkInfo> info;
    
    /**
     * Sole Constructor
     */
    public DrinkData() {
        drinkList = new ArrayList<Drink>();
        drinkSet = new HashSet<Drink>();
        favorites = new HashSet<Drink>();
        ingredients = new HashSet<String>();
        namesToDrinks = new HashMap<String, Drink>();
        info = new HashMap<Integer, DrinkInfo>();
        buildFromFile();
        findDrinkInfo();
    }
    
    /**
     * Returns a list of all drinks
     */
    public List<Drink> getAllDrinks() {
        return drinkList;
    }
    
    /**
     * Returns a set of all drink names
     */
    public Set<String> getDrinkNames() {
        return namesToDrinks.keySet();
    }
    
    /**
     * Parses the master list of drink info, searching for drinks that exist in drinkSet
     */
    private void findDrinkInfo() {
        try {
            Scanner sc = new Scanner(new File("IngredientsLarge.txt"));
            List<String> lines = new ArrayList<String>();
            String line = sc.nextLine();
            while (sc.hasNext()) {
                if (line.equals("")) {
                    // found end of drink, process lines
                    String name = lines.get(0);
                    if (namesToDrinks.containsKey(name)) {
                        // we have this drink, build a DrinkInfo for it
                        for (String s : lines)
                            System.out.println(s);
                        System.out.println();
                        int len = lines.size();
                        String instructions = lines.get(len - 1);
                        String garnish = lines.get(len - 2);
                        List<String> ingr = new ArrayList<String>();
                        for (int i = 1; i < len - 2; i++) {
                            ingr.add(lines.get(i));
                        }
                        Drink d = namesToDrinks.get(name);
                        DrinkInfo di = new DrinkInfo(ingr, null, garnish, instructions, null, d.id);
                        info.put(d.id, di);
                    }
                    lines.clear();
                } else {
                    lines.add(line);
                }
                line = sc.nextLine();
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Builds this DrinkData from file
     */
    private void buildFromFile() {
        try {
            Scanner sc = new Scanner(new File("drinks.tsv"));
            sc.nextLine(); //throw away first line
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                String[] tokens = s.split("\t");
                String complete = tokens[2];
                if (complete.equals("1")) {
                    // valid drink, add to list
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    List<String> categories = new ArrayList<String>();
                    for (String c : tokens[3].split(", "))
                        categories.add(c);
                    String glass = tokens[4];
                    int[] attributes = new int[11];
                    for (int i = 0; i < 11; i++) {
                        attributes[i] = Integer.parseInt(tokens[i + 5]);
                    }
                    double rating = getAvgRating(id);
                    Drink d = new Drink(name, id, rating, attributes, categories, glass);
                    drinkList.add(d);
                    drinkSet.add(d);
                    namesToDrinks.put(name, d);
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Gets average user rating for a drink
     */
    private double getAvgRating(int drinkId) {
        Random r = new Random();
        return r.nextDouble() * 5;
    }

}
