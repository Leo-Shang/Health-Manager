package com.GP.groupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RateActivity extends Activity{
	String message, getGrade, patient_name, doc_name;
	RelativeLayout rating;
	EditText grade;
	Button submit_grade;
	float mark, new_grade;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patient_name = getIntent().getStringExtra("getName");
		setContentView(R.layout.activity_rate);
		setupSubmit();
	}

	private void setupSubmit() {
		
		grade = (EditText) findViewById(R.id.grade);
		submit_grade = (Button) findViewById(R.id.submit_button);
		submit_grade.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getGrade = grade.getText().toString();
				mark = Float.valueOf(getGrade);
				
				Log.i("database opearation", String.valueOf(mark));
				
				// check if the input rate is out of range
			if(mark < 1.0 || mark > 5.0){
				AlertDialog.Builder alert = new AlertDialog.Builder(RateActivity.this);
				alert.setCancelable(true);
				alert.setMessage("Your rate input is invalid. Please try again.");
				alert.show();
			}
			
			else{
				// start inserting the new rate
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				Cursor find = db.getInfo(db);
				find.moveToFirst();
				do{
					if(find.getString(0).equals(patient_name)){
						doc_name = find.getString(6);
					}
				}
				while(find.moveToNext());
				
				find.moveToFirst();
				do{
					if(find.getString(0).equals(doc_name)){
						
						// update the rate for this doctor
						if(find.getInt(8) == 1){
							new_grade = mark;
						}
						
						else{
						new_grade = find.getFloat(7);
						new_grade += mark * (find.getInt(8) - 1);
						
						Log.i("database opeartion", "new grade is: " + String.valueOf(new_grade));
						new_grade /= find.getInt(8);     // the final new rate
						Log.i("database operation", "final grade is: " + String.valueOf(new_grade));
						// update the row
						}
						db.updateRate(doc_name, new_grade);
					}
					
					else if(find.getString(0).equals(patient_name)){
						
						// delete patient cood column
						db.updateCood(patient_name, null);
					}
				}
				while(find.moveToNext());
				
				startActivity(new Intent(RateActivity.this, PatLoginActivity.class));
			}
			}
		});
	
		
	}

	
}
