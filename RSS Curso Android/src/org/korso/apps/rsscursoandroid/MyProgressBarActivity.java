package org.korso.apps.rsscursoandroid;

import java.util.List;
import java.util.Random;

import org.korso.apps.rsscursoandroid.data.DummyFeed;
import org.korso.apps.rsscursoandroid.data.MyDbHelper;
import org.korso.apps.rsscursoandroid.data.FeedsContract.FeedsTable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import android.widget.ProgressBar;

/*
 * On this class we load the articles into the data base of the app.
 * */
public class MyProgressBarActivity extends Activity {
	
	private static final int PROGRESS = 0x1;

    //private ProgressBar mProgress;
	ProgressDialog progressBar;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
    
    private long fileSize = 0;
    
    Context context = this;
    

	private static final String TAG = "MyProgressBarActivity CicloActivity";
	
	protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_splash);        
     // prepare for a progress bar dialog   
        
        startBarThread();
    }
	
	public void startBarThread(){
		
		progressBar = new ProgressDialog(this);
		progressBar.setCancelable(true);
		progressBar.setMessage("Loading the application ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressBar.setProgress(0);
		progressBar.setMax(100);
		progressBar.show();
		
		 // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    //mProgressStatus = doWork();
                    
                	mProgressStatus =createDataFeeds();
                    // your computer is too fast, sleep 1 second
                    /*
                    try {
                    	Thread.sleep(1);
  				  	} catch (InterruptedException e) {
  				  		e.printStackTrace();
  				  	}
                    */
                    
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                        	progressBar.setProgress(mProgressStatus);
                        }
                    });
                }         
                
                createDataFeeds();
                
             // ok, file is downloaded,
				if (mProgressStatus >= 100) {
 
					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(1);
						goToArticleList();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
 
					// close the progress bar dialog
					progressBar.dismiss();
				}
                
                
            }               
             
        }).start();
        
        
	}

	
	public int doWork() {
		 
		while (fileSize <= 1000000) {
 
			fileSize++;
 
			if (fileSize == 100000) {
				return 10;
			} else if (fileSize == 200000) {
				return 20;
			} else if (fileSize == 400000) {
				return 40;
			}else if (fileSize == 600000) {
				return 60;
			}else if (fileSize == 800000) {
				return 80;
			}else if (fileSize == 900000) {
				return 90;
			}
			// ...add your own
 
		}
 
		return 100;
 
	}
	
	//Ceration of the database for the feeds
	public int createDataFeeds(){

		final MyDbHelper helper = new MyDbHelper(this);
		final SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		DummyFeed.createFeeds(db, values);		
		
		db.close();
		
		return 100;
	}
	
	public void goToArticleList(){
		
		Intent intent = new Intent(context, ArticleListActivity.class);
		startActivity(intent);
		finish();
	}
	
		
}
