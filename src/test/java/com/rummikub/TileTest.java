package com.rummikub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
	private static ByteArrayOutputStream outContent; 
	
	@BeforeAll
	static void setUpAll() throws Exception {
		Logger.info("setUpAll");
		tile1 = new Tile(1,"red");
		tile2 = new Tile(1,"red");
		tile3 = new Tile(1,"blue");
		tile4 = new Tile(12,"red");
		outContent= new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	void printTests() {		
		//Ensure that the tile prints are printing the correct value and symbol
		tile1.print(); 
		assertTrue(outContent.toString().equals("1R"));
		tile3.print();
		assertTrue(outContent.toString().equals("1B"));
	}
	
	@Test
	void comparisonTests() {
		//Compare the different compare methods in the tile class
		//1. Compare same rank (value)
		assertThat(false, is(tile1.isSameRank(tile4)));
		assertThat(true, is(tile1.isSameRank(tile2)));
		//2. Compare same color
		assertThat(true, is(tile1.isSameColor(tile2)));
		assertThat(false, is(tile2.isSameColor(tile3)));
		//3. Compare same tile
		assertThat(false, is(tile1.equalTo(tile3)));
		assertThat(true, is(tile1.equalTo(tile2)));
	}
	
	@Test
	void valueTests() {
		//Compare the values of tiles
		assertThat(1, is(tile1.getValue()));
		assertThat(1, is(tile3.getValue()));
		assertThat(12, is(tile4.getValue()));
	}
}
