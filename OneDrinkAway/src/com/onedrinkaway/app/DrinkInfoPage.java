package com.onedrinkaway.app;

import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Drink;

public class DrinkInfoPage extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drink_info_page);
		helpID = R.string.okay;
		Drink whiskeySour = TestData.whiskeySour;
		
		setTitle(whiskeySour.name);
		fillIngredients();
		setGlassPicture();
		fillDescription();
		setRatingBar();
		//manageFlavorSeekBars(whiskeySour);
		

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
	
	/*private List<Seekbar> getSeekBarList(drink) {
		
	}
	
	private void manageFlavorSeekBars(Drink drink) {
		Map<String, Integer> flavors = TestData.interpretFlavors(drink);
		SeekBar bitter = (SeekBar) findViewById(R.id.drink_info_bitter_seekbar);
		bitter.setEnabled(false);
		bitter.setProgress(flavors.get("Bitter"));
		
	} */

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
