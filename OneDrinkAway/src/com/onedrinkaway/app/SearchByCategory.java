package com.onedrinkaway.app;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onedrinkaway.R;

public class SearchByCategory extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_category);
		
		helpID = R.string.search_by_category;
		
		//String[] categories = (String[]) DatabaseInterface.getCategories().toArray();
		String[] categories = TempListOfCategories.CATEGORIES; //temp
		displayCategories(categories);
	}
	
	private void displayCategories(String[] categories) {
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout container = (LinearLayout) findViewById(R.id.category_space);
		LinearLayout curRow = null;
		for (int i = 0; i < categories.length; i++) {
			// Add a new row (horizontal LinearLayout) every 3 TextViews
			if (i % 3 == 0) {
				curRow = new LinearLayout(this);
				container.addView(curRow);
			}
			
			// Add TextView
			TextView category = (TextView) inflater.inflate(R.layout.category_text_area, null);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
			category.setLayoutParams(param);
			category.setText(categories[i]);
			curRow.addView(category);
		}
	}

	public void goToResults(View view) {
		startActivity(new Intent(this, ResultsPage.class));
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_search_by_category, container, false);
			return rootView;
		}
	}
}

class TempListOfCategories {
	public static final String[] CATEGORIES = {"Martinis", "Margaritas", "Tropical", 
		"Sours", "Screwdrivers", "Daiquiris", "On the Rocks", "Shooters", 
		"Jello Shots", "Classic", "Holiday", "Non-Alcoholic", "Martinis", "Margaritas", "Tropical", 
		"Sours", "Screwdrivers", "Daiquiris", "On the Rocks", "Shooters", 
		"Jello Shots", "Classic", "Holiday", "Non-Alcoholic"};
}