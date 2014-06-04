package com.onedrinkaway.app;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.onedrinkaway.R;

/**
 * This class is a custom array adapter for Search By Ingredients. It filters the list
 * when a user types in an ingredient, and checks the correct ingredient check boxes
 * @author nicolekihara
 *
 */

public class IngredientsArrayAdapter extends ArrayAdapter<String> {
	// keeps track of which ingredients are checked
	private Map<String, Boolean> myChecked;
	
	// The context (or activity) that is using this adapter
	private Context context;
	
	// When filtering, this keeps track of the ingredient's previous position
	private List<Integer> positions;
	
	// Maps each ingredient name to its original position in the overall list
	private Map<String, Integer> ingredientPosition;
	
	// All of the ingredients
	private List<String> allIngredients;
	
	// The list containing all of the ingredients to search by
	private ListView listView;
	
	// whether or not this is used for advanced search
	private AdvancedSearch as;

    public IngredientsArrayAdapter(Context context, int resource, int textViewResourceId,
    								List<String> objects, ListView listView, AdvancedSearch as) {
    	super(context, resource, textViewResourceId, objects); 
    	myChecked = new HashMap<String, Boolean>();
    	this.context = context;
		positions = new ArrayList<Integer>();
        ingredientPosition = new HashMap<String, Integer>();
        allIngredients = new ArrayList<String>();
    	allIngredients.addAll(objects);
    	this.listView = listView;
    	this.as = as;
    	
    	// initially put all checkboxes as un-checked
    	for (int i = 0; i < objects.size(); i++) {
    		myChecked.put(objects.get(i), false);
    	}

    	// put the correct position for each ingredient in the list
    	for (int i = 0; i < objects.size(); i++) {
    		ingredientPosition.put(objects.get(i), i);
    	}
    }

    /**
     * Checks/un-checks the checkbox for the ingredient when it is pressed
     * @param ingredient the ingredient to check/un-check
     */
    public void toggleChecked(String ingredient) {
    	if (myChecked.get(ingredient)) {
    		myChecked.put(ingredient, false);
    	} else {
    		myChecked.put(ingredient, true);
    	}
  
    	notifyDataSetChanged();
    }
 
    /**
     * Returns a list of all of the ingredients in the list that are checked
     * @return List<String> containing all of the checked ingredients
     */
	public List<String> getCheckedItems() {
		List<String> checkedItems = new ArrayList<String>();
		for (String ingredient : myChecked.keySet()) {
			if (myChecked.get(ingredient)) {
				checkedItems.add(ingredient);
			}
		}
		
		return checkedItems;
	}

	/**
	 * Displays the ingredients in the list view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CheckedTextView checkedTextView;
		if (row == null) {
			// create a new row
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.oda_ingredient_item, null);  
		}
		
		// get the checked text view in the row
		checkedTextView = (CheckedTextView) row.findViewById(R.id.ingredient_check_box);
		
		int prevPosition = position;
		if (positions.size() > 0) {
			// the list has been filtered, need to offset this position
			position = position + positions.get(0);
			// update this ingredient's position, it is now at prevPosition
			ingredientPosition.put(allIngredients.get(position), prevPosition);
		} else {
			// the list was not filtered, ingredient is in its original position
			ingredientPosition.put(allIngredients.get(position), position);
		}
		checkedTextView.setText(allIngredients.get(position));
	
		// set this item to be checked/un-checked based on myChecked
		listView.setItemChecked(prevPosition, myChecked.get(allIngredients.get(position)));
		
		// reset the on-click listener
		checkedTextView.setOnClickListener(null);
		checkedTextView.setOnClickListener(new OnClickListener() {

			/**
			 * Sets this text box to be checked or unchecked when the user presses it
			 */
			@Override
			public void onClick(View view) {
				String ingredient = ((CheckedTextView) view).getText().toString();
			    toggleChecked(ingredient);
				Boolean checked = myChecked.get(ingredient);
				listView.setItemChecked(ingredientPosition.get(ingredient), checked);
				if (as != null) {
					if (checked)
						as.query.add(ingredient);
					else
						as.query.remove(ingredient);
				}
			}
			
		});
		
		return row;
	}

	/**
	 * Filters the ingredients to display in the list based on the user-typed constraint
	 * @param constraint the user's text in the Search Box
	 */
	@SuppressLint("DefaultLocale")
	public void filterIngredients(CharSequence constraint) {
		// clear all of the ingredients to display
		clear();
		// start with a new positions list
		positions.clear();

		constraint = constraint.toString().toLowerCase();
		for (int i = 0; i < allIngredients.size(); i++) {
			// check each ingredient in the list to see if it starts with this constraint
			String ingredient = allIngredients.get(i);
			if (ingredient.toLowerCase().startsWith(constraint.toString()))  {
				// display this ingredient
				add(ingredient);
				// provide this ingredient's position
				positions.add(i);
			}
		}
		notifyDataSetChanged();
	}
	
	/**
	 * Returns the position of the given ingredient in the ListView
	 * @param ingr - String containing name of ingredient
	 * @return an int representing the position (0-based) of the 
	 * given ingredient in the ListView or null if not found
	 */
	public int getIngredientPosition(String ingr) {
		return ingredientPosition.get(ingr);
	}
}
