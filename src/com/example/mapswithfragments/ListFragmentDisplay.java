package com.example.mapswithfragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class ListFragmentDisplay extends SherlockFragment {
	public static final String TAG = "listFragmentDisplay";
	// listbyday is a list of hash maps each list of hash maps represents a day
	// of the week for the bar that was clicked on.
	private ArrayList<ArrayList<HashMap<String, String>>> listByDay = null;

	public ArrayList<ArrayList<HashMap<String, String>>> getList(int day) {
		return listByDay;
	}

	public static ListFragmentDisplay newInstance() {
		return new ListFragmentDisplay();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the ListView layout file.
		return inflater.inflate(R.layout.list_view, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		mViewPager.setAdapter(new FragAdapter(getChildFragmentManager()));
	}
}
