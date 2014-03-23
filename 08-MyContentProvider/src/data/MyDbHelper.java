package data;


import data.MembersContract.UsersTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This class helps us to create the data base, update, write 
public class MyDbHelper extends SQLiteOpenHelper {
	
	//We use the data base version because there could be different versions on the different updates
	private static final int DATABASE_VERSION = 1;
	public MyDbHelper (Context context){
		super(context, MembersContract.DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+ UsersTable.TABLE_NAME+"("
				+UsersTable._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+UsersTable.USERNAME + " STRING, "
				+UsersTable.EMAIL+ " STRING,"
				+UsersTable.DATE+ " LONG"				
				+")"
				);

	}

	//Here we receive an old data base created with an oldeversion. If we receive an old database
	//we dont create a new one, we just modify the old database, or we create the databaes.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
