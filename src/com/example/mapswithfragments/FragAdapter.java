package com.example.mapswithfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

	public class FragAdapter extends FragmentPagerAdapter {
		
		private static final String[] CONTENT = new String[] {"Sunday", "Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday"};
		
		public FragAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Bundle args = new Bundle();
			args.putInt(MyListFragment.TAG, arg0);
			return MyListFragment.newInstance(args);
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}

	}

