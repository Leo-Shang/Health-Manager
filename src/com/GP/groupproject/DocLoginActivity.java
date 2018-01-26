package com.GP.groupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DocLoginActivity extends Activity{
	
	int start;
	String doctor_name, scheduel;
	StringBuffer print;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doc_login);
		doctor_name = getIntent().getStringExtra("doctor_name");
		
		setViewScheBtn();
		setStartTreatBtn();
		setupLogoutBtn();
		setupViewRateBtn();
	}

	private void setupViewRateBtn() {
		Button view_grade = (Button) findViewById(R.id.view_rate);
		view_grade.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent view = new Intent(DocLoginActivity.this, ViewRateActivity.class);
				view.putExtra("doc_name", doctor_name);
				startActivity(view);
			}
		});
		
	}

	private void setViewScheBtn() {
		Button btn = (Button)findViewById(R.id.view_sche);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				Cursor find = db.getInfo(db);
				find.moveToFirst();
				do{
					if(find.getString(0).equals(doctor_name) && find.getString(6) != null){
						scheduel = find.getString(6);
					}
					
					else if(find.getString(0).equals(doctor_name) && find.getString(6) == null){
						scheduel = " ";
					}
				}
				while(find.moveToNext());
				
				print = new StringBuffer();
				start = 0;
				// create the alert dialog
				AlertDialog.Builder time_table = new AlertDialog.Builder(DocLoginActivity.this);
				for(int i = 0; i < scheduel.length(); i++){
					if(scheduel.substring(i, i + 1).equals(";")){
						print.append(scheduel.substring(start, i) + "\n");
						start = i + 1;
					}
				}
				
				time_table.setCancelable(true);
				time_table.setTitle("Your work scheduel today");
				time_table.setMessage(print.toString());
				time_table.show();
			}
		});
	}

	private void setStartTreatBtn() {
		Button btn = (Button)findViewById(R.id.start_treat);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent start_treatment = new Intent(DocLoginActivity.this, NewDescriptionActivity.class);
				start_treatment.putExtra("doc_name", doctor_name);
				startActivity(start_treatment);
			}
		});
	}
	
	private void setupLogoutBtn() {
		Button btn = (Button)findViewById(R.id.logoutBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(DocLoginActivity.this, MainActivity.class));
			}
		});
	}
}
