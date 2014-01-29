package org.korso.apps.listactivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MyListActivity2 extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//int datos=0;
		
		ArrayList<HashMap <String,String>> data = new ArrayList<HashMap<String,String>>();
		
		for(int x=0;x<10;x++){
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("nombre", "Pepe"+x);
			map.put("apellido", "Lopez"+x);
			data.add(map);
		}
		
		int layout = android.R.layout.simple_expandable_list_item_2;
		String[] from = new String[]{"nombre", "apellido"};
		int[] to = new int[]{android.R.id.text1, android.R.id.text2};
		
		SimpleAdapter adapter = new SimpleAdapter(this, 
				data, 
				layout, from, to);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {		
		super.onListItemClick(l, v, position, id);	
		
		@SuppressWarnings("unchecked")
		String text = ((HashMap<String,String>) l.getItemAtPosition(position)).get("nombre")
				+" "
				+((HashMap<String,String>) l.getItemAtPosition(position)).get("apellido");
		
		Toast.makeText(this, 
				text, 
				Toast.LENGTH_LONG).show();
	}
	
	

	
}
