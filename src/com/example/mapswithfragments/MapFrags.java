package com.example.mapswithfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.google.android.maps.MapView;

public class MapFrags extends SherlockFragmentActivity {

	private MapFragment mMapFragment;
	private ListFragmentDisplay mListFragment;
	private BarListFrag myBarListFragment;
	private BACFrag bacFrag;
	private View mapViewContainer;
	private Facebook facebook = null;
	private String bName = null;
	// We use this fragment as a pointer to the visible one, so we can hide it
	// easily.
	private Fragment mVisible = null;
	private Fragment mFragment1 = null;
	private Menu mMenu = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_frags);
		// We instantiate the MapView here, it’s really important!
		mapViewContainer = LayoutInflater.from(this)
				.inflate(R.layout.map, null);
		//The exchanger ensures that there is only one mapactivity fragment used at a time. aM
		Exchanger.mMapView = (MapView) mapViewContainer
				.findViewById(R.id.mapview);

		setupFragments();
	}

	public View getMapContainer() {
		return mapViewContainer;
	}

	public void setListData(String barName) {
		bName = barName;
		showFragment(1);
	}

	public String getBarSelected() {
		return bName;
	}

	/**
	 * This method does the setting up of the Fragments. It basically checks if
	 * the fragments exist and if they do, we’ll hide them. If the fragments
	 * don’t exist, we create them, add them to the FragmentManager and hide
	 * them.
	 */
	private void setupFragments() {
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();

		// this sets up a menu fragment that i will modify based on which
		// fragment is active.
		/*
		 * mFragment1 = fm.findFragmentByTag("f1"); if (mFragment1 == null) {
		 * mFragment1 = new MenuFragment(); ft.add(mFragment1, "f1");
		 * ft.hide(mFragment1);
		 * 
		 * }
		 */
		// If the activity is killed while in BG, it’s possible that the
		// fragment still remains in the FragmentManager, so, we don’t need to
		// add it again.
		//We only add the map fragment at first. Everything else is added later. aM
		mMapFragment = (MapFragment) getSupportFragmentManager()
				.findFragmentByTag(MapFragment.TAG);
		if (mMapFragment == null) {
			mMapFragment = MapFragment.newInstance(0);
			ft.add(R.id.fragment_container, mMapFragment, MapFragment.TAG);
		}
		// ft.detach(mMapFragment);

		/*
		 * myBarListFragment = (BarListFrag) getSupportFragmentManager()
		 * .findFragmentByTag(BarListFrag.TAG); if (myBarListFragment == null) {
		 * myBarListFragment = new BarListFrag(); //
		 * ft.add(R.id.fragment_container, myBarListFragment, //
		 * BarListFrag.TAG); }
		 */
		// ft.detach(myBarListFragment);

		/*
		 * bacFrag = (BACFrag) getSupportFragmentManager().findFragmentByTag(
		 * BACFrag.TAG); if (bacFrag == null) { bacFrag = new BACFrag(); //
		 * ft.add(R.id.fragment_container, bacFrag, BACFrag.TAG); }
		 */
		// ft.detach(bacFrag);
		//always commit at the end of a fragment transaction. 
		ft.commit();
	}

	/**
	 * This method creates or replaces the given Fragment into view. If there was another visible
	 * fragment, it gets added to the back stack and replaced with fragIn. 
	 *
	 * @param fragmentIn
	 *            The fragment to show.
	 */
	public void showFragment(int fragIn) {
		final FragmentTransaction ft = getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		// if (mVisible != null) {
		// ft.detach(mVisible);
		/*
		 * if (mVisible == mListFragment) { ft.remove(mListFragment); } else {
		 * ft.hide(mVisible); }
		 */
		// }

		switch (fragIn) {
		case 0:
			//check if the fragment exists and create it if it does not. 
			mMapFragment = (MapFragment) getSupportFragmentManager()
					.findFragmentByTag(MapFragment.TAG);
			if (mMapFragment == null) {
				mMapFragment = MapFragment.newInstance(0);
			}
			ft.replace(R.id.fragment_container, mMapFragment, MapFragment.TAG)
					.addToBackStack(null).commit();

			mVisible = mMapFragment;
			break;
		case 1:
			mListFragment = (ListFragmentDisplay) getSupportFragmentManager()
					.findFragmentByTag(ListFragmentDisplay.TAG);
			Toast.makeText(this, "startListFrag", Toast.LENGTH_LONG).show();
			if (mListFragment == null) {
				mListFragment = new ListFragmentDisplay();
			}
			// ft.attach(mListFragment).commit();

			ft.replace(R.id.fragment_container, mListFragment,
					ListFragmentDisplay.TAG).addToBackStack(null).commit();
			mMenu.getItem(0).setVisible(true);
			mVisible = mListFragment;
			break;
		case 2:
			myBarListFragment = (BarListFrag) getSupportFragmentManager()
					.findFragmentByTag(BarListFrag.TAG);
			if (myBarListFragment == null) {
				myBarListFragment = new BarListFrag();
			}
			ft.replace(R.id.fragment_container, myBarListFragment,
					BarListFrag.TAG).addToBackStack(null).commit();
			;
			mVisible = myBarListFragment;

			break;
		case 3:
			bacFrag = (BACFrag) getSupportFragmentManager().findFragmentByTag(
					BACFrag.TAG);
			if (bacFrag == null) {
				bacFrag = new BACFrag();
			}
			ft.replace(R.id.fragment_container, bacFrag, BACFrag.TAG)
					.addToBackStack(null).commit();
			;
			mVisible = bacFrag;
			break;
		}
	}

	public void facebookInit() {
		facebook = new Facebook("281094275327241");

		AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu with the options to show the Map and the ListView.
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		mMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.ic_list:
			mMenu.getItem(0).setVisible(false);
			showFragment(2);
			return true;

		case R.id.ic_map:
			// Show mMapFragment.
			mMenu.getItem(0).setVisible(false);
			showFragment(0);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}