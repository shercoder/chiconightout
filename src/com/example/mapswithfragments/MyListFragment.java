package com.example.mapswithfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public class MyListFragment extends SherlockListFragment {

	public static final String TAG = "listFragment";

	private final String[] mItems = { "List Item 1", "List Item 2",
			"List Item 3", "List Item 4", "List Item 5", "List Item 6",
			"List Item 7", "List Item 8", "List Item 9", "List Item 10" };

	public static MyListFragment newInstance(Bundle args) {
		MyListFragment frag = new MyListFragment();
		frag.setArguments(args);
		return (frag);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		// Inflate the ListView layout file.
		setListAdapter(new ArrayAdapter<String>(getSherlockActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, mItems));
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
}