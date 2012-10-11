package com.NightOutApps.chiconightout;

import java.io.IOException;
import java.util.Calendar;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;


public class CNOMapView extends MapActivity {
	private MapView map;
	private MapController control;
	Calendar calendar = Calendar.getInstance();
	int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	public static final String DRINKNAME = "DrinkName";
	
	public static final String DRINKDESCRIPT = "DrinkDescription";
	private Cursor c = null;
	public static final String DATABASE_TABLE = "Drink";
	private String[] colsfrom = {"_id", DRINKNAME, DRINKDESCRIPT};
	String barStr = "Bar_id = 2";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		initMapView();
		initMyLocation();
		MyDBHelper myDbHelper = new MyDBHelper(CNOMapView.this);
	        try {
	        	myDbHelper.createDataBase();
	            Toast.makeText(CNOMapView.this, "Success in creation", Toast.LENGTH_SHORT).show();

	        }
	        catch (IOException ioe) {
	            throw new Error("Unable to create database");
	        }
		
		
		GeoPoint bansheePoint = new GeoPoint(39730006,-121841087); //39729912,-121841041
		GeoPoint beachPoint = new GeoPoint(39730693,-121839199); //39730685,-121839423
		GeoPoint bellasPoint = new GeoPoint(39729984,-121841755);
		GeoPoint crazyPoint = new GeoPoint(39729596,-121839414); //39729621,-121839522
		GeoPoint downPoint = new GeoPoint(39729491,-121839170);
		GeoPoint duffysPoint = new GeoPoint(39729171,-121838808);
		GeoPoint joesPoint = new GeoPoint(39723920,-121844260);
		GeoPoint lasallesPoint = new GeoPoint(39729414,-121840720);
		GeoPoint lostPoint = new GeoPoint(39729412,-121839089);
		GeoPoint bearPoint = new GeoPoint(39729000,-121842430); //39729192, 121842705
		GeoPoint maltesePoint = new GeoPoint(39721210,-121827139);
		GeoPoint panamasPoint = new GeoPoint(39730627,-121839792); //39730517,-121839671
		GeoPoint rileysPoint = new GeoPoint(39724563,-121843874);
		GeoPoint gradPoint = new GeoPoint(39724585,-121838365);
		GeoPoint townPoint = new GeoPoint(39729303,-121838974);
		GeoPoint ubarPoint = new GeoPoint(39730858,-121839387); //39730685, -121839423
		
		Drawable bansheeIcon = this.getResources().getDrawable(R.drawable.banshee_icontest);
		Drawable beachIcon = this.getResources().getDrawable(R.drawable.beach_icontest);
		Drawable bellasIcon = this.getResources().getDrawable(R.drawable.bellas_icon);
		Drawable crazyIcon = this.getResources().getDrawable(R.drawable.crazy_horse_icontest);
		Drawable downIcon = this.getResources().getDrawable(R.drawable.down_lo_icontest);
		Drawable duffysIcon = this.getResources().getDrawable(R.drawable.duffys_icontest);
		Drawable joesIcon = this.getResources().getDrawable(R.drawable.joes_icontest);
		Drawable lasallesIcon = this.getResources().getDrawable(R.drawable.lasalles_icontest);
		Drawable lostIcon = this.getResources().getDrawable(R.drawable.lost_on_main_icontest);
		
		Drawable bearIcon = this.getResources().getDrawable(R.drawable.madison_bear_icontest);
		Drawable malteseIcon = this.getResources().getDrawable(R.drawable.maltese_icontest);
		Drawable panamasIcon = this.getResources().getDrawable(R.drawable.panamas_icontest);
		Drawable rileysIcon = this.getResources().getDrawable(R.drawable.rileys_icontest);
		Drawable gradIcon = this.getResources().getDrawable(R.drawable.the_grad_icontest);
		Drawable townIcon = this.getResources().getDrawable(R.drawable.town_lounge_icontest);
		Drawable ubarIcon = this.getResources().getDrawable(R.drawable.u_bar_icontest);
		Drawable cnoIco = this.getResources().getDrawable(R.drawable.ic_launcher);
		
		OverlayItem overlaybanshee = new OverlayItem(bansheePoint, "Banshee", "I'm in Mexico City!");
		OverlayItem overlaybeach = new OverlayItem(beachPoint, "Beach", "I'm in Mexico City!");
		OverlayItem overlaybellas = new OverlayItem(bellasPoint, "Bellas", "I'm in Mexico City!");
		OverlayItem overlaycrazy = new OverlayItem(crazyPoint, "Crazy Horse", "I'm in Mexico City!");
		OverlayItem overlaydown = new OverlayItem(downPoint, "Down Lo", "I'm in Mexico City!");
		OverlayItem overlayduffys = new OverlayItem(duffysPoint, "Duffys", "I'm in Mexico City!");
		OverlayItem overlayjoes = new OverlayItem(joesPoint, "Joes", "I'm in Mexico City!");
		OverlayItem overlaylasalles = new OverlayItem(lasallesPoint, "Lasalles", "I'm in Mexico City!");
		OverlayItem overlaylost = new OverlayItem(lostPoint, "Lost On Main", "I'm in Mexico City!");
		OverlayItem overlaybear = new OverlayItem(bearPoint, "Madison Bear Garden", "I'm in Mexico City!");
		OverlayItem overlaymaltese = new OverlayItem(maltesePoint, "Maltese", "I'm in Mexico City!");
		OverlayItem overlaypanamas = new OverlayItem(panamasPoint, "Panamas", "I'm in Mexico City!");
		OverlayItem overlayrileys = new OverlayItem(rileysPoint, "Rileys", "I'm in Mexico City!");
		OverlayItem overlaygrad = new OverlayItem(gradPoint, "The Grad", "I'm in Mexico City!");
		OverlayItem overlaytown = new OverlayItem(townPoint, "Town Lounge", "I'm in Mexico City!");
		OverlayItem overlayubar = new OverlayItem(ubarPoint, "University Bar", "I'm in Mexico City!");
		
		BarOverlay barsOverlay = new BarOverlay(cnoIco, this);
		
		barsOverlay.addOverlay(overlaybanshee, bansheeIcon);
		barsOverlay.addOverlay(overlaybeach, beachIcon);
		barsOverlay.addOverlay(overlaybellas, bellasIcon);
		barsOverlay.addOverlay(overlaycrazy, crazyIcon);
		barsOverlay.addOverlay(overlaydown, downIcon);
		barsOverlay.addOverlay(overlayduffys, duffysIcon);
		barsOverlay.addOverlay(overlayjoes, joesIcon);
		barsOverlay.addOverlay(overlaylasalles, lasallesIcon);
		barsOverlay.addOverlay(overlaylost, lostIcon);
		barsOverlay.addOverlay(overlaybear, bearIcon);
		barsOverlay.addOverlay(overlaymaltese, malteseIcon);
		barsOverlay.addOverlay(overlaypanamas, panamasIcon);
		barsOverlay.addOverlay(overlayrileys, rileysIcon);
		barsOverlay.addOverlay(overlaygrad, gradIcon);
		barsOverlay.addOverlay(overlaytown, townIcon);
		barsOverlay.addOverlay(overlayubar, ubarIcon);
		
		map.getOverlays().add(barsOverlay);
}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	private void initMapView() {
		map = (MapView) findViewById(R.id.map);
		control = map.getController();
		map.setBuiltInZoomControls(true);
		map.setSatellite(false);
		map.invalidate();
	}
	private void initMyLocation() {
		final MyLocationOverlay overlay = new MyLocationOverlay(this, map);
		overlay.enableMyLocation();
		overlay.runOnFirstFix(new Runnable() {
			public void run() {
				control.setZoom(18);
				control.animateTo(overlay.getMyLocation());
			}
		});
		map.getOverlays().add(overlay);
	}

}
