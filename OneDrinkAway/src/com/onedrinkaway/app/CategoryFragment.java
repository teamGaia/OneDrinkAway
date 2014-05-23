package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.onedrinkaway.R;

public class CategoryFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_category, null);
       return ll;
   }
}