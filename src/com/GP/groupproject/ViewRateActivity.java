package com.GP.groupproject;

import java.text.DecimalFormat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewRateActivity extends Activity{
	
	float grade, round_grade;
	String doctor_name, final_grade;
	LinearLayout rate_view;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doctor_name = getIntent().getStringExtra("doc_name");
		setView();
		setContentView(rate_view);
	}

	private void setView() {
		
		rate_view = new LinearLayout(this);
		
		// search the rate of doctor
		DatabaseOpns db = new DatabaseOpns(getBaseContext());
		Cursor find = db.getInfo(db);
		find.moveToFirst();
		do{
			if(find.getString(0).equals(doctor_name)){
				grade = find.getFloat(7);
				
				Log.i("database opearation", "grade of doctor is: " + String.valueOf(grade));
			}
		}
		while(find.moveToNext());
		
		// round off rate
		final_grade = String.format("%.1f", grade);
		
		Log.i("database opearation", "grade is: " + final_grade);
		
		// set up the textView 
		TextView my_rate = new TextView(this);
		my_rate.setText("Your current grade is: " + final_grade);
		my_rate.setTextSize(30);
		rate_view.addView(my_rate);
	}

}
