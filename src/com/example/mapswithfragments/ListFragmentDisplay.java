package com.example.mapswithfragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class ListFragmentDisplay extends SherlockFragment {
	public static final String TAG = "listFragmentDisplay";
	public static final String DRINKS_TAG = "drinks";
	public static final String EVENTS_TAG = "events";
	public static final String NAME_TAG = "name";
	public static final String DESCRIPTION_TAG = "description";
	public static final String DATE_TAG = "event_date";
	public static final String TIME_TAG = "event_time";
	public static final String WEEKDAY_TAG = "weekday";
	public static final String SUNDAY = "sunday";
	public static final String MONDAY = "monday";
	public static final String TUESDAY = "tuesday";
	public static final String WEDNESDAY = "wednesday";
	public static final String THURSDAY = "thursday";
	public static final String FRIDAY = "friday";
	public static final String SATURDAY = "saturday";

	Calendar calendar = Calendar.getInstance();
	private int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	// listbyday is a list of hash maps each list of hash maps represents a day
	// of the week for the bar that was clicked on.
	private ArrayList<ArrayList<HashMap<String, String>>> listByDay = null;
	private String barName = null;
	public ArrayList<HashMap<String, String>> eventList = null;

	public ArrayList<HashMap<String, String>> getList(int day) {
			return listByDay.get(day);
	
	}

	private void getBarName() {
		barName = ((MapFrags) getActivity()).getBarSelected();
	}

	public static ListFragmentDisplay newInstance() {
		return new ListFragmentDisplay();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the ListView layout file.
		initArrList();
		getBarName();
		fillList();

		return inflater.inflate(R.layout.list_view, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
		if (mViewPager.getAdapter() == null)
			mViewPager.setAdapter(new FragAdapter(getChildFragmentManager()));
		mViewPager.setCurrentItem(dayOfWeek);
	}

	private void initArrList() {
		if (listByDay == null) {
			listByDay = new ArrayList<ArrayList<HashMap<String, String>>>();
		} else {
			listByDay.clear();
		}
		for (int i = 0; i < 7; i++) {
			ArrayList<HashMap<String, String>> hm = new ArrayList<HashMap<String, String>>();
			listByDay.add(hm);

		}
	}

	synchronized private void fillList() {
		LoadWebTask lWT = new LoadWebTask();
		executeAsyncTask(lWT, getSherlockActivity().getApplicationContext());
	}

	@TargetApi(11)
	static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
			T... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}

	private class LoadWebTask extends AsyncTask<Context, Void, Void> {

		JSONArray mDrinks = null;
		JSONArray mEvents = null;
		HashMap<String, String> specialMap = null;

		@Override
		protected Void doInBackground(Context... params) {

			try {
				URL CNO = new URL(
						"http://chiconightout.no-ip.org/api/bar_data/"
								+ barName);
				URLConnection tc = CNO.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					// this prints the entire line
					JSONObject ja = new JSONObject(line);
					// gets the Drinks array as a JSON Array
					mDrinks = ja.getJSONArray(DRINKS_TAG);
					// gets the Events as a JSON object
					mEvents = ja.getJSONArray(EVENTS_TAG);

					for (int i = 0; i < mDrinks.length(); i++) {
						JSONObject d = mDrinks.getJSONObject(i);
						String dName = d.getString(NAME_TAG);
						String dDescription = d.getString(DESCRIPTION_TAG);
						String dDay = d.getString(WEEKDAY_TAG);
						specialMap = new HashMap<String, String>();
						specialMap.put(NAME_TAG, dName);
						specialMap.put(DESCRIPTION_TAG, dDescription);

						if (SUNDAY.equals(dDay)) {
							listByDay.get(0).add(specialMap);
						} else if (dDay.equals(MONDAY)) {
							listByDay.get(1).add(specialMap);
						} else if (dDay.equals(TUESDAY)) {
							listByDay.get(2).add(specialMap);
						} else if (dDay.equals(WEDNESDAY)) {
							listByDay.get(3).add(specialMap);
						} else if (dDay.equals(THURSDAY)) {
							listByDay.get(4).add(specialMap);
						} else if (dDay.equals(FRIDAY)) {
							listByDay.get(5).add(specialMap);
						} else if (dDay.equals(SATURDAY)) {
							listByDay.get(6).add(specialMap);
						}

					}
					for (int i = 0; i < mEvents.length(); i++) {
						JSONObject d = mEvents.getJSONObject(i);
						Time time = new Time();
						String eTime = d.getString(TIME_TAG);
						String eDescription = d.getString(DESCRIPTION_TAG);
						String eDate = d.getString(DATE_TAG);
						Boolean mTime = time.parse3339(eTime);
						if (mTime == true) {
							eTime = time.toString();
						}
						HashMap<String, String> eventMap = new HashMap<String, String>();
						eventMap.put(DATE_TAG, eDate);
						eventMap.put(DESCRIPTION_TAG, eDescription);
						eventMap.put(TIME_TAG, eTime);
						if (eventList == null) {
							eventList = new ArrayList<HashMap<String, String>>();
						}
						eventList.add(eventMap);
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}
}
