package com.example.mapswithfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragAdapter extends FragmentPagerAdapter {

	private static final String[] CONTENT = new String[] { "Sunday", "Monday",
			"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

	public FragAdapter(FragmentManager fm) {
		super(fm);
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
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

/* public Fragment getItem(int position) {
  Integer key = new Integer(position);
  if(fragments.containsKey(key)) {
   return fragments.get(key);
  }

  PojoGroup group = pojoGroups.get(position);
  PojoFrag frag = new PojoFrag(group);
  fragments.put(key, frag);
  return frag;
 }

 public int getCount() {
  return (null == pojoGroups) ? 0 : pojoGroups.size();
 }

 public int getItemPosition(Object object) {
  if(!fragments.containsKey(object)) 
   return POSITION_NONE;
  return POSITION_UNCHANGED;
 }*/
