package com.example.mapswithfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Random;

public class CallACab {

	private String phoneNum[];
	private static final int entries = 4; // made it static final because
											// ArrayList needs static reference
	private static ArrayList<Integer> tries = new ArrayList<Integer>(entries);
	// private static int currentPhoneNum = 0;
	private static int dialCounter = 0; // to keep track how many times user has
										// dialed
	private Context context;

	public CallACab(Context c) {
		context = c;

		/**
		 * In a practical application the arrays phoneNum and buttonLabels could
		 * be updated dynamically from the Web in this method. For this project
		 * we just hard-wire in some values to illustrate how to use such data,
		 * once obtained, to make phone calls.
		 */
		// AA Alan I have placed the population of your phone numbers in the
		// Constructor of this class .
		phoneNum = new String[entries];
		phoneNum[0] = "530-898-1776";
		phoneNum[1] = "530-894-6666";
		phoneNum[2] = "530-899-9997";
		phoneNum[3] = "530-354-2981";

		if (dialCounter < entries) {
			launchDialer(phoneNum[randomNum()], context);
			++dialCounter;

		} else {
			// Create new array list when the old one is full
			tries = new ArrayList<Integer>(entries);
			dialCounter = 0;

			// Alert user that they have used all the phone numbers
			AlertDialog.Builder alertBox = new AlertDialog.Builder(context);
			alertBox.setMessage("You have tried all taxi contacts available. Would you like to start over?");
			alertBox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							launchDialer(phoneNum[randomNum()], context);
						}
					});
			alertBox.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			alertBox.show();
		}
	}

	public void launchDialer(String number, Context context) {
		String numberToDial = "tel:" + number;
		context.startActivity(new Intent(Intent.ACTION_DIAL, Uri
				.parse(numberToDial)));
	}

	/**
	 * Generates random number between 0(inclusive) and number of phone entries
	 * (exclusive)
	 * 
	 * @return rand of type int
	 */
	private int randomNum() {
		int rand = 0;
		boolean found = false;

		while (!found) {
			// Generates random number to choose between phone numbers
			Random r = new Random();

			// Chooses between [0,4)
			rand = r.nextInt(entries);

			// Adds the random number to ArrayList if it does not exist in the
			// list
			if (!tries.contains(rand)) {
				tries.add(rand);
				found = true;
			}
		}
		return rand;
	}

}
