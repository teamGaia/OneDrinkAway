package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

/**
 * A dialog that appears when the user is opening the application for the first time.
 * Asks the user if they want to rate some drinks to get started.
 * @author nicolekihara
 *
 */
public class NewUserDialog extends DialogFragment {	
	
	/**
	 * Opens up the dialog and displays the "Would you like to rate some drinks?" message
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.welcome_dialog)
        	   .setMessage(R.string.welcome_dialog_message)
               .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            	   /**
            	    * The "Sure!" button listener that brings the user to the NewUserRatingActivity
            	    */
                   public void onClick(DialogInterface dialog, int id) {
                	   // Intent intent = new Intent(getBaseContext(), NewUserRatingActivity.class);    
                	   // startActivity(i);
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            	   /**
            	    * The "No" button listener that dismisses the dialog
            	    */
                   public void onClick(DialogInterface dialog, int id) {
                	   dismiss();
                   }
               })
               .setNeutralButton(R.string.why, new DialogInterface.OnClickListener() {
            	   /**
            	    * The "Why?" button listener that brings up the NewUserWhyDialog
            	    */
                   public void onClick(DialogInterface dialog, int id) {
                	   DialogFragment newFragment = new NewUserWhyDialog();
                	   newFragment.show(getFragmentManager(), "newUserWhy");
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
