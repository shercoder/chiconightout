package com.NightOutApps.chiconightout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallACab {
	
	private String phoneNum[];
	int entries = 4;
    int x=0;
    Context cont;
	public CallACab(Context context) {
		cont = context;
		/** In a practical application the arrays phoneNum and buttonLabels could be 
	        * updated dynamically from the Web in this method.  For this project we just 
	        * hard-wire in some values to illustrate how to use such data, once obtained,
	        * to make phone calls.*/
		//AA Alan I have placed the population of your phone numbers in the Constructor of this class .
		phoneNum = new String[entries];
		phoneNum[0] = "530-898-1776";
        phoneNum[1] = "530-894-6666";
        phoneNum[2] = "530-899-9997";   
        phoneNum[3] = "530-354-2981";
        
        switch (x) {
        
        case 0: //R.id.image:
            launchDialer(phoneNum[0]);
            x++;
            break;
                
        case 1: //R.id.button2:
            launchDialer(phoneNum[1]);
            x++;
            break;
                
        case 2: //R.id.button3:
            launchDialer(phoneNum[2]);
            x++;
            break;
                
        case 3: //R.id.button4:
            launchDialer(phoneNum[3]);
            x++;
            break;

        case 4:
        	AlertDialog.Builder alertBox = new AlertDialog.Builder(context);
    		alertBox.setMessage("You tried all of our taxi contacts. It will show the same cab numbers.");
    		alertBox.show();
    		x=0;
    		break;
    }
	}
	public void launchDialer(String number){
        String numberToDial = "tel:"+ number;
        cont.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(numberToDial)));
    }

}
