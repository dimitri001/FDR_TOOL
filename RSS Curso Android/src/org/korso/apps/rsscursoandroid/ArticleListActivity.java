package org.korso.apps.rsscursoandroid;

import java.util.ArrayList;
import java.util.HashMap;


import org.korso.apps.rsscursoandroid.data.MyDbHelper;
import org.korso.apps.rsscursoandroid.data.FeedsContract.FeedsTable;
import org.korso.apps.rsscursoandroid.widget.MyCustomAdapter;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleListActivity extends ListActivity implements OnClickListener  {

	TextView textAbout;
	
	private String[] headers;
	private String[] paragraphs;
	private String[] dates;
	private MyCustomAdapter adapter;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		textAbout = (TextView) findViewById(R.id.tvAbout);
		textAbout.setOnClickListener(this);
		
		ArrayList< HashMap<String, String>> data = new ArrayList< HashMap<String, String>>();
		
		ListView listView =(ListView) findViewById(android.R.id.list);
		/*
		String[] headers = getResources().getStringArray(R.array.prueba_capital);
		String[] paragraphs = getResources().getStringArray(R.array.prueba_pais);
		String[] dates = getResources().getStringArray(R.array.prueba_pais);
		String[] images = getResources().getStringArray(R.array.prueba_imagen);
		*/
 
		
		
		
		/*
		for (int x= 0; x<headers.length; x++){
			HashMap<String, String> entrada = new HashMap<String, String>();
			entrada.put("header", headers[x]);
			entrada.put("paragraph", paragraphs[x]);
			entrada.put("date", dates[x]);
			entrada.put("image", images[x]);
			
			data.add(entrada);			
		}
		*/
		//int layout = R.layout.my_list_row_layout;
		int layout = R.layout.row_layout;
		
		
		adapter = new MyCustomAdapter(this,layout, data);
		  //setting the adapter
		setListAdapter(adapter);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tvAbout:
				Context context =this;
				Intent intent = new Intent(context, AboutActivity.class);
				startActivity(intent);
			break;
		}
			
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		@SuppressWarnings("unchecked")
		String text = ((HashMap<String,String>) l.getItemAtPosition(position)).get("header")+" "+
		((HashMap<String,String>) l.getItemAtPosition(position)).get("paragraph");
		
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(context, ArticleActivity.class);
		//here we put an extra parameter to put in the activity called 
		intent.putExtra("EXTRA_ARTICLE",(HashMap<String,String>) l.getItemAtPosition(position));
		startActivity(intent);
		
	}
	
		
	//With this when the return key is pressed the app is closed from this view
	@Override public void onBackPressed(){ 
		moveTaskToBack(true); 
	}
	
	
	public Cursor fillTheFeeds(){
		
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String table = FeedsTable.TABLE_NAME;
		String[] columns = new String[] {FeedsTable._ID, FeedsTable.TITLE, FeedsTable.DATE, FeedsTable.CONTENT, FeedsTable.IMAGE_URL};
		String selection = null;//"Where edad > ?";
		
		String[] selectionArgs = null; //new String[] {"pepe"};
		String groupBy = null;
		String having = null;
		String orderBy = FeedsTable.DATE + " DESC";
		return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
	}

	public ArrayList< HashMap<String, String>> getFeeds(){
	
		ArrayList< HashMap<String, String>> data = new ArrayList< HashMap<String, String>>();
		Cursor cursor =fillTheFeeds();
				
		 if(cursor!=null){
	     
			 while(cursor.moveToNext()){	
				 				 
				 HashMap<String, String> entrada = new HashMap<String, String>();
				 entrada.put("header", cursor.getString(cursor.getColumnIndex(FeedsTable.TITLE)));
				 entrada.put("paragraph", cursor.getString(cursor.getColumnIndex(FeedsTable.CONTENT)));
				 entrada.put("date", cursor.getString(cursor.getColumnIndex(FeedsTable.DATE)));
				 entrada.put("image", cursor.getString(cursor.getColumnIndex(FeedsTable.IMAGE_URL)));
					
				 data.add(entrada);		

			}
		 }
		 return data;
	}
	@Override
	protected void onStart() {
		super.onStart();
		adapter.addAll(getFeeds());//Here we load the cursor every time the app is started
		
	}

	@Override
	protected void onStop() {
		adapter.clear();//Here we free the cursor, every time the app is stopped, because it uses so much memory
		super.onStop();
	}

	

}
