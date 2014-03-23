package org.korso.apps.rsscursoandroid.db;

import org.korso.apps.rsscursoandroid.db.FeedsContract.FeedsTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	public MyDbHelper (Context context){
		super(context, FeedsContract.DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		createFeedTable(db);	

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void createFeedTable(SQLiteDatabase db){
		
		//db.execSQL("DROP TABLE IF EXISTS" + FeedsTable.TABLE_NAME);
				db.execSQL("CREATE TABLE "+ FeedsTable.TABLE_NAME+"("
						+FeedsTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+FeedsTable.TITLE + " STRING, "
						+FeedsTable.CONTENT+ " STRING, "
						+FeedsTable.DATE + " LONG, "
						+FeedsTable.IMAGE_URL + " STRING UNIQUE, "
						+FeedsTable.CATEGORY + " STRING, "
						+FeedsTable.LINK_URL + " STRING UNIQUE"
						+")"
						);
	}
	

}
