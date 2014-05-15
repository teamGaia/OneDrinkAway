package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

public class CategoryDialog extends DialogFragment {	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        Bundle bundle = this.getArguments();
        String title = bundle.getString("title");

        builder.setTitle(title)
        	   .setMessage("test, test, 123")
               .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
