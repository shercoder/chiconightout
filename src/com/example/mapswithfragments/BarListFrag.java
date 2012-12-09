package com.example.mapswithfragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class BarListFrag extends SherlockListFragment {
	public static final String TAG = "barlistfrag";
	String[] names = new String[] { "banshee", "beach", "bellas",
			"crazyhorse", "downlo", "duffys", "joes", "lasalles",
			"lost", "madisonbeargarden", "maltese", "panamas",
			"rileys", "thegrad", "townlounge", "ubar" };

	int[] barIcon = new int[] { R.drawable.banshee_icon, R.drawable.beach_icon,
			R.drawable.bellas_icon, R.drawable.crazy_horse_icon,
			R.drawable.down_lo_icon, R.drawable.duffys_icon,
			R.drawable.joes_icon, R.drawable.lasalles_icon,
			R.drawable.lost_on_main_icon, R.drawable.madison_bear_icon,
			R.drawable.maltese_icon, R.drawable.panamas_icon,
			R.drawable.rileys_icon, R.drawable.the_grad_icon,
			R.drawable.town_lounge_icon, R.drawable.u_bar_icon };
	String[] barName = new String[] {
			"Banshee", "Beach", "Bellas",
			"Crazy Horse", "Down Lo", "Duffys", "Joes", "Lasalles",
			"Lost On Main", "Madison Bear Garden", "Maltese", "Panamas",
			"Rileys", "The Graduate", "Town Lounge", "University Bar"
	};

	Calendar calendar = Calendar.getInstance();
	int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

	static BarListFrag newInstance() {
		return new BarListFrag();
	}

	@Override
	public View onCreateView(LayoutInflater inflate, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		setHasOptionsMenu(true);
		View result = inflate.inflate(R.layout.activity_bar_list_view,
				container, false);

		// Each row stores Bar name, Bar Icon
		List<HashMap<String, String>> barList = new ArrayList<HashMap<String, String>>();
		// add bar icons and bar names to the list
		for (int i = 0; i < 16; i++) {
			HashMap<String, String> barMap = new HashMap<String, String>();

			barMap.put("barname", barName[i]);
			barMap.put("baricon", Integer.toString(barIcon[i]));

			barList.add(barMap);
		}
		// Keys used in Hashmap that will be mapped to the rows
		String[] bname = { "baricon", "barname" };

		// Ids of views in row.xml that will define the layout of the list
		// items.
		int[] bicon = { R.id.baricon, R.id.barname };
		// setting up the list view that and defining the layout of that list.
		// Creating an adapter to store each row item
		// R.layout.row.xml defines the layout of each entry in the list
		SimpleAdapter adapter = new SimpleAdapter(getSherlockActivity(),
				barList, R.layout.row, bname, bicon);

		// binding the adapter to the list view.
		setListAdapter(adapter);
		
		return result;

	}
	@Override
	public void onListItemClick(ListView parent, View view, int position, long id) {
		MapFrags mp = ((MapFrags) this.getSherlockActivity());
		mp.setListData(names[position]);
	}
}
