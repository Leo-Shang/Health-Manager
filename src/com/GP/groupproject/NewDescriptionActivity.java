package com.GP.groupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewDescriptionActivity extends Activity {
	
	EditText get_patient_name, get_patient_num, get_description;
	Button submit;
	String treatment_history, getName, getNum, getDes, appo_order, temp, doc_name, schedule, delete_appo;
	int start, end, pat_num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_description);
		doc_name = getIntent().getStringExtra("doc_name");
		
		setupSubmitBtn();
		setupMedcine();
	}

	private void setupSubmitBtn() {
		
		get_patient_name = (EditText) findViewById(R.id.get_patient_name);
		get_patient_num = (EditText) findViewById(R.id.get_patient_num);
		get_description = (EditText) findViewById(R.id.addDiscription);
		Button btn = (Button)findViewById(R.id.submit);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getName = get_patient_name.getText().toString();
				getNum = get_patient_num.getText().toString();
				getDes = get_description.getText().toString();
				
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				Cursor find = db.getInfo(db);
				find.moveToFirst();
				
			// if all info are filled
			if(!getName.equals("") && !getNum.equals("") && !getDes.equals("")){
				do{
					if(find.getString(0).equals(doc_name)){
						schedule = find.getString(6);
					}
				}
				while(find.moveToNext());
				
				// extract the appo_info that is needed to be deleted from doctor work schedule
				start = 0;
				for(int i = 0; i < schedule.length(); i++){
					if(schedule.substring(i, i + 1).equals(";")){
						for(int j = 0; j < schedule.substring(start, i).length(); j++){
							if(schedule.substring(start, i).substring(j, j + 1).equals(":")){
								if(schedule.substring(start, i).substring(0, j).equals(getName)){
									delete_appo = schedule.substring(0, i + 1);
								}
							}
						}
						start = i + 1;
					}
				}
				
				Log.i("database operations", "delete_appo = " + delete_appo);
				find.moveToFirst();
				
				do{
					if(find.getString(0).equals(getName)){
						
						if(find.getString(5) != null){
						treatment_history = find.getString(5) + "\n" + "Patient name: " + getName + "\n" + 
							"Patient SIN number: " + getNum + "\n" +
							"Diagnose: " + getDes + "\n" +
							"----------------------------------";
						}
						else{
						treatment_history = "Patient name: " + getName + "\n" + 
							"Patient SIN number: " + getNum + "\n" +
							"Diagnose: " + getDes + "\n" +
							"----------------------------------";
						}
						// insert description
						db.updateDes(getName, treatment_history);
						
						// get appo_order
						for(int i = 0; i < find.getString(6).length(); i++){
							if(find.getString(6).substring(i, i + 1).equals(",")){
								temp = find.getString(6).substring(0, i);
							}
						}
						
						Log.i("database operation", "doc_name = " + doc_name);
						Log.i("database operation", "temp = " + temp);
						if(temp.equals("appo1")){
							db.updateRow(null, doc_name, 2);
						}
						
						else if(temp.equals("appo2")){
							db.updateRow(null, doc_name, 3);
						}
						
						else if(temp.equals("appo3")){
							db.updateRow(null, doc_name, 4);
						}
						// delete patient appo_info (both in table1 and table2)
						
						db.updateCood(getName, null);
						
						// delete patient appo_info from doctor work schedule
						db.updateCood(doc_name, schedule.replace(delete_appo, ""));
					}
					
					// increase patient number
					else if(find.getString(0).equals(doc_name)){
						pat_num = find.getInt(8);
						pat_num ++;
						
						Log.i("database opeartion", "patient_num = " + String.valueOf(pat_num));
						// update the row
						db.updatePat(doc_name, pat_num);
					}
				}
				while(find.moveToNext());
				
				// insert doc_name to patient row for rating
				find.moveToFirst();
				do{
					if(find.getString(0).equals(getName)){
						db.updateCood(getName, doc_name );
					}
				}
				while(find.moveToNext());
					
				startActivity(new Intent(NewDescriptionActivity.this, LoginActivity.class));
			}			
				else{
					Toast.makeText(getApplicationContext(), "You are missing inputs, please fill them up.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void setupMedcine(){
		Button medcine = (Button)findViewById(R.id.medicinelist);
	
		medcine.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(NewDescriptionActivity.this, MedcineList.class));
		}
			
	});
	}

}
