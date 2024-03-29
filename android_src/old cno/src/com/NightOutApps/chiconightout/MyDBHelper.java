package com.NightOutApps.chiconightout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/* http://mobisys.in/blog/2012/01/tutorial-using-database-in-android-applications/
Code Credit going to vikashiran many thanks for helping me understand the importance 
of speed when creating and updating databases. Deleting the database and inserting a 
new one is clearly the quickest solution. Other solutions included parsing out strings 
from a file that contained SQLite statements, creating a program that would allow user 
input directly into the database, or code in the database creation on the first run of the app.
The code below takes a 10-12 second operation and makes it a 2 second one.
*/
public class MyDBHelper extends SQLiteOpenHelper{
        
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.NightOutApps.chiconightout/databases/";
     
    private static String DB_NAME = "bartestdata";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
     
    /**
      * Constructor
      * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
      * @param context
      */
    public MyDBHelper(Context context) {
    	super(context, DB_NAME, null, 1);
    	this.myContext = context;
    }	
    /**
      * Creates a empty database on the system and rewrites it with your own database.
      * */
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		//do nothing - database already exist

    		//this is where i will call the upodate databasefunction to contact the webserver.
    	Toast.makeText(myContext, "doing nothing", Toast.LENGTH_LONG).show();
    	}
    	else {

    		//By calling this method and empty database will be created into the default system path
    		//of your application so we are gonna be able to overwrite that database with our database.
    		this.getReadableDatabase();
    		try {
    			copyDataBase();
    			//the copy of this database should also include updating it to the most recent version
    			Toast.makeText(myContext, "copy database", Toast.LENGTH_LONG).show();
     
    		} 
    		catch (IOException e) {
     
    			throw new Error("Error copying database");
     
    		}
    	}
     
    }
     
    /**
      * Check if the database already exist to avoid re-copying the file each time you open the application.
      * @return true if it exists, false if it doesn't
      */
    private boolean checkDataBase(){
     
    	SQLiteDatabase checkDB = null;
     
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}
    	catch(SQLiteException e){
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
    /**
      * Copies your database from your local assets-folder to the just created empty database in the
      * system folder, from where it can be accessed and handled.
      * This is done by transfering bytestream.
      * */
    private void copyDataBase() throws IOException{
     
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);

    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
    	
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
     
    public void openDataBase() throws SQLException{
     
    	//Open the database
    	String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
     
    	@Override
    	public synchronized void close() {
     
    		if(myDataBase != null)
    			myDataBase.close();
    		super.close();
    	}
     
    	@Override
    	public void onCreate(SQLiteDatabase db) {
    		boolean dbExist = checkDataBase();
        	if(dbExist){
        		//do nothing - database already exist
                //Toast.makeText(myContext, "database already exists", Toast.LENGTH_SHORT).show();

        	}
    	}
     
    	@Override
    	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     
    	}
    	
    	public Cursor getDrinks(String table,String[] columns, String selection,String[] selectionArgs,String groupBy,String having,String orderBy) {
			return myDataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}
    }