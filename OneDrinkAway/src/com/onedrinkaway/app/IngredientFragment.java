package com.onedrinkaway.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onedrinkaway.R;

public class IngredientFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	   return inflater.inflate(R.layout.fragment_ingredient, null);
	}
}