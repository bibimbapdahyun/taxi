package com.taxi.function;

public interface Distance {
	/**
	 * Define the distance based on the start and end points. The implementation
	 * returns a number between 5 and 30 that represents the distance traveled in
	 * kilometers.
	 * 
	 * @param start
	 * @param finish
	 * @return
	 */

	int getDistance(String start, String finish);
}
