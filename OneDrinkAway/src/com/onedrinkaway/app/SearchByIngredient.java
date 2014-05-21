package com.onedrinkaway.app;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Query;

/**
 * This class implements the Search By Ingredient feature which allows users to select ingredients
 * they want to include in the cocktail they are looking for
 * @author Nicole Kihara
 *
 */

public class SearchByIngredient extends OneDrinkAwayActivity implements SearchView.OnQueryTextListener {

	// The ingredients to include in the search
	private String[] ingredients;
	
	// The list of ingredients to search by
	private ListView listView;
	
	// Tells whether or not there was an error doing the search
	private boolean error;
	
	/**
	 * Creates the layout for Search By Ingredient
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		helpID = R.string.help_search_by_ingredient;
		error = false;
		ingredients = DrinkModel.getIngredients();
		setupSearchView();
        setupListView();
    }
    
	/**
	 * Displays the Search Bar that appears at the top of the screen
	 */
    private void setupSearchView() {
    	SearchView srchView = (SearchView) findViewById(R.id.ingredient_search_view);
        srchView.setIconifiedByDefault(false);
        srchView.setOnQueryTextListener(this);
        srchView.setQueryHint("Enter Ingredient Here");
        
        // Displays error message if no results were found
        if (error) {
        	TextView errorTextView = (TextView) findViewById(R.id.ingredient_error_text_view);
        	errorTextView.setText(R.string.error_no_results_found);
			errorTextView.setGravity(Gravity.CENTER);
			errorTextView.setTextColor(Color.parseColor("#FF0000"));
        }
    }
    
    /**
     * Displays the list of ingredients to choose from in a scrolling list view
     */
    private void setupListView() {
    	listView = (ListView) findViewById(R.id.ingredient_list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.oda_ingredient_item,
                ingredients));
        listView.setTextFilterEnabled(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    /**
     * Does the search with the selected ingredients and displays them on the
     * Results page
     * @param view the view this method was called from
     */
    public void goToResults(View view) {
    	SparseBooleanArray checked = listView.getCheckedItemPositions();
    	int size = listView.getCount();
    	// Get all of the checked ingredients and add them to the query
    	Query query = new Query(); // make sure we are starting with an empty query!!
    	for(int i = 0; i < size; i++) {
            if(checked.get(i))
            	query.add(listView.getItemAtPosition(i).toString());
    	}
    	
    	boolean drinksFound = DrinkModel.searchForDrinks(query);
    	if (!drinksFound) {
    		error = true;		
    		setupSearchView();
            setupListView();
    	} else {
        	Intent intent = new Intent(this, ResultsPage.class);
        	intent.putExtra("title", "Results");
    		startActivity(intent);
    	}
    }

    /**
     * Displays the ingredients that match the text in the search bar
     */
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    /**
     * Does nothing when the user submits text to the search bar
     */
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}


	/**
	 * A placeholder fragment containing a simple view.
	 */
/*	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_search_by_ingredient, container, false);
			return rootView;
		}
	}
*/
