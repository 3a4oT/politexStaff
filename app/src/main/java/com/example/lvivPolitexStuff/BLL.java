package com.example.lvivPolitexStuff;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class BLL {
	//настройка параметрыв бази
	private static final String DATABASE_NAME = "SESSION";
	private static final String DATABASE_TABLE = "current_session";
	private static final Integer DATABASE_VERSION = 1;
	//=====================
	//=====================
	private static final String KEY_ROW_ID = "id";
	private static final String KEY_SUBJECT_NAME = "subject_name";
	private static final String KEY_MARK = "mark";
	private static final String KEY_FACTOR = "factor";
	//=========
	private final Context myContecxt;//обовязково ыныцыалызувати в конструкторі
	private SQLiteDatabase mySQLitedataBase;
	private DAL dal;
	//===
	
	
	

	
	public BLL(Context contexT)
	{
		myContecxt=contexT;
	}
	
	public BLL open()throws SQLException{
		
		dal= new DAL(myContecxt);
		
		mySQLitedataBase=dal.getWritableDatabase();
		
		return this;
		
	}
	
	public void close()throws SQLException{
		
		dal.close();
	}
 
 
	
	public long creatEntry(String subject_name,String mark,String factor)throws SQLException{
		ContentValues cv = new ContentValues();
		cv.put(KEY_SUBJECT_NAME, subject_name);
		cv.put(KEY_MARK, mark);
		cv.put(KEY_FACTOR, factor);
		return mySQLitedataBase.insert(DATABASE_TABLE, null, cv) ;
	}
	public String getData()throws SQLException{
		
		String[] collums= new String[]{KEY_ROW_ID,KEY_SUBJECT_NAME,KEY_MARK,KEY_FACTOR};
		
		Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, null, null, null, null, null);
		String result = null;
		int iRow = cursor.getColumnIndex(KEY_ROW_ID);
		int iSub_Name = cursor.getColumnIndex(KEY_SUBJECT_NAME);
		int iMark = cursor.getColumnIndex(KEY_MARK);
		int iFactor = cursor.getColumnIndex(KEY_FACTOR);
		
		
           for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			
			result=result+cursor.getString(iRow) + "\t\t" + cursor.getString(iSub_Name)+ "\t\t" +cursor.getString(iMark)+"\t\t"+cursor.getString(iFactor)  + "\n";
			
		 }
		
		return result;
		
		
	}
	
	
	public ArrayList<String> getID()throws SQLException{
		
		String[] collums= new String[]{KEY_ROW_ID};
		
		Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, null, null, null, null, null);
		//String result = null;
		ArrayList<String> arrayID = new ArrayList<String>();
		int iRow = cursor.getColumnIndex(KEY_ROW_ID);

           for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			
			//result=result+cursor.getString(iRow) ;
        	   arrayID.add(cursor.getString(iRow)) ;
			
		 }
		
		return arrayID;
		
	}
public ArrayList<String> getSubjectName()throws SQLException{
		
		String[] collums= new String[]{KEY_SUBJECT_NAME};
		
		Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, null, null, null, null, null);
		ArrayList<String> arraySubjectName = new ArrayList<String>();
		int iSubjecName = cursor.getColumnIndex(KEY_SUBJECT_NAME);

           for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			
             arraySubjectName.add( cursor.getString(iSubjecName));
			
		 }
		
		return arraySubjectName;
		
		
	}
public void deleteEntry(long lRow1)throws SQLException {
	
	mySQLitedataBase.delete(DATABASE_TABLE, KEY_ROW_ID+"="+lRow1, null);

	
	
	
}
public void deleteAll()throws SQLException {
	
	mySQLitedataBase.delete(DATABASE_TABLE, null, null);
	
	
}

public ArrayList<String> getMarks()throws SQLException{
	
	String[] collums= new String[]{KEY_MARK};
	
	Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, null, null, null, null, null);
	ArrayList<String> arrayMarks = new ArrayList<String>();
	int iMarks = cursor.getColumnIndex(KEY_MARK);

       for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
		
		arrayMarks.add(cursor.getString(iMarks));
		
	 }
	
	return arrayMarks;
		
}

public String  getSubjectName(long l)throws SQLException {
	
	String[] collums= new String[]{KEY_ROW_ID,KEY_SUBJECT_NAME,KEY_MARK,KEY_FACTOR};
	
	Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, KEY_ROW_ID+"="+l, null, null, null, null);
	if(cursor!=null)
	{
		cursor.moveToFirst();
		String name = cursor.getString(1);
		return name;
	}
	
	return null;
}
public String  getMarks(long l)throws SQLException {
	
	String[] collums= new String[]{KEY_ROW_ID,KEY_SUBJECT_NAME,KEY_MARK,KEY_FACTOR};
	
	Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, KEY_ROW_ID+"="+l, null, null, null, null);
	if(cursor!=null)
	{
		cursor.moveToFirst();
		String name = cursor.getString(2);
		return name;
	}
	
	return null;
}
public String  getFactor(long l)throws SQLException {
	
	String[] collums= new String[]{KEY_ROW_ID,KEY_SUBJECT_NAME,KEY_MARK,KEY_FACTOR};
	
	Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, KEY_ROW_ID+"="+l, null, null, null, null);
	if(cursor!=null)
	{
		cursor.moveToFirst();
		String name = cursor.getString(3);
		return name;
	}
	
	return null;
}
public void updeteEntry(long lRow, String mName, String mMark,String mFactor) throws SQLException {
	
	ContentValues cvUp = new ContentValues();
	
	cvUp.put(KEY_SUBJECT_NAME, mName);
	cvUp.put(KEY_MARK,mMark);
	cvUp.put(KEY_FACTOR,mFactor);
	
	mySQLitedataBase.update(DATABASE_TABLE,cvUp,KEY_ROW_ID+"="+lRow , null);
	
}

public ArrayList<String> getFactor()throws SQLException{
	
	String[] collums= new String[]{KEY_FACTOR};
	
	Cursor cursor = mySQLitedataBase.query(DATABASE_TABLE, collums, null, null, null, null, null);
	ArrayList<String> arrayRezz = new ArrayList<String>();
	int iFactor = cursor.getColumnIndex(KEY_FACTOR);

       for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
		
		    arrayRezz.add(cursor.getString(iFactor)) ;
		
	 }
	
	return arrayRezz;
		
}	
	public static String getDatabaseName() {
	return DATABASE_NAME;
}
	private static class DAL extends SQLiteOpenHelper{

		private static final String creatCMD=
		
		"CREATE TABLE " + DATABASE_TABLE + " ("+                                        
		KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
		KEY_SUBJECT_NAME + " TEXT NOT NULL,"+
		KEY_MARK + " TEXT NOT NULL,"+
        KEY_FACTOR + " TEXT NOT NULL);";//строка створення БД
		
		
		public DAL(Context context)//переопреділити конструктиор одним параметром
		{
			super(context, BLL.getDatabaseName(), null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
	           db.execSQL(creatCMD);  	
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			onCreate(db);
		}
		
	}

}