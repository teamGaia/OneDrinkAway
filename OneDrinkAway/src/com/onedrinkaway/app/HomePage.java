package com.onedrinkaway.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
		if (view.getId() == R.id.search_by_name) {
			intent = new Intent(this, SearchByName.class);
		}
		startActivity(intent);
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
