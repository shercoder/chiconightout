package com.example.mapswithfragments;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ZoomControls;

import com.actionbarsherlock.app.SherlockFragment;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MapFragment extends SherlockFragment implements OnClickListener {

	public static final String TAG = "mapFragment";
	private MapController control;
	private View mapViewContainer;
	private MapView mapView;
	private CallACab cacButton;
	private Facebook facebook;
	private LoadOverlaysTask ovTsk = null;
	private static final GeoPoint centerP = new GeoPoint(39728478, -121842176);
	private String selected = null;

	public static MapFragment newInstance(int index) {
		MapFragment mf = new MapFragment();
		Bundle args = new Bundle();
		args.putInt("index", index);
		mf.setArguments(args);

		return mf;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle data) {
		//super.onCreateView(inflater, vg, data);
		if (vg == null)
	        return null;
		// The Activity created the MapView for us, so we can do some init
		// stuff.

		initMapView();
		setRetainInstance(true);
		setHasOptionsMenu(true);
		return mapViewContainer;
	}

	/*
	 * private void initMyLocation() {
	 * 
	 * final MyLocationOverlay overlay = new MyLocationOverlay(this,
	 * Exchanger.mMapView); overlay.enableMyLocation();
	 * overlay.runOnFirstFix(new Runnable() { public void run() {
	 * control.setZoom(18); control.setCenter(centerP); } });
	 * map.getOverlays().add(overlay); }
	 */
	public void facebookInit() {
		facebook = new Facebook("281094275327241");

		AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
	}

	private void initMapView() {
		MapFrags mapActivity = (MapFrags) getActivity();
		mapViewContainer = mapActivity.getMapContainer();
		mapView = Exchanger.mMapView;
		
		View bacButton = mapViewContainer.findViewById(R.id.bacbutton);
		bacButton.setOnClickListener(this);

		View barListButton = mapViewContainer.findViewById(R.id.barlistbutton);
		barListButton.setOnClickListener(this);

		View callACabButton = mapViewContainer.findViewById(R.id.cacbutton);
		callACabButton.setOnClickListener(this);

		View facebookButton = mapViewContainer
				.findViewById(R.id.facebookbutton);
		facebookButton.setOnClickListener(this);

		if (null != mapView) {
			// mapView.setBuiltInZoomControls(true);
			mapView.setSatellite(false);
			control = mapView.getController();
			final ZoomControls zoomer = (ZoomControls) mapViewContainer
					.findViewById(R.id.zoom);
			zoomer.setOnZoomInClickListener(new OnClickListener() {
				public void onClick(View v) {
					control.zoomIn();
					checkzoom(zoomer);
				}
			});
			zoomer.setOnZoomOutClickListener(new OnClickListener() {
				public void onClick(View v) {
					control.zoomOut();
					checkzoom(zoomer);

				}
			});
		}
		deliverModel(mapActivity);
	}

	// not sure if i need this yet -->
	// Exchanger.mMapView.setClickable(true);
	public void checkzoom(ZoomControls zoomer) {
		if (mapView.getZoomLevel() == 1) // dont know if 0 or 1, just wrote it
		{
			zoomer.setIsZoomOutEnabled(false);
			// or u can change drawable and disable click
		}
		if (mapView.getZoomLevel() == 18) {
			zoomer.setIsZoomInEnabled(false);
			// or u can change drawable and disable click
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		// The way MainActivity creates this fragment, it will call
		// onCreateView()
		// each time we start (or navigate back to) this map. To prevent the
		// "You are only allowed to have a single MapView in a MapActivity"
		// message,
		// we only inflate the map's XML layout once. When we try to add it a
		// second
		// time, we get "IllegalStateException: The specified child already has
		// a
		// parent. You must call removeView() on the child's parent first."
		// So, here we remove the view from MainActivity's parent layout
		// so we can re-add it later when onCreateView() is called.
		// TODO: change this once the map doesn't go away (i.e. on Tablets)
		ViewGroup parentViewGroup = (ViewGroup) mapViewContainer.getParent();
		if (null != parentViewGroup) {
			parentViewGroup.removeView(mapViewContainer);
		}
	}

	@Override
	public void onClick(View v) {
		MapFrags boss = (MapFrags) getActivity();
		switch (v.getId()) {
		case R.id.bacbutton:
			boss.showFragment(3);
			break;
		case R.id.barlistbutton:
			boss.showFragment(2);
			break;
		case R.id.cacbutton:
			cacButton = new CallACab(getSherlockActivity());
			break;
		case R.id.facebookbutton:
			break;
		}
		// Show mMyListFragment.
	}

	public void authorizeFacebook() {
		facebook.authorize(getSherlockActivity(), new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	synchronized private void deliverModel(MapFrags frag) {

		initMyLocation();
		ovTsk = new LoadOverlaysTask(frag);
		executeAsyncTask(ovTsk, getSherlockActivity().getApplicationContext());
	}

	private void initMyLocation() {

		final MyLocationOverlay overlay = new MyLocationOverlay(
				getSherlockActivity(), mapView);
		overlay.enableMyLocation();
		control.setZoom(18);
		control.setCenter(centerP);
		//overlay.runOnFirstFix(new Runnable() {
		//	public void run() {
		//		
		//	}
	//});
		mapView.getOverlays().add(overlay);
	}
	public void setItemClick(String itemClicked) {
		selected = itemClicked;
	}

	@TargetApi(11)
	static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
			T... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}

	private class LoadOverlaysTask extends AsyncTask<Context, Void, Void> {
		BarOverlay barsOverlay;
		private MapFrags mFrag;
		public LoadOverlaysTask(MapFrags frag) {
			mFrag = frag;
		}

		@Override
		protected Void doInBackground(Context... ctxt) {
			GeoPoint bansheePoint = new GeoPoint(39730006, -121841087); // 39729912,-121841041
			GeoPoint beachPoint = new GeoPoint(39730693, -121839199); // 39730685,-121839423
			GeoPoint bellasPoint = new GeoPoint(39729984, -121841755);
			GeoPoint crazyPoint = new GeoPoint(39729596, -121839414); // 39729621,-121839522
			GeoPoint downPoint = new GeoPoint(39729491, -121839170);
			GeoPoint duffysPoint = new GeoPoint(39729171, -121838808);
			GeoPoint joesPoint = new GeoPoint(39723920, -121844260);
			GeoPoint lasallesPoint = new GeoPoint(39729414, -121840720);
			GeoPoint lostPoint = new GeoPoint(39729412, -121839089);
			GeoPoint bearPoint = new GeoPoint(39729000, -121842430); // 39729192,
																		// 121842705
			GeoPoint maltesePoint = new GeoPoint(39721210, -121827139);
			GeoPoint panamasPoint = new GeoPoint(39730627, -121839792); // 39730517,-121839671
			GeoPoint rileysPoint = new GeoPoint(39724563, -121843874);
			GeoPoint gradPoint = new GeoPoint(39724585, -121838365);
			GeoPoint townPoint = new GeoPoint(39729303, -121838974);
			GeoPoint ubarPoint = new GeoPoint(39730858, -121839387); // 39730685,
																		// -121839423
			Drawable bansheeIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.banshee_icon_trans);
			Drawable beachIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.beach_icon_trans);
			Drawable bellasIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.bellas_icon_trans);
			Drawable crazyIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.crazy_horse_icon_trans);
			Drawable downIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.down_lo_icon_trans);
			Drawable duffysIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.duffys_icon_trans);
			Drawable joesIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.joes_icon_trans);
			Drawable lasallesIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.lasalles_icon_trans);
			Drawable lostIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.lost_on_main_icon_trans);
			Drawable bearIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.madison_bear_icon_trans);
			Drawable malteseIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.maltese_icon_trans);
			Drawable panamasIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.panamas_icon_trans);
			Drawable rileysIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.rileys_icon_trans);
			Drawable gradIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.the_grad_icon_trans);
			Drawable townIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.town_lounge_icon_trans);
			Drawable ubarIcon = getSherlockActivity().getResources()
					.getDrawable(R.drawable.u_bar_icon_trans);
			Drawable cnoIco = getSherlockActivity().getResources().getDrawable(
					R.drawable.ic_launcher);
			Rect bansheeR = new Rect(-bansheeIcon.getIntrinsicWidth() / 2,
					-bansheeIcon.getIntrinsicHeight(),
					bansheeIcon.getIntrinsicWidth() / 2, 0);
			Rect beachR = new Rect(0, -beachIcon.getIntrinsicHeight(),
					beachIcon.getIntrinsicWidth(), 0);
			Rect bellasR = new Rect(-bellasIcon.getIntrinsicWidth() / 2,
					-bellasIcon.getIntrinsicHeight(),
					bellasIcon.getIntrinsicWidth() / 2, 0);
			Rect crazyR = new Rect(-crazyIcon.getIntrinsicWidth() / 2,
					-crazyIcon.getIntrinsicHeight(),
					crazyIcon.getIntrinsicWidth() / 2, 0);
			Rect downR = new Rect(-downIcon.getIntrinsicWidth(),
					-downIcon.getIntrinsicHeight(), 0, 0);
			Rect duffysR = new Rect(0, -duffysIcon.getIntrinsicHeight(),
					duffysIcon.getIntrinsicWidth(), 0);
			Rect joesR = new Rect(-joesIcon.getIntrinsicWidth() / 2,
					-joesIcon.getIntrinsicHeight(),
					joesIcon.getIntrinsicWidth() / 2, 0);
			Rect lasallesR = new Rect(-lasallesIcon.getIntrinsicWidth() / 2,
					-lasallesIcon.getIntrinsicHeight(),
					lasallesIcon.getIntrinsicWidth() / 2, 0);
			Rect lostR = new Rect(0, -lostIcon.getIntrinsicHeight(),
					lostIcon.getIntrinsicWidth(), 0);
			Rect bearR = new Rect(-bearIcon.getIntrinsicWidth() / 2,
					-bearIcon.getIntrinsicHeight(),
					bearIcon.getIntrinsicWidth() / 2, 0);
			Rect malteseR = new Rect(-malteseIcon.getIntrinsicWidth() / 2,
					-malteseIcon.getIntrinsicHeight(),
					malteseIcon.getIntrinsicWidth() / 2, 0);
			Rect panamasR = new Rect(-panamasIcon.getIntrinsicWidth() / 2,
					-panamasIcon.getIntrinsicHeight(),
					panamasIcon.getIntrinsicWidth() / 2, 0);
			Rect rileysR = new Rect(-rileysIcon.getIntrinsicWidth() / 2,
					-rileysIcon.getIntrinsicHeight(),
					rileysIcon.getIntrinsicWidth() / 2, 0);
			Rect gradR = new Rect(-gradIcon.getIntrinsicWidth() / 2,
					-gradIcon.getIntrinsicHeight(),
					gradIcon.getIntrinsicWidth() / 2, 0);
			Rect townR = new Rect(-townIcon.getIntrinsicWidth(),
					-townIcon.getIntrinsicHeight(), 0, 0);
			Rect ubarR = new Rect(-ubarIcon.getIntrinsicWidth() / 2,
					-ubarIcon.getIntrinsicHeight(),
					ubarIcon.getIntrinsicWidth() / 2, 0);

			BarItem overlaybanshee = new BarItem(bansheePoint, "banshee",
					"I'm at Banshee!", bansheeIcon, bansheeR);
			BarItem overlaybeach = new BarItem(beachPoint, "beach",
					"I'm at The Beach!", beachIcon, beachR);
			BarItem overlaybellas = new BarItem(bellasPoint, "bellas",
					"I'm at Bellas", bellasIcon, bellasR);
			BarItem overlaycrazy = new BarItem(crazyPoint, "crazyhorse",
					"I'm at The Crazy Horse!", crazyIcon, crazyR);
			BarItem overlaydown = new BarItem(downPoint, "downlo",
					"I'm at the DownLo!", downIcon, downR);
			BarItem overlayduffys = new BarItem(duffysPoint, "duffys",
					"I'm at Duffy's!", duffysIcon, duffysR);
			BarItem overlayjoes = new BarItem(joesPoint, "joes",
					"I'm at Joe's!", joesIcon, joesR);
			BarItem overlaylasalles = new BarItem(lasallesPoint, "lasalles",
					"I'm at Lasalles!", lasallesIcon, lasallesR);
			BarItem overlaylost = new BarItem(lostPoint, "lost",
					"I'm at Lost On Main!", lostIcon, lostR);
			BarItem overlaybear = new BarItem(bearPoint, "madisonbeargarden",
					"I'm at The Bear", bearIcon, bearR);
			BarItem overlaymaltese = new BarItem(maltesePoint, "maltese",
					"I'm at the Maltese!", malteseIcon, malteseR);
			BarItem overlaypanamas = new BarItem(panamasPoint, "panamas",
					"I'm at Panamas!", panamasIcon, panamasR);
			BarItem overlayrileys = new BarItem(rileysPoint, "rileys",
					"I'm at Rileys!", rileysIcon, rileysR);
			BarItem overlaygrad = new BarItem(gradPoint, "thegrad",
					"I'm at The Graduate!", gradIcon, gradR);
			BarItem overlaytown = new BarItem(townPoint, "townlounge",
					"I'm at Town Lounge!", townIcon, townR);
			BarItem overlayubar = new BarItem(ubarPoint, "ubar",
					"I'm at the U-Bar!", ubarIcon, ubarR);

			barsOverlay = new BarOverlay(cnoIco, getSherlockActivity(), mFrag);
			barsOverlay.addOverlay(overlaybanshee);
			barsOverlay.addOverlay(overlaybeach);
			barsOverlay.addOverlay(overlaybellas);
			barsOverlay.addOverlay(overlaycrazy);
			barsOverlay.addOverlay(overlaydown);
			barsOverlay.addOverlay(overlayduffys);
			barsOverlay.addOverlay(overlayjoes);
			barsOverlay.addOverlay(overlaylasalles);
			barsOverlay.addOverlay(overlaylost);
			barsOverlay.addOverlay(overlaybear);
			barsOverlay.addOverlay(overlaymaltese);
			barsOverlay.addOverlay(overlaypanamas);
			barsOverlay.addOverlay(overlayrileys);
			barsOverlay.addOverlay(overlaygrad);
			barsOverlay.addOverlay(overlaytown);
			barsOverlay.addOverlay(overlayubar);

			return (null);
		}

		@Override
		public void onPostExecute(Void arg0) {
			mapView.getOverlays().add(barsOverlay);
		}
	}
}