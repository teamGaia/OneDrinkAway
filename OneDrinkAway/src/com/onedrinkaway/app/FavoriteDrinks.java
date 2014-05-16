package com.onedrinkaway.app;

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
 * Displays the Favorites drinks page for the user where they can view the drinks that they have
 * saved.
 * @author Andrea Martin
 *
 */
public class FavoriteDrinks extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_drinks);
		helpID = R.string.favorite_drinks_help;
		//get favorites list
		Drink[] favoriteDrinks = DrinkModel.getFavorites();
		if(favoriteDrinks != null) {
			Arrays.sort(favoriteDrinks, new DrinkNameComparator());
			LinearLayout listView = (LinearLayout) findViewById(R.id.favorites_container);
		
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
					//Set ratings bar
					RatingBar ratingBar = (RatingBar) listItems.findViewById(R.id.favorite_drink_rating);
					ratingBar.setEnabled(false);
					ratingBar.setRating((float) drink.getRating()); 
					ratingBar.setIsIndicator(true);
					//add each drink option row to the favorites page linear layout
					listView.addView(listItems); 
				} else {
					break;
				}
			} 
		}
			
			
	 }
	
	/**
	 * Goes to drink info page of given drink 
	 * @param drink the drink in which the drink info page will appear for
	 */
	private void goToDrinkInfo(Drink drink) {
		Intent intent = new Intent(this, DrinkInfoPage.class);
		intent.putExtra("drink", drink);
		startActivity(intent);
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
