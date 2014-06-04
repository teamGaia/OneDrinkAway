package com.onedrinkaway.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;

/**
 * This class implements the Search By Ingredient feature which allows users to select ingredients
 * they want to include in the cocktail they are looking for
 * @author Nicole Kihara
 *
 */

public class IngredientFragment extends Fragment {
	// Custom array adapter which keeps track of which ingredients are checked
	IngredientsArrayAdapter myArrayAdapter;
	LinearLayout ll;
	boolean firstTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		firstTime = true;
	}
	
	/**
	 * Fills this IngredientFragment view with all content
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ll = (LinearLayout) inflater.inflate(R.layout.fragment_ingredient, null);
		   
		setupSearchBox();
	    setupListView();
	    
	    return ll;
   }
   
	/**
	 * Displays the Search Bar that appears at the top of the screen
	 */
   private void setupSearchBox() {
	   EditText searchBox = (EditText) ll.findViewById(R.id.fragment_ingredient_search_box);
   	// A TextWatcher is a listener for the searchBox
       searchBox.addTextChangedListener(new TextWatcher() {
       	 
       	/**
       	 * When a user enters text in the text box, this filters the list of ingredients
       	 * to display
       	 */
       	 public void onTextChanged(CharSequence s, int start, int before, int count) {
       		 //get the text in the EditText
       		 myArrayAdapter.filterIngredients(s);
       	 }

       	 @Override
       	 public void beforeTextChanged(CharSequence s, int start, int count,
       	     int after) {
       	 }

       	 @Override
       	 public void afterTextChanged(Editable arg0) {
	        		 // TODO Auto-generated method stub
       	 }
       });
      
   }
   
   /**
    * Displays the list of ingredients to choose from in a scrolling list view
    */
   private void setupListView() {
	   ListView listView = (ListView) ll.findViewById(R.id.ingredient_list_view);
	   List<String> ingredientsList = new ArrayList<String>();
	   ingredientsList.addAll(Arrays.asList(DrinkModel.getIngredients()));
	   myArrayAdapter = new IngredientsArrayAdapter(
			   getActivity(),
			   R.layout.oda_ingredient_item,
			   R.id.ingredient_check_box,
			   ingredientsList,
			   listView,
			   (AdvancedSearch) getActivity()
			   );
	   listView.setAdapter(myArrayAdapter);
	   listView.setTextFilterEnabled(true);
	   listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
   }
   
   /**
    * Restores the state of the checkboxes when returning to this tab from a
    * different one.
    */
   public void onResume() {
	   super.onResume();

	   AdvancedSearch as = (AdvancedSearch) getActivity();
	   for (String ingr : as.query.getIngredients()) {
		   myArrayAdapter.toggleChecked(ingr);
	   }
   }
}