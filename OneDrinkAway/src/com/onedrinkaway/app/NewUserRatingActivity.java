package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;

import com.onedrinkaway.R;

/**
 * Shows the new user two ways to rate common drinks, by name and by common drinks
 */
public class NewUserRatingActivity extends OneDrinkAwayActivity {

	/**
	 * Creates the layout for the page, adds the Find and Rate tab and the Rate Common Drinks tab
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user_rating);
		
		helpID = R.string.new_user_help;
		
		FragmentTabHost mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Popular Drinks"),
            NewUserRateCommonDrinks.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("All Drinks"),
                NewUserRateByName.class, null);
	}
	
	/**
	 * Goes home when a user clicks finish
	 * @param view View of the finish button
	 */
	public void goHome(View view) {
		Intent intent = new Intent(this, HomePage.class);
		startActivity(intent);
	}
}
