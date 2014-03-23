package org.korso.apps.contentprovidercalendar;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity implements LoaderCallbacks<Cursor> {

	private Context context = this;
	private SimpleCursorAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main); we dont take this so the default activity will be taken
		
		int layout = android.R.layout.simple_list_item_2;
		Cursor cursor = null;
		String[] from = new String[]{
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DTSTART
		};
		int[] to = new int[]{
				android.R.id.text1, 
				android.R.id.text2
		};
		
		adapter = new SimpleCursorAdapter(this, layout, cursor, from, to, 0);
		setListAdapter(adapter);
		
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		Uri uri = CalendarContract.Events.CONTENT_URI;
		String[] projection = new String[]{
				CalendarContract.Events._ID,
				CalendarContract.Events.TITLE,
				CalendarContract.Events.DTSTART
		};
		String selection = null;
		String[] selectionArgs = null;
		String shortOrder = CalendarContract.Events.DTSTART+" DESC";
		CursorLoader cursorLoader = new CursorLoader(context, uri, projection, selection, selectionArgs, shortOrder);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
		
	}

}
