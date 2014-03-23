package org.korso.apps.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private static final String TAG = "MyService";
	private DummyAsyncTask task;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "onIBinder");
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		//Dummy heavyTask = new Dummy();
		//heavyTask.hardWork();
		task = new DummyAsyncTask();
		task.execute();
		stopSelf();
		return super.onStartCommand(intent, flags, startId);

	}

}
