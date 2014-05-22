package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

public class NewUserWhyDialog extends DialogFragment {	
	
	/**
	 * Opens up the dialog and displays the help for that feature
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
                   public void onClick(DialogInterface dialog, int id) {
                	// Intent intent = new Intent(getBaseContext(), NewUserRatingActivity.class);    
                	// startActivity(intent);
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}