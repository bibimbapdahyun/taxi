package com.taxi.function;

import com.taxi.entity.CarType;

public class PriceCalculator implements Calculator {

	@Override
	public int calculate(int distance, CarType type) {
		return type.getStartPrice() + (type.getPricePerKm() * distance);
	}
	
}
