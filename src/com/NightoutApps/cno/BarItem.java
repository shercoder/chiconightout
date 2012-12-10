package com.NightoutApps.cno;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class BarItem extends OverlayItem{
	
	Drawable mMarker = null;
	Rect mBounds = null;
	public BarItem(GeoPoint point, String title, String snippet, Drawable draw, Rect bounds) {
		super(point, title, snippet);
		
		mBounds = bounds;
		//0,0 position is the geopoint on the map. Positive is going right and down. negative goes left and up. setBounds accepts (leftBound, topbound, right bound, bottom bound)
		draw.setBounds(-draw.getIntrinsicWidth()/2, -draw.getIntrinsicHeight(), draw.getIntrinsicWidth()/2, 0);
		this.setMarker(draw);
		mMarker = draw;
	}
	//TODO Fix Markers. 
	/* The Above code creates a new constructor for an overlayitem that accepts a drawable.
	 * When accepting a drawable you have to set its bounds for it to be displayed. This class
	 * will be used to modify that markers that are shown on the map from the geopoint specified.
	 * If we would like more than just an item then this is the place to start. 
	 */
	@Override
	public Drawable getMarker(int stateBitset) {
		
		mMarker.setBounds(mBounds);
		return mMarker;
		
	}
}
