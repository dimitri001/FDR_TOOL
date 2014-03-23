package data;

import java.util.ArrayList;
import java.util.Arrays;

import data.MembersContract.UsersTable;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider{

	private MyDbHelper mDbHelper;
	private static final int TYPE_USERS_COLLECTION = 1;
	private static final int TYPE_USERS_ITEM = 2;

	//here we create the UriMatcher here this name start with the s because this is an stactic value
	private static final UriMatcher sUriMatcher; 
	 //here is initialized in an static block
	//the uri's here are changed to number values
	//Every uri that we create is added here

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(MembersContract.AUTHORITY, "users", TYPE_USERS_COLLECTION);//all the regs
		sUriMatcher.addURI(MembersContract.AUTHORITY, "users/#", TYPE_USERS_ITEM);//one reg, the # represents t 
	}
	
	//we instanced what we need here at the contentprovider
	@Override
	public boolean onCreate() {
		mDbHelper = new MyDbHelper(getContext());
		return true;
	}
	
	//returns a mimetype used to filter the reg that correspond to a uri 
	@Override
	public String getType(Uri uri) {
		switch(sUriMatcher.match(uri)){
			case TYPE_USERS_COLLECTION:
				return "android.cursor.dir/vnd.org.korso.apps.mycontentprovider";
			case TYPE_USERS_ITEM:
				return "android.cursor.item/vnd.org.korso.apps.mycontentprovider";
			default:
				return null;
		}
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int uriType = sUriMatcher.match(uri);
		String[] selectionArgs2 = null;
		
		switch (uriType) {
		case TYPE_USERS_ITEM:
			
			String id = uri.getLastPathSegment();
			
			if (!TextUtils.isEmpty(selection)){
				selection += " AND ";
			}else{
				selection="";
			}
			
			
			//first option using delete without selectionArgs
			selection = UsersTable._ID+"=="+id;
			
			//second option using delete with selectionArgs
			/*selection += UsersTable._ID+"== ?";
			if (selectionArgs != null){
				ArrayList<String> selectionList = new ArrayList<String>(Arrays.asList(selectionArgs));
				selectionList.add(id);
				selectionArgs2= (String[]) selectionList.toArray();				
			}else{
				selectionArgs2 = new String[1];
				selectionArgs2[0]= id;
			}
			*/
			
			//return db.delete(UsersTable.TABLE_NAME, selection, selectionArgs2);			
			
			return db.delete(UsersTable.TABLE_NAME, selection, selectionArgs);			
			
		case TYPE_USERS_COLLECTION:
			
			int count = db.delete(UsersTable.TABLE_NAME, selection, selectionArgs);
			return count;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		int uriType = sUriMatcher.match(uri);
		if(uriType!=TYPE_USERS_COLLECTION){
			return null;
			
		} 

		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long id = db.insert(UsersTable.TABLE_NAME, null, values);
		
		Uri newUri = UsersTable.getUri(id);
		
		return newUri;
	}

	

	@Override
	/**
	 * uri	The URI to query. This will be the full URI sent by the client; if the client is requesting a specific record, the URI will end in a record number that the implementation should parse and add to a WHERE or HAVING clause, specifying that _id value.
	   projection	The list of columns to put into the cursor. If null all columns are included.
	   selection	A selection criteria to apply when filtering rows. If null then all rows are included.
	   selectionArgs	You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
       sortOrder	How the rows in the cursor should be sorted. If null then the provider is free to define the sort order.
	 */
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String orderBy) {
		
		final SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String table =UsersTable.TABLE_NAME;
		String groupBy = null;
		String having = null;
		Cursor cursor;
		
		switch (sUriMatcher.match(uri)){
		case TYPE_USERS_ITEM:
			String id = uri.getLastPathSegment();
			
			if (!TextUtils.isEmpty(selection)){
				selection += " AND ";
			}else {
				selection = "";
			}
			
			selection += UsersTable._ID+ "=="+id;
			
			
			//cursor = db.query(table, projection, selection, selectionArgs, groupBy, having, orderBy);
			//return cursor;
			
		case TYPE_USERS_COLLECTION:		
			
			cursor = db.query(table, projection, selection, selectionArgs, groupBy, having, orderBy);
			return cursor;
			
		default:
			return null;
			
		}
	}

	@Override
	public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int uriType = sUriMatcher.match(uri);
		
		switch (uriType) {
		case TYPE_USERS_ITEM:
			
			String id = uri.getLastPathSegment();
			
			if (!TextUtils.isEmpty(selection)){
				selection += " AND "; 
			}else{
				selection="";
			}
			
			selection += UsersTable._ID + "=" + id;
			int count=db.update(UsersTable.TABLE_NAME, values, selection, selectionArgs);			
			
			return count;
			
		case TYPE_USERS_COLLECTION:		
		
			count=db.update(UsersTable.TABLE_NAME, values, selection, selectionArgs);			
			return count;

		default:
			return 0;
		}
		
		
		
	}

}
