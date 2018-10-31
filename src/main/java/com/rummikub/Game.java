package com.rummikub;

import java.io.IOException;
import java.util.ArrayList;

import org.pmw.tinylog.Logger;

public class Game
{
	//Primitive Variables
	boolean gameRunning = true;
	String pName = "";
	
	//Data Structure Variables
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Meld> meldsPlayed = new ArrayList<>();
	
	//My data Variables
	Print printer = new Print(); // Some methods are static and other are not in this class.
	Prompt prompter = new Prompt(); // Some methods are static and other are not in this class.
	Stock stock = new Stock();
	Table table = new Table(stock);
	
	
	public void start() throws IOException
	{
		//Start game
		printer.printIntroduction();
		prompter.promptEnterKey();
		pName = Prompt.promptInput("Enter your name: ");
		Logger.debug(pName);
		
		players.add(new Player(pName,new Strategy0()));
		players.add(new Player("Computer 1",new Strategy1()));
		players.add(new Player("Computer 2",new Strategy2()));
		players.add(new Player("Computer 3",new Strategy3()));
		
		for (Player player: players) 
		{
			table.addPlayers(player);
	    }
		
		table.initPlayersTurn();
		
		do 
		{
			Player currentPlayer = table.getNextPlayerTurn();
			Logger.info(currentPlayer.getName());
			Logger.info(currentPlayer.isHuman());//log to file
			meldsPlayed = currentPlayer.play();
			
			for(Meld m: meldsPlayed)
			{
				table.addMeldToTable(m);
			}
				
			table.notifyObservers();
			
			if (currentPlayer.getPlayerRack().getSize() == 0) 
			{
				gameRunning = false;
		    }
			gameRunning = false; // only for testing
			
	}while(gameRunning );
	
	printer.printEnding(); // we can maybe give it a winner so that it can print it.
	
	}
}
