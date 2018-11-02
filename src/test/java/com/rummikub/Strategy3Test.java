package com.rummikub;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pmw.tinylog.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class Strategy3Test {
	
	private static List<Meld> meld1,meld2;
	private static Player player1;
	private static Player player2;
	private static Player player3;

	@BeforeAll
	static void setUpBeforeClass() throws Exception 
	{
		//Table because strategy 3 needs to observe
		Stock stock = new Stock();
		Table table = new Table(stock);
		
		//player1 has tiles greater than 30 and can play them.
		player1 = new Player("Naz",new Strategy3());
		player2 = new Player("Prady",new Strategy3());

		//player 1 has two nice runs should play them all.
		player1.getPlayerRack().addTile(new Tile("G", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player1.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player1.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player1.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		
		//player2 does not have tiles greater than 30
		player2.getPlayerRack().addTile(new Tile("R", "9")); //G4
		player2.getPlayerRack().addTile(new Tile("G", "2")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "3")); //G6
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("R", "4")); //G4
		player2.getPlayerRack().addTile(new Tile("B", "4")); //G5
		player2.getPlayerRack().addTile(new Tile("G", "4")); //G6
		player2.getPlayerRack().addTile(new Tile("O", "4")); //G6
		
		//player 3 
		player3.getPlayerRack().addTile(new Tile("G", "10")); //G5
		player3.getPlayerRack().addTile(new Tile("G", "11")); //G6
		player3.getPlayerRack().addTile(new Tile("G", "12")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "9"));  //G4
		player3.getPlayerRack().addTile(new Tile("R", "10")); //G5
		player3.getPlayerRack().addTile(new Tile("R", "11")); //G6
		player3.getPlayerRack().addTile(new Tile("R", "12")); //G6
		
		table.addPlayers(player1,player2,player3);
		table.notifyObservers();
		
		//meld
		meld1 = player1.getPlayerRack().getMelds();
		meld2 = player2.getPlayerRack().getMelds();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		player1 = null;
		meld1 = null;
		meld2 = null;
	}

	@BeforeEach
	void setUp() throws Exception 
	{
		
	}

	@AfterEach
	void tearDown() throws Exception 
	{
		
	}
	
	@Test
	void useStrategy_removeTiles_Test() throws IOException
	{
		//Test initial values
		assertEquals(7,player1.getPlayerRack().getSize());
		assertEquals(8,player2.getPlayerRack().getSize());
		
		//player1 tests
		assertEquals("[G10 G11 G12 , R9 R10 R11 R12 ]", player1.play().toString());
		assertEquals(0,player1.getPlayerRack().getSize());
		
		//player2 tests
		assertEquals(Collections.emptyList() ,player2.play());
		assertEquals(8,player2.getPlayerRack().getSize());
		
		//player3 tests (this tests has 3 fewer tiles).
		player3.
		
	}
}
