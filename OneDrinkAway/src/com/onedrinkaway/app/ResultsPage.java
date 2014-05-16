package com.onedrinkaway.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Drink;
import com.onedrinkaway.model.DrinkModel;

/**
 * Displays the results for the users search from highest predicted/user rating to lowest
 * @ Andrea Martin
 *
 */
public class ResultsPage extends OneDrinkAwayActivity {

	/**
	 * Creates and fills the view of the Results page with the dynamic results
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_page);
		helpID = R.string.results_help;
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			String title = extras.getString("title");
			if(title != null) {
				setTitle(title);
			}
			//Drink[] drinkResults = (Drink[]) extras.get("results"); 
			Drink[] drinkResults = DrinkModel.getResults();
			
			if(drinkResults != null) { // should never be null, either empty or non-empty right?
				
				//sort results by name
				//Arrays.sort(drinkResults, new DrinkRatingComparator());
				LinearLayout listView = (LinearLayout) findViewById(R.id.results_container);
			
			
				for(Drink drink: drinkResults) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View listItems = inflater.inflate(R.layout.oda_result_item, null);
					//Add listener to each drink result
					LinearLayout resultListClickable = (LinearLayout) listItems.findViewById(R.id.result_wrapper);
					resultListClickable.setOnClickListener(new ResultDrinkOnClickListener(drink));
					//Add drink name to each drink result
					TextView drinklabel = (TextView) listItems.findViewById(R.id.result_title);
					drinklabel.setText(drink.name);
					//Add rating bar to show rating for each drink result
					RatingBar ratingBar = (RatingBar) listItems.findViewById(R.id.result_rating);
					ratingBar.setEnabled(false);
					ratingBar.setRating((float) drink.getRating()); 
					ratingBar.setIsIndicator(true);
				
					listView.addView(listItems); 
				} 
			}
		}	
		

	}
	/**
	 * Goes home if the house icon is pressed, displays help if the question mark is pressed
	 * and goes to the previous page if the back button is pressed
	 */
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
	

	/**
	 * OnClickListener for each drink result. Goes to drink info page for 
	 * the drink result that has been clicked
	 */
	private class ResultDrinkOnClickListener implements OnClickListener {
		private Drink drink;
		/**
		 * Constructs new listener with the given drink object
		 * @param drink
		 */
		public ResultDrinkOnClickListener(Drink drink) {
			this.drink = drink;
		}
		/**
		 * Goes to the drink info page for the clicked drink result 
		 */
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



