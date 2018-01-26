package com.GP.groupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MedcineDB extends SQLiteOpenHelper{
public static final String DATABASE_NAME="Meddatabase.db";

public static final String TABLE_NAME="Medicine";

//public static final String ID="ID";
public static final String MedNm="MedNm";
public static final String Remain="Remain";
public static final String Cost="Cost";
public static final String Price="Price";


public MedcineDB(Context context){
	super(context, DATABASE_NAME , null, 1);

}

@Override
public void onCreate(SQLiteDatabase db){
	db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(MedNm TEXT,Remain INTEGER,Price INTEGER,Cost DOUBLE)");
}

@Override
public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
	db.execSQL("Drop Table if exists"+TABLE_NAME);
	onCreate(db);
}
public boolean insertData(String name,String R,String P,String C){
	SQLiteDatabase db=this.getWritableDatabase();
	ContentValues contentValues = new ContentValues();
	if(tableIsExist(TABLE_NAME, db)){
	contentValues.put(MedNm,name);
	contentValues.put(Remain,R);
	contentValues.put(Price,P);
	contentValues.put(Cost,C);
	}
	long result = db.insert(TABLE_NAME, null, contentValues);
	
	if (result == -1)
		return false;
	else
		return true;
}

public Cursor getAllData(){
	SQLiteDatabase db = this .getWritableDatabase();
	Cursor res = db.rawQuery("select * from " +  TABLE_NAME,null);
	return res;
}
public boolean tableIsExist(String tabName, SQLiteDatabase db) {
    boolean result = false;  
    if (tabName == null) {  
        return false;  
    }  
    Cursor cursor = null;  
    try {  

        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";  
        cursor = db.rawQuery(sql, null);  
        if (cursor.moveToNext()) {  
            int count = cursor.getInt(0);  
            if (count > 0) {  
                result = true;  
            }  
        }  

    } catch (Exception e) {  
        // TODO: handle exception  
    }  
    return result;  
}

}