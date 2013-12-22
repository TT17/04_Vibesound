package com.example.vibesound;

import com.example.vibsound.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Exit extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit);
		exitYesListener();
		exitNoListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void exitYesListener()
	{	 		
		Button yes = (Button) findViewById(R.id.yes); 
		yes.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// http://stackoverflow.com/questions/14001963/finish-all-activities-at-a-time
				Intent intent = new Intent(getApplicationContext(), Load.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("EXIT", true);
				startActivity(intent);
			}
		});		
	}
	
	public void exitNoListener()
	{
		final Context context = this;
		Button no = (Button) findViewById(R.id.no); 
		no.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				 Intent intent = new Intent(context, Start.class);
	             startActivity(intent);  
			}
		});
	}
}
