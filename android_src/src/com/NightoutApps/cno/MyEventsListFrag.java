package com.NightoutApps.cno;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public class MyEventsListFrag extends SherlockListFragment {
	public static final String DATE_TAG = "event_date";
	public static final String DESCRIPTION_TAG = "description";
	public static final String TIME_TAG = "event_time";
	public static final String TAG = "eventlistFragment";
	// Keys used in Hashmap that will be mapped to the rows
	String[] dFrom = { DESCRIPTION_TAG, DATE_TAG, TIME_TAG };
	private ArrayList<HashMap<String, String>> eList;
	int[] dTo = { R.id.e_description, R.id.e_date, R.id.time };
	SimpleAdapter adapter = null;

	public void upDateList() {
		ListFragmentDisplay lFD = (ListFragmentDisplay) this
				.getParentFragment();
		eList = lFD.getList(getArguments().getInt(TAG));
		if(adapter != null)
		adapter.notifyDataSetChanged();

	}

	public static MyEventsListFrag newInstance(int pos) {
		MyEventsListFrag mfrag = new MyEventsListFrag();
		Bundle args = new Bundle();
		args.putInt(TAG, pos);
		mfrag.setArguments(args);
		return (mfrag);
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
		adapter = new SimpleAdapter(getParentFragment().getActivity(), eList,
				R.layout.eventrow, dFrom, dTo);
		upDateList();
		setListAdapter(adapter);
		

		return results;

	}

}
