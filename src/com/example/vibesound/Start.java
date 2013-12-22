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
import android.widget.ImageView;
import android.widget.TextView;

public class Start extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		gameListener();
		howtoListener();
		hiscoresListener();
		exitListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void gameListener()
	{
		final Context context = this;		 
		Button b = (Button) findViewById(R.id.newgame); 
		b.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    Intent intent = new Intent(context, Game.class);
                startActivity(intent);  
			}
		});
	}
	
	public void howtoListener()
	{
		final Context context = this;		 
		Button b = (Button) findViewById(R.id.howto); 
		b.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    Intent intent = new Intent(context, HowTo.class);
                startActivity(intent);  
			}
		});
	}
	
	public void hiscoresListener()
	{
		final Context context = this;		 
		Button b = (Button) findViewById(R.id.hiscores); 
		b.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    Intent intent = new Intent(context, HiScores.class);
                startActivity(intent);  
			}
		});
	}
	
	public void exitListener()
	{
		final Context context = this;		 
		Button b = (Button) findViewById(R.id.exit); 
		b.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    Intent intent = new Intent(context, Exit.class);
                startActivity(intent);  
			}
		});
	}
}
