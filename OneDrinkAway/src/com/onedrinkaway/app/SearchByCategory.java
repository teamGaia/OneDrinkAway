package com.onedrinkaway.app;

import android.app.ActionBar.LayoutParams;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Query;

/**
 * SearchByCategory displays all drink categories to the user and allows the user to browse drinks
 * based on the category they choose
 * 
 * @author Taylor Juve
 *
 */
public class SearchByCategory extends OneDrinkAwayActivity {
	
	private Query query;
	
	/**
	 * Fills the SearchByCateogry view with all content
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_category);
		
		helpID = R.string.help_search_by_category;
		
		query = new Query();
		
		String[] categories = DrinkModel.getCategories();
		//String[] categories = TempListOfCategories.CATEGORIES; //temp
		displayCategories(categories);
	}
	
	/**
	 * Displays list of categories on view
	 * @param categories list of all drink categories
	 */
	private void displayCategories(String[] categories) {
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout container = (LinearLayout) findViewById(R.id.category_space);
		LinearLayout curRow = null;
		for (int i = 0; i < categories.length; i++) {
			// Add a new row (horizontal LinearLayout) every 3 TextViews
			if (i % 3 == 0) {
				curRow = new LinearLayout(this);
				container.addView(curRow);
			}
			
			// Add TextView
			TextView category = (TextView) inflater.inflate(R.layout.category_text_area, null);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
			category.setLayoutParams(param);
			category.setText(categories[i]);
			
			String iconIdName = "ic_" + categories[i].replace(' ', '_').toLowerCase();
			int iconId = getResources().getIdentifier(iconIdName, "drawable", getPackageName());
			category.setCompoundDrawablesWithIntrinsicBounds(0, iconId, 0, 0);
			
			category.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View view) {
					DialogFragment newFragment = new CategoryDialog();
					Bundle bundle = new Bundle();
					
					String name = (String) ((TextView) view).getText();
					bundle.putString("name", name);
					
					newFragment.setArguments(bundle);
				    newFragment.show(getFragmentManager(), "category help");
				    return true;
				}
			});
			curRow.addView(category);
		}
	}

	/**
	 * Goes to results page containing the list of drinks within the category selected
	 * @param view
	 */
	public void categorySelected(View view) {
		Intent intent = new Intent(this, ResultsPage.class);
		String categoryName = (String) ((TextView) view).getText();
		query.setCategory(categoryName);
		boolean drinksFound = DrinkModel.searchForDrinks(query);
		if (drinksFound) {
		    // hurray found at least one drink, go to ResultsPage
		    intent.putExtra("title", categoryName);
		    startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "No results found!", Toast.LENGTH_LONG).show();
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
					R.layout.fragment_search_by_category, container, false);
			return rootView;
		}
	}
}

