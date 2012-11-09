package com.NightOutApps.chiconightout;


import android.os.Bundle;
import android.app.ListActivity;
import android.database.SQLException;
 
import android.content.Intent;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
	
public class DrinkListView extends ListActivity {

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
	          
	        Intent thisIntent = getIntent();
	        bar = thisIntent.getIntExtra("_bar", 0); //gets the intent that started this mess and gets an extra value that was passed called _bar.
	        //0 is the default argument is nothing is passed.
	        day = thisIntent.getIntExtra("_day", 0); //same idea as above.
	        //Toast.makeText(DrinkListView.this, "try to access database", Toast.LENGTH_SHORT).show();
             MyDBHelper myDbHelper = new MyDBHelper(this);
	            try {
	                myDbHelper.openDataBase();  
	            }
	            catch(SQLException sqle){
	                throw sqle; 
	            }
	            //there are 2 bar ids as of now. As we make the database bigger we can setup querys based on what is selected.
	            String wHERE = "Bar_id = " + bar + " AND Day_id = " + day ; //this is a sql statement that gets all rows where Bar_id = the bar that was picked
	            c = myDbHelper.getDrinks(DATABASE_TABLE, colsfrom, wHERE, null, null,null, null);
	            /*this creates a new cursor adapter
	            @param Context is the list context that you will be filling. 
	            @param int layout is the layout that you will use for the rows
	            @param Cursor is the cursor that was returned from the query
	            @param from is the column names to map from
	            @param to is the layout ids that the column fields will be put in. 
	            */
	            SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(this, R.layout.drinkrow, c, colsfrom, to);
	            setListAdapter(myAdapter);
	            
	            myDbHelper.close();
	           
	            }
}