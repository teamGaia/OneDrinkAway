package com.onedrinkaway.app;

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onedrinkaway.R;

import com.onedrinkaway.common.Drink;
import com.onedrinkaway.model.DatabaseInterface;

public class ResultsPage extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_page);
		helpID = R.string.results_help;
		Drink[] drinkResults = DatabaseInterface.getAllDrinks();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			setTitle(extras.getString("title"));
			/*if (extras.get("results") != null)
				drinkResults = (Drink[]) extras.get("results");*/
		}
		
		
		Arrays.sort(drinkResults, new DrinkNameComparator());
		LinearLayout listView = (LinearLayout) findViewById(R.id.results_container);
		
		
		for(Drink drink: drinkResults) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View listItems = inflater.inflate(R.layout.oda_result_item, null);
			LinearLayout resultListClickable = (LinearLayout) listItems.findViewById(R.id.result_clickable);
			resultListClickable.setOnClickListener(new ResultDrinkOnClickListener(drink));
			TextView drinklabel = (TextView) listItems.findViewById(R.id.result_title);
			drinklabel.setText(drink.name);
			RatingBar ratingBar = (RatingBar) listItems.findViewById(R.id.result_rating);
			ratingBar.setEnabled(false);
			ratingBar.setRating((float) drink.getRating()); 
			ratingBar.setIsIndicator(true);
			
			listView.addView(listItems); 
		} 
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	        onBackPressed();
	    	return true;
	    }

	    return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Goes to the drink info page with given drink
	 * @param drink drink in which will be displayed on the drink info page
	 */
	private void goToDrinkInfo(Drink drink) {
		Intent intent = new Intent(this, DrinkInfoPage.class);
		intent.putExtra("drink", drink);
		startActivity(intent);
	}
	

	// Listener for if a result listening is clicked
	private class ResultDrinkOnClickListener implements OnClickListener {
		private Drink drink;
		public ResultDrinkOnClickListener(Drink drink) {
			this.drink = drink;
		}
		@Override
		public void onClick(View arg0) {
			goToDrinkInfo(drink);
			
		}
	};
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_results_page,
					container, false);
			return rootView;
		}
	}
}



