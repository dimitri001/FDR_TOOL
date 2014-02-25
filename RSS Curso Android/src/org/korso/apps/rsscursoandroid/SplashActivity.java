package org.korso.apps.rsscursoandroid;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	
	TextView texto;
	Context context = SplashActivity.this;
	private static final String TAG = "SplashActivity CicloActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int seconds = 30;
               
        
        texto = (TextView) findViewById(R.id.tvGoArticleList);
        texto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				startLoading();				
			}
		});
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {                
            	startLoading();            	
            }
       }, seconds * 10);        
        
        
    }

    public void startLoading(){
    	
    	Context context = this;
		Intent intent = new Intent(context, MyProgressBarActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
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
			Log.d(TAG,"onStart");
			super.onStart();
		}


		@Override
		protected void onStop() {
			Log.d(TAG,"onStop");
			super.onStop();
		}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
    
}
