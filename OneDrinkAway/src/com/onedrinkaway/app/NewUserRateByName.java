package com.onedrinkaway.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkModel;

/**
 * The page that displays when a new user wants to rate drinks and presses the Rate By Name tab.
 * It allows the user to look at all of our drinks and rate them.
 * @author Andrea Martin
 *
 */
public class NewUserRateByName extends Fragment {
	//temp string names of common drinks
	private Drink[] allDrinks;
	
	//maps id value of rating bar to its drink name
	private Map<RatingBar, String> ratingBarsMap;
	
	private int[] currentRatings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		allDrinks = DrinkModel.getAllDrinks();
		currentRatings = new int[allDrinks.length];
		
		// Sort the arrays alphabetically
		Arrays.sort(allDrinks);
		ratingBarsMap = new HashMap<RatingBar, String>();
	   LinearLayout newUserCommonView = 
			   (LinearLayout) inflater.inflate(R.layout.fragment_new_user_rate_by_name, null);
	   
	   LinearLayout drinkContainer = (LinearLayout) newUserCommonView.findViewById(R.id.rate_by_name_container);
	   
	   // Display each drink name and rating bar
	   for(int i = 0; i < allDrinks.length; i++) {
		   LinearLayout rateDrink = (LinearLayout) inflater.inflate(R.layout.common_drinks, null);
		   TextView commonDrinkTitle = (TextView) rateDrink.findViewById(R.id.common_drink_title);
		   commonDrinkTitle.setText(allDrinks[i].name);
		   
		   RatingBar ratingBar = (RatingBar) rateDrink.findViewById(R.id.common_drink_rating_bar);
		   ratingBar.setId(i);
		   
		   ratingBar.setNumStars(5);
		   ratingBar.setStepSize((float)1.0);
		   
		   int userRating = allDrinks[i].getUserRating();
		   ratingBar.setRating(userRating == -1 ? 0 : userRating);
		
		   // Set the rating bar listener
		   ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				
			   /**
			    * Stores the user's rating when they press 1 to 5 stars on the rating bar
			    */
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		 
					if(rating != 0 && fromUser)  {
						String drink = ratingBarsMap.get(ratingBar);
						DrinkModel.addRating(DrinkModel.getDrink(drink), (int)rating);
					}
				}
		   
		   	});
		   ratingBarsMap.put(ratingBar, allDrinks[i].name);
		   drinkContainer.addView(rateDrink);
	   }
	   
	   return newUserCommonView;
	}
	
	 public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		 
		 for (int i = 0; i < currentRatings.length; i++) {
		   RatingBar ratingBar = (RatingBar) getActivity().findViewById(i);
		   ratingBar.setProgress(currentRatings[i]);         
		 }
	 }
}