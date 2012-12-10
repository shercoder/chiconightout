package com.NightoutApps.cno;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

public class BarOverlay extends ItemizedOverlay<BarItem> {
	
	private ArrayList<BarItem> bOverlay = new ArrayList<BarItem>();
	private Context contx;
	private MapFrags mFrag;
	private String mName;
	public BarOverlay(Drawable defaultMarker, Context cont, MapFrags frag) {
		super(boundCenterBottom(defaultMarker));
		contx = cont;
		mFrag = frag;
	}
	@Override
	protected BarItem createItem(int i) {
		return bOverlay.get(i);
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return bOverlay.size();
	}
	
	public void addOverlay(BarItem overlay/*, Drawable marker*/) {
		//overlay.setMarker(boundCenterBottom(marker));
		bOverlay.add(overlay);
		populate();
	}
	
	
	@Override
	protected boolean onTap(final int index) {
		
		BarItem item = bOverlay.get(index);
		mName = item.getTitle();
		mFrag.getSupportActionBar().setIcon(item.getMarker(0));
		mFrag.getSupportActionBar().setTitle(firstToUpper(mName));
		
		mFrag.setListData(mName);
		
		return true;
	}
	
	private String firstToUpper(String string) {
		char[] stringArray = string.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		string = new String(stringArray);
		return string;
	}
}
