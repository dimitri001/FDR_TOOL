package org.korso.apps.rsscursoandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MyProgressBarActivity extends Activity {
	
	private static final int PROGRESS = 0x1;

    //private ProgressBar mProgress;
	ProgressDialog progressBar;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
    
    private long fileSize = 0;
    
    Context context = this;
	
	protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_splash);
        //mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        
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
                    mProgressStatus = doWork();
                    
                 
                    // your computer is too fast, sleep 1 second
                    try {
                    	Thread.sleep(100);
  				  	} catch (InterruptedException e) {
  				  		e.printStackTrace();
  				  	}
                    
                    
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                        	progressBar.setProgress(mProgressStatus);
                        }
                    });
                }         
                
                
             // ok, file is downloaded,
				if (mProgressStatus >= 100) {
 
					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(1000);
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
	
	public void goToArticleList(){
		
		Intent intent = new Intent(context, ArticleListActivity.class);
		startActivity(intent);
	}
}
