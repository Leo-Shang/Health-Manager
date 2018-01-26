package com.GP.groupproject;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreatHistActivity extends Activity {
	
	TextView treatment;
	String treatment_list, patient_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patient_name = getIntent().getStringExtra("getName");
		
		// set up a new layout containing treatment history
		LinearLayout view_treatment = new LinearLayout(this);
		treatment = new TextView(this);
		
		DatabaseOpns db = new DatabaseOpns(getBaseContext());
		Cursor find = db.getInfo(db);
		find.moveToFirst();
		
		// search patient treatment history
		do{
			if(find.getString(0).equals(patient_name)){
				treatment_list = find.getString(5);
			}
		}
		while(find.moveToNext());
		
		treatment.setText(treatment_list);
		treatment.setTextSize(25);
		view_treatment.addView(treatment);
		setContentView(view_treatment);
	}

}
