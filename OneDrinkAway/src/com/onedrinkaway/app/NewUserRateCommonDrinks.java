package com.onedrinkaway.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Query;

public class NewUserRateCommonDrinks extends Fragment {
	//maps id value of rating bar to its drink name
	private Map<RatingBar, String> ratingBarsMap;
	
	private int[] currentRatings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Will be used in the future (it currently returns no drinks)
		Drink[] popDrinks = getPopularDrinks();
		
		Arrays.sort(popDrinks, new Comparator<Drink>() {
			@Override
			public int compare(Drink d1, Drink d2) {
				return d1.name.compareTo(d2.name);
			}
		});
		currentRatings = new int[popDrinks.length];
		
		ratingBarsMap = new HashMap<RatingBar, String>();
	   LinearLayout newUserCommonView = 
			   (LinearLayout) inflater.inflate(R.layout.fragment_new_user_rate_common_drinks, null);
	   
	   LinearLayout commonDrinkContainer = (LinearLayout) newUserCommonView.findViewById(R.id.common_drink_container);
	   
	   for(int i = 0; i < popDrinks.length; i++) {
		   LinearLayout commonDrink = (LinearLayout) inflater.inflate(R.layout.common_drinks, null);
		   TextView commonDrinkTitle = (TextView) commonDrink.findViewById(R.id.common_drink_title);
		   commonDrinkTitle.setText(popDrinks[i].name);
		   
		   RatingBar ratingBar = (RatingBar) commonDrink.findViewById(R.id.common_drink_rating_bar);
		   
		   ratingBar.setId(i);
		   ratingBar.setNumStars(5);
		   ratingBar.setStepSize((float)1.0);
		   ratingBar.setRating((float) 0.0);
		
		   ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				
				public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
		 
					if(rating != 0 && fromUser)  {
						String drink = ratingBarsMap.get(ratingBar);
						DrinkModel.addRating(DrinkModel.getDrink(drink), (int)rating);
					}
				}
		   
		   		});
		   ratingBarsMap.put(ratingBar, popDrinks[i].name);
		   setGlassPicture(commonDrink, DrinkModel.getDrink(popDrinks[i].name));
		   commonDrinkContainer.addView(commonDrink);
	   }
	   
	   return newUserCommonView;
	}
	
	private Drink[] getPopularDrinks() {
		Query query = new Query();
		query.setCategory("Popular");
		DrinkModel.searchForDrinks(query);
		Drink[] popDrinks = DrinkModel.getResults();
		
		Arrays.sort(popDrinks);
		return popDrinks; 
	}
	
	/**
	 * Fills each common drink image view with image of glass for given drink
	 * @param listItems the View that contains the ImageView of the glass image
	 * @param drink the drink that listItems represents
	 */
	@SuppressLint("DefaultLocale")
	private void setGlassPicture(View listItems, Drink drink) {
		ImageView glassImageView = (ImageView) listItems.findViewById(R.id.common_drink_image);
		int imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + drink.image, null, null);
		
		if(imageID == 0) {
			String glassString = drink.glass.toLowerCase() + "_glass";
			imageID = getResources().getIdentifier("com.onedrinkaway:drawable/" + glassString, null, null);
		}
		glassImageView.setImageResource(imageID);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		 
		 for (int i = 0; i < currentRatings.length; i++) {
		   RatingBar ratingBar = (RatingBar) getActivity().findViewById(i);
		   if (ratingBar != null)
			   ratingBar.setProgress(currentRatings[i]);         
		 }
	 }
}