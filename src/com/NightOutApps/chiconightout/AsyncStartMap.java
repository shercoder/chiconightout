package com.NightOutApps.chiconightout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AsyncStartMap extends AsyncTask<Void, Void, Void>{

	private Activity ctx;
	private ProgressDialog dialog;
	public AsyncStartMap(Activity cont){
		this.ctx = cont;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(ctx, "Loading", "Loading Map Icons");
	}
	
	
	@Override
	protected Void doInBackground(Void... arg0) {

		return null;
	}

}
