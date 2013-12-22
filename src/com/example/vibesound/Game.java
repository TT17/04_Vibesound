package com.example.vibesound;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.vibsound.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity 
{
	private MediaPlayer player;
	private Button sound, o1, o2, o3, o4;;
	private ImageButton previous, next, playpauze; 
	private String question, option1, option2, option3, option4, answer, naam;
	private TextView a, cdt;
	private int vraag = 1;
	private int score = 0;
	private int teller = -1;
	private boolean answered = false;
	private int wit, groen, rood, zwart, grijs, tijdOOC = 0;
	private AlertDialog.Builder alert;
	private Vibrator vibe;
	private CountDownTimer countdown;
	private String tijd = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		startListener();
		hiscoresListener();
		stopListener();
		
		vibe = (Vibrator) Game.this.getSystemService(Context.VIBRATOR_SERVICE);
		
		sound = (Button) findViewById(R.id.sound); 
		//previous = (ImageButton) findViewById(R.id.vorige);
		next = (ImageButton) findViewById(R.id.volgende);
		o1 = (Button) findViewById(R.id.option1);
		o2 = (Button) findViewById(R.id.option2);
		o3 = (Button) findViewById(R.id.option3);
		o4 = (Button) findViewById(R.id.option4);
		a = (TextView) findViewById(R.id.answer);
		cdt = (TextView) findViewById(R.id.countdowntimer);
		playpauze = (ImageButton) findViewById(R.id.playpauze);
		
		wit = Color.rgb(255, 255, 255);
		groen = Color.rgb(0, 128, 0);
		rood = Color.rgb(255, 0, 0);
		zwart = Color.rgb(0, 0, 0);
		grijs = Color.rgb(61, 61, 61);
		
		//previous.setVisibility(View.INVISIBLE);
		
		try 
		{
			setQuestion();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		countDownTimer();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("tekst", (String) cdt.getText());
		//savedInstanceState.putInt("time", tijdOOC);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		super.onRestoreInstanceState(savedInstanceState);
		cdt.setText(savedInstanceState.getString("tekst"));
		System.out.println((String) cdt.getText());
		//tijdOOC = savedInstanceState.getInt("time");
	}*/
	
	public void countDownTimer()
	{
		// http://developer.android.com/reference/android/os/CountDownTimer.html
		o1.setClickable(true);
    	o2.setClickable(true);
    	o3.setClickable(true);
    	o4.setClickable(true);
		countdown = new CountDownTimer(30000, 1000) 
		{
		     public void onTick(long millisUntilFinished) 
		     {
		         cdt.setText("Seconds remaining: " + millisUntilFinished / 1000);
		         cdt.setTextSize(21);
		     }
		     public void onFinish() 
		     {
		    	 cdt.setText("Time up, go to next question !");
		    	 cdt.setTextSize(17);
		    	 countdown.cancel();
		 		 o1.setClickable(false);
		    	 o2.setClickable(false);
		    	 o3.setClickable(false);
		    	 o4.setClickable(false);
		     }	     
		};
		countdown.start();
	}
	
	public void startListener()
	{
		final Context context = this;		 
		ImageButton ib = (ImageButton) findViewById(R.id.home); 
		ib.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				finish();
			    Intent intent = new Intent(context, Start.class);
                startActivity(intent);  
			}
		});
	}
	
	public void hiscoresListener()
	{
		final Context context = this;		 
		ImageButton ib = (ImageButton) findViewById(R.id.hiscores); 
		ib.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    Intent intent = new Intent(context, HiScores.class);
                startActivity(intent);  
			}
		});
	}
	
	public void stopListener()
	{
		final Context context = this;		 
		ImageButton ib = (ImageButton) findViewById(R.id.stop); 
		ib.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
			    showPopup();
			}
		});
	}
	
	public void setQuestion() throws Exception
	{ 	
		InputStream is = getResources().openRawResource(R.raw.questions);
		Scanner read = new Scanner(is);
		readText("questions.txt");
		read.useDelimiter(";");
		//Log.v("vibesound", read.next());

		if(read.hasNext())
		{
		   for(int i=0; i<vraag-1; i++)
		   {
			   read.nextLine();
		   }
		   question = read.next();
		   option1 = read.next();
		   option2 = read.next();
		   option3 = read.next();
		   option4 = read.next();
		   answer = read.next();
		   
		   TextView q = (TextView) findViewById(R.id.question);
		   q.setText(question);
			
		   TextView o1 = (TextView) findViewById(R.id.option1);
		   o1.setText(option1);
			
		   TextView o2 = (TextView) findViewById(R.id.option2);
		   o2.setText(option2);
			
		   TextView o3 = (TextView) findViewById(R.id.option3);
		   o3.setText(option3);
			
		   TextView o4 = (TextView) findViewById(R.id.option4);
		   o4.setText(option4);
		}   
		read.close();		 
	}
	
	public void playSound(String sound) throws Exception
	{
		AssetFileDescriptor afd = getAssets().openFd(sound);
		player = new MediaPlayer();
		//player.setVolume(1.0f, 1.0f);
		player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
		player.prepare();
		player.start();
	}
	
	public void readText(String text) throws Exception
	{
		InputStream is = getResources().openRawResource(R.raw.questions);
	}
			 
	public void sound(View view) throws Exception
	{
		if(view.equals(sound))
		{
			if(vraag == 1)
			{
				playSound("bird.mp3");
				Log.v("sound", "bird.mp3");
			}
			else if(vraag == 2)
			{
				playSound("twotimes.mp3");
				Log.v("sound", "twotimes.mp3");
			}
			else if(vraag == 3)
			{
				playSound("intel.wav");
				Log.v("sound", "intel.wav");
			}
			else if(vraag == 4)
			{
				playSound("headswillroll.wav");
				Log.v("sound", "headswillroll.wav");
			}
			else if(vraag == 5)
			{
				playSound("volkslied.wav");
				Log.v("sound", "volkslied.wav");
			}
		}
	}
	
	public void disableEnable()
	{
		if(answered)
		{
			o1.setClickable(false);
			o2.setClickable(false);
			o3.setClickable(false);
			o4.setClickable(false);
		}
		else
		{
			o1.setClickable(true);
			o1.setTextColor(wit);
			o2.setClickable(true);
			o2.setTextColor(wit);
			o3.setClickable(true);
			o3.setTextColor(wit);
			o4.setClickable(true);
			o4.setTextColor(wit);
		}
	}
	
	/*public void options(View view)
	{
		TextView[] options = new TextView[4];
		options[0] = (TextView) findViewById(R.id.option1);
		options[1] = (TextView) findViewById(R.id.option2);
		options[2] = (TextView) findViewById(R.id.option3);
		options[3] = (TextView) findViewById(R.id.option4);
		
		String[] gelezen = {option1, option2, option3, option4};
		
		for(int i=0; i<2; i++)
		{
			if(view.equals(options[i]))
			{
				answered = true;
				disableEnable();
				Log.v("vibesound", options[i].getText().toString());
				Log.v("vibesound", answer);
				for(int j=0; j<gelezen.length; i++)
				{
					if(gelezen[j].trim().equals(answer.trim()))
					{
						vibe.vibrate(80);
						options[i].setTextColor(groen);
						score++;
						a.setText("Congrats, your score : " + score + " !");
					}
					else
					{
						options[i].setTextColor(rood);
						a.setText("Onfortunately, the answer was : " + answer);
					}
				}	
			}
		}
	}*/
	
	public void binnenTijd()
	{
		countdown.cancel();
		playpauze.setClickable(false);
		cdt.setText("Answered within the time limit !");
		cdt.setTextSize(17);
	}
	
	public void zetZichtbaar()
	{
		o1.setTextColor(wit);
		o1.setBackgroundColor(grijs);
		
		o2.setTextColor(wit);
		o2.setBackgroundColor(grijs);

		o3.setTextColor(wit);
		o3.setBackgroundColor(grijs);
		
		o4.setTextColor(wit);
		o4.setBackgroundColor(grijs);
	}
	
	public void zetOnzichtbaar()
	{
		o1.setTextColor(zwart);
		o1.setBackgroundColor(zwart);
		
		o2.setTextColor(zwart);
		o2.setBackgroundColor(zwart);

		o3.setTextColor(zwart);
		o3.setBackgroundColor(zwart);
		
		o4.setTextColor(zwart);
		o4.setBackgroundColor(zwart);
	}
	
	public void playpauze(View view)
	{
		if(view.equals(playpauze))
		{
			teller++;
			if(teller%2 == 1)
			{
				playpauze.setClickable(true);
				sound.setClickable(true);
				o1.setClickable(true);
		    	o2.setClickable(true);
		    	o3.setClickable(true);
		    	o4.setClickable(true);
		    	zetZichtbaar();
				int beginTijd = Integer.parseInt(tijd);
				System.out.println(beginTijd);
				countdown = null;
				countdown = new CountDownTimer(beginTijd*1000, 1000) 
				{
				     public void onTick(long millisUntilFinished) 
				     {
				         cdt.setText("Seconds remaining: " + millisUntilFinished / 1000);
				         cdt.setTextSize(21);
				     }
				     public void onFinish() 
				     {
				    	 cdt.setText("Time up, go to next question !");
				    	 cdt.setTextSize(17);
				    	 countdown.cancel();
				    	 playpauze.setClickable(false);
				    	 sound.setClickable(false);
				 		 o1.setClickable(false);
				    	 o2.setClickable(false);
				    	 o3.setClickable(false);
				    	 o4.setClickable(false);
				     }	     
				};
				countdown.start();
			}
			if(teller%2 == 0)
			{
				countdown.cancel();
				String tekst = (String) cdt.getText();
				tijd = tekst.substring(19).trim();	
				sound.setClickable(false);
				o1.setClickable(false);
		    	o2.setClickable(false);
		    	o3.setClickable(false);
		    	o4.setClickable(false);
		    	zetOnzichtbaar();
				System.out.println(tijd);
			}
		}
	}
	
	public void option1(View view)
	{
		if(view.equals(o1))
		{
			answered = true;
			disableEnable();
			binnenTijd();
			Log.v("vibesound", o1.getText().toString());
			Log.v("vibesound", answer);
			if(option1.trim().equals(answer.trim()))
			{
				vibe.vibrate(80);
				o1.setTextColor(groen);
				score++;
				a.setText("Congrats, your score : " + score + " !");
			}
			else
			{
				o1.setTextColor(rood);
				a.setText("Onfortunately, the answer was : " + answer);
			}
		}
	}
	
	public void option2(View view)
	{
		if(view.equals(o2))
		{
			answered = true;
			disableEnable();
			binnenTijd();
			Log.v("vibesound", o2.getText().toString());
			Log.v("vibesound", answer);
			if(option2.trim().equals(answer.trim()))
			{
				vibe.vibrate(80);
				o2.setTextColor(groen);
				score++;
				a.setText("Congrats, your score : " + score + " !");
			}
			else
			{
				o2.setTextColor(rood);
				a.setText("Onfortunately, the answer was : " + answer);
			}
		}
	}
	
	public void option3(View view)
	{
		if(view.equals(o3))
		{
			answered = true;
			disableEnable();
			binnenTijd();
			Log.v("vibesound", o3.getText().toString());
			Log.v("vibesound", answer);
			if(option3.trim().equals(answer.trim()))
			{
				vibe.vibrate(80);
				o3.setTextColor(groen);
				score++;
				a.setText("Congrats, your score : " + score + " !");
			}
			else
			{
				o3.setTextColor(rood);
				a.setText("Onfortunately, the answer was : " + answer);
			}
		}
	}
	
	public void option4(View view)
	{
		if(view.equals(o4))
		{
			answered = true;
			disableEnable();
			binnenTijd();
			Log.v("vibesound", o1.getText().toString());
			Log.v("vibesound", answer);
			if(option4.trim().equals(answer.trim()))
			{
				vibe.vibrate(80);
				o4.setTextColor(groen);
				score++;
				a.setText("Congrats, your score : " + score + " !");
			}
			else
			{
				o4.setTextColor(rood);
				a.setText("Onfortunately, the answer was : " + answer);
			}
		}
	}
	
	public void showPopup()
	{
		// http://stackoverflow.com/questions/16191562/display-textview-in-alert-dialog
		alert = new AlertDialog.Builder(this);
		alert.setTitle("Vibesound");
		alert.setMessage("Save score");
		
		// http://stackoverflow.com/questions/14834685/android-alertdialog-setview-rules
		LayoutInflater inflater = getLayoutInflater();
		final View inflatedV = inflater.inflate(R.layout.popupname, null);
		alert.setView(inflatedV);

		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				String check = "";
				Log.v("vibesound", "when clicked save!");
				//setContentView(R.layout.popupname);
			    EditText persoon = (EditText) inflatedV.findViewById(R.id.persoonnaam);
			    naam = persoon.getText().toString();
			    Log.v("vibesound", "before writing!");
			    Log.v("vibesound", naam);
				try 
				{
					schrijfWeg(naam, score);
				} 
				catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			    
			        			   
				//alert.setMessage("Saved !");
		        final Context context = context();
				Intent intent = new Intent(context, HiScores.class);
				//intent.putExtra("naam", naam);
				//intent.putExtra("score", score);
		        startActivity(intent);
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				// Canceled.
				final Context context = context();
				Intent intent = new Intent(context, Start.class);
                startActivity(intent);  
			}
		});
		alert.show();
	}
	
	public Context context()
	{
		final Context context = this;
		return context;
	}
	
	public void schrijfWeg(String naam, int score) throws FileNotFoundException
	{				
		Log.v("vibesound", naam +";"+ score);
		try 
		{		
			// http://stackoverflow.com/questions/17858975/read-only-file-system	
			String FileName = "scores.txt";
			String content =  naam + ";" + score + ";";
			
			FileOutputStream fos = openFileOutput(FileName, Context.MODE_APPEND);
			fos.write(content.getBytes());
			fos.close(); 
			
			Log.v("vibesound", "writing done!");
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*public void previous(View view) throws Exception
	{
		if(view.equals(previous))
		{
			vraag--;
			setQuestion();	
			a.setText("You already answered this question.");
			if(vraag == 1)
			{
				previous.setVisibility(View.INVISIBLE);
				next.setVisibility(View.VISIBLE);
			}
			else
			{
				previous.setVisibility(View.VISIBLE);
				next.setVisibility(View.VISIBLE);
			}
		}
	}*/
	
	public void next(View view) throws Exception
	{
		if(view.equals(next) && answered || cdt.getText().equals("Time up, go to next question !"))
		{
			vraag++;
			if(vraag != 6)
			{
				setQuestion();
				a.setText("A new sound, a new opportunity.");
				answered = false;
				disableEnable();
				countDownTimer();
				sound.setClickable(true);
				playpauze.setClickable(true);
			}
			else if(view.equals(next) && vraag == 6)
			{
				showPopup();
			}			
			/*if(vraag == 5)
			{
				next.setVisibility(View.INVISIBLE);
				previous.setVisibility(View.VISIBLE);
			}
			else
			{
				previous.setVisibility(View.VISIBLE);
				next.setVisibility(View.VISIBLE);
			}*/
		}		
		else
		{
			a.setText("You have to answer first before you can continue.");
		}
	}
}