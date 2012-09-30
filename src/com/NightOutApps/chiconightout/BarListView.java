package com.NightOutApps.chiconightout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class BarListView extends Activity {

	String[] barName = new String[] {
	        "Banshee",
	        "Beach",
	        "Bellas",
	        "Crazy Horse",
	        "Down Lo",
	        "Duffys",
	        "Joes",
	        "Lasalles",
	        "Lost On Main",
	        "Madison Bear Garden",
	        "Maltese",
	        "Panamas",
	        "Rileys",
	        "The Graduate",
	        "Town Lounge",
	        "University Bar"
	    };
	
	int[] barIcon = new int[]{
	        R.drawable.banshee_icon,
	        R.drawable.beach_icon,
	        R.drawable.bellas_icon,
	        R.drawable.crazy_horse_icon,
	        R.drawable.down_lo_icon,
	        R.drawable.duffys_icon,
	        R.drawable.joes_icon,
	        R.drawable.lasalles_icon,
	        R.drawable.lost_on_main_icon,
	        R.drawable.madison_bear_icon,
	        R.drawable.maltese_icon,
	        R.drawable.panamas_icon,
	        R.drawable.rileys_icon,
	        R.drawable.the_grad_icon,
	        R.drawable.town_lounge_icon,
	        R.drawable.u_bar_icon
	    };
	
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
        setContentView(R.layout.activity_bar_list_view);
        MyDBHelper myDbHelper = new MyDBHelper(BarListView.this);
        try {
        	myDbHelper.createDataBase();
            Toast.makeText(BarListView.this, "Success in creation", Toast.LENGTH_SHORT).show();

        }
        catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        /*
        try {
            myDbHelper.openDataBase();
        }
        catch(SQLException sqle){
            throw sqle;
        }
        c = myDbHelper.getDrinks(DATABASE_TABLE, colsfrom, barStr, null, null,null, null);
        
        c.
        myDbHelper.close(); */
        // Each row stores Bar name, Bar Icon
        List<HashMap < String,String > > barList = new ArrayList<HashMap <String,String> >();
        //add bar icons and bar names to the list
        for(int i = 0; i<16; i++){
            HashMap<String, String> barMap = new HashMap<String,String>();
            
            barMap.put("barname", barName[i]);
            barMap.put("baricon", Integer.toString(barIcon[i]) );
            
            barList.add(barMap);
        }
        // Keys used in Hashmap that will be mapped to the rows
        String[] bname = { "baricon","barname" };
 
       	// Ids of views in row.xml that will define the layout of the list items. 
       	int[] bicon = { R.id.baricon,R.id.barname };
 
       	// Creating an adapter to store each row item
       	// R.layout.row.xml defines the layout of each entry in the list
       	SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), barList, R.layout.row, bname, bicon);
 
       	// setting up the list view that and defining the layout of that list.
       	ListView barListView = ( ListView ) findViewById(R.id.barlist);
 
       	// binding the adapter to the list view.
       	barListView.setAdapter(adapter);
       	
        
        //binding the onclicklistenter to the list view. 
       	barListView.setOnItemClickListener(new ListView.OnItemClickListener() {
    	
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			Intent drinkList = new Intent(BarListView.this, DrinkListView.class);
    		drinkList.putExtra("_bar", position+1);
    		//drinkList.putExtra("_day", dayOfWeek);
			startActivity(drinkList);
		}
       });
    }
}
