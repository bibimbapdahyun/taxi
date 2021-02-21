package com.taxi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CarsCountPrice implements Serializable {

	private static final long serialVersionUID = -8455793288838647992L;

	private CarType type;
	private List<Car> cars;
	private int price;
	private int waitingTime;

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cars, price, type, waitingTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarsCountPrice other = (CarsCountPrice) obj;
		return Objects.equals(cars, other.cars) && price == other.price && Objects.equals(type, other.type)
				&& waitingTime == other.waitingTime;
	}

	@Override
	public String toString() {
		return "CarsCountPrice [type=" + type + ", cars=" + cars + ", price=" + price + ", waitingTime=" + waitingTime
				+ "]";
	}

}