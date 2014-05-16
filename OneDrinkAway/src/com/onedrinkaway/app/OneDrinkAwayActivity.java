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

public abstract class OneDrinkAwayActivity extends ActionBarActivity {
	protected int helpID;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.one_drink_away, menu);
		return true;
	}

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
		} 
		return super.onOptionsItemSelected(item);
	}
	
	
}
