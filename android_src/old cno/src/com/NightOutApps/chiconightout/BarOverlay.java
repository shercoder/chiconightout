package com.NightOutApps.chiconightout;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;


import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class BarOverlay extends ItemizedOverlay<BarItem> {
	
	private ArrayList<BarItem> bOverlay = new ArrayList<BarItem>();
	private Context contx;
	private int dayOfWeek;
	
	public BarOverlay(Drawable defaultMarker, Context cont, int doW) {
		super(boundCenterBottom(defaultMarker));
		contx = cont;
		dayOfWeek = doW;
		
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
		Drawable tempDraw = item.getMarker(0);
		AlertDialog.Builder dialog = new AlertDialog.Builder(contx);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.setIcon(tempDraw);
		dialog.setPositiveButton("Specials and Events", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dia, int id){
				Intent drinkList = new Intent(contx, DrinkListView.class);
				drinkList.putExtra("_bar", index+1);
				drinkList.putExtra("_day", dayOfWeek);
				//drinkList.putExtra("_day", dayOfWeek);
				contx.startActivity(drinkList);	
			}
		});
		dialog.show();
		
		
		return true;
	}
}
