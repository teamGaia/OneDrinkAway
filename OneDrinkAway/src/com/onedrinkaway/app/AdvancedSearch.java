package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;

/**
 * This class implements the Advanced Search feature which allows the user to
 * search by flavors, category, name, and ingredients all at once
 * @author nicolekihara
 *
 */

public class AdvancedSearch extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search);
		
		FragmentTabHost mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Category"),
            CategoryFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Ingredients"),
            IngredientFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Flavors"),
            FlavorFragment.class, null);
	}
	
	public void goToResults(View view) {
//		Intent intent = new Intent(this, ResultsPage.class);
//		String categoryName = (String) ((TextView) view).getText();
//		query.setCategory(categoryName);
//		boolean drinksFound = DrinkModel.searchForDrinks(query);
//		if (drinksFound) {
//		    // hurray found at least one drink, go to ResultsPage
//		    intent.putExtra("title", categoryName);
//		    startActivity(intent);
//		    
//		} else {
			Toast.makeText(getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
//		}
	}
}
