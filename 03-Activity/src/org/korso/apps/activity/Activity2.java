package org.korso.apps.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity2 extends Activity implements OnClickListener {

	private static final String TAG = "A2 CicloActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		Log.d(TAG,"onCreate");
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
	}
	
	

	 @Override
		protected void onPause() {
			Log.d(TAG,"onPause");
			super.onPause();
		}
	    
	    @Override
	   	protected void onResume() {
	   		Log.d(TAG,"onResume");
	   		super.onResume();
	   	}

		@Override
		protected void onRestart() {
			Log.d(TAG,"onRestart");
			super.onRestart();
		}


		@Override
		protected void onStart() {
			super.onStart();
			String text = getIntent().getStringExtra("EXTRA_TEXTO");
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
			toast.show();
			Log.d(TAG,"onStart");
		}


		@Override
		protected void onStop() {
			Log.d(TAG,"onStop");
			super.onStop();
		}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button1:
			Context context = this;
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
			break;
		}
		
	}
	
	

}
