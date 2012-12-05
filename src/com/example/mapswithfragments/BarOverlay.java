package com.example.mapswithfragments;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;

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
		mFrag.setListData(mName);
		return true;
	}
}
