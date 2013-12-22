package com.example.vibesound;

public class Player
{
	private String naam;
	private int score;
	
	public Player()
	{
		
	}
	
	public Player(String naam, int score)
	{
		setNaam(naam);
		setScore(score);
	}
	
	public void setNaam(String naam)
	{
		this.naam = naam;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public String getNaam()
	{
		return naam;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String toString()
	{
		String output = "";
		output = getNaam() + "=" + getScore();
		return output;
	}

	public int compareTo(Player p1) 
	{
		return p1.score - this.score;
	}
}
