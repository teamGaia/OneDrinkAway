package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;

import com.onedrinkaway.R;

/**
 * This class implements the Advanced Search feature which allows the user to
 * search by flavors, category, name, and ingredients all at once
 * @author nicolekihara
 *
 */

public class AdvancedSearch extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user_rating);
		
		FragmentTabHost mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Category"),
            CategoryFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Ingredients"),
            IngredientFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("Flavors"),
            FlavorFragment.class, null);
	}
}
