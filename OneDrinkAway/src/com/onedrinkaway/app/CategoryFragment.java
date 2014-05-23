package com.onedrinkaway.app;

import android.app.DialogFragment;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onedrinkaway.R;
import com.onedrinkaway.model.DrinkModel;

public class CategoryFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_category, null);
	   
	   String[] categories = DrinkModel.getCategories();

	   displayCategories(categories, ll);
		
       return ll;
    }
	
	private void displayCategories(String[] categories, LinearLayout ll) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		LinearLayout container = (LinearLayout) ll.findViewById(R.id.category_space);
		LinearLayout curRow = null;
		for (int i = 0; i < categories.length; i++) {
			// Add a new row (horizontal LinearLayout) every 3 TextViews
			if (i % 3 == 0) {
				curRow = new LinearLayout(getActivity());
				container.addView(curRow);
			}
			
			// Add TextView
			TextView category = (TextView) inflater.inflate(R.layout.category_text_area, null);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f);
			category.setLayoutParams(param);
			category.setText(categories[i]);
			category.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View view) {
					DialogFragment newFragment = new CategoryDialog();
					Bundle bundle = new Bundle();
					
					String name = (String) ((TextView) view).getText();
					bundle.putString("name", name);
					
					newFragment.setArguments(bundle);
				    newFragment.show(getActivity().getFragmentManager(), "category help");
				    return true;
				}
			});
			curRow.addView(category);
		}
	}
	
}