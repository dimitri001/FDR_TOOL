package org.korso.apps.listactivity;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView =(ListView) findViewById(R.id.listView1);
		String []datos = getResources().getStringArray(R.array.datos);
		
		ArrayAdapter <String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, datos);
		//adapter.addAll(datos);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				Toast.makeText(MainActivity.this, 
						(String) adapter.getItemAtPosition(position), 
						Toast.LENGTH_LONG).show();			
				}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
