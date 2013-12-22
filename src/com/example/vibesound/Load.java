package com.example.vibesound;

import com.example.vibsound.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Load extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		logoListener();
		
		if (getIntent().getBooleanExtra("EXIT", false)) 
		{
		    finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void logoListener()
	{
		final Context context = this;		 
		ImageView iv = (ImageView) findViewById(R.id.logoimg); 
		iv.setOnClickListener(new OnClickListener() 
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
