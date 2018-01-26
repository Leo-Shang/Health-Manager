package com.GP.groupproject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class EmployeeInfoActivity extends Activity {
	Context context = this;
	String docInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employee_info);

		listDocInfo();
	}

	private void listDocInfo() {
		DatabaseOpns ops = new DatabaseOpns(context);
		Cursor cur = ops.getInfo(ops);
		cur.moveToFirst();
		boolean firstLine = true;
		do{
			if(cur.getString(3).equals("doctor")&& firstLine == true){
				docInfo = "Name: " + cur.getString(0) + "\t\t\t" + "SIN Number: " + cur.getString(1) + "\t\t\t" + "Age: " + cur.getString(4) + "\t\t\t" + "Rate: " + cur.getString(7) + "\n";
				firstLine = false;
				cur.moveToNext();
			}
			if(cur.getString(3).equals("doctor")){
				docInfo = docInfo + "Name: " + cur.getString(0) + "\t\t\t" + "SIN Number: " + cur.getString(1) + "\t\t\t" + "Age: " + cur.getString(4) + "\t\t\t" + "Rate: " + cur.getString(7) + "\n";
			}
		}
		while(cur.moveToNext());
		TextView view = (TextView)findViewById(R.id.docInfoList);
		view.setText(docInfo);
	}

}
