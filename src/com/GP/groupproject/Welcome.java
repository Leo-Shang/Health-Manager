package com.GP.groupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Welcome extends Activity{
	
	private static int splash_time_out = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_page);
		
		// set up handler
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				
				Intent home = new Intent(Welcome.this, MainActivity.class);
				startActivity(home);
				finish();
				
			}
			
		}, splash_time_out);
			}
		

}
