package com.onedrinkaway.app;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;

/**
 * Displays the main Home Screen of the OneDrinkAway application
 * @author Nicole Kihara, Andrea Martin, and Taylor Juve
 *
 */
public class HomePage extends OneDrinkAwayActivity {
    
    public static Context appContext;
    
    /**
     * Creates the layout for the Home Screen
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		
		// setup global context for database
		appContext = getApplicationContext();
		
		helpID = R.string.help_home_page;
		getSupportActionBar().setTitle(R.string.app_name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		
		checkNewUser();
	}
	
	private void checkNewUser() {
		final String PREFS_NAME = "MyPrefsFile";

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

		// if (settings.getBoolean("my_first_time", true)) {
		    //the app is being launched for first time launch new user dialog       
			DialogFragment newFragment = new NewUserDialog();
		    newFragment.show(getFragmentManager(), "newUser");

		    // record the fact that the app has been started at least once
		    //settings.edit().putBoolean("my_first_time", false).commit(); 
		//}
	}
	
	/**
	 * Goes to the screen of the feature the user pressed
	 * @param view
	 */
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
			DrinkModel.findTrySomethingNewDrinks();
			intent = new Intent(this, ResultsPage.class);
			intent.putExtra("title", "New Drinks Just For You");
		}
		startActivity(intent);
	}
	
	/**
	 * A back button listener
	 */
	@Override
	public void onBackPressed() {
        moveTaskToBack(true);
	}
	
	/**
	 * The menu options listener
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		if (item.getItemId() != R.id.action_home) {
			return super.onOptionsItemSelected(item);
		}
		return true;
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
