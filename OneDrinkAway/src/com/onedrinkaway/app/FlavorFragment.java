package com.onedrinkaway.app;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Flavor;

/**
 * This class allows the user to enter what flavors and their intensity that they want to search by
 * on Advanced Search
 * @author Taylor Juve
 *
 */

public class FlavorFragment extends Fragment {
	private TableLayout flavorsScrollViewTable;
	
	/**
	 * Fills this FlavorFragment view with all content
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_flavor, null);
		flavorsScrollViewTable = (TableLayout) view.findViewById(R.id.flavors_scroll_view_table);
		displayFlavors();	
		return view;
	}
	
	/**
	 * Displays each flavor textView and it's seek bar
	 */
	private void displayFlavors() {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		for (int i = 0; i < Flavor.flavorsArr.length; i++) {
			// Set the TextView in search_by_flavor_row
			View flavorRow = inflater.inflate(R.layout.activity_search_by_flavor_row, null);
			TextView flavorTextView = (TextView) flavorRow.findViewById(R.id.flavor_text_view);
			flavorTextView.setText(Flavor.flavorsArr[i]);
			View flavorRow2 = inflater.inflate(R.layout.activity_search_by_flavor_row2, null);
			// Set the SeekBar in search_by_flavor_row2
			SeekBar seekbar = (SeekBar) flavorRow2.findViewById(R.id.flavor_seek_bar);
			seekbar.setId(Flavor.flavorsArr[i].hashCode());
			seekbar.setOnSeekBarChangeListener(new FlavorSeekBarListener(Flavor.flavorsArr[i]));
			View flavorRow3 = inflater.inflate(R.layout.activity_search_by_flavor_row3, null);
			// Add each row to the view
			flavorsScrollViewTable.addView(flavorRow, i * 3);
			flavorsScrollViewTable.addView(flavorRow2, i * 3 + 1);
			flavorsScrollViewTable.addView(flavorRow3, i * 3 + 2);
		}
	}
	
	/**
	 * Updates this FlavorFragment's view to reflect the user's previous selections
	 */
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AdvancedSearch as = (AdvancedSearch) getActivity();
        List<Flavor> selectedFlavs = as.query.getFlavors();
        for (int i = 0; i < selectedFlavs.size(); i++) {
        	Flavor flav = selectedFlavs.get(i);
        	SeekBar seekbar = (SeekBar) as.findViewById(flav.name.hashCode());
        	if (seekbar != null) {
        		seekbar.setProgress(flav.value);
        	}
        }
	}
	
	/**
	 * Class for a Seek Bar Listener that adjusts what flavors to search by depending
	 * on the number the user adjusts the seek bar to
	 */
	public class FlavorSeekBarListener implements OnSeekBarChangeListener {
		int progressChanged = 0;
		String flavor;
		
		/**
		 * Creates a new FlavorSeekBarListener
		 * @param flavor: the flavor this seek bar is for
		 */
		FlavorSeekBarListener(String flavor) {
			this.flavor = flavor;
		}
	
		/**
		 * Adjusts the flavor intensity to search by when the user changes
		 * the progress on this flavor's seek bar
		 */
		@Override
		public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
			Flavor newFlavor = new Flavor(flavor, progress);
			AdvancedSearch as = (AdvancedSearch) getActivity();
			as.query.add(newFlavor);
		}
		

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
	}
}
