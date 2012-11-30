package com.example.mapswithfragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

public class MyListFragment extends SherlockListFragment {

	public static final String TAG = "listFragment";
	// Keys used in Hashmap that will be mapped to the rows
	private static final String[] listRow = { "specialName","specialDescription" };
	private List<HashMap < String,String > > drinkSpList = new ArrayList<HashMap <String,String> >();
	
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
private class SetListTask extends AsyncTask<Context, Void, Void> {
	BarOverlay barsOverlay;

	@Override
	protected Void doInBackground(Context... ctxt) {
List<HashMap < String,String > > barList = new ArrayList<HashMap <String,String> >();
//add bar icons and bar names to the list
for(int i = 0; i<16; i++){
    HashMap<String, String> barMap = new HashMap<String,String>();
    
    barMap.put("barname", barName[i]);
    barMap.put("baricon", Integer.toString(barIcon[i]) );
    
    barList.add(barMap);
}
// Keys used in Hashmap that will be mapped to the rows
String[] bname = { "baricon","barname" };

	// Ids of views in row.xml that will define the layout of the list items. 
	int[] bicon = { R.id.baricon,R.id.barname };

	// Creating an adapter to store each row item
	// R.layout.row.xml defines the layout of each entry in the list
	SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), barList, R.layout.row, bname, bicon);

	// setting up the list view that and defining the layout of that list.
	ListView barListView = ( ListView ) findViewById(R.id.barlist);

	// binding the adapter to the list view.
	barListView.setAdapter(adapter);