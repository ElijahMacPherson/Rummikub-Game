package com.rummikub;

public class Tile implements Comparable<Tile> {
	
	/**The rank and colour of the tile */
	private Ranks rank;
	private Colours colour;
	
	//Constructor
	public Tile(Colours colour, Ranks rank) {
		this.rank = rank;
		this.colour = colour;
	};
	
	public Tile(String colourSymbol, String rankSymbol) {
		//Get the rank and colour associated with the symbols and call the constructor 
		this(Colours.getColourFromSymbol(colourSymbol), Ranks.getRankFromSymbol(rankSymbol));
	}
	
	@Override
	public String toString() { 
		return this.colour.getSymbol() + this.rank.getSymbol();
	}
	
	public boolean isSameRank(Tile tile) {
		return this.rank == tile.rank;
	}

	public boolean isSameColour(Tile tile) {
		return this.colour == tile.colour;
	}
	
	public boolean equals(Tile tile) {
		return isSameRank(tile) && isSameColour(tile);
	}
	
	//Getters and Setters
	public Colours getColour() {
		return this.colour;
	}
	
	public Ranks getRank() {
		return this.rank;
	}

	public int getValue() {
		return this.rank.getValue();
	}
	
	@Override
	public int compareTo(Tile tile) {
		//if colours don't match just return -1
		int compareValue;
		if ((compareValue = tile.colour.compareTo(this.colour)) != 0) {
			return compareValue;	
		}
		
		return Integer.compare(this.getValue(), tile.getValue());
	}

	public boolean isRunOn(Tile tile) {
		//If the colours are different return false
		if (this.colour != tile.colour) {
			return false;
		}
		//Otherwise return true if the values are either +1 or -1
		return (this.getValue() == (tile.getValue() - 1) || this.getValue() == (tile.getValue() + 1));
	}
}