package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.onedrinkaway.R;

public class NewUserRatingActivity extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user_rating);
		
		FragmentTabHost mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Find and Rate"),
            NewUserRateByName.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Rate Common Drinks"),
            NewUserRateCommonDrinks.class, null);
	}
}
