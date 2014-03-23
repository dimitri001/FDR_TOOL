package org.korso.apps.registrollamadas;

import utils.UtilsContacts;
import data.CallsContract;
import data.MyDbHelper;
import data.CallsContract.CallsTable;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

	
	private static final String TAG = "MyBroadcastReceiver";

	private MyDbHelper mDbHelper ;
	private ContentValues values = new ContentValues();
	
	@Override
	public void onReceive(Context context, Intent intent) {

		//We can use this for allowing functionalities to our app while we have internet connection
		//otherwise we dont allow that functionalities
		Log.d(TAG, "onReceive MyBroadcastReceiver");

		mDbHelper = new MyDbHelper(context);
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
			String tlf = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			//String name = intent.getStringExtra(TelephonyManager.EX)
			Log.d(TAG,"1 llamada entrante de " + tlf );
			
			final SQLiteDatabase db = mDbHelper.getWritableDatabase();
			
			String name = UtilsContacts.getContactByNumber(tlf, context);
			//String date= "2014-02-21T16:00:00.000Z";//letter Z means UTC, which replace the GMT (Greenwhich mean time) that´s why the time is different from the spanish mobile
			Long millis = System.currentTimeMillis();//parseDate(date);
			
			values.put(CallsTable.NAME, name);
			values.put(CallsTable.NUMBER, tlf);
			values.put(CallsTable.DATE, millis);
			
			long id = db.insert(CallsTable.TABLE_NAME, null, values);
			
			Log.d(TAG,"2 llamada entrante de "+ name + "  "  + tlf  );
			
			db.close();
		}
		
	}
	
	/*
	public MyBroadcastReceiver(){	
		super();
		
		Log.d(TAG, "creado mDbHelper");
		
	}
	*/
	
	private static Long parseDate(String date){
		Time time = new Time();
		time.parse3339(date);
		Long millis = time.normalize(false);
		return millis;
	}
	
	
}