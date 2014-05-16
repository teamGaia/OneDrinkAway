package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onedrinkaway.R;

/**
 * This class implements the Advanced Search feature which allows the user to
 * search by flavors, category, name, and ingredients all at once
 * @author nicolekihara
 *
 */

public class AdvancedSearch extends OneDrinkAwayActivity {

	/**
	 * Creates the layout for Advanced Search
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search);

		
		helpID = R.string.advanced_search_help;
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
			View rootView = inflater.inflate(R.layout.fragment_advanced_search,
					container, false);
			return rootView;
		}
	}

}
