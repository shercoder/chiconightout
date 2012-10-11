package com.NightOutApps.chiconightout;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;


import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class BarOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> bOverlay = new ArrayList<OverlayItem>();
	private Context contx;
	
	public BarOverlay(Drawable defaultMarker, Context cont) {
		super(boundCenterBottom(defaultMarker));
		contx = cont;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return bOverlay.get(i);
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return bOverlay.size();
	}
	
	public void addOverlay(OverlayItem overlay, Drawable marker) {
		overlay.setMarker(boundCenterBottom(marker));
		bOverlay.add(overlay);
		populate();
	}
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = bOverlay.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(contx);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		
		Intent drinkList = new Intent(contx, DrinkListView.class);
		drinkList.putExtra("_bar", index+1);
		//drinkList.putExtra("_day", dayOfWeek);
		contx.startActivity(drinkList);
		return true;
	}
}
