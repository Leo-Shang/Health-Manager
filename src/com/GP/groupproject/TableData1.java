package com.GP.groupproject;

import android.provider.BaseColumns;

public class TableData1 {
	
	// constructor 
	public void TableData1(){
		
	}
	
	// create the column names for table
	// This table contains the work time of doctors, the first, second, and third appointment time
	public static abstract class Table2 implements BaseColumns{
	
		public static final String DOC_NAME = "doc_name";
		public static final String DOC_TIME = "doc_worktime";
		public static final String APPOINTMENT1 = "first_appointment";
		public static final String APPOINTMENT2 = "second_appointment";
		public static final String APPOINTMNET3 = "third_appointmnet";
		
		// create the name of table
		public static final String TABLE_NAME = "appointment_info";
	}
}
