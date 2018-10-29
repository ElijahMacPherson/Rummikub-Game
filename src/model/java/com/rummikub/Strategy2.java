package com.rummikub;

public class Strategy2 implements Behaviour, Observer {
	private TableInfo tableInfo = new TableInfo(); 
	
	Strategy2(Subject subject) {
		subject.registerObserver(this);
	}
	
	@Override
	public void play() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void update(TableInfo tableInfo) {
		this.tableInfo = tableInfo;		
	}

	

}