package org.korso.apps.rsscursoandroid;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	TextView texto;
	Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int seconds = 30;
        
        /*
        TextView text = (TextView)findViewById(R.id.tvHello);
        text.setText("Dos Dos");
        text.setVisibility(View.GONE);
        */
        
        texto = (TextView) findViewById(R.id.tvGoArticleList);
        texto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(context, ArticleListActivity.class);
				startActivity(intent);
			}
		});
        
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                
            	Intent intent = new Intent(context, ArticleListActivity.class);
            	startActivity(intent);
            	
            	/*
                Intent mInHome = new Intent(Splash_Screen_Activity.this, InvoiceASAPTabActivity.class);
                Splash_Screen_Activity.this.startActivity(mInHome);
                Splash_Screen_Activity.this.finish();*/
            }
        }, seconds * 1000);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
    
}
