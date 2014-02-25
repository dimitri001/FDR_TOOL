package org.korso.apps.persistencia;


import org.korso.apps.persistencia.db.FeedsContract.UsersTable;
import org.korso.apps.persistencia.db.MyDbHelper;
import org.korso.apps.widget.MySimpleCursorAdapter;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.widget.Adapter;

public class MainActivity extends ListActivity {

	private static final String TAG = "05-Persistencia";
	private SimpleCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		//getFilesDir();
		//getCacheDir();
		//getExternalCacheDir();
		//getExternalFilesDir(type);
		
		final MyDbHelper helper = new MyDbHelper(this);
		final SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(UsersTable.EMAIL, "pepe@uno.man");
		values.put(UsersTable.USERNAME, "Pepe");
		String date= "2014-02-21T16:00:00.000Z";//letter Z means UTC, which replace the GMT (Greenwhich mean time) that why the time is diferent from the spanish mobile
		Long millis = parseDate(date);
		
		Long now = System.currentTimeMillis();
		millis = now - 50000;//we rest 50 seconds
		
		values.put(UsersTable.DATE, millis);
		
		long id = db.insert(UsersTable.TABLE_NAME,null, values);
		db.close();

		Context context = this;
		int layout = android.R.layout.simple_list_item_2 ;
		Cursor c = null;
		String[] from = new String[] {UsersTable.USERNAME, UsersTable.DATE};
		int[] to = new int[] {android.R.id.text1, android.R.id.text2} ;
		int flags = SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
		
		//adapter = new SimpleCursorAdapter(context, layout, c, from, to, flags);
		adapter = new MySimpleCursorAdapter(context, layout, c, from, to, flags);
		setListAdapter(adapter);
		
		Log.d(TAG,"onCreate");
				
		
	}

	private Cursor getUsers() {

		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String table = UsersTable.TABLE_NAME;
		String[] columns = new String[] {UsersTable._ID, UsersTable.USERNAME, UsersTable.DATE};
		String selection = null;//"Where edad > ?";
		
		String[] selectionArgs = null; //new String[] {"pepe"};
		String groupBy = null;
		String having = null;
		String orderBy = UsersTable.USERNAME + " DESC";
		return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		//This helps us to make simple data preferences
		SharedPreferences prefs = getSharedPreferences("MisPrefs", MODE_PRIVATE );
        //This counts the times that the app goes from 2 to 1 plane that's why this is in resume 
		int counter = prefs.getInt("COUNTER", 0);
		counter++;
		
		Editor editor = prefs.edit();
		editor.putInt("COUNTER", counter);
		editor.commit();
		
		Log.d("Counter", ""+counter);
		
	}

	@Override
	protected void onStart() {
		super.onStart();		
		adapter.changeCursor(getUsers());
	}

	@Override
	protected void onStop() {	
		adapter.changeCursor(null);
		super.onStop();
		
	}
	
	private static Long parseDate(String date){
		Time time = new Time();
		time.parse3339(date);
		Long millis = time.normalize(false);
		return millis;
	}
	
	

	
}
