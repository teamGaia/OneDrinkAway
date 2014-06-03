package com.onedrinkaway.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
public class SearchByName extends OneDrinkAwayActivity {
    private NameArrayAdapter myArrayAdapter;

	/**
	 * Creates the view of the search by name page
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        helpID = R.string.search_by_name_help;
        setContentView(R.layout.activity_search_by_name);
        setupSearchBox();
        setupListView();
    }
    
	/**
	 * Displays the Search Box that appears at the top of the screen
	 */
    private void setupSearchBox() {
    	EditText searchBox = (EditText) findViewById(R.id.search_by_name_edit_text);
    	// A TextWatcher is a listener for the searchBox
        searchBox.addTextChangedListener(new TextWatcher() {
        	 
        	/**
        	 * When a user enters text in the text box, this filters the list of names
        	 * to display
        	 */
        	 public void onTextChanged(CharSequence s, int start, int before, int count) {
        		 //get the text in the EditText
        		 myArrayAdapter.filterNames(s);
        		 if (myArrayAdapter.getCount() == 0) {
        			Toast toast = Toast.makeText(getApplicationContext(),
        										 "No drinks named \"" + s + "\"", Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.TOP, 0, 170);
        			toast.show();
        		 }
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
     * Displays the list of names to choose from in a scrolling list view
     */
    private void setupListView() {
    	ListView listView = (ListView) findViewById(R.id.list_view);
    	List<String> nameList = new ArrayList<String>();
    	nameList.addAll(Arrays.asList(DrinkModel.getDrinkNames()));
    	myArrayAdapter = new NameArrayAdapter(
    	          this,
    	          R.layout.oda_list_item,
    	          R.id.list_item,
    	          nameList
    	          );
        
        listView.setAdapter(myArrayAdapter);
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
    	finish();
    	Intent intent = new Intent(this, DrinkInfoPage.class);
    	intent.putExtra("drink", name);
    	intent.putExtra("prevActivity", "SearchByName");
    	startActivity(intent);
    }

    public class NameArrayAdapter extends ArrayAdapter<String> {
    	// The context (or activity) that is using this adapter
    	//private Context context;
    	

    	// All of the ingredients
    	private List<String> allNames;

        public NameArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        	super(context, resource, textViewResourceId, objects);
        	//this.context = context;
            allNames = new ArrayList<String>();
        	allNames.addAll(objects);
        }

    	/**
    	 * Filters the names to display in the list based on the user-typed constraint
    	 * @param constraint the user's text in the Search Box
    	 */
    	@SuppressLint("DefaultLocale")
    	public void filterNames(CharSequence constraint) {
    		// clear all of the name to display
    		clear();

    		constraint = constraint.toString().toLowerCase();
    		for (int i = 0; i < allNames.size(); i++) {
    			// check each name in the list to see if it starts with this constraint
    			String name = allNames.get(i);
    			if (name.toLowerCase().startsWith(constraint.toString()))  {
    				// display this ingredient
    				add(name);
    			}
    		}
    		notifyDataSetChanged();
    	}
    }
}




