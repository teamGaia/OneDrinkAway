package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;
import com.onedrinkaway.model.Query;

public class IngredientFragment extends Fragment implements SearchView.OnQueryTextListener {
	private String[] ingredients;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_ingredient, null);
	   
	   ingredients = DrinkModel.getIngredients();
	   setupSearchView(ll);
       setupListView(ll);
       
       return ll;
   }
   
	/**
	 * Displays the Search Bar that appears at the top of the screen
	 */
   private void setupSearchView(LinearLayout ll) {
   	   SearchView srchView = (SearchView) ll.findViewById(R.id.ingredient_search_view);
       srchView.setIconifiedByDefault(false);
       srchView.setOnQueryTextListener(this);
       srchView.setQueryHint("Enter Ingredient Here");
   }
   
   /**
    * Displays the list of ingredients to choose from in a scrolling list view
    */
   private void setupListView(LinearLayout ll) {
   	   listView = (ListView) ll.findViewById(R.id.ingredient_list_view);
       listView.setAdapter(new ArrayAdapter<String>(getActivity(),
               R.layout.oda_ingredient_item,
               ingredients));
       listView.setTextFilterEnabled(true);
       listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
       listView.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    		   AdvancedSearch as = (AdvancedSearch) getActivity();
    		   Query query = as.query;
    		   String checkedIngr = (String) ((CheckedTextView) v).getText();

    		   if (query.getIngredients().contains(checkedIngr)) {
    			   query.remove(checkedIngr);
    		   } else {
    			   query.add(checkedIngr);
    		   }
           }
       });
   }

   public boolean onQueryTextChange(String newText) {
       if (TextUtils.isEmpty(newText)) {
           listView.clearTextFilter();
       } else {
           listView.setFilterText(newText.toString());
       }
       return true;
   }

   public boolean onQueryTextSubmit(String query) {
       return false;
   }
}