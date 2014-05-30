package com.onedrinkaway.app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Query;

/**
 * This class implements the Search By Ingredient feature which allows users to select ingredients
 * they want to include in the cocktail they are looking for
 * @author Nicole Kihara
 *
 */

public class SearchByIngredient extends OneDrinkAwayActivity {
	
	// The list of ingredients to search by
	private ListView listView;
	
	private List<Integer> positions;
	
	private Map<String, Integer> ingredientPosition;
	
	// All of the ingredients
	private String[] allIngredients;
	
	MyArrayAdapter myArrayAdapter;
	
	/**
	 * Creates the layout for Search By Ingredient
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		helpID = R.string.help_search_by_ingredient;
		allIngredients = DrinkModel.getIngredients();
        ingredientPosition = new HashMap<String, Integer>();
		positions = new ArrayList<Integer>();
		setupSearchBox();
        setupListView();
    }
    
	/**
	 * Displays the Search Bar that appears at the top of the screen
	 */
    private void setupSearchBox() {
    	final EditText searchBox = (EditText) findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
        	 
        	 public void onTextChanged(CharSequence s, int start, int before, int count) {
        		 //get the text in the EditText
        		 myArrayAdapter.filterIngredients(s);
        		 // listView.clearChoices();
        	 }

        	 @Override
        	 public void beforeTextChanged(CharSequence s, int start, int count,
        	     int after) {
        	 }

        	 @Override
        	 public void afterTextChanged(Editable arg0) {
	        		 // TODO Auto-generated method stub
        	 }
        });
       
    }
    
    /**
     * Displays the list of ingredients to choose from in a scrolling list view
     */
    private void setupListView() {
    	for (int i = 0; i < allIngredients.length; i++) {
    		ingredientPosition.put(allIngredients[i], i);
    	}
    	listView = (ListView) findViewById(R.id.ingredient_list_view);
    	List<String> ingredientsList = new ArrayList<String>();
    	ingredientsList.addAll(Arrays.asList(allIngredients));
    	myArrayAdapter = new MyArrayAdapter(
    	          this,
    	          R.layout.oda_ingredient_item,
    	          R.id.ingredient_check_box,
    	          ingredientsList
    	          );
        
        listView.setAdapter(myArrayAdapter);
        listView.setTextFilterEnabled(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    /**
     * Does the search with the selected ingredients and displays them on the
     * Results page
     * @param view the view this method was called from
     */
    public void goToResults(View view) {
    	// Get all of the checked ingredients and add them to the query
    	Query query = new Query(); // make sure we are starting with an empty query!!
    	List<String> resultList = myArrayAdapter.getCheckedItems();
        for (int i = 0; i < resultList.size(); i++) {
        	query.add(resultList.get(i));
        }
    	
    	boolean drinksFound = DrinkModel.searchForDrinks(query);
    	if (!drinksFound) {
    		Toast.makeText(getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
    	} else {
			TextView errorTextView = (TextView) findViewById(R.id.ingredient_error_text_view);
			errorTextView.setText("");
        	Intent intent = new Intent(this, ResultsPage.class);
        	intent.putExtra("title", "Results");
    		startActivity(intent);
    	}
    }


public class MyArrayAdapter extends ArrayAdapter<String> {
    
    private Map<String, Boolean> myChecked;

    public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
    	super(context, resource, textViewResourceId, objects); 
    	myChecked = new HashMap<String, Boolean>();

    	for (int i = 0; i < objects.size(); i++) {
    		myChecked.put(objects.get(i), false);
    	}
    }

    public void toggleChecked(String ingredient) {
    	if (myChecked.get(ingredient)) {
    		myChecked.put(ingredient, false);
    	} else {
    		myChecked.put(ingredient, true);
    	}
  
    	notifyDataSetChanged();
    }
 
	public List<String> getCheckedItems() {
		List<String> checkedItems = new ArrayList<String>();
		for (String ingredient : myChecked.keySet()) {
			if (myChecked.get(ingredient)) {
				checkedItems.add(ingredient);
			}
		}
		
		return checkedItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CheckedTextView checkedTextView;
		if (row == null) {
			LayoutInflater inflater = getLayoutInflater();
			row = inflater.inflate(R.layout.oda_ingredient_item, null);  
		} /*else {
			checkedTextView = (CheckedTextView) row.findViewById(R.id.ingredient_check_box);
		}*/
		checkedTextView = (CheckedTextView) row.findViewById(R.id.ingredient_check_box);
		
		int prevPosition = position;
		if (positions.size() > 0) {
			position = position + positions.get(0);
			ingredientPosition.put(allIngredients[position], prevPosition);
		} else {
			ingredientPosition.put(allIngredients[position], position);
		}
		checkedTextView.setText(allIngredients[position]);
	
		listView.setItemChecked(prevPosition, myChecked.get(allIngredients[position]));
		
		checkedTextView.setOnClickListener(null);
		checkedTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String ingredient = ((CheckedTextView) view).getText().toString();
			    myArrayAdapter.toggleChecked(ingredient);
			    Log.i("ingredient", ingredient);
				Boolean checked = myChecked.get(ingredient);
				listView.setItemChecked(ingredientPosition.get(ingredient), checked);
			}
			
		});
		
		return row;
	}
	
	public void filterIngredients(CharSequence constraint) {
		clear();
		positions.clear();

		constraint = constraint.toString().toLowerCase();
		for (int i = 0; i < allIngredients.length; i++) {
			String ingredient = allIngredients[i];
			if (ingredient.toLowerCase().startsWith(constraint.toString()))  {
				add(ingredient);
				positions.add(i);
			}
		}
		notifyDataSetChanged();
	}
}

}