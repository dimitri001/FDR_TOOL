package org.korso.apps.contentprovider.test;

import data.MembersContract;
import data.MembersContract.UsersTable;
import data.MyContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

public class MyContentProviderTest extends ProviderTestCase2<MyContentProvider> {

	private MockContentResolver mContentResolver;//It calls to the contentprovider


	public MyContentProviderTest() {
		super(MyContentProvider.class, MembersContract.AUTHORITY);
		// TODO Auto-generated constructor stub
	}

		
	//Each method is considered a test, if there is any exception it`s a failed test
	//otherwise the test is ok
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mContentResolver = getMockContentResolver();
	}
	
	public void testUsersUri(){
		Uri expected = Uri.parse("content://org.korso.apps.mycontentprovider/users");
		Uri actual = UsersTable.getUri();
		
		assertEquals(expected, actual);
	}
	
	public void testInsert(){
		
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clar Kent");
		values.put(MembersContract.UsersTable.EMAIL,"super@man.com");
		
		Uri uri = UsersTable.getUri();
		
		Uri newUri = mContentResolver.insert(uri, values);
		
		assertNotNull(newUri);
		
	}
	
	public void testSelectOne(){
		
		//Fisrt we insert a row and we get an uri
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clar Kent");
		values.put(MembersContract.UsersTable.EMAIL,"super@man.com");
		
		Uri uri = UsersTable.getUri();
		
		Uri newUri = mContentResolver.insert(uri, values);
		
		assertNotNull(newUri);
		
		//with uri of the register, we get a cursor with all the data of the row
		
		Cursor cursor =mContentResolver.query(newUri, null, null, null, null);
		assertEquals(cursor.getCount(), 1);
		
		cursor.moveToFirst();
		String email = cursor.getString(cursor.getColumnIndex(UsersTable.EMAIL));
		String name = cursor.getString(cursor.getColumnIndex(UsersTable.USERNAME));
		
		assertEquals("super@man.com", email);
		assertEquals("Clar Kent", name);
		
		
	}
	
	
	public void testSelectMany(){
		
		insertReg("Clar Kent", "super@man.com");
		insertReg("Barney Stinson", "barney@stinson.com");
		insertReg("Ted Mosby", "ted@mosby.com");
		//with uri of the register, we get a cursor with all the data of the row
		
		Uri uri = UsersTable.getUri();
		Cursor cursor =mContentResolver.query(uri , null, null, null, null);
		assertEquals(cursor.getCount(), 3);
		
		compareReg(cursor,"Clar Kent","super@man.com");
		compareReg(cursor,"Barney Stinson","barney@stinson.com");
		compareReg(cursor,"Ted Mosby","ted@mosby.com");
		
		cursor.close();
		
		
		cursor =mContentResolver.query(uri , null, UsersTable.EMAIL + " like ?", new String[]{"ted@mosby.com"}, null);
		assertEquals(cursor.getCount(), 1);
		
	}
	
	public void testDeleteOne(){
		
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Peter Parker");
		values.put(MembersContract.UsersTable.EMAIL, "Spider@Man");
		
		Uri uri = UsersTable.getUri();
		Uri newUri = mContentResolver.insert(uri, values);
		
		assertNotNull(newUri);
		
		int count= mContentResolver.delete(newUri, null, null);
		assertEquals(count,1);
		
		Cursor cursor = mContentResolver.query(uri, null, null, null, null);
		assertEquals(cursor.getCount(), 0);	
		
				
	}
	
	
	public void testDeleteMany(){
		
		insertReg("Clar Kent", "super@man.com");
		insertReg("Barney Stinson", "barney@stinson.com");
		insertReg("Ted Mosby", "ted@mosby.com");
		//with uri of the register, we get a cursor with all the data of the row
		
		Uri uri = UsersTable.getUri();
		Cursor cursor =mContentResolver.query(uri , null, null, null, null);
		assertEquals(cursor.getCount(), 3);
		
		//String strWhere = UsersTable.EMAIL +" == "  + " 'super@man.com' " + " OR " + UsersTable.EMAIL +" == " + "'ted@mosby.com'";
		String strWhere = UsersTable.EMAIL +" == ?" + " OR " + UsersTable.EMAIL +" == ?";
		
		int count = mContentResolver.delete(uri, strWhere, new String[]{"super@man.com", "ted@mosby.com"});
		//int count = mContentResolver.delete(uri, strWhere, null);
		
		assertEquals(count, 2);
		
		cursor = mContentResolver.query(uri, null, UsersTable.EMAIL+ " like ?", new String[]{"barney@stinson.com"}, null);
		assertEquals(cursor.getCount(), 1);		
		
		
	}
	
	
	public void testDeleteAll(){
		
		insertReg("Clar Kent", "super@man.com");
		insertReg("Barney Stinson", "barney@stinson.com");
		insertReg("Ted Mosby", "ted@mosby.com");
		//with uri of the register, we get a cursor with all the data of the row
		
		Uri uri = UsersTable.getUri();
		Cursor cursor =mContentResolver.query(uri , null, null, null, null);
		assertEquals(cursor.getCount(), 3);
		
		
		int count = mContentResolver.delete(uri, null, null);
		
		assertEquals(count, 3);
		
		cursor = mContentResolver.query(uri, null, null, null, null);
		assertEquals(cursor.getCount(), 0);
		
	}
	
	
	public void testUpdateOneUri(){
		
		ContentValues values = new ContentValues();
		values.put(UsersTable.USERNAME, "Clar Kent");
		values.put(UsersTable.EMAIL, "super@man");
		
		Uri uri = UsersTable.getUri();
		
		Uri newUri = mContentResolver.insert(uri, values);
		
		assertNotNull(newUri);
		
		values.clear();
		values.put(UsersTable.USERNAME, "Clar Kent Cambiado");
		
		int count = mContentResolver.update(newUri, values, null, null);
		assertEquals(count, 1);
	}
	
	public void testUpdateOneWhere(){
		
		insertReg("Clar Kent", "super@man.com");
		insertReg("Barney Stinson", "barney@stinson.com");
		insertReg("Ted Mosby", "ted@mosby.com");
		
		Uri uri = UsersTable.getUri();
		
		ContentValues values = new ContentValues();
		values.clear();
		values.put(UsersTable.USERNAME, "Clar Kent Cambiado");
		
		String strWhere = UsersTable.USERNAME+ "="+ " 'Clar Kent' ";
		
		int count = mContentResolver.update(uri, values, strWhere, null);
		assertEquals(count, 1);
		
		strWhere = UsersTable.USERNAME+ "= ?";
		
		values.clear();
		values.put(UsersTable.USERNAME, "Clar Kent ReCambiado");
		
		count = mContentResolver.update(uri, values, strWhere, new String[]{"Clar Kent Cambiado"});
		assertEquals(count, 1);
		
		
	}
	
	
	public void testUpdateMany(){
		
		insertReg("Clar Kent", "super@man.com");
		insertReg("Barney Stinson", "barney@stinson.com");
		insertReg("Ted Mosby", "ted@mosby.com");
		
		Uri uri = UsersTable.getUri();
		
		ContentValues values = new ContentValues();
		values.put(UsersTable.EMAIL, "mail@comun.com");
		
		String strWhere = UsersTable.EMAIL +" like ?";
		
		int count = mContentResolver.update(uri, values, strWhere, new String[]{"%@%"});
		
		assertEquals(count, 3);
		
		values.clear();
		values.put(UsersTable.EMAIL, "mailCambiado@comun.com");		
		
		strWhere = UsersTable.EMAIL +" like '%@%' ";
		
		count = mContentResolver.update(uri, values, strWhere, null);
		
		assertEquals(count, 3);
		
		
		
		
	}
	
	public void compareReg(Cursor cursor, String myName, String myEmail){
		
		cursor.moveToNext();
		
		String name = cursor.getString(cursor.getColumnIndex(UsersTable.USERNAME));
		String email = cursor.getString(cursor.getColumnIndex(UsersTable.EMAIL));
		
		assertEquals(myName, name);
		assertEquals(myEmail, email);
	}
	
	
	public Uri insertReg(String name, String mail){
	
		//Fisrt we insert a row and we get an uri
			ContentValues values = new ContentValues();
			values.put(MembersContract.UsersTable.USERNAME,name);
			values.put(MembersContract.UsersTable.EMAIL,mail);
			
			Uri uri = UsersTable.getUri();
			
			Uri newUri = mContentResolver.insert(uri, values);
			
			assertNotNull(newUri);
			
			return newUri;
	}
	

}
