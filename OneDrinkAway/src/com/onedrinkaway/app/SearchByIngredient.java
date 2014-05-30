package com.onedrinkaway.app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
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
	
	// Custom array adapter which keeps track of which ingredients are checked
	IngredientsArrayAdapter myArrayAdapter;
	
	/**
	 * Creates the layout for Search By Ingredient
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		helpID = R.string.help_search_by_ingredient;
		setupSearchBox();
        setupListView();
    }
    
	/**
	 * Displays the Search Box that appears at the top of the screen
	 */
    private void setupSearchBox() {
    	EditText searchBox = (EditText) findViewById(R.id.searchBox);
    	// A TextWatcher is a listener for the searchBox
        searchBox.addTextChangedListener(new TextWatcher() {
        	 
        	/**
        	 * When a user enters text in the text box, this filters the list of ingedients
        	 * to display
        	 */
        	 public void onTextChanged(CharSequence s, int start, int before, int count) {
        		 //get the text in the EditText
        		 myArrayAdapter.filterIngredients(s);
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
    	ListView listView = (ListView) findViewById(R.id.ingredient_list_view);
    	List<String> ingredientsList = new ArrayList<String>();
    	ingredientsList.addAll(Arrays.asList(DrinkModel.getIngredients()));
    	myArrayAdapter = new IngredientsArrayAdapter(
    	          this,
    	          R.layout.oda_ingredient_item,
    	          R.id.ingredient_check_box,
    	          ingredientsList,
    	          listView
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
    		// no drinks were found, hide the keyboard and display a toast
    		EditText searchBox = (EditText) findViewById(R.id.searchBox);
    		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
    		imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
    		Toast.makeText(getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
    	} else {
    		// go to results page
        	Intent intent = new Intent(this, ResultsPage.class);
        	intent.putExtra("title", "Results");
    		startActivity(intent);
    	}
    }

}