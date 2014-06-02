package com.onedrinkaway.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkModel;

/**
 * Displays the results for the users search from highest predicted/user rating to lowest
 * @ Andrea Martin
 *
 */
public class ResultsPage extends OneDrinkAwayActivity {
	private boolean isTrySomethingNew;
	
	/**
	 * Creates and fills the view of the Results page with the dynamic results
	 * @param prevActivity 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_page);
		helpID = R.string.results_help;
    	
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			setTitle(extras);
			displayResults();
		}	
	}
	
	@SuppressLint("NewApi")
	private void setTitle(Bundle extras) {
		String title = extras.getString("title");
		if(title != null) {
			if (title.equals("New Drinks Just For You")) {
				isTrySomethingNew = true;
			}
			setTitle(title);
			if (!(title.equals("Results") || title.equals("New Drinks Just For You"))
					&& android.os.Build.VERSION.SDK_INT >= 14) {
				// It's a category
				String iconIdName = "ic_" + title.replace(' ', '_').toLowerCase();
				int iconId = getResources().getIdentifier(iconIdName, "drawable", getPackageName());
				getActionBar().setIcon(iconId);
			}
		}
	}
    
    private void displayResults() {
    	//Drink[] drinkResults = (Drink[]) extras.get("results"); 
    	
    	if (isTrySomethingNew) {
    		DrinkModel.findTrySomethingNewDrinks();
    	}
		Drink[] drinkResults = DrinkModel.getResults();
		
		if(drinkResults != null) { // should never be null, either empty or non-empty right?
			
			//sort results by name
			//Arrays.sort(drinkResults, new DrinkRatingComparator());
			LinearLayout listView = (LinearLayout) findViewById(R.id.results_container);
			
			int numDrinks = 0; 
			for (Drink drink : drinkResults) {
				if (!isTrySomethingNew || (isTrySomethingNew && !drink.getRatingType().equals("user"))) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View listItems = inflater.inflate(R.layout.oda_result_item, null);
					//Add listener to each drink result
					LinearLayout resultListClickable = (LinearLayout) listItems.findViewById(R.id.result_wrapper);
					resultListClickable.setOnClickListener(new ResultDrinkOnClickListener(drink));
					
					//Add drink name to each drink result
					TextView drinklabel = (TextView) listItems.findViewById(R.id.result_title);
					drinklabel.setText(drink.name);
					//Add rating bar to show rating for each drink result
					setRatingBar(listItems, drink);
					setGlassPicture(listItems, drink);
					
					listView.addView(listItems);
					if (isTrySomethingNew) {
						numDrinks++;
					}
				}
				if (numDrinks >= 5) {
					break;
				}
			}
		} 
    }
    
	/**
	 * Distinguishes whether to use user rating bar or predicted rating bar and sets the correct one
	 * @param listItems favorites_list_item View
	 * @para drink the favorite drink being displayed for the particular listItems View
	 */
	private void setRatingBar(View listItems, Drink drink) {
		RatingBar userRatingBar = (RatingBar) listItems.findViewById(R.id.result_user_rating);
		RatingBar predictedRatingBar = (RatingBar) listItems.findViewById(R.id.result_predicted_rating);
		RatingBar ratingBar;
		String typeRating = drink.getRatingType();
		if(typeRating.equals("user")) {
			Log.d("Tag ________", "user");
			userRatingBar.setVisibility(View.VISIBLE);
			predictedRatingBar.setVisibility(View.GONE);
			ratingBar = userRatingBar;
		} else {
			userRatingBar.setVisibility(View.GONE);
			ratingBar = predictedRatingBar;
		}
		ratingBar.setEnabled(false);
		ratingBar.setRating((float) drink.getRating()); 
		ratingBar.setIsIndicator(true);
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
	 * Fills each result item image view with image of glass for given drink
	 * @param listItems the View that contains the ImageView of the glass image
	 * @param drink the drink that listItems represents
	 */
	@SuppressLint("DefaultLocale")
	private void setGlassPicture(View listItems, Drink drink) {
		ImageView glassImageView = (ImageView) listItems.findViewById(R.id.result_glass_image);
		int imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + drink.image, null, null);
		
		if(imageID == 0) {
			String glassString = drink.glass.toLowerCase() + "_glass";
			imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + glassString, null, null);
		}
		glassImageView.setImageResource(imageID);
		

	}
	
	/**
	 * Goes to the drink info page with given drink
	 * @param drink drink in which will be displayed on the drink info page
	 */
	private void goToDrinkInfo(Drink drink) {
		finish();
		Intent intent = new Intent(this, DrinkInfoPage.class);
		intent.putExtra("drink", drink.name);
		intent.putExtra("prevActivity", "ResultsPage");
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
}



