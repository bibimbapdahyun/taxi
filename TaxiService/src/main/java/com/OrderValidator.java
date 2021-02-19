package com;

import java.util.HashMap;
import java.util.Map;

import com.taxi.entity.Order;

public class OrderValidator implements Validator{

	@Override
	public <T> Map<String, String> validator(T t) {
		Map<String, String> map = new HashMap<>();
		Order order = (Order) t;
		if(order.getStart().isEmpty()) {
			map.put("Start", "Start is epmty, should be enter the start position");
		}
		if(order.getFinish().isEmpty()) {
			map.put("Finish", "Finish is epmty, should be enter the finish position");
		}
		if(order.getNumber().isEmpty()) {
			map.put("Number", "Number is epmty, should be enter the Number");
		}
		if(order.getPlaces() == 0) {
			map.put("Places", "Places can't be zero");
		}
		if(order.getPrice() == 0) {
			map.put("Price", "Price can't be zero, somthing was wrong in PriceCalculator");
		}
		return map;
	}

}
