package com.GP.groupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	String getName, getSinNum, getPass;
	EditText get_name, get_sinnum, get_pass;
	boolean check, search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		setupLoginBtn();
		
	}

	private void setupLoginBtn() {
		Button btn = (Button)findViewById(R.id.loginBtn);
		
		// new added
		get_name = (EditText) findViewById(R.id.inputName);
		get_sinnum = (EditText) findViewById(R.id.inputSINnumber);
		get_pass = (EditText) findViewById(R.id.inputPw);
		//
		
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CheckBox docCheck = (CheckBox)findViewById(R.id.docCB);
				CheckBox patCheck = (CheckBox)findViewById(R.id.patCB);
				CheckBox stakeCheck = (CheckBox)findViewById(R.id.stakeholderCB);
				
				// get the input String
				getName = get_name.getText().toString();
				getSinNum = get_sinnum.getText().toString();
				getPass = get_pass.getText().toString();
				
				// create cursor searching table1
				DatabaseOpns db = new DatabaseOpns(getBaseContext());
				Cursor Find = db.getInfo(db);
				Find.moveToFirst();
				
				// doctor log in
				if(docCheck.isChecked() == true && patCheck.isChecked() == false && stakeCheck.isChecked() == false){
					
					search = false;
					
					do{
						if(Find.getString(0).equals(getName) && Find.getString(1).equals(getSinNum) && Find.getString(2).equals(getPass) && Find.getString(3).equals("doctor")){
							search = true;
						}
					}
					while(Find.moveToNext());
					
					if(search == true){
					
					Intent doc_login = new Intent(LoginActivity.this, DocLoginActivity.class);
					doc_login.putExtra("doctor_name", getName);
					startActivity(doc_login);
					docCheck.setChecked(false);
					}
					
					else{
						Toast.makeText(getApplicationContext(), "Invalid input, please try again!", Toast.LENGTH_SHORT).show();
					}
				}
				
				// patient log in
				else if(docCheck.isChecked() == false && patCheck.isChecked() == true && stakeCheck.isChecked() == false){
					
					check = false;
					
					Log.i("database operations", getName + " " + getSinNum + " " + getPass);
					// new modified
					do{
						Log.i("database operations", Find.getString(0) + " " + Find.getString(1) + " " + Find.getString(2));
						if(Find.getString(0).equals(getName) && Find.getString(1).equals(getSinNum) && Find.getString(2).equals(getPass) && Find.getString(3).equals("patient")){
							check = true;
						}
					}
					while(Find.moveToNext());
					
					Log.i("database operations", String.valueOf(check));
					
					if(check == true){
					Intent patient_login = new Intent(LoginActivity.this, PatLoginActivity.class);
					patient_login.putExtra("patient_name", getName);
					startActivity(patient_login);
					patCheck.setChecked(false);				
					}
					
					else{
						Toast.makeText(getApplicationContext(), "Invalid input, please try again!", Toast.LENGTH_SHORT).show();
					}
				}
				
				// stakeholder log in
				else if(docCheck.isChecked() == false && patCheck.isChecked() == false && stakeCheck.isChecked() == true){
					check = false;
					
					Log.i("database operations", getName + " " + getSinNum + " " + getPass);
					// new modified
					do{
						Log.i("database operations", Find.getString(0) + " " + Find.getString(1) + " " + Find.getString(2));
						if(Find.getString(0).equals(getName) && Find.getString(1).equals(getSinNum) && Find.getString(2).equals(getPass) && Find.getString(3).equals("stakeholder")){
							check = true;
						}
					}
					while(Find.moveToNext());
					
					Log.i("database operations", String.valueOf(check));
					
					if(check == true){
					Intent patient_login = new Intent(LoginActivity.this, StakeLoginActivity.class);
					startActivity(patient_login);
					patCheck.setChecked(false);				
					}
					
					else{
						Toast.makeText(getApplicationContext(), "Invalid input, please try again!", Toast.LENGTH_SHORT).show();
					}
				}
				
				else{
					Toast.makeText(getApplicationContext(), "Please select one specific user type", Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}

}
