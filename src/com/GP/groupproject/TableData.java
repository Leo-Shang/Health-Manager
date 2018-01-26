package com.GP.groupproject;

import android.provider.BaseColumns;

public class TableData {
	public TableData(){	}
	
	public static abstract class Table1 implements BaseColumns{
		public static final String NAME = "name";
		public static final String SIN_NUM = "SIN_num";
		public static final String PASSWORD = "password";
		public static final String TYPE = "type";
		public static final String AGE = "age";
		public static final String DISCRIPTION = "discription";
		public static final String COOD = "cood";
		public static final String RATE = "rate";
		public static final String PAT_NUM = "pat_num";
		public static final String DATABASE_NAME = "user_info";
		public static final String TABLE_NAME = "reg_info";
		
	}
}
