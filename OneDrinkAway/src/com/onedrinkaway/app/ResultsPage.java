package com.onedrinkaway.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.onedrinkaway.R;

public class ResultsPage extends OneDrinkAwayActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_page);
		helpID = R.string.results_help;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			setTitle(extras.getString("title"));
		}
		
        ListView view = (ListView) findViewById(R.id.list_view);
        view.setAdapter(new ArrayAdapter<String>(this,
                R.layout.oda_result_item,
                R.id.result_title,
                tempResults.RESULTS));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    if (item.getItemId() == android.R.id.home) {
	        onBackPressed();
	    	return true;
	    }

	    return super.onOptionsItemSelected(item);
	}

	public void goToDrinkInfo(View view) {
		startActivity(new Intent(this, DrinkInfoPage.class));
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_results_page,
					container, false);
			return rootView;
		}
	}
}

class tempResults {
	public static final String[] RESULTS = {"One", "One", "Two", "Three",
											"Five", "Eight", "Thirteen", "Twenty-one"};
}
