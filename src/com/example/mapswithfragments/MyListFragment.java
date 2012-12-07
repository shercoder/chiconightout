package com.example.mapswithfragments;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public class MyListFragment extends SherlockListFragment {
	public static final String NAME_TAG = "name";
	public static final String DESCRIPTION_TAG = "description";
	public static final String TAG = "listFragment";
	// Keys used in Hashmap that will be mapped to the rows
	String[] dFrom = { NAME_TAG, DESCRIPTION_TAG };
	private ArrayList<HashMap<String, String>> dList;
	int[] dTo = { R.id.drink_name, R.id.d_description };
	SimpleAdapter adapter = null;

	public void upDateList() {
		ListFragmentDisplay lFD = (ListFragmentDisplay) this
				.getParentFragment();
		dList = lFD.getList(getArguments().getInt(TAG));
		if(adapter != null)
		adapter.notifyDataSetChanged();

	}

	public static MyListFragment newInstance(int pos) {
		MyListFragment frag = new MyListFragment();
		Bundle args = new Bundle();
		args.putInt(TAG, pos);
		frag.setArguments(args);
		return (frag);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the ListView layout file.
		// ListFragmentDisplay lFD = (ListFragmentDisplay) this
		// .getParentFragment();
		// dList = lFD.getList(dow);
		upDateList();
		View results = inflater.inflate(R.layout.list_fragment, container,
				false);
		adapter = new SimpleAdapter(getParentFragment().getActivity(), dList,
				R.layout.listrow, dFrom, dTo);
		upDateList();
		setListAdapter(adapter);
		

		return results;

	}

}