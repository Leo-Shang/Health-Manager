package com.GP.groupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AppointmentActivity extends Activity {
	
	public final String tag = "database operations";
	EditText enterStartTime, enterEndTime;
	Button complete;
	Button viewWorkTime;
	String selectedDoc, getStartTime, getEndTime, appo_times, coordinates, scheduel, order, patient_name, check_appo;
	int selected_Doc, getStart_Time, getEnd_Time, doc_StartTime, doc_EndTime, appo_StartTime, appo_EndTime, record;
	
	// set up the spinner
	Spinner doc_spinner;
	ArrayAdapter<CharSequence> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_appointment);
		patient_name = getIntent().getStringExtra("patient_name");
		makeAppointment();
	}

	private void makeAppointment() {
		
		Log.i("database operations", "name passed" + patient_name);
		// apply the spinner adapter to doc_spinner
		doc_spinner = (Spinner) findViewById(R.id.doc_spinner);
		adapter = ArrayAdapter.createFromResource(this, R.array.doc_list, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		doc_spinner.setAdapter(adapter);
		
		enterStartTime = (EditText) findViewById(R.id.startTime);
		enterEndTime = (EditText) findViewById(R.id.endTime);
		complete = (Button) findViewById(R.id.complete);
		viewWorkTime = (Button) findViewById(R.id.doc_view);
		record = 0;
		
		// set up viewWorkTime Button
		viewDocTime();
		
		complete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// get the input strings
				selectedDoc = doc_spinner.getSelectedItem().toString();
				Log.i("database operations", selectedDoc);
				getStartTime = enterStartTime.getText().toString();
				getEndTime = enterEndTime.getText().toString();
				appo_times = getStartTime + "-" + getEndTime;
				
				// criteria checking if appointment will be made successfully
				boolean check = true;
				
				// check if start_time and end_time input is empty
				if(getStartTime == null || getEndTime == null){
					check = false;
				}
				
				// convert the input strings to integers
				getStart_Time = Integer.parseInt(getStartTime);
				getEnd_Time = Integer.parseInt(getEndTime);
				
				// check if end_time is smaller than start_time
				if(getEnd_Time <= getStart_Time){
					check = false;
				}
				
				// start querying table2
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				
				// create a cursor for searching
				Cursor Find = db.getAppointmentInfo(db);
				Find.moveToFirst();  // move the cursor to the first row of table
				
				Cursor peek = db.getInfo(db);
				peek.moveToFirst();
				
				String temp = null;
				
				// situation in which no space for appointment
				if(Find.getString(2) != null && Find.getString(3) != null && Find.getString(4) != null){
					Toast.makeText(getBaseContext(), "sorry! no space for appointments right now", Toast.LENGTH_LONG).show();
				}
				
				// check table1 if cood is null
				do{
					if(peek.getString(0).equals(patient_name)){
						check_appo = peek.getString(6);
					}
					
					else if(peek.getString(0).equals(selectedDoc)){
						if(peek.getString(6) == null){
							scheduel = patient_name + ":" + appo_times + ";";
						}
						
						else{
							scheduel = peek.getString(6) + patient_name + ":" + appo_times + ";";
						}
					}
				}
				while(peek.moveToNext());
				
				do{ 
					Log.i("database operations", Find.getString(0));
					if(Find.getString(0).equals(selectedDoc)){
						// extract doctor's work time
						for(int i = 0; i < Find.getString(1).length(); i++){
							if(Find.getString(1).substring(i, i + 1).equals("-")){
								temp = Find.getString(1).substring(0, i);
								Log.i("database operations", temp);
								doc_StartTime = Integer.parseInt(temp);
								doc_EndTime = Integer.parseInt(Find.getString(1).substring(i + 1, Find.getString(1).length()));
							}
						}
							
						temp = null;
							
						// compare with doctor time
						if(getStart_Time < doc_StartTime || getEnd_Time > doc_EndTime){
							check = false;
						}
							
						// check if appointment to be made has conflict with other appointments
						for(int k = 2; k <= 4; k++){
						if(Find.getString(k) != null){
							for(int j = 0; j < Find.getString(k).length(); j++){
								if(Find.getString(k).substring(j, j + 1).equals("-")){
									temp = Find.getString(k).substring(0, j);
									appo_StartTime = Integer.parseInt(temp);
									appo_EndTime = Integer.parseInt(Find.getString(k).substring(j + 1, Find.getString(2).length()));
								}
							}
								
							if((getStart_Time > appo_StartTime && getStart_Time < appo_EndTime) || (getEnd_Time > appo_StartTime && getEnd_Time < appo_EndTime)
									|| getStart_Time == appo_StartTime || getEnd_Time == appo_EndTime || (getStart_Time < appo_StartTime && getEnd_Time > appo_EndTime)){
									check = false;
							}
						}
						
						temp = null;
					}
						
						// if no conflict, insert it (update the row)
						if(check == true){
							for(int k = 2; k <= 4; k++){
								if(Find.getString(k) == null && record == 0){
									if(check_appo == null){
										
										// update the row in table2
										db.updateRow(appo_times, selectedDoc, k);
										
										// update the cood column in table1
										if(k == 2){
											order = "appo1";
										}
									
										else if(k == 3){
											order = "appo2";
										}
									
										else if(k == 4){
											order = "appo3";
										}
										coordinates = order + "," + selectedDoc;
										Log.i(tag, coordinates);
										db.updateCood(patient_name, coordinates);
										
										// update the doctor rows in table1(for doctor viewing his schedule
										db.updateCood(selectedDoc, scheduel);
										record++;
									}
								}
							}
						}			
				}
					
				}
				while(Find.moveToNext());
				
				if(check == false){
					Toast.makeText(getBaseContext(), "unable to complete appointment", Toast.LENGTH_SHORT).show();
				}
				
				if(check == true && check_appo != null){
					AlertDialog.Builder alert = new AlertDialog.Builder(AppointmentActivity.this);
					alert.setCancelable(true);
					alert.setMessage("Dear " + patient_name +", you have already made an appointment.");
					alert.show();
				}
				else{
					Toast.makeText(getBaseContext(), "appointment is completed", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}

	private void viewDocTime() {
		
		viewWorkTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				Cursor info = db.getInfo();
				if(info.getCount() == 0){
					Toast.makeText(getBaseContext(), "no available doctor currently", Toast.LENGTH_SHORT).show();
				}
				
				StringBuffer show = new StringBuffer();
				while(info.moveToNext()){
					
					// append message into StringBuffer
					show.append(info.getString(0) + " : " + info.getString(1) + "\n");
				}
				
				// create a dialog show doctor work time
				AlertDialog.Builder message = new AlertDialog.Builder(AppointmentActivity.this);
				message.setCancelable(true);
				message.setTitle("Doctor's work schedule");
				message.setMessage(show.toString());
				message.show();		
			}
		});
		
	}		
}
