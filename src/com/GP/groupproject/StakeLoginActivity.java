package com.GP.groupproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StakeLoginActivity extends Activity{
	MedcineDB mydb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stake_login);
		
		setEmpInfoBtn();
		setMedRepoBtn();
		setupLogoutBtn();
		 
		mydb= new MedcineDB(this);

	}

	private void setEmpInfoBtn() {
		Button btn = (Button)findViewById(R.id.emp_info);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StakeLoginActivity.this, EmployeeInfoActivity.class));
			}
		});
	}

	private void setMedRepoBtn() {
		Button btn = (Button)findViewById(R.id.med_repository);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(StakeLoginActivity.this, ViewMedActivity.class));
				Cursor res=mydb.getAllData();
				if(res.getCount()==0){
					
					showMessage("Error","Nothing found");
					return;
				}
				
				StringBuffer buffer= new StringBuffer();
				while(res.moveToNext()){
					buffer.append("Medcine Name:"+res.getString(0)+"\n");
					buffer.append("Quantity:"+res.getString(1)+"\n");
					buffer.append("Price:"+res.getString(2)+"\n");
					buffer.append("Cost:"+res.getString(3)+"\n");
					buffer.append("\n");
					
					
				}
				showMessage("Medicine List",buffer.toString());
			}
		});
	}
	
	private void setupLogoutBtn() {
		Button btn = (Button)findViewById(R.id.logoutBtn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StakeLoginActivity.this, MainActivity.class));
			}
		});
	}
	
	public void showMessage(String title, String Message){
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(Message);
		builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
		
	}
	
}
