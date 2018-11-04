package com.rummikub;

import java.io.IOException;
import java.util.List;

public class Strategy4 implements StrategyBehaviour
{
	private TableInfo tableInfo; 

	@Override
	public void update(TableInfo tableInfo) 
	{
		this.tableInfo = tableInfo;		
	}

	@Override
	public void setSubject(Subject subject) 
	{
		subject.registerObserver(this);
	}

	@Override
	public List<Meld> useStrategy(Player currPlayer) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void playStrategy(Player currPlayer, List<Meld> possibleMelds, List<Meld> returnMelds) throws Exception {
		// TODO Auto-generated method stub
		
	}

}