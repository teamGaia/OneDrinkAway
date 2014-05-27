package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Flavor;
import com.onedrinkaway.model.Query;

/**
 * This class implements the Advanced Search feature which allows the user to
 * search by flavors, category, and ingredients all at once
 * @author nicolekihara
 *
 */

public class AdvancedSearch extends OneDrinkAwayActivity {
	public Query query;
	private String[] categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search);
		helpID = R.string.advanced_search_help;
		query = new Query();
		categories = DrinkModel.getCategories();
		
		FragmentTabHost mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Category"),
            CategoryFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Ingredients"),
            IngredientFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Flavors"),
            FlavorFragment.class, null);
	}
	
	@SuppressWarnings("deprecation") 
	public void categorySelected(View view) {
		String selectedCat = (String) ((TextView) view).getText();
		
		if (!query.hasCategory()) {
			query.setCategory(selectedCat);
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));		
		} else {
			String oldCategory = query.getCategory();
			if (selectedCat.equals(oldCategory)) {
				query.setCategory(null);
				view.setBackgroundDrawable(null);
			} else {
				query.setCategory(selectedCat);
				int oldCatID = indexOfCategory(oldCategory);
				int viewID = getResources().getIdentifier("" + oldCatID, "id", "com.onedrinkaway");
				
				TextView tv = (TextView) findViewById(viewID);
				if (tv != null) {
					tv.setBackgroundDrawable(null);
					view.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));	
				}				
			}
		}
	}
	
	public int indexOfCategory(String cat) {
		for (int i = 0; i < categories.length; i++) {
			if (categories[i].equals(cat)) {
				return i;
			}
		}
		return -1;
	}
	
	public void goToResults(View view) {
		// For debugging purposes
		if (query.getCategory() != null)
			Log.d("Category", query.getCategory());
    	for (String ingr : query.getIngredients()) 
    		Log.d("Ingredients", ingr);
    	for (Flavor flav : query.getFlavors())
    		Log.d("Flavors", flav.name + ": " + flav.value);
    	
    	
		if (DrinkModel.searchForDrinks(query)) {
		    startActivity(new Intent(this, ResultsPage.class));
		} else {
			Toast.makeText(getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
		}
	}
}
