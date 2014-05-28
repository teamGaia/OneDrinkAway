package com.onedrinkaway.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkModel;

/**
 * Displays the Favorites drinks page for the user where they can view the drinks that they have
 * saved.
 * @author Andrea Martin
 *
 */
public class FavoriteDrinks extends OneDrinkAwayActivity {
	
	//Maps the view of each favorite drink to the drink that it displays
	private Map<View, Drink> favoriteItems;

	/**
	 * Creates and fills the layout of the Favorite Drinks page
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_drinks);
		helpID = R.string.favorite_drinks_help;
		//get favorites list
		Drink[] favoriteDrinks = DrinkModel.getFavorites();
		
		favoriteItems = new HashMap<View, Drink>();
		//container holding each favorite item linear layout
		LinearLayout listView = (LinearLayout) findViewById(R.id.favorites_container);
		if(favoriteDrinks.length != 0) {
			Arrays.sort(favoriteDrinks, new DrinkNameComparator());
			addButtonListeners();
			
			for(int i = 0; i < favoriteDrinks.length; i++) {
				Drink drink = favoriteDrinks[i];
				if(drink != null) {
					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View listItems = inflater.inflate(R.layout.favorites_list_item, null);
					LinearLayout favoriteListClickable = (LinearLayout) listItems.findViewById(R.id.favorite_drink_clickable);
					favoriteListClickable.setOnClickListener(new FavoriteDrinkOnClickListener(drink));
					//Set drink name
					TextView drinklabel = (TextView) listItems.findViewById(R.id.favorite_drink_title);
					drinklabel.setText(drink.name);
					
					setRatingBar(listItems, drink);
					setGlassPicture(listItems, drink);
					
					//add each drink option row to the favorites page linear layout
					listView.addView(listItems); 
					favoriteItems.put(listItems, drink);
					
				} else {
					break;
				}
			
			} 
		} else { //favorites list is empty
			TextView emptyFavoritesTextView = (TextView) findViewById(R.id.favorites_empty_text_view);
			emptyFavoritesTextView.setVisibility(View.VISIBLE);
			
		}
	
	}
	
	/**
	 * Distinguishes whether to use user rating bar or predicted rating bar and sets the correct one
	 * @param listItems favorites_list_item View
	 * @para drink the favorite drink being displayed for the particular listItems View
	 */
	private void setRatingBar(View listItems, Drink drink) {
		RatingBar userRatingBar = (RatingBar) listItems.findViewById(R.id.favorite_user_drink_rating);
		RatingBar predictedRatingBar = (RatingBar) listItems.findViewById(R.id.favorite_predicted_drink_rating);
		RatingBar ratingBar;
		String typeRating = drink.getRatingType();
		if(typeRating.equals("user")) {
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
	 * Adds listeners to buttons in the edit button bar
	 */
	private void addButtonListeners() {
		Button editButton = (Button) findViewById(R.id.favorites_edit_button);
		editButton.setOnClickListener(new EditFavoritesOnClickListener());
		editButton.setVisibility(View.VISIBLE);
		Button removeButton = (Button) findViewById(R.id.favorites_remove_button);
		removeButton.setOnClickListener(new RemoveFavoritesOnClickListener());
		Button doneButton = (Button) findViewById(R.id.favorites_done_button);
		doneButton.setOnClickListener(new DoneOnClickListener());
	}
	
	/**
	 * Fills the drink_info_glass_type image view with image of glass for given drink
	 * @param listItems the View that contains the ImageView of the glass image
	 * @param drink the drink that listItems represents
	 */
	@SuppressLint("DefaultLocale")
	private void setGlassPicture(View listItems, Drink drink) {
		ImageView glassImageView = (ImageView) listItems.findViewById(R.id.favorite_glass_image);
		String glassString = drink.glass.toLowerCase() + "_glass";
		int imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + glassString, null, null);
		glassImageView.setImageResource(imageID);
	}
	
	/**
	 * Goes to drink info page of given drink 
	 * @param drink the drink in which the drink info page will appear for
	 */
	private void goToDrinkInfo(Drink drink) {
		Intent intent = new Intent(this, DrinkInfoPage.class);
		intent.putExtra("drink", drink.name);
		startActivity(intent);
	}
	
	/**
	 * Displays checkboxes to remove items from favorites list
	 */
	private void displayEditView() {
		for (View favoriteView: favoriteItems.keySet()) {
			CheckBox checkBox = (CheckBox) favoriteView.findViewById(R.id.favorites_checkbox);
			checkBox.setVisibility(View.VISIBLE);
			//display remove button
			Button removeButton = (Button) findViewById(R.id.favorites_remove_button);
			removeButton.setVisibility(View.VISIBLE);
			Button doneButton = (Button) findViewById(R.id.favorites_done_button);
			doneButton.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * Listener for the Done button
	 * Displays original Favorite's page view
	 */
	private class DoneOnClickListener implements OnClickListener {

		
		/**
		 * Displays original Favorite's page view
		 */
		@Override
		public void onClick(View arg0) {
			displayOriginalView();

		}
	}
	
	/**
	 * Display default Favorite's page view
	 */
	private void displayOriginalView() {
		//set done and remove buttons to invisible
		Button removeButton = (Button) findViewById(R.id.favorites_remove_button);
		Button doneButton = (Button) findViewById(R.id.favorites_done_button);
		removeButton.setVisibility(View.INVISIBLE);
		doneButton.setVisibility(View.INVISIBLE);
		
		//make each checkbox invisible
		for (View favoriteView: favoriteItems.keySet()) {
			CheckBox checkBox = (CheckBox) favoriteView.findViewById(R.id.favorites_checkbox);
			checkBox.setVisibility(View.GONE);
		}
		
		
	}
	
	/**
	 * Listener for the Edit button
	 * Displays checkboxes for each drink view and display the remove button and done button
	 */
	private class EditFavoritesOnClickListener implements OnClickListener {

		
		/**
		 * Displays checkboxes for each drink view and displays the remove button and done button
		 */
		@Override
		public void onClick(View arg0) {
			displayEditView();
			
		}
	}
	
	/**
	 * Listener for Remove button
	 * On click it removes each drink from the view and user's favorite list if the
	 * checkbox of the drink's view is checked
	 *
	 */
	private class RemoveFavoritesOnClickListener implements OnClickListener {
		
		/**
		 * Removes the View of each drink if the corresponding checkbox is checked
		 * Removes the corresponding drink from the user's favorite's list if the checkbox is checked
		 */
		@Override
		public void onClick(View arg0) {
			for (View favoriteView: favoriteItems.keySet()) {
				CheckBox checkBox = (CheckBox) favoriteView.findViewById(R.id.favorites_checkbox);
				if(checkBox.isChecked()) {
					favoriteView.setVisibility(View.GONE);
					DrinkModel.removeFavorite(favoriteItems.get(favoriteView));
					//favoriteItems.remove(favoriteView);
					
				}
			}
			
		}
		
	}
	/**
	 * Listener for if a drink in the list is selected
	 * Goes to the corresponding drink's info page
	 *
	 */
	private class FavoriteDrinkOnClickListener implements OnClickListener {
		private Drink drink;
		
		/**
		 * Constructs a listener corresponding to the given drink
		 * @param drink the drink who's drink info page will be displayed on click
		 */
		public FavoriteDrinkOnClickListener(Drink drink) {
			this.drink = drink;
		}
		
		/**
		 * Goes to the drink info page of the drink
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
			View rootView = inflater.inflate(R.layout.fragment_favorite_drinks,
					container, false);
			return rootView;
		}
		
	}

}
