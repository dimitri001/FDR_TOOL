package org.korso.apps.rsscursoandroid.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FeedsContract {
	
	public static final String DB_NAME ="feeds.db";
	public static final String AUTHORITY = "org.korso.apps.feedscontentprovider";

	private FeedsContract(){}
	
	//Implementing BaseColumns we make that our new class has an _id used in android
	public static class FeedsTable implements BaseColumns{
		
		private FeedsTable(){}
		
		public static final String TABLE_NAME = "my_feeds";		
		public static final String TITLE ="title";
		public static final String CONTENT ="content";
		public static final String DATE="date";
		public static final String IMAGE_URL="image_url";
		public static final String LINK_URL="link_url";
		public static final String CATEGORY="category";
		
		public static Uri getUri(){
			return (Uri.parse("content://"+AUTHORITY+"/feeds"));			
		}
		
		public static Uri getUri(long id){
			return (Uri.parse("content://"+AUTHORITY+"/feeds/"+id));
		}
	}
	
	
	
}
