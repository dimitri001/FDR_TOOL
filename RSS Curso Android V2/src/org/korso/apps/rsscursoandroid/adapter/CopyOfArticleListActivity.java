package org.korso.apps.rsscursoandroid.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.korso.apps.rsscursoandroid.AboutActivity;
import org.korso.apps.rsscursoandroid.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfArticleListActivity extends ListActivity implements OnClickListener  {

	TextView texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		
		//ListView listView =getListView();
		
		ListView listView =(ListView) findViewById(android.R.id.list);
		
		texto = (TextView) findViewById(R.id.tvAbout);
		texto.setOnClickListener(this);
		
		ArrayList< HashMap<String, String>> data = new ArrayList< HashMap<String, String>>();
		
		String[] capitales = getResources().getStringArray(R.array.prueba_capital);
		String[] paises = getResources().getStringArray(R.array.prueba_pais);
		
		for (int x= 0; x<capitales.length; x++){
			HashMap<String, String> entrada = new HashMap<String, String>();
			entrada.put("capital", capitales[x]);
			entrada.put("pais", paises[x]);
			
			data.add(entrada);			
		}
		
		int layout = android.R.layout.simple_expandable_list_item_2;
		String[] from = new String[]{"pais","capital"};
		int[] to = new int[]{android.R.id.text1, android.R.id.text2};
		
		SimpleAdapter adapter = new SimpleAdapter(this, data, layout, from, to);
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
		String text = ((HashMap<String,String>) l.getItemAtPosition(position)).get("pais")+" "+
		((HashMap<String,String>) l.getItemAtPosition(position)).get("capital");
		
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
		
		
	}
	
	//With this when the return key is pressed the app is closed from this view
	@Override public void onBackPressed(){ 
		moveTaskToBack(true); 
	}
	
}
