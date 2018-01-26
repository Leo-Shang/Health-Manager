package com.GP.groupproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	EditText NAME, SIN_NUM, PASSWORD, AGE;
	String name, sin_num, password, age, type, doc_sin, doc_pass, doc_age;
	Context context = this;
	MedcineDB mydb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		NAME = (EditText)findViewById(R.id.inputName);
		SIN_NUM = (EditText)findViewById(R.id.inputSINnumber);
		PASSWORD = (EditText)findViewById(R.id.inputPw);
		AGE = (EditText)findViewById(R.id.inputAge);
		
		setupCreateUserBtn();
	}

	private void setupCreateUserBtn() {
		Button btn = (Button)findViewById(R.id.create);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox docCheck = (CheckBox)findViewById(R.id.docCB);
				CheckBox patCheck = (CheckBox)findViewById(R.id.patCB);
				CheckBox stakeCheck = (CheckBox)findViewById(R.id.stakeholderCB);
				name = NAME.getText().toString();
				sin_num = SIN_NUM.getText().toString();
				password = PASSWORD.getText().toString();
				age = AGE.getText().toString();
				if(docCheck.isChecked() == true && patCheck.isChecked() == false && stakeCheck.isChecked() == false){
					
					// set up doctor info
					doc_sin = "12345";
					doc_pass = "12345";
					doc_age = "20";
					type = "doctor";
					
					// insert doctor info into table1
					insertData("Leo", doc_sin, doc_pass, type, doc_age, null, null, 0, 0);
					insertData("James", doc_sin, doc_pass, type, doc_age, null, null, 0, 0);
					insertData("Zack", doc_sin, doc_pass, type, doc_age, null, null, 0, 0);
					insertData("Mick", doc_sin, doc_pass, type, doc_age, null, null, 0, 0);
										
					// new added
					// set up doctor work time
						DatabaseOpns db = new DatabaseOpns(getBaseContext());
						db.setAppointmentInfo(db, "Leo", "10-14", null, null, null);
						db.setAppointmentInfo(db, "James", "12-15", null, null, null);
						db.setAppointmentInfo(db, "Zack", "14-18", null, null, null);
						db.setAppointmentInfo(db, "Mick", "17-21", null, null, null);		
					
					// insert medicine
						mydb= new MedcineDB(getBaseContext());
						
						mydb.insertData( "Amoxicillin", "3", "15", "5");
						mydb.insertData( "Aspirin", "8", "10", "4");
						mydb.insertData( "Herb", "3", "11", "7");
						
					Toast.makeText(getApplicationContext(), "Signup Succeed", Toast.LENGTH_LONG).show();
					startActivity(new Intent(SignupActivity.this, MainActivity.class));
				}
				else if(docCheck.isChecked() == false && patCheck.isChecked() == true && stakeCheck.isChecked() == false){
					type = "patient";
					insertData(name, sin_num, password, type, age, null, null, 0, 0);
					Toast.makeText(getApplicationContext(), "Signup Succeed", Toast.LENGTH_LONG).show();
					startActivity(new Intent(SignupActivity.this, MainActivity.class));
				}
				else if(docCheck.isChecked() == false && patCheck.isChecked() == false && stakeCheck.isChecked() == true){
					type = "stakeholder";
					insertData(name, sin_num, password, type, age, null, null, 0, 0);
					Toast.makeText(getApplicationContext(), "Signup Succeed", Toast.LENGTH_LONG).show();
					startActivity(new Intent(SignupActivity.this, MainActivity.class));
				}
				else{
					Toast.makeText(getApplicationContext(), "Please select one specific user type", Toast.LENGTH_LONG).show();
				}				
			}

			private void insertData(String name, String sin_num, String password, String type, String age,
					String disc, String cood, float rate, int pat_num) {
				DatabaseOpns ops = new DatabaseOpns(context);
				ops.setInfo(ops, name, sin_num, password, type, age, disc, cood, rate, pat_num);
			}
		});
	}

}
