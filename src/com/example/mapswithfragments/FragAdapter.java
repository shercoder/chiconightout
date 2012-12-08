package com.example.mapswithfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragAdapter extends FragmentPagerAdapter {

	private static final String[] CONTENT = new String[] { "Sunday", "Monday",
			"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Events" };

	public FragAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		if(CONTENT[arg0].equals("Events"))
			return MyEventsListFrag.newInstance(arg0);
		else
		return MyListFragment.newInstance(arg0);
	}

	@Override
	public int getCount() {
		return CONTENT.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return CONTENT[position % CONTENT.length];
	}
	@Override
    public int getItemPosition(Object object) {
       return POSITION_NONE; //To make notifyDataSetChanged() do something
   }

}


