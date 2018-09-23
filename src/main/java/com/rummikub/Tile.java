package com.rummikub;

public class Tile 
{
	//Variable
	
	private int number;
	private String colour;
	
	//Constructor
	
	public Tile() {};
	
	public Tile(int n, String c) 
	{
		this.number = n;
		this.colour = c;
	}
	
	//Method
	
	public void print()
	{
		System.out.println("The tile number is: " + this.number + " and colour is: " + this.colour + ".");
	}
	
	

	// Getter and Setter
	
	public String getColour() {
		return colour;
	}
	
	public void setColour(String colour) {
		this.colour = colour;
	}	
}
