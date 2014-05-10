package com.onedrinkaway.app;

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

public class SearchByFlavor extends OneDrinkAwayActivity {
	
	// the scroll view containing the flavors and seek bars
	private TableLayout flavorsScrollViewTable;
	
	// The Search button that enters the user's search
	Button flavorSearchButton;
	
	// All of the flavors to display
	private String[] flavors = {"Bitter", "Boozy", "Citrusy", "Creamy", 
								"Fruity", "Herbal", "Minty", "Salty", "Sour",
								"Spicy", "Sweet" };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_flavor);
		helpID = R.string.search_by_flavor;
		
		flavorsScrollViewTable = (TableLayout) findViewById(R.id.flavorsScrollViewTable);
		flavorSearchButton = (Button) findViewById(R.id.flavorSearchButton);
		flavorSearchButton.setOnClickListener(new flavorSearchButtonListener());
		displayFlavors();
	}

	private void displayFlavors() {
		for (int i = 0; i < flavors.length; i++) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View flavorRow = inflater.inflate(R.layout.activity_search_by_flavor_row, null);
			TextView flavorTextView = (TextView) flavorRow.findViewById(R.id.flavorTextView);
			flavorTextView.setText(flavors[i]);
			SeekBar seekbar = (SeekBar) flavorRow.findViewById(R.id.flavorSeekBar);
			seekbar.setOnSeekBarChangeListener(new flavorSeekBarListener());
			flavorsScrollViewTable.addView(flavorRow, i);
		}
	}

	// The Search button listener for Search By Flavor
	public class flavorSearchButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public class flavorSeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			
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
