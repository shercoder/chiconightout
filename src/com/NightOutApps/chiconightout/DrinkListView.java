package com.NightOutApps.chiconightout;


import android.os.Bundle;
import android.app.ListActivity;
import android.database.SQLException;
 
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
	
public class DrinkListView extends ListActivity implements OnClickListener{

	public static final String DRINKNAME = "DrinkName";
	
	public static final String DRINKDESCRIPT = "DrinkDescription";
	
	public static final String DATABASE_TABLE = "Drink";
	
	public static final String DAY = "Day_id";
	
	    /** Called when the activity is first created. */
	private Cursor c = null;
	private String[] colsfrom = {"_id", DRINKNAME, DRINKDESCRIPT, DAY};
	private int[] to = new int[] {R.id.text01, R.id.text02, R.id.text03, R.id.text03};
	
	public int bar = 0; //the bar number. Bars are alphabetical
	public int day = 0; //0==sunday 6 == Saturday
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.drinklistview);
	        
	        View left = findViewById(R.id.left_button);
	        left.setOnClickListener(this);
	        View events = findViewById(R.id.events_button);
	        events.setOnClickListener(this);
	        View right = findViewById(R.id.right_button);
	        right.setOnClickListener(this);
	          
	        Intent thisIntent = getIntent();
	        bar = thisIntent.getIntExtra("_bar", 0); //gets the intent that started this mess and gets an extra value that was passed called _bar.
	        //0 is the default argument is nothing is passed.
	        day = thisIntent.getIntExtra("_day", 0); //same idea as above.
	        //Toast.makeText(DrinkListView.this, "try to access database", Toast.LENGTH_SHORT).show();
	     //   String wHERE = "Bar_id = " + c.bar + " AND Day_id = " + c.day ; //this is a sql statement that gets all rows where Bar_id = the bar that was picked
	     //   cur = myDbHelper.getDrinks(c.DATABASE_TABLE, colsfrom, wHERE, null, null,null, null);  
	        new AsyncList(this).execute();
	   }
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		case R.id.left_button:
			if(day == 1)
			{
				day = 7;
			Toast.makeText(DrinkListView.this, "back to 7", Toast.LENGTH_SHORT).show();
			}
			else
			{
				day--;
			Toast.makeText(DrinkListView.this, "day is " + day, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.events_button:
	        Toast.makeText(DrinkListView.this, "Events List View...", Toast.LENGTH_SHORT).show();
			break;
		case R.id.right_button:
			if(day == 7)
			{	
				day = 1;
				Toast.makeText(DrinkListView.this, "back to 1", Toast.LENGTH_SHORT).show();
			}
			else
			{
				day++;
			Toast.makeText(DrinkListView.this, "day is " + day, Toast.LENGTH_SHORT).show();
			}
			break;
		}
		new AsyncList(this).execute();
		
	}	
	
}