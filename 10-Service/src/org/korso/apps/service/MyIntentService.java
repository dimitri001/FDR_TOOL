package org.korso.apps.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

public class MyIntentService extends IntentService {

	private static final String TAG = "MyIntentService";
	private ResultReceiver resultReceiver;
	
	public MyIntentService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "OnHandleIntent");
		resultReceiver = (ResultReceiver) intent.getParcelableExtra("EXTRA_MYRESULTRECEIVER");
		
		if(resultReceiver != null){
			resultReceiver.send(1, null);
		}
		Dummy heavyTask = new Dummy();
		heavyTask.hardWork();
		
		//We send this when the heavyTask is just finished
		if(resultReceiver != null){
			resultReceiver.send(0, null);
		}
		
	}



	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "OnCreate");
	}



	@Override
	public void onDestroy() {
		Log.d(TAG, "OnDestroy");
		super.onDestroy();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "OnStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	

}
