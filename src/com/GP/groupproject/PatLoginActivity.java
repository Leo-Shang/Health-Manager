package com.GP.groupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PatLoginActivity extends Activity {
	
	String patient_name, doc_name, order, schedule, delete_info;
	int temp, start;
	boolean check_appo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pat_login);
		
		patient_name = getIntent().getStringExtra("patient_name");
		setupTreatHistBtn();
		setupAppointmentBtn();
		setupLogoutBtn();
		setupRateBtn();
		
	}

	private void setupRateBtn() {
		Button rate = (Button) findViewById(R.id.pat_rate);
		rate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent rating = new Intent(PatLoginActivity.this, RateActivity.class);
				rating.putExtra("getName", patient_name);
				startActivity(rating);			
			}
		});
		
	}

	private void setupTreatHistBtn() {
		Button btn = (Button)findViewById(R.id.view_treat_hist);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent view_history = new Intent(PatLoginActivity.this, TreatHistActivity.class);
				view_history.putExtra("getName", patient_name);
				startActivity(view_history);
			}
		});
	}

	private void setupAppointmentBtn() {
		Button btn = (Button)findViewById(R.id.appointment);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				check_appo = false;
				AlertDialog.Builder setAlert = new AlertDialog.Builder(PatLoginActivity.this);
				setAlert.setMessage("Select make appointment or cancel appointment")
					.setNegativeButton("Make", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							// new modified
							Intent make_appo = new Intent(PatLoginActivity.this, AppointmentActivity.class);
							make_appo.putExtra("patient_name", patient_name);
							startActivity(make_appo);
						}
					})
					.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					
						// cancel appointment
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							DatabaseOpns db = new DatabaseOpns(getBaseContext());
							Cursor Find = db.getInfo(db);
							Find.moveToFirst();
							
							do{
								if(Find.getString(0).equals(patient_name) && Find.getString(6) != null){
									
									check_appo = true;
									// delete appointment info in table 2 first
									for(int i = 0; i < Find.getString(6).length(); i++){
										if(Find.getString(6).substring(i, i + 1).equals(",")){
											order = Find.getString(6).substring(0, i);
											doc_name = Find.getString(6).substring(i + 1, Find.getString(6).length());
										}
									}
									
									if(order.equals("appo1")){
										temp = 2;
									}
									
									else if(order.equals("appo2")){
										temp = 3;
									}
									
									else if(order.equals("appo3")){
										temp = 4;
									}
									
									// cancel the appo_info in table2
									db.updateRow(null, doc_name, temp);
																									
									db.updateCood(patient_name, null);
								}
								
								else if(Find.getString(0).equals(patient_name) && Find.getString(6) == null){
									Toast.makeText(getApplicationContext(), "You don't have an appointment to cancel", Toast.LENGTH_SHORT).show();
								}
							}
							while(Find.moveToNext());
							
						if(check_appo == true){
							Find.moveToFirst();
							do{
								if(Find.getString(0).equals(doc_name) && Find.getString(6) != null){
									schedule = Find.getString(6);
								}
								
								else if(Find.getString(0).equals(doc_name) && Find.getString(6) == null){
									schedule = null;     // bug fixed
								}
							}
							while(Find.moveToNext());
							
							// extract the appo_info that is needed to be deleted from doctor work schedule
							start = 0;
							for(int i = 0; i < schedule.length(); i++){
								if(schedule.substring(i, i + 1).equals(";")){
									for(int j = 0; j < schedule.substring(start, i).length(); j++){
										if(schedule.substring(start, i).substring(j, j + 1).equals(":")){
											if(schedule.substring(start, i).substring(0, j).equals(patient_name)){
												delete_info = schedule.substring(0, i + 1);
											}
										}
									}
									start = i + 1;
								}
							}
							
							// cancel the appo_info from doctor work schedule
							db.updateCood(doc_name, schedule.replace(delete_info, ""));
							
							Toast.makeText(getBaseContext(), "Your appointment has been canceled", Toast.LENGTH_SHORT).show();
						}
					}			
					});
				AlertDialog alert = setAlert.create();
				alert.show();
			}
		});
		
	}

	private void setupLogoutBtn() {
		Button btn = (Button)findViewById(R.id.logoutBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PatLoginActivity.this, MainActivity.class));
			}
		});
	}
}
