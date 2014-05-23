package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.Drink;
import com.onedrinkaway.model.DrinkModel;


/**
 * Search by name displays the UI view that originally shows all the drinks to the user. The user can filter
 * drinks by name via the search bar at the top of the screen. From this page, the user can navigate to 
 * each drink's info page
 * 
 * @author Taylor Juve
 *
 */
public class SearchByName extends OneDrinkAwayActivity implements SearchView.OnQueryTextListener {
    private String[] drinkNames;
    
	private ListView listView;

	/**
	 * Creates the view of the search by name page
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drinkNames  = DrinkModel.getDrinkNames();
        
        helpID = R.string.search_by_name_help;
        setContentView(R.layout.activity_search_by_name);
        setupSearchView();
        setupListView();
    }
    
    /**
     * Sets up the search text view box
     */
    private void setupSearchView() {
    	SearchView srchView = (SearchView) findViewById(R.id.search_view);
        srchView.setIconifiedByDefault(false);
        srchView.setOnQueryTextListener(this);
        srchView.setSubmitButtonEnabled(false);
        srchView.setQueryHint("Enter Name Here");
    }
    
    /**
     * Sets a filterable list view of all of the drinks
     */
    private void setupListView() {
    	listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.oda_list_item,
                R.id.list_item,
                drinkNames));
        listView.setTextFilterEnabled(true);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	goToDrinkInfo(((TextView) view).getText().toString());
            }

        });
    }

    /**
     * Goes to drink info page of the given drink name
     * @param name the name of the drink who's info page will be navigated to
     */
    private void goToDrinkInfo(String name) {
    	Intent intent = new Intent(this, DrinkInfoPage.class);
    	intent.putExtra("drink", name);
    	startActivity(intent);
    }

    /**
     * Resets the filter of the drinks when the text in the search box changes
     */
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    /**
     * Removes the submit query button from the search box when this
     * returns false
     */
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}




