package com.onedrinkaway.app;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.Flavor;
import com.onedrinkaway.common.Query;
import com.onedrinkaway.model.DatabaseInterface;

public class SearchByFlavor extends OneDrinkAwayActivity {
	
	// the scroll view containing the flavors and seek bars
	private TableLayout flavorsScrollViewTable;
	
	// The Search button that enters the user's search
	Button flavorSearchButton;

	// The flavors to include in the search
	private Query query;
	
	// Error is true when no results were found
	private boolean error;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_flavor);
		helpID = R.string.search_by_flavor;
		query = new Query();
		error = false;
		
		flavorsScrollViewTable = (TableLayout) findViewById(R.id.flavors_scroll_view_table);
		flavorSearchButton = (Button) findViewById(R.id.flavor_search_button);
		flavorSearchButton.setOnClickListener(new flavorSearchButtonListener());
		displayFlavors();
	}

	/**
	 * Displays each flavor textView and it's seek bar
	 */
	private void displayFlavors() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		boolean displayError = false;
		if (error) {	// the last search was false
			error = false;
			// add a "No Results Found" textview
			TextView errorTextView = new TextView(this);
			errorTextView.setText(R.string.error_no_results_found);
			errorTextView.setGravity(Gravity.CENTER);
			errorTextView.setTextColor(Color.parseColor("#FF0000"));
			flavorsScrollViewTable.addView(errorTextView, 0);
			displayError = true;
		}
			
		for (int i = 0; i < Flavor.flavorsArr.length; i++) {
			// Set the TextView in search_by_flavor_row
			View flavorRow = inflater.inflate(R.layout.activity_search_by_flavor_row, null);
			TextView flavorTextView = (TextView) flavorRow.findViewById(R.id.flavor_text_view);
			flavorTextView.setText(Flavor.flavorsArr[i]);
			View flavorRow2 = inflater.inflate(R.layout.activity_search_by_flavor_row2, null);
			// Set the SeekBar in search_by_flavor_row2
			SeekBar seekbar = (SeekBar) flavorRow2.findViewById(R.id.flavor_seek_bar);
			seekbar.setOnSeekBarChangeListener(new FlavorSeekBarListener(Flavor.flavorsArr[i]));
			View flavorRow3 = inflater.inflate(R.layout.activity_search_by_flavor_row3, null);
			// Add each row to the view
			int j = i * 3;
			int k = i * 3 + 1;
			int l = i * 3 + 2;
			if (displayError) {
				j++;
				k++;
				l++;
			}
			flavorsScrollViewTable.addView(flavorRow, j);
			flavorsScrollViewTable.addView(flavorRow2, k);
			flavorsScrollViewTable.addView(flavorRow3, l);
		}
	}

	// The Search button listener for Search By Flavor
	public class flavorSearchButtonListener implements OnClickListener {
		
		@Override
		public void onClick(View arg0) {
			
			/*List<Drink> results = DatabaseInterface.getDrinks(query);
			if (results == null) {
				error = true;
				ViewGroup vg = (ViewGroup) findViewById(R.id.search_by_flavor);
				vg.invalidate();
			}*/
				
		}
	};
	
	public class FlavorSeekBarListener implements OnSeekBarChangeListener {
		int progressChanged = 0;
		String flavor;
		
		FlavorSeekBarListener(String flavor) {
			this.flavor = flavor;
		}
	
		/**
		 * Adds this flavor and its amount to the list of search flavors when the user changes
		 * the progress on this flavor's seek bar
		 */
		@Override
		public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
			progressChanged = progress;
			Flavor newFlavor = new Flavor(flavor, progressChanged);
			query.add(newFlavor);
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
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
					R.layout.fragment_search_by_flavor, container, false);
			return rootView;
		}
	}

}
