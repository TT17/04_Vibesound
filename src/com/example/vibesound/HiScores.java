package com.example.vibesound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.example.vibsound.R;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HiScores extends Activity 
{		
	private ArrayList<String> naamArray = new ArrayList<String>();
	private ArrayList<String> scoreArray = new ArrayList<String>();
	
	private ArrayList<String> naamArray1 = new ArrayList<String>();
	private ArrayList<String> scoreArray1 = new ArrayList<String>();

	private String naam;
	private int score;
	
	private List<Player> players = new ArrayList<Player>();
	private Player p = new Player(naam, score);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hiscores);
		
		//Intent intent = getIntent();
		//Bundle bundle = intent.getExtras();
		//naam = bundle.getString("naam");
		//score = bundle.getInt("score"); 
		
		try 
		{
			loadHiscores();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void loadHiscores() throws IOException
	{
		Log.v("vibesound", "loading hiscores");		
		
		// http://stackoverflow.com/questions/17894960/writing-internal-file-content-into-listview-in-android
		StringBuilder sb = new StringBuilder();
		FileInputStream fis = openFileInput("scores.txt");
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		  	String line = null;
		    while ((line = reader.readLine()) != null) 
		    {
		    	sb.append(line).append("");
		    }
		    
		    fis.close();
		} 
		catch(OutOfMemoryError om)
		{
			om.printStackTrace();
		} 
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}

		String result = sb.toString();
		Log.v("vibesound", result);
		String[] parts = result.split(";");
		for(int i=0; i<parts.length; i++)
		{
			Log.v("partslength", Integer.toString(parts.length));
			Log.v("partsarray", parts[i]);
			if(i%2 == 0)
			{
				naamArray.add(parts[i]);				
			}
			else if(i%2 == 1)
			{
				scoreArray.add(parts[i]);
			}
		}
		
		for(int i=0; i<parts.length/2; i++)
		{
			//p = new Player(naamArray.get(i), Integer.parseInt(scoreArray.get(i)));
			//p.setNaam(naamArray.get(i));
			//p.setScore(Integer.parseInt(scoreArray.get(i)));
			players.add(new Player(naamArray.get(i), Integer.parseInt(scoreArray.get(i))));
		}
			
		System.out.println(players);
		Collections.sort(players, Collections.reverseOrder(new Comparator<Player>() 
		{
		    @Override
		    public int compare(Player p1, Player p2) 
		    {
	            return p2.compareTo(p1);            
	        }
		}));
		
		for(int i=0; i<players.size(); i++)
		{
			naamArray1.add(players.get(i).getNaam());
			scoreArray1.add(Integer.toString(players.get(i).getScore()));
		}
		
		
		/*Map<String,String> test1 = new TreeMap<String, String>();
		
		for(int i=0; i<parts.length/2; i++)
		{
			test1.put(naamArray.get(i), scoreArray.get(i));
		}
		
		SortedSet<Map.Entry<String, String>> sortedset = new TreeSet<Map.Entry<String, String>>(new Comparator<Map.Entry<String, String>>() 
		{
		    @Override
		    public int compare(Map.Entry<String, String> e1,Map.Entry<String, String> e2) 
		    {
		    	int compare = -e1.getValue().compareTo(e2.getValue());
		    	return compare;
		    }
	    });

		sortedset.addAll(test1.entrySet());	
		System.out.println(sortedset);*/
		
		//Collections.sort(scoreArray);
		//Collections.reverse(scoreArray);		
		
		/*for(int i=0; i<naamArray.size(); i++)
		{
			if(naamArray.contains(naam))
			{
				Log.v("naam", naam);
				Log.v("sorting", "eerste if");
				int locatie = naamArray.indexOf(naam);
				Log.v("sorting1", Integer.toString(score));
				Log.v("sorting2", scoreArray.get(locatie).trim());
				if(score > Integer.parseInt(scoreArray.get(locatie).trim()))
				{
					naamArray.set(locatie, naam);
					scoreArray.set(locatie, Integer.toString(score));
					Log.v("sorting", "tweede if");
				}
				if(naamArray.contains(naam) && naamArray.indexOf(naam) != locatie)
				{
					naamArray.remove(naam);
				}
			}
		}*/
		
		// http://www.vogella.com/articles/AndroidListView/article.html
		ListView listview1 = (ListView) findViewById(R.id.nameTabel);
		StableArrayAdapter adapter1 = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, naamArray1);
		listview1.setAdapter(adapter1);
	    adapter1.notifyDataSetChanged();
	    
	    ListView listview2 = (ListView) findViewById(R.id.scoreTabel);
	    StableArrayAdapter adapter2 = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, scoreArray1);
	    listview2.setAdapter(adapter2);
	    adapter2.notifyDataSetChanged();

		Log.v("vibesound", "hiscores loaded");
	}
	
	private static String removeLastChar(String str) 
	{
        return str.substring(0,str.length()-1);
    }
	
	// http://www.vogella.com/articles/AndroidListView/article.html
	private class StableArrayAdapter extends ArrayAdapter<String> 
	{
		 HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		 public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) 
		 {
			 super(context, textViewResourceId, objects);
		     for(int i=0; i<objects.size(); i++) 
		     {
		    	 mIdMap.put(objects.get(i), i);
		     }
		 }
		 
		 // http://stackoverflow.com/questions/9460407/change-text-color-for-listview-items
		 @Override
		 public View getView(int position, View convertView, ViewGroup parent) 
		 {
			 View v = super.getView(position, convertView, parent);
			 ((TextView) v).setTextColor(Color.WHITE) ;
			 ((TextView) v).setGravity(Gravity.CENTER);
			 ((TextView) v).setTextSize(20);
		     return v;
		 }
	}
}
