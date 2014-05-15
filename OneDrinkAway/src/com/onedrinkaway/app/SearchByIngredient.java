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
import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.model.DatabaseInterface;


public class SearchByIngredient extends OneDrinkAwayActivity implements SearchView.OnQueryTextListener {

	private String[] ingredients;
    
	// The ingredients to include in the search
	private Query query;
	
	private ListView listView;
	
	private boolean error;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		helpID = R.string.help_search_by_ingredient;
		error = false;
		query = new Query();
		ingredients = DatabaseInterface.getIngredients();
		setupSearchView();
        setupListView();
    }
    
    private void setupSearchView() {
    	SearchView srchView = (SearchView) findViewById(R.id.ingredient_search_view);
        srchView.setIconifiedByDefault(false);
        srchView.setOnQueryTextListener(this);
        srchView.setQueryHint("Enter Ingredient Here");
        
        if (error) {
        	TextView errorTextView = (TextView) findViewById(R.id.ingredient_error_text_view);
        	errorTextView.setText(R.string.error_no_results_found);
			errorTextView.setGravity(Gravity.CENTER);
			errorTextView.setTextColor(Color.parseColor("#FF0000"));
        }
    }
    
    private void setupListView() {
    	listView = (ListView) findViewById(R.id.ingredient_list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.oda_ingredient_item,
                ingredients));
        listView.setTextFilterEnabled(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    public void goToResults(View view) {
    	SparseBooleanArray checked = listView.getCheckedItemPositions();
    	int size = listView.getCount();
    	for(int i = 0; i < size; i++) {
            if(checked.get(i))
            	query.add(listView.getItemAtPosition(i).toString());
    	}
    	
    	Drink[] results = DatabaseInterface.getAllDrinks();
   
    	if (results.length == 0) {
    		displayError();
    	} else {
        	Intent intent = new Intent(this, ResultsPage.class);
        	intent.putExtra("title", "Results");
        	intent.putExtra("results", results);
    		startActivity(intent);
    	}
    }
    
    private void displayError() {
    	error = true;
    	// invalidate();
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

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
