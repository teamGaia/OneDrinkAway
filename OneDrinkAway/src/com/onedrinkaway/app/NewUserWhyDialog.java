package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.onedrinkaway.R;

/**
 * A dialog that explains to the new user why they should rate some drinks
 * @author nicolekihara
 *
 */
public class NewUserWhyDialog extends DialogFragment {	
	
	/**
	 * Opens up the dialog and displays the "You should rate some drinks" message
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        //Retrieve Class name for caller Activity
        // Bundle bundle = this.getArguments();

        builder.setTitle(R.string.why)
        	   .setMessage(R.string.why_message)
               .setPositiveButton(R.string.rate_drinks, new DialogInterface.OnClickListener() {
            	   /**
            	    * The "Rate Drinks!" button listener which brings the user to the
            	    * NewUserRatingActivity to rate some drinks
            	    */
                   public void onClick(DialogInterface dialog, int id) {
                	   Intent intent = new Intent(getActivity(), NewUserRatingActivity.class);    
                	   startActivity(intent);
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            	   /**
            	    * The "No" button listener which dismisses the dialog
            	    */
                   public void onClick(DialogInterface dialog, int id) {
                	   dismiss();
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
}