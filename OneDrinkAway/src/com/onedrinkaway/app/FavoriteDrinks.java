package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.onedrinkaway.R;

public class FavoriteDrinks extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_drinks);

		helpID = R.string.favorite_drinks_help;
		
		 ListView view = (ListView) findViewById(R.id.list_view_favorites);
	        view.setAdapter(new ArrayAdapter<String>(this,
	                R.layout.oda_result_item,
	                R.id.result_title,
	                tempResults.RESULTS));
		

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
			View rootView = inflater.inflate(R.layout.fragment_favorite_drinks,
					container, false);
			return rootView;
		}
		
	}

}
