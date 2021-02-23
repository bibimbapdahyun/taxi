package com.taxi.function;

public class WaitingTime implements Time{
	
	private static final int SPEED = 50;
	private static final int MINUTES = 60;
	
	@Override
	public int time(int distance) {
		return (distance * MINUTES) / SPEED;
	}

}
