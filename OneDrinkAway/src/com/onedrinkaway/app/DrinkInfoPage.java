package com.onedrinkaway.app;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkInfo;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Flavor;

/**
 * This class displays the ingredients, description, and flavor profiles for a
 * Drink
 * 
 * @author Andrea Martin
 * 
 */
public class DrinkInfoPage extends OneDrinkAwayActivity {

	// list of flavors to display

	private LinearLayout seekBarView;
	private Drink drink;
	private DrinkInfo drinkInfo;
	private String prevActivity;
	private boolean favorited;
	
	

	/**
	 * Creates and fills the layout for the Drink Info Page
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drink_info_page);
		seekBarView = (LinearLayout) findViewById(R.id.drink_info_seek_bars_layout);
		helpID = R.string.drink_info_help;

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			String name = (String) extras.get("drink");
			drink = DrinkModel.getDrink(name);
			prevActivity = extras.getString("prevActivity");
		}
		if (drink != null) {
			drinkInfo = DrinkModel.getDrinkInfo(drink);
			setTitle(drink.name);
			fillInstructions();
			fillIngredients();
			setGlassPicture();
			addSeekBarsToView();
			setRatingBar();
			displayFavoritesButton();
			

		}
	}
	
	/**
	 * Displays Add To Favorites button if the drink is not already on the user's 
	 * favorite drink list
	 */
	private void displayFavoritesButton() {
		// Add Listener to Add To Favorites button
		ImageView addToFavoritesImage = 
				(ImageView) findViewById(R.id.drink_info_add_to_favorites);
		
		Drink[] favoritesList = DrinkModel.getFavorites();
		favorited = false;
		for(Drink favDrink: favoritesList) {
			if(favDrink.equals(drink)) 
				favorited = true;
				
		}
		
		addToFavoritesImage.setOnClickListener(new AddToFavoritesButtonListener());
		
		int imageID;
		if(favorited) {
			imageID = getResources().getIdentifier
					("com.onedrinkaway:drawable/red_heart_filled", null, null);;
		} else {
			imageID = getResources().getIdentifier
					("com.onedrinkaway:drawable/red_heart_unfilled", null, null);
		}
		addToFavoritesImage.setImageResource(imageID);
	}
	
	/**
	 * Fills the drink_info_ingredients TextView with given drink ingredients
	 * list Appends garnish to end of ingredients list if drinkInfo.garnish
	 * !=null
	 */
	@SuppressLint("DefaultLocale")
	private void fillIngredients() {
		TextView ingredientsTextView = (TextView) findViewById(R.id.drink_info_ingredients);
		if (drinkInfo.ingredients != null) {
			for (int i = 0; i < drinkInfo.ingredients.size(); i++) {
				ingredientsTextView.append("\n" + drinkInfo.ingredients.get(i));

			}
			ingredientsTextView.append("\n");
			String testNoGarnish = drinkInfo.garnish.toLowerCase();
			testNoGarnish.trim();
			testNoGarnish.toLowerCase();
			if (!testNoGarnish.equals("no garnish")) {
				ingredientsTextView.append("Garnish: " + drinkInfo.garnish);
			}

		} else {
			ingredientsTextView.setText("");
		}

	}

	/**
	 * Fills the drink_info_glass_type image view with image of glass for given drink
	 */
	@SuppressLint("DefaultLocale")
	private void setGlassPicture() {
		ImageView glassImageView = (ImageView) findViewById(R.id.drink_info_glass_type);
		int imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + drink.image, null, null);
		
		if(imageID == 0) {
			String glassString = drink.glass.toLowerCase() + "_glass";
			imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + glassString, null, null);
		}
		glassImageView.setImageResource(imageID);
		
	}


	/**
	 * Fills the drink instruction section with the given drinkInfo's
	 * instructions Leaves blank if there are no instructions
	 */
	private void fillInstructions() {
		TextView instructionTextView = (TextView) findViewById(R.id.drink_info_instructions);
		if (drinkInfo.instructions != null) {
			instructionTextView.append("\n" + drinkInfo.instructions);
		} else {
			instructionTextView.setText("");
		}
	}

	/**
	 * Sets RatingBar Steps to integer value and sets what current star rating
	 * should If user has rated, then shows user rating otherwise if there is a
	 * predicted rating for the user it shows the predicted rating If the other
	 * two ratings are -1 then the average user rating is shown
	 */
	private void setRatingBar() {
		RatingBar userRatingBar = (RatingBar) findViewById(R.id.drink_info_user_rating_bar);
		RatingBar predictedRatingBar = (RatingBar) findViewById(R.id.drink_info_predicted_rating_bar);
		String typeRating = drink.getRatingType();
		Float rating = (float) drink.getRating();

		if (typeRating.equals("user")) { // user rating is set
			userRatingBar.setRating(rating);
			predictedRatingBar.setVisibility(View.GONE);
			TextView predictedRatingLabel = (TextView) findViewById(R.id.drink_info_predicted_text_view);
			predictedRatingLabel.setVisibility(View.GONE);

		} else {
			userRatingBar.setRating((float) 0.0);
			predictedRatingBar.setRating(rating);
		}

		userRatingBar
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {

						if (rating != 0)
							DrinkModel.addRating(drink, (int) Math.ceil(rating));
					}

				});
	}

	// On click listener for add to favorites 
	public class AddToFavoritesButtonListener implements OnClickListener {
		ImageView addToFavoritesImage = 
				(ImageView) findViewById(R.id.drink_info_add_to_favorites);
		@Override
		public void onClick(View button) {
			int imageID;
			if(favorited) {
				DrinkModel.removeFavorite(drink);
				Toast.makeText(getApplicationContext(), "Drink favorite removed", Toast.LENGTH_LONG).show();
				imageID = getResources().getIdentifier
						("com.onedrinkaway:drawable/red_heart_unfilled", null, null);
				favorited = false;
				
				
			} else {
				DrinkModel.addFavorite(drink);
				Toast.makeText(getApplicationContext(),
						"Drink favorited",
						Toast.LENGTH_LONG).show();
				imageID = getResources().getIdentifier
						("com.onedrinkaway:drawable/red_heart_filled", null, null);
				favorited = true;
			}
			
			addToFavoritesImage.setImageResource(imageID);
			
		}
	}

	/**
	 * Adds seek bars that show the drink's flavor profile to view and only adds
	 * seek bars with flavors that are greater than 0
	 * 
	 * @param drink
	 *            for which the info page consists of
	 */
	private void addSeekBarsToView() {
		int[] attributes = AttributeSort.alphabetizeAttributes(drink);
		String[] flavors = Arrays.copyOf(Flavor.flavorsArr,
				Flavor.flavorsArr.length);
		Arrays.sort(flavors);
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i] != 0) {
				TextView flavorName = new TextView(this);

				flavorName.setText("\n" + flavors[i]);
				seekBarView.addView(flavorName);

				// adds seek bar with max five, disabled, with given attribute
				// (1 - 5)
				SeekBar seekBar = new SeekBar(this);
				seekBar.setMax(5);
				seekBar.setEnabled(false);
				LayoutParams sbParams = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				seekBar.setLayoutParams(sbParams);
				seekBar.setProgress(attributes[i]);
				seekBarView.addView(seekBar);

				// adds 0 - 5 under each flavor seek bar
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View flavorLabels = inflater.inflate(
						R.layout.activity_drink_info_seek_labels, null);
				seekBarView.addView(flavorLabels);

			}
		}

	}

	/**
	 * Delegates action if icons in action bar are pushed
	 * 
	 * @param item
	 *            the menu icon that has been selected
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
	 * Delegates action if back button is pressed.
	 */
	@Override 
	public void onBackPressed() {
		super.onBackPressed();
		
		if (prevActivity != null) {
			if (prevActivity.equals("SearchByName")) {
				startActivity(new Intent(this, SearchByName.class));
			} else {
				Intent i = new Intent(this, ResultsPage.class);
				i.putExtra("title", prevActivity);
				startActivity(i);
			}
		} else {
			startActivity(new Intent(this, FavoriteDrinks.class));
		}
	}
}
