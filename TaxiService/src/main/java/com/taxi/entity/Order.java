package com.taxi.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

	private static final long serialVersionUID = -5760608207826203998L;

	private int id;
	private String start;
	private String finish;
	private Timestamp data;
	private int price;
	private String number;
	private int places;
	private TripState tState;
	private CarType type;
	private List<Car> car;
	private Account account;
	
	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Car> getCar() {
		return car;
	}

	public void setCar(List<Car> car) {
		this.car = car;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public TripState gettState() {
		return tState;
	}

	public void settState(TripState tState) {
		this.tState = tState;
	}

	@Override
	public int hashCode() {
		return Objects.hash(car, data, finish, id, number, places, price, start, tState, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(car, other.car) && Objects.equals(data, other.data)
				&& Objects.equals(finish, other.finish) && id == other.id && Objects.equals(number, other.number)
				&& places == other.places && price == other.price && Objects.equals(start, other.start)
				&& Objects.equals(tState, other.tState) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", start=" + start + ", finish=" + finish + ", data=" + data + ", price=" + price
				+ ", number=" + number + ", places=" + places + ", tState=" + tState + ", type=" + type + ", car=" + car
				+ "]";
	}

}
