package com.taxi.function;

public interface Time {
	/**
	 * The method calculates the waiting time based on an average speed of 50 km/h.
	 * 
	 * @param distance
	 * @return
	 */
	int time(int distance);
}
