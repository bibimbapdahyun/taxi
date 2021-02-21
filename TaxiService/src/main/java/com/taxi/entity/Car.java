package com.taxi.entity;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String carNumber;
	private String mark;
	private int places;
	private Account account;
	private CarState state;
	private CarType carType;
	private String currentPosition;


	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, carNumber, carType, id, places, state);
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CarState getState() {
		return state;
	}

	public void setState(CarState state) {
		this.state = state;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(account, other.account) && Objects.equals(carNumber, other.carNumber)
				&& Objects.equals(carType, other.carType) && id == other.id && places == other.places
				&& Objects.equals(state, other.state);
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", carNumber=" + carNumber + ", mark=" + mark + ", places=" + places + ", account="
				+ account + ", state=" + state + ", carType=" + carType + "]";
	}

}
