package com.onedrinkaway.app;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Drink;
import com.onedrinkaway.common.DrinkInfo;
import com.onedrinkaway.db.DrinkDb;
import com.onedrinkaway.model.DatabaseInterface;

public class DrinkInfoPage extends OneDrinkAwayActivity {
	
	//list of flavors to display
	private String[] flavors = {"Bitter", "Boozy", "Citrusy", "Creamy", 
			"Fruity", "Herbal", "Minty", "Salty", "Sour",
			"Spicy", "Sweet" };
	private LinearLayout seekBarView;
	
	private Drink drink;
	private DrinkInfo drinkInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drink_info_page);
		seekBarView = (LinearLayout) findViewById(R.id.drink_info_seek_bars_layout);
		helpID = R.string.drink_info_help;
		drink = DatabaseInterface.getAllDrinks().get(0);
		drinkInfo = DatabaseInterface.getDrinkInfo(drink);
		
		/********************************** Temp Data *******************************************************/
		String source = "Yay for source recognition!";
		List<String> whiskeySourIngredients = new ArrayList<String>();
		 whiskeySourIngredients.add("3/4 oz Fresh Lemon Juice"); 
		 whiskeySourIngredients.add("3/4 oz Simple syrup" );
		 whiskeySourIngredients.add("1 1/2 oz Whiskey");
		String whiskeySourDescription = "Add all the ingredients to a shaker and fill with ice. Shake, and "
				+ "strain into a rocks glass filled with fresh ice. Garnish with a cherry and/or lemon wedge if "
				+ "desired.";
		//drinkInfo = new DrinkInfo(whiskeySourIngredients, whiskeySourDescription, null, null, source, 0);
		/*************************************Temp Data *************************************************/

		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			//drink = (Drink)extras.get("drink"); //for future use
			//drinkInfo = (DrinkInfo) extras.get("drinkInfo");
			
			//name = extras.getString("name");
		} 
		
		
		setTitle(drink.name);		
		
			
		fillIngredients();
		setGlassPicture();
		fillDescription();
		fillDescriptionRecognition();
		addSeekBarsToView();
		setRatingBar();
		Button addToFavoritesButton = (Button) findViewById(R.id.drink_info_add_to_favorites);
		//Add Listener to Add To Favorites button
		addToFavoritesButton.setOnClickListener(new AddToFavoritesButtonListener());
		
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	/**
	 * Fills the drink_info_ingredients TextView with given drink ingredients list
	 * Appends garnish to end of ingredients list if drinkInfo.garnish !=null
	 */
	private void fillIngredients() {
		TextView ingredientsTextView = (TextView) findViewById(R.id.drink_info_ingredients);
		ingredientsTextView.append("\n"); //put extra line between header and ingredients
		if(drinkInfo.ingredients != null) {
			for(int i = 0 ; i < drinkInfo.ingredients.size(); i++) {
					ingredientsTextView.append("\n" + drinkInfo.ingredients.get(i));
			
			}
		}
		
		if(drinkInfo.garnish != null) {
			ingredientsTextView.append("Garnish: " + drinkInfo.garnish);
		}
	}
	
	/**
	 * Fills the drink_info_glass_type image view with image of glass for given drink
	 */
	private void setGlassPicture() {
		ImageView glassTextView = (ImageView) findViewById(R.id.drink_info_glass_type);
		int imageID = getResources().getIdentifier("yourpackagename:drawable/ic_launcher", null, null);
		glassTextView.setImageResource(imageID);
	}
	
	/**
	 * Cites the source of the drink description at the bottom of the page
	 */
	private void fillDescriptionRecognition() {
		TextView descriptionRecognitionTextView = (TextView) findViewById(R.id.drink_info_description_recognition);
		if(drinkInfo.source != null) {
			descriptionRecognitionTextView.setText(drinkInfo.source);
		} else {
			descriptionRecognitionTextView.setText("");
		}
	}
	/**
	 * Fills the drink_info_description text view with the description of given drink
	 */
	private void fillDescription() {
		TextView descriptionTextView = (TextView) findViewById(R.id.drink_info_description);
		if(drinkInfo.description != null) {
			descriptionTextView.append("\n" + drinkInfo.description);
		}
	}
	
	/**
	 * Sets RatingBar Steps to integer value and sets what current star rating should show as
	 */
	private void setRatingBar() {
		RatingBar ratingBar = (RatingBar) findViewById(R.id.drink_info_rating_bar);
		ratingBar.setStepSize((float) 1.0);
		if(drink.getUserRating() != -1) {
			//sets rating if user has rated the drink 
			ratingBar.setRating((float) drink.getUserRating());  
		} else {
			//else sets ratings to 0 stars
			ratingBar.setRating((float) 0.0);
		}
		
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
	 
				//DatabaseInterface.addRating(drink, (int)rating);
	 
			}
		});
	}
	
	// The Search button listener for Search By Flavor
	public class AddToFavoritesButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			//DatabaseInterface.addFavorite(drink);
		}
	};

	/**
	 * Add seek bars that show the drink's flavor profile to view
	 * @param drink for which the info page consists of
	 */
	private void addSeekBarsToView() {
		int[] attributes = TestData.alphabetizeAttributes(drink);
		for(int i = 0; i < attributes.length; i++) {
			if(attributes[i] != 0) {
				TextView flavorName = new TextView(this);
						
				flavorName.setText(flavors[i]);
				seekBarView.addView(flavorName);
						
				SeekBar seekBar = new SeekBar(this);
				seekBar.setMax(5);
				seekBar.setEnabled(false);
				LayoutParams sbParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				seekBar.setLayoutParams(sbParams);
				seekBar.setProgress(attributes[i]);
				seekBarView.addView(seekBar);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View flavorLabels = inflater.inflate(R.layout.activity_drink_info_seek_labels, null);
				seekBarView.addView(flavorLabels);
				
				}
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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_drink_info_page,
					container, false);
			return rootView;
		}
	}

}
