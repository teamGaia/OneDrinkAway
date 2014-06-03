package com.onedrinkaway.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Flavor;
import com.onedrinkaway.model.Query;
// import com.onedrinkaway.common.Drink;

/**
 * This class allows the user to enter what flavors and their intensity that they want to search by
 * on the Search By Flavor page
 * @author Nicole Kihara
 *
 */

public class SearchByFlavor extends OneDrinkAwayActivity {
	
	// the scroll view containing the flavors and seek bars
	private TableLayout flavorsScrollViewTable;
	
	// The Search button that enters the user's search
	Button flavorSearchButton;
	
	// Maps flavor string name to its current seekbar progress
	private Map<String, SeekBar> flavorSettings;

	/**
	 * Creates the layout for Search By Flavor
	 * @param savedInstanceState: keeps the information for this activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_flavor);
		helpID = R.string.search_by_flavor_help;
		flavorSettings = new HashMap<String, SeekBar>();
		setUpView();
	}
	
	private void setUpView() {		
		flavorsScrollViewTable = (TableLayout) findViewById(R.id.flavors_scroll_view_table);
		flavorSearchButton = (Button) findViewById(R.id.flavor_search_button);
		displayFlavors();	
	}

	/**
	 * Displays each flavor textView and it's seek bar
	 */
	private void displayFlavors() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String[] flavors = Arrays.copyOf(Flavor.flavorsArr, Flavor.flavorsArr.length);
		Arrays.sort(flavors);
		for (int i = 0; i < flavors.length; i++) {
			// Set the TextView in search_by_flavor_row
			View flavorRow = inflater.inflate(R.layout.activity_search_by_flavor_row, null);
			TextView flavorTextView = (TextView) flavorRow.findViewById(R.id.flavor_text_view);
			flavorTextView.setText(flavors[i]);
			View flavorRow2 = inflater.inflate(R.layout.activity_search_by_flavor_row2, null);
			// Set the SeekBar in search_by_flavor_row2
			SeekBar seekbar = (SeekBar) flavorRow2.findViewById(R.id.flavor_seek_bar);
			View flavorRow3 = inflater.inflate(R.layout.activity_search_by_flavor_row3, null);
			flavorSettings.put(flavors[i], seekbar);
			// Add each row to the view
			flavorsScrollViewTable.addView(flavorRow, i * 3);
			flavorsScrollViewTable.addView(flavorRow2, i * 3 + 1);
			flavorsScrollViewTable.addView(flavorRow3, i * 3 + 2);
		}
	}
		
	/**
	 * Does the search using the flavors the user selected, then sends the results
	 * to the results page for it to display
	 * @param view the view from which this method was called
	 */
	public void goToResults(View view) {
		Query query = new Query();
		for(String flavor: flavorSettings.keySet()) {
			int progress = flavorSettings.get(flavor).getProgress();
			if(progress != 0) {
				Flavor queryFlavor = new Flavor(flavor, progress);
				query.add(queryFlavor);
			}
		}
			

		boolean drinksFound = DrinkModel.searchForDrinks(query);
		

		if (!drinksFound) {
			Toast.makeText(getApplicationContext(),
					"No results found!",
					Toast.LENGTH_LONG).show();

		} else { 
	    	Intent intent = new Intent(this, ResultsPage.class);
	    	intent.putExtra("title", "Flavor Results");
			startActivity(intent);
		}
	}
}
