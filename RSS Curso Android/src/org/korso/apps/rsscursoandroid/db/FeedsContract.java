package org.korso.apps.rsscursoandroid.db;

import android.provider.BaseColumns;

public class FeedsContract {
	
	public static final String DB_NAME ="feeds_db";

	private FeedsContract(){}
	
	//Implementing BaseColumns we make that our new class has an _id used in android
	public static class FeedsTable implements BaseColumns{
		
		private FeedsTable(){}
		
		public static final String TABLE_NAME = "feeds";		
		public static final String TITLE ="title";
		public static final String CONTENT ="content";
		public static final String DATE="date";
		public static final String IMAGE_URL="image_url";
		public static final String LINK_URL="link_url";
		public static final String CATEGORY="category";
	}
	
	
	
}
