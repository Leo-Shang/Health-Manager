package com.GP.groupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.GP.groupproject.TableData.Table1;

// new added
import com.GP.groupproject.TableData1.Table2;
//

public class DatabaseOpns extends SQLiteOpenHelper {
	
	// new modified
	// databaase_version is changed into 2
	public static final int database_version = 2;
	public String CREATE_QUERY = "CREATE TABLE " + Table1.TABLE_NAME + "(" + Table1.NAME + " TEXT," + Table1.SIN_NUM + " TEXT," +
	Table1.PASSWORD + " TEXT," + Table1.TYPE + " TEXT," + Table1.AGE + " TEXT," + Table1.DISCRIPTION + " TEXT," + Table1.COOD + " TEXT," + Table1.RATE + " REAL," + Table1.PAT_NUM + " INTEGER);";
	
	// new added 
	// Table QUERY String for table 2 (appointment_info)
	public String CREATE_QUERY2 = "CREATE TABLE " + Table2.TABLE_NAME + "(" + Table2.DOC_NAME + " TEXT," + Table2.DOC_TIME + " TEXT," + Table2.APPOINTMENT1 +" TEXT," + 
	Table2.APPOINTMENT2 + " TEXT," + Table2.APPOINTMNET3 + " TEXT);";
	//

	public DatabaseOpns(Context context) {
		super(context, Table1.DATABASE_NAME, null, database_version);
		Log.d("Database operations","database created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_QUERY);
		
		// new added
		// declare the second table
		db.execSQL(CREATE_QUERY2);
		//
		Log.d("Database operations","table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// new added
		// update the existing database
		db.execSQL(CREATE_QUERY2);
		onCreate(db);
		//
	}
	
	public void setInfo(DatabaseOpns ops, String name, String SIN, String password, String type, String age, String disc, String cood, double rate, int pat_num){
		SQLiteDatabase sq = ops.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(Table1.NAME, name);
		content.put(Table1.SIN_NUM, SIN);
		content.put(Table1.PASSWORD, password);
		content.put(Table1.TYPE, type);
		content.put(Table1.AGE, age);
		content.put(Table1.DISCRIPTION, disc);
		content.put(Table1.COOD, cood);
		content.put(Table1.RATE, rate);
		content.put(Table1.PAT_NUM, pat_num);
		sq.insert(Table1.TABLE_NAME, null, content);
		Log.d("database operations","inserted");
	}
	
	// new added
	// insert doctor work time and appointment info into table 2
	public void setAppointmentInfo(DatabaseOpns ops, String Doc_name, String Doc_time, String First_appointment, String Second_appointment, String Third_appointment){
		SQLiteDatabase sq = ops.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(Table2.DOC_NAME, Doc_name);
		content.put(Table2.DOC_TIME, Doc_time);
		content.put(Table2.APPOINTMENT1, First_appointment);
		content.put(Table2.APPOINTMENT2, Second_appointment);
		content.put(Table2.APPOINTMNET3, Third_appointment);
		sq.insert(Table2.TABLE_NAME, null, content);
		Log.d("database operations", "table2 has been inserted");
	}
	//
	
	// new added
	// update row
	public void updateRow(String appo_time, String doc_name, int order){
		
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		
		if(order == 2){
			content.put(Table2.APPOINTMENT1, appo_time);
		}
		
		else if(order == 3){
			content.put(Table2.APPOINTMENT2, appo_time);
		}
		
		else if(order == 4){
			content.put(Table2.APPOINTMNET3, appo_time);
		}
		
		String selection = Table2.DOC_NAME + " = ?";
		String[] args = {doc_name};
		
		int value = sq.update(Table2.TABLE_NAME, content, selection, args);
		Log.i("database oepartions", String.valueOf(value));
	}
	//
	
	// new added
	// update row for table 1 (update coordinates for patient canceling appointment)
	public void updateCood(String name, String cood){
		
		Log.i("database operations", "inserting");
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		Log.i("database operations", "cood: " + cood);
		Log.i("database operations", "patient: " + name);
		content.put(Table1.COOD, cood);
		
		String selection = Table1.NAME + " = ?";
		String[] args = {name};
		sq.update(Table1.TABLE_NAME, content, selection, args);
	}
	//
	
	// new added
	// update row for table1 (insert new description)
	public void updateDes(String name, String description){
		
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(Table1.DISCRIPTION, description);
		
		String selection = Table1.NAME + " = ?";
		String[] args = {name};
		sq.update(Table1.TABLE_NAME, content, selection, args);
	}
	//
	
	// new added
	// update row for the number of patients
	public void updatePat(String name, int pat_num){
		
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(Table1.PAT_NUM, pat_num);
		
		String selection = Table1.NAME + " = ?";
		String[] args = {name};
		sq.update(Table1.TABLE_NAME, content, selection, args);
	}
	//
	
	// new added
	// update row for the new rate of doctor
	public void updateRate(String name, float rate){
		
		SQLiteDatabase sq = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(Table1.RATE, rate);
		
		String selection = Table1.NAME + " = ?";
		String[] args = {name};
		sq.update(Table1.TABLE_NAME, content, selection, args);		
	}
	//
	
	public Cursor getInfo(DatabaseOpns ops){
		SQLiteDatabase sq = ops.getReadableDatabase();
		String[] coloumns = {Table1.NAME, Table1.SIN_NUM, Table1.PASSWORD, Table1.TYPE, Table1.AGE, Table1.DISCRIPTION, Table1.COOD, Table1.RATE, Table1.PAT_NUM};
		Cursor cor = sq.query(Table1.TABLE_NAME, coloumns, null, null, null, null, null);
		return cor;
	}
	
	// new added
	// set up the cursor for appointment info
	public Cursor getAppointmentInfo(DatabaseOpns ops){
		SQLiteDatabase sq = ops.getReadableDatabase();
		
		// set up the search criteria
		String[] columns = {Table2.DOC_NAME, Table2.DOC_TIME, Table2.APPOINTMENT1, Table2.APPOINTMENT2, Table2.APPOINTMNET3};
		Cursor search = sq.query(Table2.TABLE_NAME, columns, null, null, null, null, null);
		return search;
	}
	
	// new added
	// create the cursor to contain doctor work time
	public Cursor getInfo(){
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor info = db.rawQuery("select * from " + Table2.TABLE_NAME, null);
		return info;
	}

}
