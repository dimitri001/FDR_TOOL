package org.korso.apps.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

	
	private static final String TAG = "09-MyBroadcastReceiver";

	@Override
	public void onReceive(Context arg0, Intent intent) {

		//We can use this for allowing functionalities to our app while we have internet connection
		//otherwise we dont allow that functionalities
		Log.d(TAG, "onReceive MyBroadcastReceiver");
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
			String tlf = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.d(TAG,"llamada entrante de " + tlf );
		}
		
	}
	
	
}
