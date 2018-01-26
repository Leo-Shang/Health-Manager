package com.GP.groupproject;



import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;

import android.widget.TextView;

public class MedcineList extends Activity {
	MedcineDB mydb;
	private TextView myText = null;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mydb= new MedcineDB(this);
		Cursor res = mydb.getAllData();
		res.moveToFirst();
		LinearLayout lView = new LinearLayout(this);
		
		

	     myText = new TextView(this);
	     String display= "";
	     
	     do{
	    	 display=display+"Medcine Name:"+res.getString(0).toString()+"\n";
	    	 display=display+"Remain:"+res.getString(1).toString()+"\n";
	    	
	     }
	     while(res.moveToNext());
	     myText.setText(display);
	     myText.setTextSize(30);
	     lView.addView(myText);

	     setContentView(lView);

	}
}