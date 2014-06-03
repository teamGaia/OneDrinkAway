package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrinkaway.R;
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
	
	private ArrayAdapter<String> adapter;

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
    	adapter = new ArrayAdapter<String>(this,
				 R.layout.oda_list_item,
				 R.id.list_item,
				 drinkNames);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	goToDrinkInfo(((TextView) view).getText().toString());
            }

        });
        /*
        View empty = getLayoutInflater().inflate(R.layout.empty_text_view, null);
        ((ViewGroup) listView.getParent()).addView(empty, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        listView.setEmptyView(empty);*/
    }

    /**
     * Goes to drink info page of the given drink name
     * @param name the name of the drink who's info page will be navigated to
     */
    private void goToDrinkInfo(String name) {
    	finish();
    	Intent intent = new Intent(this, DrinkInfoPage.class);
    	intent.putExtra("drink", name);
    	intent.putExtra("prevActivity", "SearchByName");
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
            listView.post(new Runnable() {
                public void run() {
                    if (listView.getLastVisiblePosition() == -1) {
                    	Toast toast = Toast.makeText(getApplicationContext(),
        	                     "No drinks by that name!", Toast.LENGTH_SHORT);
                    	toast.setGravity(Gravity.TOP, 0, 150);
                    	toast.show();
                    }
                }
            });
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




