package com.example.mapswithfragments;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ListPagerFrag extends SherlockFragmentActivity {
	public static final String TAG = "listFragment";

	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("ViewPager Fragment Activity");

		if (getSupportFragmentManager().findFragmentByTag(MyListFragment.TAG) == null) {
		    getSupportFragmentManager().beginTransaction()
			    .add(android.R.id.content, MyListFragment.newInstance(0), MyListFragment.TAG).commit();
		}
	    }
	
}