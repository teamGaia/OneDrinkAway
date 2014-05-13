package com.onedrinkaway.app;



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
import android.widget.SeekBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Drink;

public class DrinkInfoPage extends OneDrinkAwayActivity {
	
	//list of flavors to display
	private String[] flavors = {"Bitter", "Boozy", "Citrusy", "Creamy", 
			"Fruity", "Herbal", "Minty", "Salty", "Sour",
			"Spicy", "Sweet" };
	private LinearLayout seekBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drink_info_page);
		seekBarView = (LinearLayout) findViewById(R.id.drink_info_seek_bars_layout);
		helpID = R.string.okay;
		Drink whiskeySour = TestData.whiskeySour;
		
		Bundle extras = getIntent().getExtras();
		String name = whiskeySour.name;
		if (extras != null) {
			name = extras.getString("name");
		} 
		setTitle(name);		
			
		fillIngredients();
		setGlassPicture();
		fillDescription();
		fillDescriptionRecognition();
		addSeekBarsToView(whiskeySour);
		setRatingBar();
		Button addToFavoritesButton = (Button) findViewById(R.id.drink_info_add_to_favorites);
		addToFavoritesButton.setOnClickListener(new AddToFavoritesButtonListener());
		
		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	/**
	 * Fills the drink_info_ingredients TextView with given drink ingredients list
	 */
	private void fillIngredients() {
		TextView ingredientsTextView = (TextView) findViewById(R.id.drink_info_ingredients);
		
		String[] ingredients = TestData.whiskeySourIngredients;
		ingredientsTextView.append("\n"); //put extra line between header and ingredients
		for(int i = 0 ; i < ingredients.length; i++) {
				ingredientsTextView.append("\n" + ingredients[i]);
			
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
	
	private void fillDescriptionRecognition() {
		TextView descriptionRecognitionTextView = (TextView) findViewById(R.id.drink_info_description_recognition);
		descriptionRecognitionTextView.setText("Yay for description recognition!");
	}
	/**
	 * Fills the drink_info_description text view with the description of given drink
	 */
	private void fillDescription() {
		TextView descriptionTextView = (TextView) findViewById(R.id.drink_info_description);
		descriptionTextView.append("\n" + TestData.whiskeySourDescription);
	}
	
	/**
	 * Sets RatingBar Steps to integer value and sets what current star rating should show as
	 */
	private void setRatingBar() {
		RatingBar ratingBar = (RatingBar) findViewById(R.id.drink_info_rating_bar);
		ratingBar.setStepSize((float) 1.0);
		ratingBar.setRating((float) 2.0);  //sets rating shown
	}
	
	// The Search button listener for Search By Flavor
	public class AddToFavoritesButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Add Drink to Favorites list if not already in favorites list
		}
	};

	/**
	 * Add seek bars that show the drink's flavor profile to view
	 * @param drink for which the info page consists of
	 */
	private void addSeekBarsToView(Drink drink) {
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
