package com.onedrinkaway.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.onedrinkaway.R;

/**
 * This class displays a help dialog explaining what the pressed Category is
 * @author Taylor Juve
 *
 */
public class CategoryDialog extends DialogFragment {	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");
        
        int messageId = 0;
		try {
			messageId = R.string.class.getField("category_help_" + name.replace(' ', '_')).getInt(null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        
        builder.setTitle(name)
        	   .setMessage(messageId)
               .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
