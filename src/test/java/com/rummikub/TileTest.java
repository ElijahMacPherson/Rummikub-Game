package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
	
import com.rummikub.Tile;

class TileTest {
	
	private static Tile tile1;
	private static Tile tile2;
	private static Tile tile3;
	private static Tile tile4;
	
	@BeforeAll
	static void setUpAll() 
	{
		Logger.info("setUpAll");
		tile1 = new Tile("R", "1");
		tile2 = new Tile("R", "1");
		tile3 = new Tile("B", "1");
		tile4 = new Tile("R", "12");
		
	}
	
	@Test
	void creationTest() {
		Tile tileStandard = new Tile("R", "1");
		assertTrue(Tile.verifyTile("R1"));
		Tile tileFileInput1 = new Tile("R1");
		assertTrue(Tile.verifyTile("r1"));
		Tile tileFileInput2 = new Tile("r1");
		
		assertEquals(tileStandard.toString(), tileFileInput1.toString());
		assertEquals(tileStandard.toString(), tileFileInput2.toString());
		
		try {
			new Tile("1");
		}
		catch (IllegalArgumentException ex) {
			assertEquals("Invalid tile", ex.getMessage());
		}
		
		try {
			new Tile("R");
		}
		catch (IllegalArgumentException ex) {
			assertEquals("Invalid tile", ex.getMessage());
		}
	}
	
	@Test
	void verifyTileTest() {
		assertTrue(Tile.verifyTile("R1"));
		assertFalse(Tile.verifyTile("R"));
		assertFalse(Tile.verifyTile("1"));
	}
	
	@Test
	void comparisonTests() {
		//Compare the different compare methods in the tile class
		//1. Compare same rank (value)
		assertThat(false, is(tile1.isSameRank(tile4)));
		assertThat(true, is(tile1.isSameRank(tile2)));
		//2. Compare same color
		assertThat(true, is(tile1.isSameColour(tile2)));
		assertThat(false, is(tile2.isSameColour(tile3)));
		//3. Compare same tile
		assertThat(false, is(tile1.equals(tile3)));
		assertThat(true, is(tile1.equals(tile2)));
		//4. Compare less than 
		//1R is less then 12R
	    assertThat(-1, is(tile1.compareTo(tile4)));
	    //5. Compare greater than 
	    //12R is greater then 1R
	    assertThat(1, is(tile4.compareTo(tile1)));
	    //6. Compare equal to 
	    //1R is equal to 1B
	    assertThat(0, is(tile1.compareTo(tile2)));
	}
	
	@Test
	void runTest() {
		//Compare if a tile is a run
		//ex: 4R is a run on both 5R and 3R
		Tile tile4R = new Tile("R", "4");
		Tile tile5R = new Tile("R", "5");
		Tile tile3R = new Tile("R", "3");
		Tile tile2R = new Tile("R", "2");
		//4R is a run on both 5R and 3R
		assertThat(true, is(tile4R.isRunOn(tile5R)));
		assertThat(true, is(tile4R.isRunOn(tile3R)));
		//4R is not a run on 2R
		assertThat(false, is(tile4R.isRunOn(tile2R)));
		//2R is not a run on 1B because of different colors
		assertThat(false, is(tile2R.isRunOn(tile3)));
	}
	
	@Test
	void valueTests() {
		//Compare the values of tiles
		assertThat(1, is(tile1.getValue()));
		assertThat(1, is(tile3.getValue()));
		assertThat(12, is(tile4.getValue()));
	}
}
