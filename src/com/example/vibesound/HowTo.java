package com.example.vibesound;

import com.example.vibsound.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class HowTo extends Activity
{
	private TextView tv;
	private String howto1, howto2, howto3, howto4, howto5, howto6, red, green;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.howto);
		
		tv = (TextView) findViewById(R.id.howto);
		
		// http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
		tv.setMovementMethod(new ScrollingMovementMethod());
		
		// http://stackoverflow.com/questions/7221930/change-text-color-of-one-word-in-a-textview
		howto1 = "You can start a new game by clicking 'new' in the top menu of this game.";
		howto2 = "When you do, you will arrive at the first question.";
		howto3 = "You have to click on 'play sound' to hear the sound and afterwards you need to give an answer to continue to the next question.";
		howto4 = "If your answer is correct, your phone will vibrate and the textcolor will change to ";
		green =	"<font color='#008000'>green.</font>";
		howto5 = "If not, your phone won't vibrate and the textcolor will change to ";
		red = "<font color='#FF0000'>red.</font>";
		howto6 = "After you filled in the entire quiz, you can choose to fill in your name and save it to the hiscores or click 'cancel' and go to the homescreen.";
		
		tv.setText(Html.fromHtml(howto1 + "<br/>" + "<br/>" + howto2 + "<br/>" + "<br/>" + howto3 + "<br/>" + "<br/>" + howto4 + green + "<br/>" + "<br/>" + howto5 + red + "<br/>" + "<br/>" + howto6));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
