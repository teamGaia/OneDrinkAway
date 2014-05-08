package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onedrinkaway.R;

public class HomePage extends OneDrinkAwayActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// My code
		helpID = R.string.home_page_help;
		getSupportActionBar().setTitle(R.string.app_name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	public void goToActivity(View view) {
		Intent intent = null;
		int viewID = view.getId();
		if (viewID == R.id.search_by_name) {
			intent = new Intent(this, SearchByName.class);
		} else if(viewID == R.id.search_by_category) {
			intent = new Intent(this, SearchByCategory.class);
		} else if(viewID == R.id.search_by_flavor) {
			intent = new Intent(this, SearchByFlavor.class);
		} else if(viewID == R.id.search_by_ingredient) {
			intent = new Intent(this, SearchByIngredient.class);
		} else if(viewID == R.id.favorites) {
			intent = new Intent(this, FavoriteDrinks.class);
		} else if(viewID == R.id.advanced_search) {
			intent = new Intent(this, AdvancedSearch.class);
		} else if(viewID == R.id.try_something_new) {
			intent = new Intent(this, DrinkInfoPage.class);
		}
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
	   this.finish();
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
			View rootView = inflater.inflate(R.layout.fragment_home_page,
					container, false);
			return rootView;
		}
	}

}
