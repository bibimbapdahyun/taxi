package com.taxi.function;

public class DiscountImpl implements Discount{

	@Override
	public int discount(int price) {
		if(price > 500) {
			price = (price * 90) / 100;
		}
		return price;
	}

}
