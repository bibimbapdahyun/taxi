package com.taxi.function;

import com.taxi.entity.CarType;

public interface Calculator {
	/**
	 * The method calculate price for trip by distance. The calculation is based on
	 * the parameters taken by their type.
	 * 
	 * @param distance
	 * @param type
	 * @return price for trip
	 */
	int calculate(int distance, CarType type);
}
