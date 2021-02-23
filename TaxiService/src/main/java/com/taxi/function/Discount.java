package com.taxi.function;

public interface Discount {
	/**
	 *  The method calculates a discount if price more than 500 and return price if less than 500. 
	 * @param price
	 * @return
	 */
	int discount(int price);
}
