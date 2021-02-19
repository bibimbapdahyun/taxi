package com.taxi.function;

import java.security.SecureRandom;

public class DefineDistance implements Distance{

	private static final int MIN = 5;
	private static final int MAX = 30; 
	
	@Override
	public int getDistance(String start, String finish) {
		SecureRandom random = new SecureRandom();
		return random.nextInt(MAX - MIN) + MIN;
	}

}
