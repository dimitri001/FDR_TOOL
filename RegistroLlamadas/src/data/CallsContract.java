package data;

import android.net.Uri;
import android.provider.BaseColumns;

/*
 * This class is used to create the constants that we are going to use.
 * */

public class CallsContract {
	
	public static final String DB_NAME ="calls_db";
	public static final String AUTHORITY = "org.korso.apps.mycontentprovider";

	//Making the constructor private, we avoid that anybody instance this class
	private CallsContract(){}
	
	//Implementing BaseColumns we make that our new class has an _id used in android
	//we implement the BaseColumns because it´s added the id_ field
	public static class CallsTable implements BaseColumns{
		
		//Making the constructor private, we avoid that anybody instance this class
		private CallsTable(){}
		
		public static final String TABLE_NAME = "calls";		
		public static final String NAME ="name";
		public static final String NUMBER="number";
		public static final String DATE="date";
		
		public static Uri getUri() {
			return Uri.parse("content://"+AUTHORITY+"/users");
		}
		
		public static Uri getUri(long id) {
			return Uri.parse("content://"+AUTHORITY+"/users/"+id);
		}
		
	

		}
	
}
