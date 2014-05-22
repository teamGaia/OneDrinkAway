package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

public class NewUserDialog extends DialogFragment {	
	
	/**
	 * Opens up the dialog and displays the help for that feature
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.welcome_dialog)
        	   .setMessage(R.string.welcome_dialog_message)
               .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   // Intent intent = new Intent(getBaseContext(), NewUserRatingActivity.class);    
                	   // startActivity(i);
                   }
               })
               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   dismiss();
                   }
               })
               .setNeutralButton(R.string.why, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   DialogFragment newFragment = new NewUserWhyDialog();
                	   newFragment.show(getFragmentManager(), "newUserWhy");
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
