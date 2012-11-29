package com.NightOutApps.chiconightout;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AsyncList extends AsyncTask <Void, Void, Void> {
public static final String DRINKNAME = "DrinkName";
	
	public static final String DRINKDESCRIPT = "DrinkDescription";
	
	public static final String DATABASE_TABLE = "Drink";
	
	public static final String DAY = "Day_id";
	private String[] colsfrom = {"_id", DRINKNAME, DRINKDESCRIPT, DAY};
	private int[] to = new int[] {R.id.text01, R.id.text02, R.id.text03, R.id.text03};
	DrinkListView c;
	ProgressDialog pd;
	Cursor cur;
	
	public AsyncList (DrinkListView con) {
		this.c = con;
		pd = new ProgressDialog(this.c);
        pd.setMessage("Loading...");
	}
	
	public void onPreExecute() {
        pd.show();
      }

    public Void doInBackground(Void... unused) {
    	
    	 MyDBHelper myDbHelper = new MyDBHelper(c);
         try {
             myDbHelper.openDataBase();  
         }
         catch(SQLException sqle){
             throw sqle; 
         }
			//Toast.makeText(c, "async day is " + c.day, Toast.LENGTH_SHORT).show();
    	String wHERE = "Bar_id = " + c.bar + " AND Day_id = " + c.day ; //this is a sql statement that gets all rows where Bar_id = the bar that was picked
        cur = myDbHelper.getDrinks(c.DATABASE_TABLE, colsfrom, wHERE, null, null,null, null);
        /*this creates a new cursor adapter
        @param Context is the list context that you will be filling. 
        @param int layout is the layout that you will use for the rows
        @param Cursor is the cursor that was returned from the query
        @param from is the column names to map from
        @param to is the layout ids that the column fields will be put in. 
        */
        myDbHelper.close();
		return null;
    
    }
    public void onPostExecute (Void Unused) {
    	pd.dismiss();
    	if (cur.getCount() == 0){
    		
    	}	
    	else {
    	//c.drinkList = new SimpleCursorAdapter(c, R.layout.drinkrow, cur, colsfrom, to);
    	//c.setListAdapter(c.drinkList);
    	//c.drinkList.notifyDataSetChanged();
    	}
    }
    	  
}
