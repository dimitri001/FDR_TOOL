package org.korso.apps.rsscursoandroid;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleActivity extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
				
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		HashMap<String,String> article = (HashMap<String,String>) getIntent().getSerializableExtra("EXTRA_ARTICLE");
		
		TextView  tvHeader = (TextView) findViewById(R.id.header_article);
	    tvHeader.setText(article.get("header"));
	      
	    TextView tvParagraph = (TextView) findViewById(R.id.text_article);
	    tvParagraph.setText(article.get("paragraph"));
	      
	    TextView tvDate = (TextView) findViewById(R.id.date_article);
	    tvDate.setText(article.get("date"));
	      
	    ImageView image = (ImageView) findViewById(R.id.image_article);
	      
	}	

}
