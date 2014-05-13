package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.common.Query;


public class SearchByIngredient extends OneDrinkAwayActivity implements SearchView.OnQueryTextListener {

	private final String[] aBigAssArrayOfCheeseNames = ClassWithABigAssArrayOfCheeseNames.theBigAssArrayOfCheeseNames;
	//private final Drink[] allDrinks = (Drink[]) DatabaseInterface.getAllDrinks().toArray();
    
	// The ingredients to include in the search
	private Query query;
	
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		helpID = R.string.search_by_ingredient;
		setupSearchView();
        setupListView();
    }
    
    private void setupSearchView() {
    	SearchView srchView = (SearchView) findViewById(R.id.ingredient_search_view);
    	Button ingredientSearchButton = (Button) findViewById(R.id.search_by_ingredient_button);
        srchView.setIconifiedByDefault(false);
        srchView.setOnQueryTextListener(this);
        srchView.setSubmitButtonEnabled(false);
        srchView.setQueryHint("Enter Ingredient Here");
        query = new Query();
    }
    
    private void setupListView() {
    	listView = (ListView) findViewById(R.id.ingredient_list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.oda_ingredient_item,
                R.id.ingredient_check_box,
                aBigAssArrayOfCheeseNames));
        listView.setTextFilterEnabled(true);
        
        listView.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	CheckBox checkbox = (CheckBox) view;
            	if (checkbox.isChecked())
            		query.add(checkbox.getText().toString());
            	// else
            		// query.remove()
            	goToDrinkInfo(checkbox.getText().toString());
            }

        });
    }
    
    private void goToDrinkInfo(String name) {
    	Intent intent = new Intent(this, DrinkInfoPage.class);
    	intent.putExtra("name", name);
    	startActivity(intent);
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


	/**
	 * A placeholder fragment containing a simple view.
	 */
/*	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_search_by_ingredient, container, false);
			return rootView;
		}
	}
*/
