/**
 * OneDrinkAway v0.1 (Zero-feature release) 
 * @author Andrea, Nicole, Taylor
 * 
 * OneDrinkAwayActivity is an abstract class with two items on it's ActionBar:
 * help and home. Each Activity in the OneDrinkAway project will extend this
 * abstract class so they can inherit the ActionBar. 
 */

package com.onedrinkaway.app;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.onedrinkaway.R;

/**
 * This class contains the Tool Bar on the top of every screen. The tool bar contains
 * the back button, name of the page, help icon, and home icon
 * @author Nicole Kihara, Andrea Martin, and Taylor Juve
 *
 */
public abstract class OneDrinkAwayActivity extends ActionBarActivity {
	protected int helpID;
	
	/**
	 * Creates the tool bar menu
	 * @param menu: the menu to create
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_drink_away, menu);
		return true;
	}

	/**
	 * Displays the help screen if the user pressed the help icon, or goes to
	 * the home screen if the user pressed home
	 * @param item: the menu icon that was pressed
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_help) {
			DialogFragment newFragment = new HelpDialog();
			Bundle bundle = new Bundle();
			
			//String className = this.getLocalClassName();
			//className = className.substring(className.indexOf('.') + 1);
			//bundle.putString("Activity", className + "_help");
			
			bundle.putInt("helpID", helpID);
			
			newFragment.setArguments(bundle);
		    newFragment.show(getFragmentManager(), "help");
		} else if(id == R.id.action_home) {
			Intent goHomeIntent = new Intent(this, HomePage.class);
			goHomeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			finish();
			startActivity(goHomeIntent);
		} else if (id == R.id.action_secret) {
			startActivity(new Intent(this, NewUserRatingActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
