package com.onedrinkaway.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.onedrinkaway.common.Flavor;

public class SearchByFlavor extends OneDrinkAwayActivity {
	
	// the scroll view containing the flavors and seek bars
	private TableLayout flavorsScrollViewTable;
	
	// The Search button that enters the user's search
	Button flavorSearchButton;


	// The flavors to include in the search
	private List<Flavor> searchFlavors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_flavor);
		helpID = R.string.search_by_flavor;
		searchFlavors = new ArrayList<Flavor>();
		
		flavorsScrollViewTable = (TableLayout) findViewById(R.id.flavors_scroll_view_table);
		flavorSearchButton = (Button) findViewById(R.id.flavor_search_button);
		flavorSearchButton.setOnClickListener(new flavorSearchButtonListener());
		displayFlavors();
	}

	/**
	 * Displays each flavor textView and it's seek bar
	 */
	private void displayFlavors() {
		for (int i = 0; i < Flavor.flavorsArr.length; i++) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			flavorsScrollViewTable.addView(flavorRow, i * 3);
			flavorsScrollViewTable.addView(flavorRow2, i * 3 + 1);
			flavorsScrollViewTable.addView(flavorRow3, i * 3 + 2);
		}
	}

	// The Search button listener for Search By Flavor
	public class flavorSearchButtonListener implements OnClickListener {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public class FlavorSeekBarListener implements OnSeekBarChangeListener {
		int progressChanged = 0;
		String flavor;
		
		FlavorSeekBarListener(String flavor) {
			this.flavor = flavor;
		}
	
		@Override
		public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
			progressChanged = progress;
			Flavor newFlavor = new Flavor(flavor, progressChanged);
			if (!searchFlavors.contains(newFlavor))
				searchFlavors.add(newFlavor);
			else {
				int index = searchFlavors.indexOf(newFlavor);
				searchFlavors.remove(index);
				searchFlavors.add(newFlavor);
			}
				
				
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
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
