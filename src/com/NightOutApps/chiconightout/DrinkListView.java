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

 	private String[] colsfrom = {"_id", DRINKNAME, DRINKDESCRIPT, DAY};

 	private int[] to = new int[] {R.id.text01, R.id.text02, R.id.text03, R.id.text04};
 	
	public int bar = 0;
	
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
	        bar = thisIntent.getIntExtra("_bar", 0);
	        //day = thisIntent.getIntExtra("_day", 0);
	        bar = thisIntent.getIntExtra("_bar", 0); //gets the intent that started this mess and gets an extra value that was passed called _bar.
	        //0 is the default argument is nothing is passed.
	        day = thisIntent.getIntExtra("_day", 0); //same idea as above.
 	        
 	            fillList();
 	            
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
		fillList();
	}

	private void fillList() {
		MyDBHelper myDbHelper = new MyDBHelper(this);
		Cursor cursor;
        String wHERE = "Bar_id = " + bar + " AND Day_id = " + day; //this is a sql statement that gets all rows where Bar_id = the bar that was picked

		try {
            myDbHelper.openDataBase();  
        }
        catch(SQLException sqle){
            throw sqle; 
        }
			//Toast.makeText(c, "async day is " + c.day, Toast.LENGTH_SHORT).show();
         //this is a sql statement that gets all rows where Bar_id = the bar that was picked
        cursor = myDbHelper.getDrinks(DATABASE_TABLE, colsfrom, wHERE, null, null,null, null);
        /*this creates a new cursor adapter
       	@param Context is the list context that you will be filling. 
       	@param int layout is the layout that you will use for the rows
       	@param Cursor is the cursor that was returned from the query
       	@param from is the column names to map from
       	@param to is the layout ids that the column fields will be put in. 
        */

        	SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this, R.layout.drinkrow, cursor, colsfrom, to);
        	setListAdapter(myAdapter);
      
        myDbHelper.close();
        
       	//drinkList.notifyDataSetChanged();
	}
		
}