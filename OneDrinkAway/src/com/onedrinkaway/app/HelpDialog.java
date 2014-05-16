package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

/**
 * Displays a help dialog explaining what a certain feature does
 * @author Nicole Kihara, Taylor Juve, and Andrea Martin
 *
 */
public class HelpDialog extends DialogFragment {	
	
	/**
	 * Opens up the dialog and displays the help for that feature
	 */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        //Retrieve Class name for caller Activity
        Bundle bundle = this.getArguments();

        builder.setTitle(R.string.help_title)
        	   .setMessage(bundle.getInt("helpID"))
               .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
