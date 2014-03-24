package org.korso.apps.rsscursoandroid.data;

import org.korso.apps.rsscursoandroid.data.FeedsContract.FeedsTable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.TextUtils;

public class FeedsContentProvider extends ContentProvider{

	private MyDbHelper mDbHelper;
	public static final int TYPE_FEEDS_COLLECTION = 1;
	public static final int TYPE_FEEDS_ITEM = 2;

	public static final UriMatcher sUriMatcher;
	
	static{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(FeedsContract.AUTHORITY, "feeds", TYPE_FEEDS_COLLECTION);
		sUriMatcher.addURI(FeedsContract.AUTHORITY, "feeds/#", TYPE_FEEDS_ITEM);
	}
	
	
	@Override
	public String getType(Uri uri) {

		switch(sUriMatcher.match(uri)){
		case TYPE_FEEDS_COLLECTION:
			return "android.cursor.dir/vnd.org.korso.apps.feedscontentprovider";
		case TYPE_FEEDS_ITEM: 
			return "android.cursor.item/vnd.org.korso.apps.feedscontentprovider";
		default:
			return null;
		
		}
		
	}
	
	@Override
	public boolean onCreate() {
		mDbHelper = new MyDbHelper(getContext());
		return true;
	}


	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		int uriType = sUriMatcher.match(uri);
		
		Uri newUri;
		switch (uriType) {
		case TYPE_FEEDS_ITEM:
			
			return null;
			
		case TYPE_FEEDS_COLLECTION:			
			long id = db.insert(FeedsTable.TABLE_NAME, null, values);
			newUri = FeedsTable.getUri(id);
			return newUri;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		int uriType = sUriMatcher.match(uri);
		
		switch (uriType) {
		case TYPE_FEEDS_ITEM:
			
			if(!TextUtils.isEmpty(selection)){
				selection+= " AND ";
				
			}else{
				selection = "";
			}
			
			String id = uri.getLastPathSegment();
			selection += FeedsTable._ID+ " = "+ id;

		case TYPE_FEEDS_COLLECTION:
			
			int count =db.delete(FeedsTable.TABLE_NAME, selection, selectionArgs);
			
			return count;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		int uriType = sUriMatcher.match(uri);
		
		switch (uriType) {
		case TYPE_FEEDS_ITEM:
			String id = uri.getLastPathSegment();
			
			if (!TextUtils.isEmpty(selection)){
				selection+= " AND ";
			}else{
				selection = "";
			}
			
			selection += FeedsTable._ID+" = "+ id;
		case TYPE_FEEDS_COLLECTION:
			
			Cursor cursor = db.query(FeedsTable.TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int uriType = sUriMatcher.match(uri);
		
		switch (uriType) {
		case TYPE_FEEDS_ITEM:
			String id = uri.getLastPathSegment();
			
			if (!TextUtils.isEmpty(selection)){
				selection+= " AND ";
			}else{
				selection = "";
			}
			
			selection += FeedsTable._ID+ " = "+id;
			
			int count = db.update(FeedsTable.TABLE_NAME, values, selection, selectionArgs);
			return count;

		case TYPE_FEEDS_COLLECTION:
			
			count = db.update(FeedsTable.TABLE_NAME, values, selection, selectionArgs);
			return count;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		
	}

	
}
