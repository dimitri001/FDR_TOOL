package utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

public class UtilsContacts {

	
	

	public static String getContactByNumber(String phoneNumber, Context context){
		
		Cursor cursorContact ;
		ContentResolver resolver = context.getContentResolver();
		String contactName = null;

		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
		
		cursorContact = resolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME, PhoneLookup._ID}, null, null, null);
		
		try {
			
			if (null!= cursorContact && cursorContact.getCount() > 0){
				
				cursorContact.moveToNext();
				
				int index = cursorContact.getColumnIndexOrThrow(PhoneLookup.DISPLAY_NAME);
				contactName = cursorContact.getString(index);
			}
			
		}
		
		finally {
			
			if( null != cursorContact){
				cursorContact.close();
			}
		}
		
			
		return contactName;
	}
}
